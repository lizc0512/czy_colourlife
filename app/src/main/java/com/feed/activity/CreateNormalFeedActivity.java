package com.feed.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ImageUtil;
import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.BeeFramework.view.MyDialog;
import com.external.eventbus.EventBus;
import com.feed.model.CreateFeedModel;
import com.feed.protocol.VerFeedPublishNormalApi;
import com.feed.utils.CompressHelper;
import com.feed.view.UploadImageView;
import com.permission.AndPermission;
import com.tendcloud.tenddata.TCAgent;
import com.user.UserAppConst;
import com.user.UserMessageConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.csh.colourful.life.utils.GlideImageLoader;
import cn.csh.colourful.life.view.imagepicker.ImagePicker;
import cn.csh.colourful.life.view.imagepicker.bean.ImageItem;
import cn.csh.colourful.life.view.imagepicker.ui.ImageGridActivity;
import cn.csh.colourful.life.view.imagepicker.ui.ImagePreviewDelActivity;
import cn.net.cyberway.R;

/**
 * Created by insthub on 2016/1/9.
 */
public class CreateNormalFeedActivity extends BaseActivity implements View.OnClickListener, HttpApiResponse {
    public static final int REQUEST_PHOTO = 4;
    public static final int REQUEST_ALBUM = 5;
    public static final int REQUEST_REVIEW = 6;
    private ImageView mBack;
    private TextView mBackText;
    private TextView mTitle;
    private TextView mRightText;

