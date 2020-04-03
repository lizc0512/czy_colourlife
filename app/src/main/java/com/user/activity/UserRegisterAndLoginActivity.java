package com.user.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.Constants;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.ClearEditText;
import com.appsafekb.safekeyboard.AppSafekbConfig;
import com.appsafekb.safekeyboard.NKeyBoardTextField;
import com.appsafekb.safekeyboard.encrypt.EncryptTypeFactory;
import com.appsafekb.safekeyboard.interfaces.encrypttype.BaseEncryptionType;
import com.appsafekb.safekeyboard.interfaces.encrypttype.XYEncryptionType;
import com.appsafekb.safekeyboard.values.ValueFactory;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.GetPhoneInfoListener;
import com.eparking.helper.CustomDialog;
import com.external.eventbus.EventBus;
import com.gesturepwd.activity.UnlockGesturePasswordActivity;
import com.jpush.Constant;
import com.mob.MobSDK;
import com.mob.tools.utils.UIHandler;
import com.nohttp.utils.GsonUtils;
import com.permission.AndPermission;
import com.permission.PermissionListener;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.entity.CheckAuthRegisterEntity;
import com.user.entity.CheckRegisterEntity;
import com.user.entity.CheckWhiteEntity;
import com.user.entity.IsGestureEntity;
import com.user.model.NewUserModel;
import com.user.model.TokenModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.net.cyberway.OauthWebviewActivity;
import cn.net.cyberway.R;
import cn.net.cyberway.utils.ActivityLifecycleListener;
import cn.net.cyberway.utils.ConfigUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import static cn.net.cyberway.utils.ConfigUtils.jumpContactService;
import static cn.net.cyberway.utils.IMFriendDataUtils.userInitImData;
import static com.BeeFramework.model.Constants.GESTURE_PWD_SET_FIVE_ERROR;

/*
 * @date 创建时间 2017/4/17
 * @author  yuansk
 * @Description  登陆注册二和一的
 * @version  1.1
 */
