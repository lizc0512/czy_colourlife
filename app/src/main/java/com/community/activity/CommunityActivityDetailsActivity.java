package com.community.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
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
import com.community.entity.CommunityDynamicIdEntity;
import com.community.entity.CommunityStatusEntity;
import com.community.model.CommunityDynamicsModel;
import com.community.utils.ImagePickerLoader;
import com.community.view.CommunityImageView;
import com.community.view.DeleteNoticeDialog;
import com.community.view.JoinActivityDialog;
import com.community.view.ShareActivityDialog;
import com.community.view.WrapWebView;
import com.eparking.helper.PermissionUtils;
import com.external.eventbus.EventBus;
import com.im.activity.IMCustomerInforActivity;
import com.im.activity.IMFriendInforActivity;
import com.im.activity.IMUserSelfInforActivity;
import com.im.helper.CacheFriendInforHelper;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.permission.AndPermission;
import com.user.UserAppConst;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.utils.KeyBoardUtils;
import cn.net.cyberway.R;
import cn.net.cyberway.utils.FileUtils;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static cn.net.cyberway.home.view.HomeViewUtils.showCommunityActivity;
import static com.community.fragment.CommunityDynamicsFragment.CHANGE_ACTIVITY_STATUS;
import static com.im.activity.IMFriendInforActivity.USERIDTYPE;

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
    private ImageView img_right;

    private ImageView iv_activity_head;
    private TextView tv_activity_title;
    private CircleImageView iv_first_photo;
    private CircleImageView iv_second_photo;
    private CircleImageView iv_third_photo;
    private TextView tv_join_person; //活动的参与人数
    private TextView tv_fee_price;
    private TextView tv_activity_starttime;//活动的时间段
    private TextView tv_activity_address;
    private TextView tv_activity_endtime;//活动的截止时间
    private TextView tv_activity_person;
    private LinearLayout contact_person_layout;
    private CircleImageView iv_contact_header;
    private TextView tv_contact_name;
    private LinearLayout web_content_layout;
    private WrapWebView webview; //活动详情介绍

    private SwipeMenuRecyclerView rv_message; //活动留言列表
    private LinearLayout no_data_layout;
    private RelativeLayout send_message_layout;
    private TextView tv_join_activity;


    private ArrayList<CommunityImageView> mUploadImageViews = new ArrayList<CommunityImageView>();//参与活动 上传的图片集
    private CommunityDynamicsModel communityDynamicsModel;
    private String source_id;//活动的id
    private String contact_id;//活动的发起人的用户id
    private String contact_mobile;//活动的发起人的手机号码
    private String ac_status;//活动的状态
    private String is_join;//用户是否已参加
    public Luban.Builder lubanBuilder;
    private ImagePicker imagePicker;
    private int maxPickImageSize = 2;
    private String pickerPrompt;
    private String pickRequire;
    private int page = 1;
    private int delPos;
    private int join_number;//参与人数
    private String activityTitle;//活动的标题
    private String activityUrl;//活动的链接
    private List<String> join_user_list = new ArrayList<>(); //活动参与人数的头像

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
        communityDynamicsModel.getActivityComment(1, source_id, page, CommunityActivityDetailsActivity.this);
        communityDynamicsModel.addCommunityActivityViews(5, source_id, CommunityActivityDetailsActivity.this);
    }


    private void initImagePicker() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagePickerLoader());
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(true);
        imagePicker.setSelectLimit(maxPickImageSize);    //选中数量限制
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
        lubanBuilder = Luban.with(CommunityActivityDetailsActivity.this);
    }

    private void initHeadView() {
        View headView = LayoutInflater.from(CommunityActivityDetailsActivity.this).inflate(R.layout.community_activity_content, null);
        iv_activity_head = headView.findViewById(R.id.iv_activity_head);
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
        web_content_layout = headView.findViewById(R.id.web_content_layout);
        webview = headView.findViewById(R.id.webview);
        no_data_layout = headView.findViewById(R.id.no_data_layout);
        rv_message.addHeaderView(headView);
        contact_person_layout.setOnClickListener(this::onClick);
    }


    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        img_right = findViewById(R.id.img_right);
        img_right.setVisibility(View.VISIBLE);
        img_right.setPadding(25, 25, 25, 25);
        img_right.setImageResource(R.drawable.community_activity_share);
        rv_message = findViewById(R.id.rv_message);
        rv_message.useDefaultLoadMore();
        send_message_layout = findViewById(R.id.send_message_layout);
        tv_join_activity = findViewById(R.id.tv_join_activity);
        user_top_view_back.setOnClickListener(this);
        img_right.setOnClickListener(this);
        send_message_layout.setOnClickListener(this);
        tv_join_activity.setOnClickListener(this);
        user_top_view_title.setText(getResources().getString(R.string.community_activity_details));
        commentBeanList = new ArrayList<>();
        communityActivityCommentAdapter = new CommunityActivityCommentAdapter(CommunityActivityDetailsActivity.this, commentBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CommunityActivityDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_message.setLayoutManager(linearLayoutManager);
        rv_message.setAdapter(communityActivityCommentAdapter);
        rv_message.setLoadMoreListener(() -> {
            page++;
            communityDynamicsModel.getActivityComment(1, source_id, page, CommunityActivityDetailsActivity.this);
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
            case R.id.img_right: //分享活动
                ShareActivityDialog shareActivityDialog = new ShareActivityDialog(CommunityActivityDetailsActivity.this, R.style.custom_dialog_theme);
                shareActivityDialog.show();
                shareActivityDialog.setShareContent(activityTitle, activityTitle, activityUrl);
                break;
            case R.id.contact_person_layout://联系活动发起人
                PermissionUtils.showPhonePermission(CommunityActivityDetailsActivity.this, contact_mobile);
                break;
            case R.id.send_message_layout://因为addheadviw了
                showInputCommentDialog(null, source_id, "", "");
                break;
            case R.id.tv_join_activity://参与活动
                if ("1".equals(is_join)) { //活动已参与
                    ToastUtil.toastShow(CommunityActivityDetailsActivity.this, getResources().getString(R.string.community_activity_joined_notice));
                } else {
                    if ("1".equals(pickRequire)) {
                        if (null == joinActivityDialog) {
                            joinActivityDialog = new JoinActivityDialog(CommunityActivityDetailsActivity.this, R.style.custom_dialog_theme);
                        }
                        joinActivityDialog.show();
                        showAddPicView();
                    } else {
                        communityDynamicsModel.joinCommunityActivity(2, source_id, "", CommunityActivityDetailsActivity.this);
                    }
                }
                break;
            case R.id.tv_define_join://确定参与活动
                joinCommunityActivity();
                break;
            case R.id.tv_cancel_join://取消参与
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
        mUploadImageViews.clear();
        joinActivityGridLayout = joinActivityDialog.join_activity_photo;
        joinActivityDialog.join_activity_photo.removeAllViews();
        joinActivityDialog.tv_join_notice.setText("参与此活动，请上传" + maxPickImageSize + "张" + pickerPrompt);
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

        TextView album = dialog.findViewById(R.id.avatar_album);
        TextView photograph = dialog.findViewById(R.id.avatar_photograph);
        TextView cancel = dialog.findViewById(R.id.avatar_cancel);

        //从相册选择上传
        album.setOnClickListener(v -> {
            imagePicker.setSelectLimit(maxPickImageSize - mUploadImageViews.size());
            imagePicker.clearSelectedImages();
            Intent intent = new Intent(CommunityActivityDetailsActivity.this, ImageGridActivity.class);
            startActivityForResult(intent, REQUEST_PHOTO);
            dialog.dismiss();
        });

        //拍照上传
        photograph.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (AndPermission.hasPermission(getApplicationContext(), Manifest.permission.CAMERA)) {
                    imagePicker.setSelectLimit(maxPickImageSize - mUploadImageViews.size());
                    imagePicker.clearSelectedImages();
                    Intent intent = new Intent(CommunityActivityDetailsActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                    startActivityForResult(intent, REQUEST_PHOTO);
                } else {
                    ToastUtil.toastShow(getApplicationContext(), "相机权限未开启，请去开启该权限");
                }
            } else {
                imagePicker.setSelectLimit(maxPickImageSize - mUploadImageViews.size());
                imagePicker.clearSelectedImages();
                Intent intent = new Intent(CommunityActivityDetailsActivity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, REQUEST_PHOTO);
            }
            dialog.dismiss();
        });
        //取消
        cancel.setOnClickListener(v -> dialog.dismiss());
        contentView.setOnClickListener(v -> dialog.dismiss());
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
        int picSize = joinActivityGridLayout.getChildCount();
        if (picSize > maxPickImageSize || picSize == 0) {
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
        if (mUploadImageViews.size() < maxPickImageSize) {
            ToastUtil.toastShow(this, "图片数量不够");
            return;
        } else {
            List<String> uploadObjList = new ArrayList<>();
            for (CommunityImageView communityImageView : mUploadImageViews) {
                if (!TextUtils.isEmpty(communityImageView.mUploadPhotoId)) {
                    uploadObjList.add(communityImageView.mUploadPhotoId);
                }
            }
            if (maxPickImageSize != uploadObjList.size()) {
                ToastUtil.toastShow(this, "请等待图片上传完成后发布");
                return;
            }
            if (null != joinActivityDialog) {
                joinActivityDialog.dismiss();
            }
            communityDynamicsModel.joinCommunityActivity(2, source_id, GsonUtils.gsonString(uploadObjList), CommunityActivityDetailsActivity.this);
            FileUtils.delDynamicPicFolder();
        }
    }

    private Dialog inputDialog;
    private String fromNickName;
    private String toUserId;
    private String content;

    /***新增留言或评论留言***/
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
        inputDialog.findViewById(R.id.scrollView).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP)
                inputDialog.dismiss();
            KeyBoardUtils.closeKeybord(feed_comment_edittext, CommunityActivityDetailsActivity.this);
            return true;
        });
        inputDialog.show();
        int finalItemBottomY = itemBottomY;
        if (null != itemView) {
            itemView.postDelayed(() -> {
                LinearLayout llCommentInput = inputDialog.findViewById(R.id.ll_comment_input);
                int y = getCoordinateY(llCommentInput);
                rv_message.smoothScrollBy(0, finalItemBottomY - y);
            }, 300);
        }
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                handleWindowChange();
            }
        });
        feed_comment_submit.setOnClickListener(v -> {
            content = feed_comment_edittext.getText().toString().trim();
            if (!TextUtils.isEmpty(content)) {
                if (content.length() > 300) {
                    ToastUtil.toastShow(CommunityActivityDetailsActivity.this, "输入的内容长度不能超过300字");
                } else {
                    KeyBoardUtils.closeKeybord(feed_comment_edittext, CommunityActivityDetailsActivity.this);
                    inputDialog.dismiss();
                    communityDynamicsModel.commentCommunityActivity(3, sourceId, content, toUserId, CommunityActivityDetailsActivity.this::OnHttpResponse);
                }
            } else {
                ToastUtil.toastShow(CommunityActivityDetailsActivity.this, "输入的内容不能为空");
            }
        });
        inputDialog.setOnDismissListener(dialog -> showAcStatus());
        tv_join_activity.setBackgroundResource(R.color.white);
    }


    public void setDelCommentId(String comment_id, int position) {
        delPos = position;
        DeleteNoticeDialog deleteNoticeDialog = new DeleteNoticeDialog(CommunityActivityDetailsActivity.this, R.style.custom_dialog_theme);
        deleteNoticeDialog.show();
        deleteNoticeDialog.btn_define.setOnClickListener(v -> {
            //删除评论或留言
            communityDynamicsModel.delActivityComment(4, source_id, comment_id, CommunityActivityDetailsActivity.this);
            deleteNoticeDialog.dismiss();
        });
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

    /***格式化金额的显示**/
    public String getFormatMoney(double price, boolean halfUp) {
        DecimalFormat formater = new DecimalFormat("0.00");
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(3);
        formater.setRoundingMode(halfUp ? RoundingMode.HALF_UP : RoundingMode.FLOOR);
        return formater.format(price);
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0://活动详情
                try {
                    CommunityActivityDetailsEntity communityActivityDetailsEntity = GsonUtils.gsonToBean(result, CommunityActivityDetailsEntity.class);
                    CommunityActivityDetailsEntity.ContentBean contentBean = communityActivityDetailsEntity.getContent();
                    GlideImageLoader.loadImageDisplay(CommunityActivityDetailsActivity.this, contentBean.getAc_banner(), iv_activity_head);
                    String ac_tag = contentBean.getAc_tag();
                    String ac_fee = contentBean.getAc_fee();
                    if ("0".equals(ac_fee) || "0.0".equals(ac_fee) || "0.00".equals(ac_fee)) {
                        tv_fee_price.setText(getResources().getString(R.string.community_activity_free));
                    } else {
                        tv_fee_price.setText("￥" + getFormatMoney(Double.valueOf(ac_fee), true));
                    }
                    activityTitle = contentBean.getAc_title();
                    tv_activity_title.setText(activityTitle);
                    join_number = contentBean.getJoin_num();
                    if (null != contentBean.getJoin_user()) {
                        join_user_list.clear();
                        join_user_list.addAll(contentBean.getJoin_user());
                    }
                    showCommunityActivity(CommunityActivityDetailsActivity.this, join_number, join_user_list, iv_first_photo, iv_second_photo, iv_third_photo, tv_join_person);
                    tv_activity_starttime.setText(TimeUtil.getYearTime(contentBean.getBegin_time() * 1000, "yyyy-MM-dd") + "～" + TimeUtil.getYearTime(contentBean.getEnd_time() * 1000, "yyyy-MM-dd"));
                    tv_activity_address.setText(contentBean.getAc_address());
                    tv_activity_endtime.setText(TimeUtil.getYearTime(contentBean.getStop_apply_time() * 1000, "yyyy-MM-dd"));
                    int limit_number = contentBean.getLimit_num();
                    if (limit_number == 0) {
                        tv_activity_person.setText("不限");
                    } else {
                        tv_activity_person.setText(limit_number + "人");
                    }
                    GlideImageLoader.loadImageDisplay(CommunityActivityDetailsActivity.this, contentBean.getContact_user_avatar(), iv_contact_header);
                    tv_contact_name.setText(contentBean.getContact_user_name());
                    contact_id = contentBean.getContact_user_id();
                    contact_mobile = contentBean.getContact_user_mobile();
                    ac_status = contentBean.getAc_status();
                    maxPickImageSize = contentBean.getPicture_num();
                    pickerPrompt = contentBean.getPicture_prompt();
                    pickRequire = contentBean.getPicture_require();
                    activityUrl = contentBean.getAc_sharelink();
                    String ac_detail = contentBean.getAc_detail();
                    if (TextUtils.isEmpty(ac_detail)) {
                        web_content_layout.setVisibility(View.GONE);
                    } else {
                        web_content_layout.setVisibility(View.VISIBLE);
                        webview.loadUrl(ac_detail);
                    }
                    is_join = contentBean.getIs_join();
                    showAcStatus();
                } catch (Exception e) {

                }
                break;
            case 1://留言列表
                try {
                    CommunityActivityListEntity communityActivityListEntity = GsonUtils.gsonToBean(result, CommunityActivityListEntity.class);
                    CommunityActivityListEntity.ContentBean contentBean = communityActivityListEntity.getContent();
                    if (page == 1) {
                        commentBeanList.clear();
                    }
                    List<CommunityActivityListEntity.ContentBean.DataBean> pageContentList = contentBean.getData();
                    boolean dataEmpty = pageContentList == null || pageContentList.size() == 0;
                    commentBeanList.addAll(contentBean.getData());
                    int total = contentBean.getTotal();
                    boolean hasMore;
                    if (total > commentBeanList.size()) {
                        hasMore = true;
                    } else {
                        hasMore = false;
                    }
                    communityActivityCommentAdapter.notifyDataSetChanged();
                    if (total == 0) {
                        no_data_layout.setVisibility(View.VISIBLE);
                    } else {
                        no_data_layout.setVisibility(View.GONE);
                        rv_message.loadMoreFinish(dataEmpty, hasMore);
                    }
                } catch (Exception e) {

                }
                break;
            case 2://参与活动
                try {
                    CommunityStatusEntity communityStatusEntity = GsonUtils.gsonToBean(result, CommunityStatusEntity.class);
                    ac_status = communityStatusEntity.getContent().getAc_status();
                    if ("5".equals(ac_status)) {
                        is_join = "1";
                        ToastUtil.toastJoinActivity(CommunityActivityDetailsActivity.this);
                        join_number++;
                        if (join_number <= 3) {
                            join_user_list.add(shared.getString(UserAppConst.Colour_head_img, ""));
                        }
                        updateActivityStatus();
                    } else {
                        ToastUtil.toastShow(CommunityActivityDetailsActivity.this, communityStatusEntity.getMessage());
                    }
                } catch (Exception e) {

                }
                if (!TextUtils.isEmpty(ac_status)) {
                    showAcStatus();
                }
                break;
            case 3://添加留言
                String commentId = "";
                try {
                    CommunityDynamicIdEntity communityDynamicIdEntity = GsonUtils.gsonToBean(result, CommunityDynamicIdEntity.class);
                    commentId = communityDynamicIdEntity.getContent().getComment_id();
                } catch (Exception e) {

                }
                CommunityActivityListEntity.ContentBean.DataBean commentBean = new CommunityActivityListEntity.ContentBean.DataBean();
                commentBean.setContent(content);
                commentBean.setId(commentId);
                commentBean.setSource_id(source_id);
                commentBean.setFrom_avatar(shared.getString(UserAppConst.Colour_head_img, ""));
                commentBean.setFrom_mobile(shared.getString(UserAppConst.Colour_login_mobile, ""));
                int userId = shared.getInt(UserAppConst.Colour_User_id, 0);
                String fromSourceId = String.valueOf(userId);
                commentBean.setFrom_id(fromSourceId);
                commentBean.setFrom_nickname(shared.getString(UserAppConst.Colour_NIACKNAME, ""));
                long currentTime = System.currentTimeMillis() / 1000;
                commentBean.setUpdated_at(currentTime);
                if (TextUtils.isEmpty(toUserId)) {
                    ToastUtil.toastShow(CommunityActivityDetailsActivity.this, "留言成功");
                } else {
                    //评论别人的留言
                    ToastUtil.toastShow(CommunityActivityDetailsActivity.this, "评论成功");
                    commentBean.setTo_nickname(fromNickName);
                    commentBean.setTo_id(toUserId);
                }
                commentBeanList.add(commentBean);
                communityActivityCommentAdapter.notifyItemInserted(communityActivityCommentAdapter.getItemCount());
                communityActivityCommentAdapter.notifyItemChanged(communityActivityCommentAdapter.getItemCount());
                if (commentBeanList.size() == 0) {
                    no_data_layout.setVisibility(View.VISIBLE);
                } else {
                    no_data_layout.setVisibility(View.GONE);
                }
                toUserId = "";
                break;
            case 4://删除留言
                commentBeanList.remove(delPos);
                communityActivityCommentAdapter.notifyItemRemoved(delPos);
                communityActivityCommentAdapter.notifyItemRangeChanged(delPos, communityActivityCommentAdapter.getItemCount() - delPos);
                if (commentBeanList.size() == 0) {
                    no_data_layout.setVisibility(View.VISIBLE);
                } else {
                    no_data_layout.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void showAcStatus() {
        if ("1".equals(is_join)) { //表示已参加
            tv_join_activity.setEnabled(false);
            tv_join_activity.setBackgroundColor(getResources().getColor(R.color.color_d1d1d1));
            tv_join_activity.setText(getResources().getString(R.string.community_activity_joined));
        } else {
            switch (ac_status) {
                //1正在进行，
                //2人数已满，
                //3报名截止，
                //4已结束
                case "2":
                    tv_join_activity.setEnabled(false);
                    tv_join_activity.setBackgroundColor(getResources().getColor(R.color.color_d1d1d1));
                    tv_join_activity.setText(getResources().getString(R.string.community_activity_numberfull));
                    break;
                case "3":
                    tv_join_activity.setEnabled(false);
                    tv_join_activity.setBackgroundColor(getResources().getColor(R.color.color_d1d1d1));
                    tv_join_activity.setText(getResources().getString(R.string.community_activity_endtime));
                    break;
                case "4":
                    tv_join_activity.setEnabled(false);
                    tv_join_activity.setBackgroundColor(getResources().getColor(R.color.color_d1d1d1));
                    tv_join_activity.setText(getResources().getString(R.string.community_activity_finished));
                    break;
                default:
                    tv_join_activity.setEnabled(true);
                    tv_join_activity.setBackgroundColor(getResources().getColor(R.color.color_3282fa));
                    tv_join_activity.setText(getResources().getString(R.string.community_activity_join));
                    break;
            }
        }
        updateActivityStatus();
    }

    /***社区活动状态的更新*/
    private void updateActivityStatus() {
        EventBus eventBus = EventBus.getDefault();
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString("ac_status", ac_status);
        bundle.putString("sourceId", source_id);
        bundle.putInt("join_number", join_number);
        bundle.putString("is_join", is_join);
        bundle.putInt("position", -1);
        message.setData(bundle);
        message.obj = join_user_list;
        message.what = CHANGE_ACTIVITY_STATUS;
        eventBus.post(message);
    }
}
