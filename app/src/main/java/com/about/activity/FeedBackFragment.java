package com.about.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.CompressHelper;
import com.BeeFramework.Utils.ImageUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseFragment;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.NoScrollGridView;
import com.about.adapter.FeedBackAdapter;
import com.about.model.FeedbackModel;
import com.about.protocol.FeedBackTypeEntity;
import com.about.view.FeedBackImageView;
import com.community.utils.ImagePickerLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.nohttp.utils.GsonUtils;
import com.permission.AndPermission;
import com.user.model.NewUserModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.about.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/2/16 10:01
 * @change
 * @chang time
 * @class describe
 */
public class FeedBackFragment extends BaseFragment implements View.OnClickListener, NewHttpResponse {

    public static String FROM_WEB = "from_web";

    public static final int REQUEST_ALBUM = 5;
    public static final int REQUEST_REVIEW = 6;
    private TextView tv_commit;
    private EditText edit_text;  //输入框
    private FeedBackAdapter feedBackAdapter;
    private NoScrollGridView noScrollGridView;
    private GridLayout mGridLayout;
    private ImageView mAddImageView;
    private ArrayList<FeedBackImageView> mUploadImageViews = new ArrayList<FeedBackImageView>();
    private ArrayList<String> mImagePathList = new ArrayList<String>();//选择本地图片的集合（
    private FeedbackModel feedbackModel;
    private String typeid = "";//反馈类型
    private String imgid = "";
    private ArrayList<ImageItem> allImages = new ArrayList<>();
    private List<String> uploadPathList = new ArrayList<>();
    private boolean fromWeb = false;
    private NewUserModel newUserModel;

    public static FeedBackFragment newInstance(boolean fromWeb) {
        Bundle arguments = new Bundle();
        arguments.putBoolean(FROM_WEB, fromWeb);
        FeedBackFragment fragment = new FeedBackFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            fromWeb = bundle.getBoolean(FROM_WEB);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_feedback_layout;
    }