public class UserRegisterAndLoginActivity extends BaseActivity implements OnClickListener, Handler.Callback, NewHttpResponse {
    public static final String FORMSOURCE = "formsource";
    public static final String MOBILE = "mobile";
    public static final String PASSWORD = "password";
    public static final String SMSTYPE = "smstype";
    public static final String SMSCODE = "smscode";
    public static final String ISREGISTER = "isregister";
    public static final String ISWHITE = "iswhite";
    public static final int SUCCEED = 1;
    public static final int REQUEST_CALLPHONE = 2000;
    private boolean fromThridSource = false;
    private NewUserModel newUserModel;
    private TokenModel mTokenModel;
    private String packgeName = "";
    private String appId = "";
    private String mobile = "";
    private String password = "";
    private String loginPawd = "";
    private String smsCode = "";
    private String openCode = "";
    private String oldPhone = "";
    private String loginSource = "";
    private int loginType = 0;
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView user_top_view_right;
    private ClearEditText user_login_phone;
    private ImageView img_gesture_pwd;
    private NKeyBoardTextField user_login_password;
    private TextView user_login_find_password;
    private Button user_login_btn;
    private LinearLayout thridLoginlayout;
    private LinearLayout wechat_layout;
    private LinearLayout qq_layout;
    private TextView tv_contact_service;
    private int isRegister = 1;
    private int isWhite = 0;
    /**
     * 用户禁止注册的提示语
     **/
    private String forbidNotice = "";
    private String setGesturePawd;
    private String portraitUrl = "";
    private PlatformDb loginPlatformDb;
    private String hotLine = "1010-1778";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginandregister_layout);
        MobSDK.init(getApplicationContext());
        initView();
        EventBus.getDefault().register(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fromThridSource = bundle.getBoolean(FORMSOURCE, false);
            if (fromThridSource) {
                packgeName = bundle.getString("packgename", "");
                appId = bundle.getString("applicationId", "");
            }
            boolean loginOut = bundle.getBoolean("login_out", false);
            if (loginOut) {
                ToastUtil.toastShow(UserRegisterAndLoginActivity.this, getResources().getString(R.string.account_extrude_login));
            }
        }
        newUserModel = new NewUserModel(this);
        mTokenModel = new TokenModel(this);
        mobile = shared.getString(UserAppConst.Colour_login_mobile, "");
        if (!TextUtils.isEmpty(mobile)) {
            user_login_phone.setText(mobile);
            user_login_phone.setSelection(mobile.length());
        }
        String oneKeyShow = shared.getString(UserAppConst.COLOUR_ONEKEY_SHOW, "1");
        boolean isLogin = shared.getBoolean(UserAppConst.IS_LOGIN, false);
        if ("1".equals(oneKeyShow) && !isLogin) {
            OneKeyLoginManager.getInstance().getPhoneInfo(new GetPhoneInfoListener() {
                @Override
                public void getPhoneInfoStatus(int code, String result) {
                    if (1022 == code && ActivityLifecycleListener.authDestoryed) {
                        OneKeyLoginManager.getInstance().setAuthThemeConfig(ConfigUtils.getCJSConfig(UserRegisterAndLoginActivity.this));
                        new ConfigUtils(UserRegisterAndLoginActivity.this).jumpOneKeyLogin();
                    }
                }
            });
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void initView() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        user_login_phone = (ClearEditText) findViewById(R.id.user_login_phone);
        img_gesture_pwd = (ImageView) findViewById(R.id.img_gesture_pwd);
        user_login_password = findViewById(R.id.user_login_password);
        user_login_find_password = (TextView) findViewById(R.id.user_login_find_password);
        user_login_btn = (Button) findViewById(R.id.user_login_btn);
        thridLoginlayout = (LinearLayout) findViewById(R.id.thridLoginlayout);
        wechat_layout = findViewById(R.id.wechat_layout);
        qq_layout = findViewById(R.id.qq_layout);
        tv_contact_service = findViewById(R.id.tv_contact_service);
        findViewById(R.id.line).setVisibility(View.GONE);
        user_top_view_right.setText(getResources().getString(R.string.title_login_verify));
        user_top_view_right.setTextColor(Color.parseColor("#329dfa"));
        user_top_view_back.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        img_gesture_pwd.setOnClickListener(this);
        user_login_find_password.setOnClickListener(this);
        user_login_btn.setOnClickListener(this);
        wechat_layout.setOnClickListener(this);
        qq_layout.setOnClickListener(this);
        tv_contact_service.setOnClickListener(this);
        user_login_password.setNkeyboardType(0);
        user_login_password.setNKeyboardRandom(ValueFactory.buildAllTrue());
        user_login_password.setEditClearIcon(true);
        user_login_password.setNKeyboardKeyEncryption(false);
        user_login_password.clearNkeyboard();
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, user_top_view_back, user_top_view_title);
        addTextChangeLister();