    private EditText mEditText;
    private GridLayout mGridLayout;
    private ImageView mAddImageView;
    private CreateFeedModel mModel;
    private ArrayList<UploadImageView> mUploadImageViews = new ArrayList<UploadImageView>();
    private ArrayList<String> mImagePathList = new ArrayList<String>();//选择本地图片的集合（
    private String mCurrentCommunityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_feed);
        init();
        initImagePicker();
        initGridLayout();
    }

    private void initImagePicker() {
        final ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(true);
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    /**
     * 初始化控件
     */
    private void init() {
        mModel = new CreateFeedModel(this);
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        mBack = (ImageView) findViewById(R.id.user_top_view_back);
        mBackText = (TextView) findViewById(R.id.user_top_view_back_text);
        mTitle = (TextView) findViewById(R.id.user_top_view_title);
        mRightText = (TextView) findViewById(R.id.user_top_view_right);
        mEditText = (EditText) findViewById(R.id.create_feed_content);
        mGridLayout = (GridLayout) findViewById(R.id.create_feed_photo);
        mCurrentCommunityId = shared.getString(UserAppConst.Colour_login_community_uuid, "");
        if (TextUtils.isEmpty(mCurrentCommunityId)) {
            mCurrentCommunityId = "03b98def-b5bd-400b-995f-a9af82be01da";
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mBackText.getLayoutParams();
        layoutParams.setMargins(30, 0, 0, 0);
        mBackText.setPadding(10, 10, 10, 10);
        mBackText.setLayoutParams(layoutParams);
        mBackText.setText(getResources().getString(R.string.message_cancel));
        mTitle.setText(getResources().getString(R.string.feed_publish_talking));
        mRightText.setText(getResources().getString(R.string.feed_publish));
        mBackText.setVisibility(View.VISIBLE);
        mBack.setVisibility(View.GONE);
        mRightText.setVisibility(View.VISIBLE);
        mBackText.setOnClickListener(this);
        mRightText.setOnClickListener(this);
        ThemeStyleHelper.backTexteFrameLayout(CreateNormalFeedActivity.this, czy_title_layout, mBackText, mTitle, mRightText);
    }

    /**
     * 初始化上传的图片的展示
     */
    private void initGridLayout() {
        mGridLayout.removeAllViews();
        mAddImageView = new ImageView(this);
        mAddImageView.setImageResource(R.drawable.f0_add_photo);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        int marginPx = ImageUtil.Dp2Px(this, 5);
        layoutParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int itemWidth = (screenWidth - ImageUtil.Dp2Px(this, 50)) / 3;
        layoutParams.width = itemWidth;
        layoutParams.height = itemWidth;
        mAddImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mAddImageView.setLayoutParams(layoutParams);
        mAddImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
        mGridLayout.addView(mAddImageView);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back_text:
                exit();
                break;
            case R.id.user_top_view_right:
                release();
                break;
        }
    }

    /**
     * 监听返回的时候是否已经编辑过
     */
    private void exit() {
        if (!TextUtils.isEmpty(mEditText.getText().toString()) || mUploadImageViews.size() > 0) {
            final MyDialog dialog = new MyDialog(this, "确定退出此次编辑？");
            dialog.negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.show();
        } else {
            finish();
        }
    }

    /**
     * 发表feed
     */
    private void release() {
        String releaseContent = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(releaseContent)) {
            mEditText.requestFocus();
            ToastUtil.toastShow(this, "发布的内容不能为空");
        } else {
            int userId = shared.getInt(UserAppConst.Colour_User_id, 0);
            String mCommunityId = shared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da");
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("customer_id", userId + "");
            paramsMap.put("community_id", mCommunityId);
            TCAgent.onEvent(getApplicationContext(), "207003", "", paramsMap);
            ArrayList<String> uploadImage = new ArrayList<String>();
            for (int i = 0; i < mUploadImageViews.size(); i++) {
                UploadImageView uploadImageView = mUploadImageViews.get(i);
                if (null != uploadImageView.mUploadPhoto) {
                    uploadImage.add(uploadImageView.mUploadPhoto);
                }
            }
            //判断是否图片全部上传成功，没有成功给提示
            if (uploadImage.size() == mUploadImageViews.size()) {
                mModel.releaseFeed(this, releaseContent, uploadImage, mCurrentCommunityId);
            } else {
                ToastUtil.toastShow(this, "请等待图片上传完成后发布");
            }
        }
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(VerFeedPublishNormalApi.class)) {
            ToastUtil.toastShow(this, "发布成功");
            Message msg = new Message();
            msg.what = UserMessageConstant.CREATE_FEED;
            EventBus.getDefault().post(msg);
            finish();
        }
    }

    /**
     * 用户选择图片
     */
    private void choosePicture() {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.user_avatar_dialog, null);
        dialog.setContentView(contentView);

        android.view.ViewGroup.LayoutParams cursorParams = contentView.getLayoutParams();
        cursorParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(cursorParams);
        dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
        dialog.show();

        TextView album = (TextView) dialog.findViewById(R.id.avatar_album);
        TextView photograph = (TextView) dialog.findViewById(R.id.avatar_photograph);
        TextView cancel = (TextView) dialog.findViewById(R.id.avatar_cancel);

        //从相册选择上传
        album.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ImagePicker.getInstance().setSelectLimit(9 - allImages.size());
                Intent intent = new Intent(CreateNormalFeedActivity.this, ImageGridActivity.class);
                CreateNormalFeedActivity.this.startActivityForResult(intent, REQUEST_ALBUM);
                dialog.dismiss();
            }
        });

        //拍照上传
        photograph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (AndPermission.hasPermission(getApplicationContext(), Manifest.permission.CAMERA)) {
                        ImagePicker.getInstance().setSelectLimit(9 - allImages.size());
                        Intent intent = new Intent(CreateNormalFeedActivity.this, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent, REQUEST_ALBUM);
                    } else {
                        ToastUtil.toastShow(getApplicationContext(), "相机权限未开启，请去开启该权限");
                    }
                } else {
                    ImagePicker.getInstance().setSelectLimit(9 - allImages.size());
                    Intent intent = new Intent(CreateNormalFeedActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                    startActivityForResult(intent, REQUEST_ALBUM);
                }
                dialog.dismiss();
            }
        });
        //取消
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private ArrayList<ImageItem> allImages = new ArrayList<>();
    private List<String> uploadPathList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_ALBUM) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                allImages.addAll(images);
                selectPicHandle(images);
            } else {
                ToastUtil.toastShow(this, "没有数据");
            }
        } else {
            if (requestCode == REQUEST_REVIEW) {//从点开看大图返回
                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                allImages.clear();
                allImages.addAll(images);
                if (images.size() != mImagePathList.size()) {
                    mGridLayout.removeViews(0, mImagePathList.size());
                    mImagePathList.clear();
                    mUploadImageViews.clear();
                    if (images.size() == 0) {
                        mGridLayout.setVisibility(View.VISIBLE);
                        initGridLayout();
                    } else {
                        mGridLayout.setVisibility(View.VISIBLE);
                        selectPicHandle(images);
                    }
                }
            }
        }
    }

    /***选择完图片或删除图片之后的处理***/
    private void selectPicHandle(ArrayList<ImageItem> images) {
        uploadPathList.clear();
        for (int i = 0; i < images.size(); i++) {
            ImageItem imageItem = images.get(i);
            String path = imageItem.path;
            File newFile = CompressHelper.getDefault(this).compressToFile(new File(path));
            uploadPathList.add(newFile.getPath());
        }
        for (int j = 0; j < uploadPathList.size(); j++) {
            UploadImageView uploadImageView = new UploadImageView(CreateNormalFeedActivity.this);
            addUploadImage(uploadImageView, uploadPathList.get(j));
        }
    }


    private void addUploadImage(final UploadImageView uploadImageView, String imagePath) {
        mImagePathList.add(imagePath);
        uploadImageView.setImageWithFilePath(imagePath);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        int marginPx = ImageUtil.Dp2Px(this, 5);
        layoutParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int itemWidth = (screenWidth - ImageUtil.Dp2Px(this, 50)) / 3;
        layoutParams.width = itemWidth;
        layoutParams.height = itemWidth;
        uploadImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        uploadImageView.setLayoutParams(layoutParams);
        uploadImageView.setTag(mImagePathList.size() - 1);
        uploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击看大图
                Intent intentPreview = new Intent(CreateNormalFeedActivity.this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, allImages);
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, (Integer) uploadImageView.getTag());
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_REVIEW);
            }
        });
        mGridLayout.addView(uploadImageView, mGridLayout.getChildCount() - 1);
        mUploadImageViews.add(uploadImageView);
        if (mGridLayout.getChildCount() > 9 || mGridLayout.getChildCount() == 0) {
            mAddImageView.setVisibility(View.GONE);
        } else {
            mAddImageView.setVisibility(View.VISIBLE);
        }
    }

    // 退出操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        }
        return true;
    }
}
