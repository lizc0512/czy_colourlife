package com.community.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.CompressHelper;
import com.BeeFramework.Utils.ImageUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.community.model.CommunityDynamicsModel;
import com.community.utils.RealIdentifyDialogUtil;
import com.community.view.CommunityImageView;
import com.community.view.DeleteNoticeDialog;
import com.nohttp.utils.GsonUtils;
import com.permission.AndPermission;
import com.user.UserAppConst;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.utils.GlideImageLoader;
import cn.csh.colourful.life.view.imagepicker.ImagePicker;
import cn.csh.colourful.life.view.imagepicker.bean.ImageItem;
import cn.csh.colourful.life.view.imagepicker.ui.ImageGridActivity;
import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.cashier.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/7 10:35
 * @change
 * @chang time
 * @class describe   发送动态
 */
public class PublishDynamicsActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private final int maxLength = 500;//动态内容最大长度
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView user_top_view_right;
    private EditText create_dynamics_content;
    private GridLayout create_dynamics_photo;
    private ImageView add_ImageView;
    public static final int REQUEST_PHOTO = 5;
    private ArrayList<ImageItem> allImages = new ArrayList<>();
    private ArrayList<CommunityImageView> mUploadImageViews = new ArrayList<CommunityImageView>();
    private String publishContent = "";
    private int picSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_dynamics);
        initView();
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
     * 初始化上传的图片的展示
     */
    private void initGridLayout() {
        create_dynamics_photo.removeAllViews();
        add_ImageView = new ImageView(this);
        add_ImageView.setImageResource(R.drawable.community_dynamics_addpics);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        int marginPx = ImageUtil.Dp2Px(this, 5);
        layoutParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int itemWidth = (screenWidth - ImageUtil.Dp2Px(this, 50)) / 3;
        layoutParams.width = itemWidth;
        layoutParams.height = itemWidth;
        add_ImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        add_ImageView.setLayoutParams(layoutParams);
        add_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
        create_dynamics_photo.addView(add_ImageView);

        create_dynamics_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > maxLength) { //判断EditText中输入的字符数是不是已经大于6
                    create_dynamics_content.setText(s.toString().substring(0, maxLength)); //设置EditText只显示前面6位字符
                    create_dynamics_content.setSelection(maxLength);//让光标移至末端
                    ToastUtil.toastShow(PublishDynamicsActivity.this, "动态不可以超过500字哦～");
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                setPublishStatus();
            }
        });
        setPublishStatus();
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        user_top_view_right = findViewById(R.id.user_top_view_right);
        create_dynamics_content = findViewById(R.id.create_dynamics_content);
        create_dynamics_photo = findViewById(R.id.create_dynamics_photo);
        user_top_view_back.setOnClickListener(this::onClick);
        user_top_view_right.setOnClickListener(this::onClick);
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setText(getResources().getString(R.string.community_publish));
        user_top_view_title.setText(getResources().getString(R.string.community_dynamics));
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
                Intent intent = new Intent(PublishDynamicsActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_PHOTO);
                dialog.dismiss();
            }
        });

        //拍照上传
        photograph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (AndPermission.hasPermission(getApplicationContext(), Manifest.permission.CAMERA)) {
                        ImagePicker.getInstance().setSelectLimit(9 - mUploadImageViews.size());
                        Intent intent = new Intent(PublishDynamicsActivity.this, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent, REQUEST_PHOTO);
                    } else {
                        ToastUtil.toastShow(getApplicationContext(), "相机权限未开启，请去开启该权限");
                    }
                } else {
                    ImagePicker.getInstance().setSelectLimit(9 - mUploadImageViews.size());
                    Intent intent = new Intent(PublishDynamicsActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                    startActivityForResult(intent, REQUEST_PHOTO);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                publishDynamic();
                break;
        }
    }


    private void setPublishStatus() {
        picSize = mUploadImageViews.size();
        publishContent = create_dynamics_content.getText().toString().trim();
        if (picSize == 0 && TextUtils.isEmpty(publishContent)) {
            user_top_view_right.setEnabled(false);
            user_top_view_right.setTextColor(getResources().getColor(R.color.color_8d9299));
        } else {
            user_top_view_right.setEnabled(true);
            user_top_view_right.setTextColor(getResources().getColor(R.color.color_3282fa));
        }
    }

    private void publishDynamic() {
        String is_identity = shared.getString(UserAppConst.COLOUR_DYNAMICS_REAL_IDENTITY, "0");
        if ("1".equals(is_identity)) {
            List<String> uploadObjList = new ArrayList<>();
            if (picSize > 0) {
                for (CommunityImageView communityImageView : mUploadImageViews) {
                    if (!TextUtils.isEmpty(communityImageView.mUploadPhotoId)) {
                        uploadObjList.add(communityImageView.mUploadPhotoId);
                    }
                }
                if (picSize != uploadObjList.size()) {
                    ToastUtil.toastShow(this, "请等待图片上传完成后发布");
                    return;
                }
            }
            CommunityDynamicsModel communityDynamicsModel = new CommunityDynamicsModel(PublishDynamicsActivity.this);
            communityDynamicsModel.publicUserDynamic(0, publishContent, "1", GsonUtils.gsonString(uploadObjList), PublishDynamicsActivity.this);
        } else {
            RealIdentifyDialogUtil.showGoIdentifyDialog(PublishDynamicsActivity.this);
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                setResult(200);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_PHOTO) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                allImages.addAll(images);
                selectPicHandle(images);
            } else {
                ToastUtil.toastShow(this, "没有数据");
            }
        }
    }

    /***选择完图片或删除图片之后的处理***/
    private void selectPicHandle(ArrayList<ImageItem> images) {
        List<String> compressPathList = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            ImageItem imageItem = images.get(i);
            String path = imageItem.path;
            CompressHelper compressHelper = new CompressHelper.Builder(this).setMaxWidth(1080).setMaxHeight(1920).setQuality(90).build();
            File newFile = compressHelper.compressToFile(new File(path));
            compressPathList.add(newFile.getPath());
        }
        for (int j = 0; j < compressPathList.size(); j++) {
            CommunityImageView uploadImageView = new CommunityImageView(PublishDynamicsActivity.this);
            addUploadImage(uploadImageView, compressPathList.get(j));
        }
    }

    private void addUploadImage(final CommunityImageView uploadImageView, String imagePath) {
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        int marginPx = ImageUtil.Dp2Px(this, 5);
        layoutParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int itemWidth = (screenWidth - ImageUtil.Dp2Px(this, 50)) / 3;
        layoutParams.width = itemWidth;
        layoutParams.height = itemWidth;
        uploadImageView.setLayoutParams(layoutParams);
        create_dynamics_photo.addView(uploadImageView, create_dynamics_photo.getChildCount() - 1);
        mUploadImageViews.add(uploadImageView);
        uploadImageView.setImageWithFilePath(imagePath, BitmapFactory.decodeFile(imagePath));
        if (create_dynamics_photo.getChildCount() > 9 || create_dynamics_photo.getChildCount() == 0) {
            add_ImageView.setVisibility(View.GONE);
        } else {
            add_ImageView.setVisibility(View.VISIBLE);
        }
        uploadImageView.del_upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDelNotice(uploadImageView);
            }
        });
        setPublishStatus();
    }

    private void showDelNotice(final CommunityImageView uploadImageView) {
        final DeleteNoticeDialog deleteNoticeDialog = new DeleteNoticeDialog(PublishDynamicsActivity.this, R.style.custom_dialog_theme);
        deleteNoticeDialog.show();
        deleteNoticeDialog.btn_define.setOnClickListener(v -> {
            create_dynamics_photo.removeView(uploadImageView);
            mUploadImageViews.remove(uploadImageView);
            setPublishStatus();
            deleteNoticeDialog.dismiss();
        });
    }
}

