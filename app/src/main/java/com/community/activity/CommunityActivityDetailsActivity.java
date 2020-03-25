package com.community.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ImageUtil;
import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.CircleImageView;
import com.community.adapter.CommunityActivityCommentAdapter;
import com.community.entity.CommunityActivityDetailsEntity;
import com.community.entity.CommunityActivityListEntity;
import com.community.entity.CommunityDynamicsListEntity;
import com.community.entity.CommunityStatusEntity;
import com.community.model.CommunityDynamicsModel;
import com.community.utils.ImagePickerLoader;
import com.community.view.CommunityImageView;
import com.community.view.DeleteNoticeDialog;
import com.community.view.JoinActivityDialog;
import com.community.view.WrapWebView;
import com.eparking.helper.PermissionUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.permission.AndPermission;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.utils.KeyBoardUtils;
import cn.net.cyberway.R;
import cn.net.cyberway.utils.FileUtils;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static cn.net.cyberway.home.view.HomeViewUtils.showCommunityActivity;

/**
 * author:yuansk
 * cretetime:2020/3/19
 * desc:社区活动详情
 **/
public class CommunityActivityDetailsActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String ACTIVITY_SOURCE_ID = "activity_source_id";
    public static final int REQUEST_PHOTO = 5;


    private ImageView user_top_view_back;
    private TextView user_top_view_title;


    private ImageView iv_activity_head;
    private TextView tv_fee_status;
    private TextView tv_activity_title;
    private CircleImageView iv_first_photo;
    private CircleImageView iv_second_photo;
    private CircleImageView iv_third_photo;
    private TextView tv_join_person;
    private TextView tv_fee_price;
    private TextView tv_activity_starttime;
    private TextView tv_activity_address;
    private TextView tv_activity_endtime;
    private TextView tv_activity_person;
    private LinearLayout contact_person_layout;
    private CircleImageView iv_contact_header;
    private TextView tv_contact_name;
    private WrapWebView webview;

    private SwipeMenuRecyclerView rv_message;
    private RelativeLayout send_message_layout;
    private TextView tv_join_activity;


    private ArrayList<CommunityImageView> mUploadImageViews = new ArrayList<CommunityImageView>();
    private CommunityDynamicsModel communityDynamicsModel;
    private int picSize;
    private String source_id;//活动的id
    private String contact_mobile;//活动的发起人的联系方式
    private String ac_status;//活动的状态
    public Luban.Builder lubanBuilder;
    private ImagePicker imagePicker;
    private int page=1;

    private CommunityActivityCommentAdapter communityActivityCommentAdapter;
    private List<CommunityActivityListEntity.ContentBean.DataBean> commentBeanList;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_activity_details);
        initView();
        initHeadView();
        initData();
        initImagePicker();

        source_id = getIntent().getStringExtra(ACTIVITY_SOURCE_ID);
        communityDynamicsModel = new CommunityDynamicsModel(CommunityActivityDetailsActivity.this);
        communityDynamicsModel.getCommunityActivityDetails(0, source_id, CommunityActivityDetailsActivity.this);
        communityDynamicsModel.getActivityComment(1, source_id, page,CommunityActivityDetailsActivity.this);
    }


    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagePickerLoader());
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(true);
        imagePicker.setSelectLimit(2);    //选中数量限制
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
        lubanBuilder = Luban.with(CommunityActivityDetailsActivity.this);
    }

    private void initHeadView() {
        View headView = LayoutInflater.from(CommunityActivityDetailsActivity.this).inflate(R.layout.community_activity_content, null);
        iv_activity_head = headView.findViewById(R.id.iv_activity_head);
        tv_fee_status = headView.findViewById(R.id.tv_fee_status);
        tv_activity_title = headView.findViewById(R.id.tv_activity_title);
        iv_first_photo = headView.findViewById(R.id.iv_first_photo);
        iv_second_photo = headView.findViewById(R.id.iv_second_photo);
        iv_third_photo = headView.findViewById(R.id.iv_third_photo);
        tv_join_person = headView.findViewById(R.id.tv_join_person);


        tv_fee_price = headView.findViewById(R.id.tv_fee_price);
        tv_activity_starttime = headView.findViewById(R.id.tv_activity_starttime);
        tv_activity_address = headView.findViewById(R.id.tv_activity_address);
        tv_activity_endtime = headView.findViewById(R.id.tv_activity_endtime);
        tv_activity_person = headView.findViewById(R.id.tv_activity_person);
        contact_person_layout = headView.findViewById(R.id.contact_person_layout);
        iv_contact_header = headView.findViewById(R.id.iv_contact_header);
        tv_contact_name = headView.findViewById(R.id.tv_contact_name);
        webview = headView.findViewById(R.id.webview);
        rv_message.addHeaderView(headView);
    }


    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        rv_message = findViewById(R.id.rv_message);
        rv_message.useDefaultLoadMore();
        send_message_layout = findViewById(R.id.send_message_layout);
        tv_join_activity = findViewById(R.id.tv_join_activity);
        user_top_view_back.setOnClickListener(this);
        contact_person_layout.setOnClickListener(this);
        send_message_layout.setOnClickListener(this);
        tv_join_activity.setOnClickListener(this);
        user_top_view_title.setText(getResources().getString(R.string.community_activity_details));
        commentBeanList = new ArrayList<>();
        communityActivityCommentAdapter = new CommunityActivityCommentAdapter(CommunityActivityDetailsActivity.this, commentBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CommunityActivityDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_message.setLayoutManager(linearLayoutManager);
        rv_message.setAdapter(communityActivityCommentAdapter);
        rv_message.setLoadMoreListener(new SwipeMenuRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                communityDynamicsModel.getActivityComment(1, source_id, page,CommunityActivityDetailsActivity.this);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initData() {
        WebSettings setting = webview.getSettings();
        setting.setJavaScriptEnabled(true);//支持js
        webview.addJavascriptInterface(jsObject, "jsObject");
        webview.setOnContentChangeListener(() -> {
            jsObject.onGetWebContentHeight();
        });
        String loadurl = "https://www.baidu.com/";
        webview.loadUrl(loadurl);
    }

    private class JSObject {
        @JavascriptInterface
        public void onGetWebContentHeight() {
            //重新调整webview高度
            webview.post(() -> {
                webview.measure(0, 0);
                int measuredHeight = webview.getMeasuredHeight();
                ViewGroup.LayoutParams layoutParams = webview.getLayoutParams();
                layoutParams.height = measuredHeight;
                webview.setLayoutParams(layoutParams);
            });

        }
    }

    JSObject jsObject = new JSObject();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.contact_person_layout:
                PermissionUtils.showPhonePermission(CommunityActivityDetailsActivity.this, contact_mobile);
                break;
            case R.id.send_message_layout:
                showInputCommentDialog(rv_message.getChildAt(commentBeanList.size() - 1), source_id, "", "");
                break;
            case R.id.tv_join_activity:
                if ("5".equals(ac_status)) { //活动已参与
                    ToastUtil.toastShow(CommunityActivityDetailsActivity.this, getResources().getString(R.string.community_activity_joined_notice));
                } else {
                    if (null == joinActivityDialog) {
                        joinActivityDialog = new JoinActivityDialog(CommunityActivityDetailsActivity.this, R.style.custom_dialog_theme);
                    }
                    joinActivityDialog.show();
                    showAddPicView();
                }

                break;
            case R.id.tv_define_join:
                if (null != joinActivityDialog) {
                    joinActivityDialog.dismiss();
                }
                joinCommunityActivity();
                break;
            case R.id.tv_cancel_join:
                if (null != joinActivityDialog) {
                    joinActivityDialog.dismiss();
                }
                break;
        }
    }

    private JoinActivityDialog joinActivityDialog;
    private GridLayout joinActivityGridLayout;
    private ImageView add_ImageView;

    private void showAddPicView() {
        joinActivityGridLayout = joinActivityDialog.join_activity_photo;
        joinActivityDialog.join_activity_photo.removeAllViews();
        joinActivityDialog.tv_define_join.setOnClickListener(this::onClick);
        joinActivityDialog.tv_cancel_join.setOnClickListener(this::onClick);
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
        joinActivityGridLayout.addView(add_ImageView);
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
                imagePicker.setSelectLimit(2 - mUploadImageViews.size());
                imagePicker.clearSelectedImages();
                Intent intent = new Intent(CommunityActivityDetailsActivity.this, ImageGridActivity.class);
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
                        imagePicker.setSelectLimit(2 - mUploadImageViews.size());
                        imagePicker.clearSelectedImages();
                        Intent intent = new Intent(CommunityActivityDetailsActivity.this, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent, REQUEST_PHOTO);
                    } else {
                        ToastUtil.toastShow(getApplicationContext(), "相机权限未开启，请去开启该权限");
                    }
                } else {
                    imagePicker.setSelectLimit(2 - mUploadImageViews.size());
                    imagePicker.clearSelectedImages();
                    Intent intent = new Intent(CommunityActivityDetailsActivity.this, ImageGridActivity.class);
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

    /***选择完图片或删除图片之后的处理***/
    private void selectPicHandle(ArrayList<ImageItem> images) {
        List<String> compressPathList = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            ImageItem imageItem = images.get(i);
            String path = imageItem.path;
            compressPathList.add(path);
        }
        lubanBuilder.load(compressPathList).ignoreBy(100).setTargetDir(FileUtils.getThumbImagePaths()).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(File file) {
                CommunityImageView uploadImageView = new CommunityImageView(CommunityActivityDetailsActivity.this);
                addUploadImage(uploadImageView, file.getPath());
            }

            @Override
            public void onError(Throwable e) {

            }
        }).launch();


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
        joinActivityGridLayout.addView(uploadImageView, joinActivityGridLayout.getChildCount() - 1);
        mUploadImageViews.add(uploadImageView);
        uploadImageView.setImageWithFilePath(imagePath, BitmapFactory.decodeFile(imagePath));
        picSize = joinActivityGridLayout.getChildCount();
        if (picSize > 2 || picSize == 0) {
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
    }

    private void showDelNotice(final CommunityImageView uploadImageView) {
        final DeleteNoticeDialog deleteNoticeDialog = new DeleteNoticeDialog(CommunityActivityDetailsActivity.this, R.style.custom_dialog_theme);
        deleteNoticeDialog.show();
        deleteNoticeDialog.btn_define.setOnClickListener(v -> {
            joinActivityGridLayout.removeView(uploadImageView);
            mUploadImageViews.remove(uploadImageView);
            if (mUploadImageViews.size() == 0) {//重新添加绑定
                showAddPicView();
            } else {
                add_ImageView.setVisibility(View.VISIBLE);
            }
            deleteNoticeDialog.dismiss();
        });
    }

    private void joinCommunityActivity() {
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
        FileUtils.delDynamicPicFolder();
        communityDynamicsModel.joinCommunityActivity(2, source_id, GsonUtils.gsonString(uploadObjList), CommunityActivityDetailsActivity.this);
    }

    private Dialog inputDialog;
    private String fromNickName;
    private String toUserId;

    public void showInputCommentDialog(View itemView, String sourceId, String fromNickName, String toUserId) {
        this.fromNickName = fromNickName;
        this.toUserId = toUserId;
        int itemBottomY = 0;
        if (null != itemView) {
            itemBottomY = getCoordinateY(itemView) + itemView.getHeight();//item
        }
        inputDialog = new Dialog(CommunityActivityDetailsActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        View view = LayoutInflater.from(CommunityActivityDetailsActivity.this).inflate(R.layout.dynamic_comment_layout, null);
        inputDialog.setContentView(view);
        EditText feed_comment_edittext = view.findViewById(R.id.feed_comment_edittext);
        feed_comment_edittext.setFocusable(true);
        feed_comment_edittext.setFocusableInTouchMode(true);
        feed_comment_edittext.requestFocus();
        if (!TextUtils.isEmpty(fromNickName)) {
            feed_comment_edittext.setHint(getResources().getString(R.string.community_reply_other) + fromNickName);
        } else {
            feed_comment_edittext.setHint(getResources().getString(R.string.community_leave_comments));
        }
        TextView feed_comment_submit = view.findViewById(R.id.feed_comment_submit);
        //scrollView 点击事件，点击时将 dialog dismiss，设置 onClick 监听无效
        inputDialog.findViewById(R.id.scrollView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP)
                    inputDialog.dismiss();
                KeyBoardUtils.closeKeybord(feed_comment_edittext, CommunityActivityDetailsActivity.this);
                return true;
            }
        });
        inputDialog.show();
        int finalItemBottomY = itemBottomY;
        itemView.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayout llCommentInput = inputDialog.findViewById(R.id.ll_comment_input);
                int y = getCoordinateY(llCommentInput);
                rv_message.smoothScrollBy(0, finalItemBottomY - y);
            }
        }, 300);
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                handleWindowChange();
            }
        });
        feed_comment_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = feed_comment_edittext.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    if (content.length() > 300) {
                        ToastUtil.toastShow(CommunityActivityDetailsActivity.this, "输入的内容长度不能超过300字");
                    } else {
                        KeyBoardUtils.closeKeybord(feed_comment_edittext, CommunityActivityDetailsActivity.this);
                        inputDialog.dismiss();
                        communityDynamicsModel.commentCommunityActivity(3, content, sourceId, toUserId, CommunityActivityDetailsActivity.this::OnHttpResponse);
                    }
                } else {
                    ToastUtil.toastShow(CommunityActivityDetailsActivity.this, "输入的内容不能为空");
                }
            }
        });
    }

    public void showDelDynamics() {
        DeleteNoticeDialog deleteNoticeDialog = new DeleteNoticeDialog(CommunityActivityDetailsActivity.this, R.style.custom_dialog_theme);
        deleteNoticeDialog.show();
        deleteNoticeDialog.btn_define.setOnClickListener(v -> {
            //删除留言  删除评论
            deleteNoticeDialog.dismiss();

        });
    }

    public void setDelCommentId(String comment_id, int position) {

        showDelDynamics();
    }

    /**
     * 监听键盘的显示和隐藏
     */
    private void handleWindowChange() {
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);//获取当前界面显示范围
        int displayHeight = rect.bottom - rect.top;//app内容显示高度，即屏幕高度-状态栏高度-键盘高度
        int totalHeight = getWindow().getDecorView().getHeight();
        //显示内容的高度和屏幕高度比大于 0.8 时，dismiss dialog
        if ((float) displayHeight / totalHeight > 0.8)//0.8只是一个大致的比例，可以修改
            if (null != inputDialog && inputDialog.isShowing())
                inputDialog.dismiss();

    }


    private int getCoordinateY(View view) {
        int[] coordinate = new int[2];
        view.getLocationOnScreen(coordinate);
        return coordinate[1];
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_PHOTO) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selectPicHandle(images);
            } else {
                ToastUtil.toastShow(this, "没有数据");
            }
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0://活动详情
                try {
                    CommunityActivityDetailsEntity communityActivityDetailsEntity = GsonUtils.gsonToBean(result, CommunityActivityDetailsEntity.class);
                    CommunityActivityDetailsEntity.ContentBean contentBean = communityActivityDetailsEntity.getContent();
                    GlideImageLoader.loadImageDisplay(CommunityActivityDetailsActivity.this, contentBean.getAc_banner(), iv_activity_head);
                    String oproperty = contentBean.getAc_oproperty();
                    if ("免费".equals(oproperty)) {
                        tv_fee_status.setVisibility(View.GONE);
                        tv_fee_price.setVisibility(View.GONE);
                    } else {
                        tv_fee_status.setVisibility(View.VISIBLE);
                        tv_fee_status.setText(oproperty);
                        tv_fee_price.setVisibility(View.VISIBLE);
                        tv_fee_price.setText(contentBean.getAc_fee());
                    }
                    tv_activity_title.setText(contentBean.getAc_title());
                    int join_number = contentBean.getJoin_num();
                    showCommunityActivity(CommunityActivityDetailsActivity.this, join_number, contentBean.getJoin_user(), iv_first_photo, iv_second_photo, iv_third_photo, tv_join_person);
                    tv_activity_starttime.setText(TimeUtil.getDateToString(contentBean.getBegin_time()) + "-" + TimeUtil.getDateToString(contentBean.getEnd_time()));
                    tv_activity_address.setText(contentBean.getAc_address());
                    tv_activity_endtime.setText(TimeUtil.getDateToString(contentBean.getApply_time()));
                    tv_activity_person.setText(join_number + "人");
                    GlideImageLoader.loadImageDisplay(CommunityActivityDetailsActivity.this, contentBean.getContact_user_avatar(), iv_contact_header);
                    tv_contact_name.setText(contentBean.getContact_user_name());
                    contact_mobile = contentBean.getContact_user_mobile();
                    ac_status = contentBean.getAc_status();
                    showAcStatus();
                } catch (Exception e) {

                }
                break;
            case 1://留言列表
                try {
                    CommunityActivityListEntity communityActivityListEntity=GsonUtils.gsonToBean(result,CommunityActivityListEntity.class);
                    CommunityActivityListEntity.ContentBean  contentBean= communityActivityListEntity.getContent();
                    commentBeanList.clear();
                    List<CommunityActivityListEntity.ContentBean.DataBean> pageContentList = contentBean.getData();
                   boolean dataEmpty = pageContentList == null || pageContentList.size() == 0;
                    commentBeanList.addAll(contentBean.getData());
                    int total=contentBean.getTotal();
                    boolean hasMore=true;
                    if (total > commentBeanList.size()) {
                        hasMore = true;
                    } else {
                        hasMore = false;
                    }
                    rv_message.loadMoreFinish(dataEmpty, hasMore);
                }catch (Exception e){

                }
                break;
            case 2://参与活动
                try {
                    CommunityStatusEntity communityStatusEntity = GsonUtils.gsonToBean(result, CommunityStatusEntity.class);
                    ac_status = communityStatusEntity.getContent().getAc_status();
                    if ("5".equals(ac_status)) {
                        ToastUtil.toastJoinActivity(CommunityActivityDetailsActivity.this);
                    } else {
                        ToastUtil.toastShow(CommunityActivityDetailsActivity.this, communityStatusEntity.getMessage());
                    }
                } catch (Exception e) {

                }
                showAcStatus();
                break;
            case 3://添加留言

                break;
        }
    }

    private void showAcStatus() {
        switch (ac_status) {
            case "2":
                tv_join_activity.setEnabled(false);
                tv_join_activity.setBackgroundColor(getResources().getColor(R.color.color_8d9299));
                tv_join_activity.setText(getResources().getString(R.string.community_activity_numberfull));
                break;
            case "3":
                tv_join_activity.setEnabled(false);
                tv_join_activity.setBackgroundColor(getResources().getColor(R.color.color_8d9299));
                tv_join_activity.setText(getResources().getString(R.string.community_activity_endtime));
                break;
            case "4":
                tv_join_activity.setEnabled(false);
                tv_join_activity.setBackgroundColor(getResources().getColor(R.color.color_8d9299));
                tv_join_activity.setText(getResources().getString(R.string.community_activity_finished));
                break;
            case "5":
                tv_join_activity.setEnabled(true);
                tv_join_activity.setBackgroundColor(getResources().getColor(R.color.color_3282fa));
                tv_join_activity.setText(getResources().getString(R.string.community_activity_finished));
                break;
            default:
                tv_join_activity.setEnabled(true);
                tv_join_activity.setBackgroundColor(getResources().getColor(R.color.color_3282fa));
                tv_join_activity.setText(getResources().getString(R.string.community_activity_join));
                break;
        }
    }

}