    @Override
    protected void initView(View rootView) {
        mGridLayout = (GridLayout) rootView.findViewById(R.id.create_feed_photo);
        tv_commit = (TextView) rootView.findViewById(R.id.tv_feedback_commit);
        noScrollGridView = (NoScrollGridView) rootView.findViewById(R.id.gridview_recharge);
        tv_commit.setText(getResources().getString(R.string.feedback_submit));
        tv_commit.setVisibility(View.VISIBLE);
        tv_commit.setOnClickListener(this);
        edit_text = (EditText) rootView.findViewById(R.id.edit_text);
        feedbackModel = new FeedbackModel(getActivity());
        noScrollGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FeedBackTypeEntity.ContentBean typeBean = typeBeanList.get(position);
                typeid = typeBean.getId();
                feedBackAdapter.setSelect(position);
            }
        });
        initImagePicker();
        initGridLayout();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            feedbackModel.getFeedBackType(0, this);
            isFirst = true;
        }
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagePickerLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(true);
        imagePicker.setSelectLimit(3);    //选中数量限制
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    /**
     * 初始化上传的图片的展示
     */
    private void initGridLayout() {
        mGridLayout.removeAllViews();
        mAddImageView = new ImageView(getActivity());
        mAddImageView.setImageResource(R.drawable.btn_choose);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        int marginPx = ImageUtil.Dp2Px(getActivity(), 5);
        layoutParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int itemWidth = (screenWidth - ImageUtil.Dp2Px(getActivity(), 50)) / 3;
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

    /**
     * 用户选择图片
     */
    private void choosePicture() {
        final Dialog dialog = new Dialog(getActivity(), R.style.dialog);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.user_avatar_dialog, null);
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
                ImagePicker.getInstance().setSelectLimit(3 - allImages.size());//选中数量限制
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_ALBUM);
                dialog.dismiss();
            }
        });

        //拍照上传
        photograph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (AndPermission.hasPermission(getActivity(), Manifest.permission.CAMERA)) {
                        ImagePicker.getInstance().setSelectLimit(3 - allImages.size());
                        Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent, REQUEST_ALBUM);

                    } else {
                        ToastUtil.toastShow(getActivity(), getResources().getString(R.string.user_camerapermission_notice));
                    }
                } else {
                    ImagePicker.getInstance().setSelectLimit(3 - allImages.size());
                    Intent intent = new Intent(getActivity(), ImageGridActivity.class);
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

    private void addUploadImage(final FeedBackImageView uploadImageView, String imagePath) {
        mImagePathList.add(imagePath);
        uploadImageView.setImageWithFilePath(imagePath);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
        int marginPx = ImageUtil.Dp2Px(getActivity(), 5);
        layoutParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int itemWidth = (screenWidth - ImageUtil.Dp2Px(getActivity(), 50)) / 3;
        layoutParams.width = itemWidth;
        layoutParams.height = itemWidth;
        uploadImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        uploadImageView.setLayoutParams(layoutParams);
        uploadImageView.setTag(mImagePathList.size() - 1);
        uploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击看大图
                Intent intentPreview = new Intent(getActivity(), ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, allImages);
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, (Integer) uploadImageView.getTag());
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_REVIEW);
            }
        });
        mGridLayout.addView(uploadImageView, mGridLayout.getChildCount() - 1);
        mUploadImageViews.add(uploadImageView);
        if (mGridLayout.getChildCount() > 3 || mGridLayout.getChildCount() == 0) {
            mAddImageView.setVisibility(View.GONE);
        } else {
            mAddImageView.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_feedback_commit:
                if (edit_text.getText().toString().equals("")) {
                    ToastUtil.toastShow(getActivity(), "请填写反馈内容");
                } else if (typeid.equals("")) {
                    ToastUtil.toastShow(getActivity(), "请选择反馈类型");
                } else if (mImagePathList.size() > 3) {
                    ToastUtil.toastShow(getActivity(), "只能添加三张照片");
                } else {
                    JSONArray imageArr = new JSONArray();
                    String imgid = "";
                    for (int i = 0; i < mUploadImageViews.size(); i++) {
                        FeedBackImageView uploadImageView = mUploadImageViews.get(i);
                        if (null != uploadImageView.mUploadPhotoId) {
                            try {
                                imageArr.put(i, uploadImageView.mUploadPhotoId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    imgid = imageArr.toString();
                    feedbackModel.feedBackContent(1, edit_text.getText().toString().trim(), typeid, imgid, this);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_ALBUM) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                allImages.addAll(images);
                selectPicHandle(images);
            } else {
                ToastUtil.toastShow(getActivity(), "没有数据");
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
            File newFile = CompressHelper.getDefault(getActivity()).compressToFile(new File(path));
            uploadPathList.add(newFile.getPath());
        }
        for (int j = 0; j < uploadPathList.size(); j++) {
            FeedBackImageView uploadImageView = new FeedBackImageView(getActivity());
            addUploadImage(uploadImageView, uploadPathList.get(j));
        }
    }

    private ArrayList<FeedBackTypeEntity.ContentBean> typeBeanList = new ArrayList<>();

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    typeBeanList.clear();
                    FeedBackTypeEntity feedBackTypeEntity = GsonUtils.gsonToBean(result, FeedBackTypeEntity.class);
                    typeBeanList.addAll(feedBackTypeEntity.getContent());
                    feedBackAdapter = new FeedBackAdapter(getActivity(), typeBeanList);
                    noScrollGridView.setAdapter(feedBackAdapter);
                } catch (Exception e) {

                }
                break;
            case 1:
                try {
                    Objects.requireNonNull(getActivity()).setResult(200, new Intent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null == newUserModel) {
                    newUserModel = new NewUserModel(getActivity());
                }
                newUserModel.finishTask(2, "3", fromWeb ? "task_web" : "task_native", this);
                break;
            case 2:
                ToastUtil.toastShow(getActivity(), "提交成功～\n感谢您的反馈!");
                getActivity().finish();
                break;
        }
    }
}
