package com.user.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.ClearEditText;
import com.external.eventbus.EventBus;
import com.mob.MobSDK;
import com.mob.tools.utils.UIHandler;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.entity.CheckAuthRegisterEntity;
import com.user.entity.SendCodeEntity;
import com.user.model.NewUserModel;
import com.user.model.TokenModel;

import java.util.HashMap;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.ConfigUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import static cn.net.cyberway.utils.IMFriendDataUtils.userInitImData;
import static com.user.activity.UserRegisterAndLoginActivity.MOBILE;
import static com.user.activity.UserRegisterAndLoginActivity.SUCCEED;

/**
 * @name ${yuansk}
 * @class name：${PACKAGE_NAME}
 * @class describe
 * @anthor ${USER} QQ:827194927
 * @time ${DATE} ${TIME}
 * @change
 * @chang time
 * @class describe   短信验证码登录
 */
public class UserIdentityLoginActivity extends AppCompatActivity implements OnClickListener, NewHttpResponse, Handler.Callback {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView user_top_view_right;
    private ClearEditText user_login_phone;
    private EditText ed_sms;
    private TextView tv_get_sms;
    private TextView tv_voice_code;
    private Button user_login_btn;
    private LinearLayout wechat_layout;
    private LinearLayout qq_layout;
    private TextView tv_contact_service;
    private NewUserModel newUserModel;
    private String loginSource = "";
    private PlatformDb loginPlatformDb;
    private String openCode = "";
    private int loginType = 0;
    private String mobile;
    private String smsCode;
    public SharedPreferences shared;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identitycode_login_layout);
        shared = getSharedPreferences(UserAppConst.USERINFO, 0);
        editor = shared.edit();
        EventBus.getDefault().register(this);
        MobSDK.init(getApplicationContext());
        initView();
        newUserModel = new NewUserModel(this);
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        user_top_view_right = findViewById(R.id.user_top_view_right);
        user_top_view_right.setText("密码登录");
        user_top_view_right.setTextColor(Color.parseColor("#329dfa"));
        user_top_view_back.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        user_login_phone = findViewById(R.id.user_login_phone);
        ed_sms = findViewById(R.id.ed_sms);
        tv_get_sms = findViewById(R.id.tv_get_sms);
        tv_voice_code = findViewById(R.id.tv_voice_code);
        user_login_btn = findViewById(R.id.user_login_btn);
        wechat_layout = findViewById(R.id.wechat_layout);
        qq_layout = findViewById(R.id.qq_layout);
        tv_contact_service = findViewById(R.id.tv_contact_service);
        findViewById(R.id.line).setVisibility(View.GONE);
        user_top_view_back.setOnClickListener(this);
        user_login_btn.setOnClickListener(this);
        tv_get_sms.setOnClickListener(this);
        tv_voice_code.setOnClickListener(this);
        wechat_layout.setOnClickListener(this);
        qq_layout.setOnClickListener(this);
        tv_contact_service.setOnClickListener(this);
        mobile = getIntent().getStringExtra(MOBILE);
        if (!TextUtils.isEmpty(mobile)) {
            user_login_phone.setText(mobile);
            user_login_phone.setSelection(mobile.length());
        }
        user_login_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mobile = s.toString().trim();
                setBtnStatus();
            }
        });

        ed_sms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                smsCode = s.toString().trim();
                setBtnStatus();
            }
        });
        setBtnStatus();
    }


    private void setBtnStatus() {
        if (!TextUtils.isEmpty(mobile) && 11 == mobile.length() && !TextUtils.isEmpty(smsCode)) {
            user_login_btn.setEnabled(true);
            user_login_btn.setBackgroundResource(R.drawable.onekey_login_bg);
        } else {
            user_login_btn.setEnabled(false);
            user_login_btn.setBackgroundResource(R.drawable.onekey_login_default_bg);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_get_sms:
                if (fastClick()) {
                    if (!TextUtils.isEmpty(mobile) && 11 == mobile.length()) {
                        newUserModel.getSmsCode(0, mobile, 5, 1, this);//找回密码获取短信验证码
                    } else {
                        ToastUtil.toastShow(this, getResources().getString(R.string.user_errorphone_notice));
                    }
                }
                break;
            case R.id.tv_voice_code:
                if (fastClick()) {
                    if (!TextUtils.isEmpty(mobile) && 11 == mobile.length()) {
                        newUserModel.getSmsCode(0, mobile, 5, 2, this);//获取语音验证码
                    } else {
                        ToastUtil.toastShow(this, getResources().getString(R.string.user_errorphone_notice));
                    }
                }
                break;
            case R.id.user_login_btn:
                //进行短信验证码的登录操作
                newUserModel.getAuthToken(2, mobile, smsCode, "3", true, this);
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
            case R.id.tv_contact_service:
                ConfigUtils.jumpContactService(UserIdentityLoginActivity.this);
                break;
        }
    }


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:  //获取验证码
                if (!TextUtils.isEmpty(result)) {
                    //提示语音验证码获取成功
                    initTimeCount();
                    try {
                        SendCodeEntity sendCodeEntity = GsonUtils.gsonToBean(result, SendCodeEntity.class);
                        ToastUtil.toastShow(UserIdentityLoginActivity.this, sendCodeEntity.getContent().getNotice());
                    } catch (Exception e) {
                        ToastUtil.toastShow(UserIdentityLoginActivity.this, getResources().getString(R.string.user_hung_up));
                    }
                } else {
                    //显示发送短信到指定号码
                }
                break;
            case 2:  //获取用户信息成功，到主页
                if (!TextUtils.isEmpty(result)) {
                    newUserModel.getUserInformation(3, true, this);
                }
                break;
            case 3:
                if (!TextUtils.isEmpty(result)) {
                    userInitImData(UserIdentityLoginActivity.this, shared);
                    TokenModel tokenModel = new TokenModel(UserIdentityLoginActivity.this);
                    tokenModel.getToken(5, 6, true, this);
                }
                break;
            case 5:
                cancelTimeCount();
                editor.putBoolean(UserAppConst.Colour_user_login, true);
                editor.commit();
                ToastUtil.toastShow(this, getResources().getString(R.string.user_login_success));
                Message msg = new Message();
                msg.what = UserMessageConstant.SIGN_IN_SUCCESS;//登录成功之后，刷新各种数据
                EventBus.getDefault().post(msg);
                finish();
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
                            newUserModel.getAuthToken(2, loginMobile, openCode, String.valueOf(loginType), true, UserIdentityLoginActivity.this);
                        } else {
                            Intent intent = new Intent(UserIdentityLoginActivity.this, UserThridRegisterActivity.class);
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

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    private MyTimeCount myTimeCount = null;


    /***初始化计数器**/
    private void initTimeCount() {
        cancelTimeCount();
        tv_get_sms.setClickable(false);
        tv_get_sms.setTextColor(getResources().getColor(R.color.color_b5b5b5));
        myTimeCount = new MyTimeCount(60000, 1000);
        myTimeCount.start();
    }

    private void cancelTimeCount() {
        if (myTimeCount != null) {
            myTimeCount.cancel();
            myTimeCount = null;
        }
    }

    /**
     * 定义一个倒计时的内部类
     */
    class MyTimeCount extends CountDownTimer {
        public MyTimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            tv_get_sms.setText(getResources().getString(R.string.user_again_getcode));
            tv_get_sms.setTextColor(getResources().getColor(R.color.color_329dfa));
            tv_get_sms.setClickable(true);
            tv_get_sms.requestFocus();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            long currentSecond = millisUntilFinished / UserAppConst.INTERVAL;
            tv_get_sms.setText(getResources().getString(R.string.user_already_send) + "(" + currentSecond + "S)");
            if (currentSecond <= 20) {
                tv_voice_code.setVisibility(View.VISIBLE);
            } else {
                tv_voice_code.setVisibility(View.GONE);
            }
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newUserModel.authRegister(7, loginSource, openCode, UserIdentityLoginActivity.this);
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                ToastUtil.toastShow(UserIdentityLoginActivity.this, "授权失败:" + throwable.getMessage());
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
                newUserModel.authRegister(7, loginSource, openCode, UserIdentityLoginActivity.this);
                break;
        }
        return false;
    }

    protected boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.SIGN_IN_SUCCESS:
                finish();
                break;
        }
    }


}