//      tv_explain.setText(getResources().getString(R.string.user_bind_phone_explain));
    }


    private void addTextChangeLister() {
        user_login_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!TextUtils.isEmpty(s.toString()) && s.toString().length() == 11) {
                    oldPhone = s.toString().trim();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mobile = s.toString().trim();
                if (mobile.length() == 11 && !oldPhone.equals(mobile)) {
                    //去判断当前的手机号是否为注册用户
                    newUserModel.getCheckRegister(0, mobile, UserRegisterAndLoginActivity.this);
                }
                changeLoginBtnStatus();
            }
        });
        user_login_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeLoginBtnStatus();
            }
        });
        changeLoginBtnStatus();
    }

    private void changeLoginBtnStatus() {
        password = user_login_password.getNKeyboardText();
        if (TextUtils.isEmpty(password)) {
            password = shared.getString(UserAppConst.Colour_login_password, "");
        }
        if (!TextUtils.isEmpty(mobile) && 11 == mobile.length() && !TextUtils.isEmpty(password)) {
            user_login_btn.setEnabled(true);
            user_login_btn.setBackgroundResource(R.drawable.onekey_login_bg);
        } else {
            user_login_btn.setEnabled(true);
            user_login_btn.setBackgroundResource(R.drawable.onekey_login_default_bg);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.img_gesture_pwd:
                if (fastClick()) {
                    if (11 == mobile.length()) {
                        newUserModel.isSetGesture(1, mobile, UserRegisterAndLoginActivity.this);
                    } else {
                        user_login_phone.requestFocus();
                        ToastUtil.toastShow(UserRegisterAndLoginActivity.this, getResources().getString(R.string.user_errorphone_notice));
                    }
                }
                break;
            case R.id.user_login_find_password:
                if (fastClick()) {
                    Intent intent = new Intent(UserRegisterAndLoginActivity.this, UserForgetPawdPhoneActivity.class);
                    if (11 == mobile.length()) {
                        intent.putExtra(UserRegisterAndLoginActivity.MOBILE, mobile);
                    } else {
                        intent.putExtra(UserRegisterAndLoginActivity.MOBILE, "");
                    }
                    startActivity(intent);
                }
                break;
            case R.id.user_login_btn:
                if (fastClick()) {
                    loginType = 2;
                    //其中的第一个参数为具体的所要填充的内容,第二个参数为回调。
                    newUserModel.getCheckWhite(2, mobile, isRegister, this);
                }
                break;
            case R.id.qq_layout:
                if (fastClick()) {
                    loginSource = "qq";
                    loginType = 5;
                    Platform qq = ShareSDK.getPlatform(QQ.NAME);
                    authorize(qq);
                }
                break;
            case R.id.wechat_layout:
                if (fastClick()) {
                    loginSource = "wechat";
                    loginType = 4;
                    Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                    authorize(wechat);
                }
                break;
            case R.id.user_top_view_right:
                if (fastClick()) {
                    Intent verifyIntent = new Intent(UserRegisterAndLoginActivity.this, UserIdentityLoginActivity.class);
                    if (11 == mobile.length()) {
                        verifyIntent.putExtra(UserRegisterAndLoginActivity.MOBILE, mobile);
                    } else {
                        verifyIntent.putExtra(UserRegisterAndLoginActivity.MOBILE, "");
                    }
                    startActivity(verifyIntent);
                }
                break;
            case R.id.tv_contact_service:
                jumpContactService(UserRegisterAndLoginActivity.this);
                break;
        }
    }

    /****根据当前的手机号码判断是登陆用户还是注册用户***/
    private void showRegisterOrLoginLayout() {
        if (fromThridSource) {
            img_gesture_pwd.setVisibility(View.GONE);
            user_top_view_title.setText(getResources().getString(R.string.user_login));
            user_login_password.setHint(getResources().getString(R.string.user_input_pawd));
            user_login_btn.setText(getResources().getString(R.string.user_login));
            thridLoginlayout.setVisibility(View.GONE);
            thridLoginlayout.setVisibility(View.GONE);
            user_login_find_password.setVisibility(View.GONE);
            user_top_view_right.setVisibility(View.GONE);
        } else {
            if (isRegister == 0) { //注册
                user_login_btn.setText(getResources().getString(R.string.user_next));
                user_login_password.setHint(getResources().getString(R.string.user_set_pawd));
                user_login_find_password.setVisibility(View.GONE);
                img_gesture_pwd.setVisibility(View.INVISIBLE);
            } else {//登录
                user_login_find_password.setVisibility(View.VISIBLE);
                user_login_password.setHint(getResources().getString(R.string.user_input_pawd));
                user_login_btn.setText(getResources().getString(R.string.user_login));
                if (GESTURE_PWD_SET_FIVE_ERROR.equals(setGesturePawd)) {
                    img_gesture_pwd.setVisibility(View.GONE);
                } else {
                    img_gesture_pwd.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 根据手势密码状态判断跳转/提示
     */

    private void decideJump() {
        if (Constants.GESTURE_PWD_SET_AND_ENABLED.equals(setGesturePawd)) {
            // 手势密码已设置且开启，跳转到手势密码登录界面
            editor.putString(UserAppConst.Colour_login_mobile + UserAppConst.GESTURE_OPENED, setGesturePawd);
            if (TextUtils.isEmpty(portraitUrl)) {
                editor.putString(UserAppConst.Colour_head_img, "");
            } else {
                editor.putString(UserAppConst.Colour_head_img, portraitUrl);
            }
            editor.putString(UserAppConst.Colour_login_mobile, mobile);
            editor.commit();
            OneKeyLoginManager.getInstance().finishAuthActivity();
            Intent intent = new Intent(UserRegisterAndLoginActivity.this, UnlockGesturePasswordActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        } else if (Constants.GESTURE_PWD_SET_AND_DISABLED.equals(setGesturePawd)) {
            // 手势密码已设置且关闭，弹出提示框
            String tips = getResources().getString(R.string.user_unopen_gesturepswd);
            showCustomDialog(tips);
        } else if (Constants.GESTURE_PWD_UNSET.equals(setGesturePawd)) {
            // 未设置手势密码，弹出提示框
            String tips = getResources().getString(R.string.user_unset_gesturepswd);
            showCustomDialog(tips);
        }
    }

    /**
     * 显示提示框
     *
     * @param tips String提示文字
     */
    private CustomDialog customDialog;// 提示问题对话框

    private void showCustomDialog(String tips) {
        try {
            customDialog = new CustomDialog(UserRegisterAndLoginActivity.this, R.style.custom_dialog_theme);
            customDialog.show();
            customDialog.setCancelable(false);
            customDialog.dialog_content.setText(tips);
            customDialog.dialog_button_ok.setGravity(Gravity.CENTER_HORIZONTAL);
            customDialog.dialog_button_cancel.setVisibility(View.GONE);
            customDialog.dialog_button_ok.setText(getResources().getString(R.string.message_define));
            customDialog.dialog_button_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.dismiss();
                }
            });
        } catch (Exception e) {

        }
    }

    /**
     * 用户更换手机号是审核中的
     *
     * @param tips String提示文字
     */
    private CustomDialog reviewDialog;

    private void showReviewDialog(String tips) {
        reviewDialog = new CustomDialog(UserRegisterAndLoginActivity.this, R.style.custom_dialog_theme);
        reviewDialog.show();
        reviewDialog.setCancelable(false);
        reviewDialog.dialog_content.setText(tips);
        reviewDialog.dialog_line.setVisibility(View.VISIBLE);
        reviewDialog.dialog_button_ok.setGravity(Gravity.CENTER_HORIZONTAL);
        reviewDialog.dialog_button_cancel.setText(getResources().getString(R.string.message_define));
        reviewDialog.dialog_button_ok.setText(getResources().getString(R.string.user_contact_service));
        reviewDialog.dialog_button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewDialog.dismiss();
            }
        });
        reviewDialog.dialog_button_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (AndPermission.hasPermission(UserRegisterAndLoginActivity.this, Manifest.permission.CALL_PHONE)) {
                        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hotLine));//跳转到拨号界面，同时传递电话号码
                        startActivity(dialIntent);
                    } else {
                        ArrayList<String> permission = new ArrayList<>();
                        permission.add(Manifest.permission.CALL_PHONE);
                        if (AndPermission.hasAlwaysDeniedPermission(UserRegisterAndLoginActivity.this, permission)) {
                            ToastUtil.toastShow(getApplicationContext(), getResources().getString(R.string.user_callpermission_notice));
                        } else {
                            AndPermission.with(UserRegisterAndLoginActivity.this)
                                    .requestCode(REQUEST_CALLPHONE)
                                    .permission(Manifest.permission.CALL_PHONE)
                                    .callback(permissionListener)
                                    .start();
                        }
                    }
                } else {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hotLine));//跳转到拨号界面，同时传递电话号码
                    startActivity(dialIntent);
                }
                reviewDialog.dismiss();
            }
        });
    }

    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_CALLPHONE: {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hotLine));//跳转到拨号界面，同时传递电话号码
                    startActivity(dialIntent);
                    break;
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            if (AndPermission.hasAlwaysDeniedPermission(UserRegisterAndLoginActivity.this, deniedPermissions)) {
                ToastUtil.toastShow(getApplicationContext(), getResources().getString(R.string.user_callpermission_notice));
            }
        }
    };


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:  //检查用户是否注册
                if (!TextUtils.isEmpty(result)) {
                    try {
                        CheckRegisterEntity checkRegisterEntity = GsonUtils.gsonToBean(result, CheckRegisterEntity.class);
                        isRegister = checkRegisterEntity.getContent().getIs_register();
                    } catch (Exception e) {

                    }
                }
                String label = "";
                if (isRegister == 0) {
                    label = "200002";
                } else {
                    label = "201002";
                }
                Map<String, String> stringMap = new HashMap<String, String>();
                stringMap.put("mobile", mobile);
                showRegisterOrLoginLayout();
                break;
            case 1:  //是否设置手势密码
                if (!TextUtils.isEmpty(result)) {
                    try {
                        IsGestureEntity checkRegisterEntity = GsonUtils.gsonToBean(result, IsGestureEntity.class);
                        IsGestureEntity.ContentBean contentBean = checkRegisterEntity.getContent();
                        setGesturePawd = contentBean.getGesture();
                        portraitUrl = contentBean.getPortrait();
                    } catch (Exception e) {

                    }
                }
                decideJump();
                break;
            case 2: //检查是不是白名单
                if (TextUtils.isEmpty(password)) {
                    password = shared.getString(UserAppConst.Colour_login_password, "");
                }
                loginPawd = password;
                if (!TextUtils.isEmpty(result)) {
                    try {
                        CheckWhiteEntity checkWhiteEntity = GsonUtils.gsonToBean(result, CheckWhiteEntity.class);
                        CheckWhiteEntity.ContentBean contentBean = checkWhiteEntity.getContent();
                        isWhite = contentBean.getIs_white();
                        forbidNotice = contentBean.getNotice();
                        hotLine = contentBean.getHotLine();
                        if (TextUtils.isEmpty(hotLine)) {
                            hotLine = "1010-1778";
                        }
                    } catch (Exception e) {

                    }
                }
                if (isRegister == 1) {
                    loginSource = "REGISTERED";
                    if (isWhite == 1) {
                        editor.putString(UserAppConst.Colour_login_password, loginPawd).apply();
                        newUserModel.getAuthToken(4, mobile, loginPawd, "1", true, UserRegisterAndLoginActivity.this);
                    } else if (isWhite == 5) {
                        if (TextUtils.isEmpty(forbidNotice)) {
                            forbidNotice = getResources().getString(R.string.change_pswd_verify);
                        }
                        showReviewDialog(forbidNotice);
                    } else if (isWhite == 6) {
                        editor.putString(UserAppConst.Colour_login_password, loginPawd).apply();
                        Intent intent = new Intent(UserRegisterAndLoginActivity.this, UserSmsCodeActivity.class);
                        intent.putExtra(UserRegisterAndLoginActivity.MOBILE, mobile);
                        intent.putExtra(UserRegisterAndLoginActivity.PASSWORD, password);
                        intent.putExtra(UserRegisterAndLoginActivity.SMSTYPE, 4);
                        startActivityForResult(intent, 1000);
                    } else {
                        editor.putString(UserAppConst.Colour_login_password, loginPawd).apply();
                        Intent registerIntent = new Intent(UserRegisterAndLoginActivity.this, UserSafetyVerficationActivity.class);
                        registerIntent.putExtra(UserRegisterAndLoginActivity.MOBILE, mobile);
                        registerIntent.putExtra(UserRegisterAndLoginActivity.PASSWORD, loginPawd);
                        registerIntent.putExtra(UserRegisterAndLoginActivity.ISREGISTER, isRegister);
                        registerIntent.putExtra(UserRegisterAndLoginActivity.ISWHITE, isWhite);
                        startActivityForResult(registerIntent, 1000);
                    }
                } else { //跳转去注册页面
                    if (isWhite == 1) {
                        /****进行onepass认证******/
                        goRegisterPage();
                    } else {
                        if (isWhite == 4) {
                            if (TextUtils.isEmpty(forbidNotice)) {
                                ToastUtil.toastShow(UserRegisterAndLoginActivity.this, getResources().getString(R.string.user_forbid_register));
                            } else {
                                ToastUtil.toastShow(UserRegisterAndLoginActivity.this, forbidNotice);
                            }
                        } else if (isWhite == 5) {
                            if (TextUtils.isEmpty(forbidNotice)) {
                                forbidNotice = getResources().getString(R.string.change_pswd_verify);
                            }
                            showReviewDialog(forbidNotice);
                        } else {
                            Intent registerIntent = new Intent(UserRegisterAndLoginActivity.this, UserSafetyVerficationActivity.class);
                            registerIntent.putExtra(UserRegisterAndLoginActivity.MOBILE, mobile);
                            registerIntent.putExtra(UserRegisterAndLoginActivity.ISWHITE, isWhite);
                            registerIntent.putExtra(UserRegisterAndLoginActivity.ISREGISTER, isRegister);
                            registerIntent.putExtra(UserRegisterAndLoginActivity.PASSWORD, loginPawd);
                            startActivity(registerIntent);
                        }
                    }
                }
                if (user_login_password.keyboardIsShow()){
                    user_login_password.hideNKeyboard();
                }
                break;
            case 4://获取用户的信息 ,跳转到主页
                if (!TextUtils.isEmpty(result)) {
                    newUserModel.getUserInformation(13, true, this);
                } else {
                    Map<String, String> loginMap = new HashMap<String, String>();
                    loginMap.put("mobile", mobile);
                }
                break;
            case 6://单设备登录
                if (!fromThridSource) {
                    ToastUtil.toastShow(this, getResources().getString(R.string.user_login_success));
                } else {
                    Intent intent = new Intent(UserRegisterAndLoginActivity.this, OauthWebviewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("applicationId", appId);
                    bundle.putString("packgename", packgeName);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                Map<String, String> mainParams = new HashMap<String, String>();
                mainParams.put("customer_id", shared.getInt(UserAppConst.Colour_User_id, 0) + "");
                mainParams.put("mobile", mobile);
                if (isRegister == 0) {
                } else {
                    if (loginType == 4) {
                        mainParams.put("login_type", "WEIXIN");
                    } else if (loginType == 5) {
                        mainParams.put("login_type", "QQ");
                    } else {
                        mainParams.put("login_type", "REGISTERED");
                    }
                }
                editor.putBoolean(UserAppConst.Colour_user_login, true);
                editor.commit();
                Message msg = new Message();
                msg.what = UserMessageConstant.SIGN_IN_SUCCESS;//登录成功之后，刷新各种数据
                EventBus.getDefault().post(msg);
                sendNotification();
                finish();
                break;
            case 13:
                if (!TextUtils.isEmpty(result)) {
                    userInitImData(UserRegisterAndLoginActivity.this, shared);
                    OneKeyLoginManager.getInstance().finishAuthActivity();
                    mTokenModel.getToken(6, loginType, true, this);
                }
                break;
            case 7:
                if (!TextUtils.isEmpty(result)) {
                    //根据状态值
                    try {
                        CheckAuthRegisterEntity checkAuthRegisterEntity = GsonUtils.gsonToBean(result, CheckAuthRegisterEntity.class);
                        CheckAuthRegisterEntity.ContentBean contentBean = checkAuthRegisterEntity.getContent();
                        int status = contentBean.getIs_register();
                        String loginMobile = contentBean.getMobile();
                        if (status == 1) { //用户已注册,根据返回的mobile获取access_token
                            newUserModel.getAuthToken(4, loginMobile, openCode, String.valueOf(loginType), true, UserRegisterAndLoginActivity.this);
                        } else {
                            Intent intent = new Intent(UserRegisterAndLoginActivity.this, UserThridRegisterActivity.class);
                            String gender = loginPlatformDb.getUserGender();
                            if ("m".equals(gender)) {
                                gender = "1";
                            } else if ("f".equals(gender)) {
                                gender = "2";
                            } else {
                                gender = "0";
                            }
                            intent.putExtra(UserThridRegisterActivity.GENDER, gender);
                            intent.putExtra(UserThridRegisterActivity.NICKNAME, loginPlatformDb.getUserName());
                            intent.putExtra(UserThridRegisterActivity.OPENCODE, openCode);
                            intent.putExtra(UserThridRegisterActivity.SOURCE, loginSource);
                            intent.putExtra(UserThridRegisterActivity.PORTRAIT, loginPlatformDb.getUserIcon());
                            startActivity(intent);
                        }
                    } catch (Exception e) {

                    }
                }
                break;
            case 11:
                if (!TextUtils.isEmpty(result)) {
                    ToastUtil.toastShow(UserRegisterAndLoginActivity.this, getResources().getString(R.string.user_register_success));
                    loginSource = "REGISTERED";
                    isRegister = 1;
                    showRegisterOrLoginLayout();
                    newUserModel.getAuthToken(4, mobile, loginPawd, "1", true, UserRegisterAndLoginActivity.this);
                } else {
                    Map<String, String> paramsMap = new HashMap<String, String>();
                    paramsMap.put("mobile", mobile);
                }
                break;
        }
    }


    private void authorize(final Platform plat) {
        if (plat == null) {
            return;
        }
        //判断指定平台是否已经完成授权
        if (plat.isAuthValid()) {
            //如果已经授权，直接调用第三方登录接口
            String userId = plat.getDb().getUserId();
            if (userId != null) {
                Message msg = new Message();
                msg.what = SUCCEED;
                msg.obj = plat;
                UIHandler.sendMessage(msg, this);
                return;
            }
        }
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //判断当前的opencode是否注册
                loginPlatformDb = platform.getDb();
                openCode = loginPlatformDb.getUserId();
                UserRegisterAndLoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newUserModel.authRegister(7, loginSource, openCode, UserRegisterAndLoginActivity.this);
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                UserRegisterAndLoginActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        ToastUtil.toastShow(UserRegisterAndLoginActivity.this, "授权失败:" + throwable.getMessage());
                    }
                });
            }


            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        // true不使用SSO授权，false使用SSO授权(调用客户端)
        plat.SSOSetting(false);
        //获取用户资料
        plat.authorize();
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SUCCEED:
                Platform platform = (Platform) msg.obj;
                loginPlatformDb = platform.getDb();
                openCode = loginPlatformDb.getUserId();
                newUserModel.authRegister(7, loginSource, openCode, UserRegisterAndLoginActivity.this);
                break;
        }
        return false;
    }


    /**
     * 发送本地通知:登陆成功
     */
    private void sendNotification() {
        LocalBroadcastManager mLocalBroadcastManager = LocalBroadcastManager
                .getInstance(this);
        Intent data = new Intent();
        data.setAction(Constant.ACTION_LOGIN_FINISH_COMPLETED);
        mLocalBroadcastManager.sendBroadcast(data);
    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.SIGN_IN_FAIL: //登录失败
                newUserModel.getCheckRegister(0, mobile, UserRegisterAndLoginActivity.this);
                break;
            case UserMessageConstant.SIGN_IN_SUCCESS:
                finish();
                break;
            case UserMessageConstant.REGISTER_TYPE_FIAL: //注册失败
                switch (message.arg1) {
                    case 2:
                        user_login_phone.setText("");
                        break;
                    case 3:
                        img_gesture_pwd.setVisibility(View.GONE);
                        break;
                    case 4:
                        isRegister = 1;
                        showRegisterOrLoginLayout();
                        break;
                    default:
                        user_login_phone.setText("");
                        img_gesture_pwd.setVisibility(View.GONE);
                        break;
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeLoginBtnStatus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) { //验证码
            smsCode = data.getStringExtra(UserRegisterAndLoginActivity.SMSCODE);
            newUserModel.getAuthToken(4, mobile, loginPawd, "1", true, UserRegisterAndLoginActivity.this);
        } else if (resultCode == 500) {
            newUserModel.getAuthToken(4, mobile, loginPawd, "1", true, UserRegisterAndLoginActivity.this);
        } else if (resultCode == 1011) {
            ActivityLifecycleListener.authDestoryed = false;
        }
    }

    private void goRegisterPage() {
        Intent intent = new Intent(UserRegisterAndLoginActivity.this, UserRegisterActivity.class);
        intent.putExtra(UserRegisterAndLoginActivity.MOBILE, mobile);
        intent.putExtra(UserRegisterAndLoginActivity.PASSWORD, loginPawd);
        startActivity(intent);
    }
}
