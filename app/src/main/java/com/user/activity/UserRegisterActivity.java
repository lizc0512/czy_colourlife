package com.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.activity.WebViewActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.external.eventbus.EventBus;
import com.jpush.Constant;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GsonUtils;
import com.tendcloud.tenddata.TCAgent;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.entity.SendCodeEntity;
import com.user.model.NewUserModel;
import com.user.model.TokenModel;

import java.util.HashMap;
import java.util.Map;

import cn.net.cyberway.R;
import cn.net.cyberway.home.model.NewHomeModel;

import static cn.net.cyberway.utils.IMFriendDataUtils.userInitImData;

/**
 * @name ${yuansk}
 * @class name：com.user.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/2/27 9:48
 * @change
 * @chang time
 * @class describe  4.1.0新的用户注册页面
 */

public class UserRegisterActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView tv_register_number;
    private EditText ed_sms;
    private TextView tv_get_sms;
    private TextView tv_voice_code;
    private Button btn_user_register;
    private LinearLayout user_register_protocol;
    private NewUserModel newUserModel;
    private String mobile;
    private String password;
    private String code;
    private TokenModel mTokenModel;
    private int registerFailNumber = 0;//注册失败的次数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        Intent intent = getIntent();
        mobile = intent.getStringExtra(UserRegisterAndLoginActivity.MOBILE);
        password = intent.getStringExtra(UserRegisterAndLoginActivity.PASSWORD);
        initView();
        tv_register_number.setText(mobile);
        newUserModel = new NewUserModel(this);
        mTokenModel = new TokenModel(this);
        newUserModel.getSmsCode(0, mobile, 0, 1, this);
    }


    private void initView() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_register_number = (TextView) findViewById(R.id.tv_register_number);
        ed_sms = (EditText) findViewById(R.id.ed_sms);
        tv_get_sms = (TextView) findViewById(R.id.tv_get_sms);
        tv_voice_code = (TextView) findViewById(R.id.tv_voice_code);
        btn_user_register = (Button) findViewById(R.id.btn_user_register);
        user_register_protocol = (LinearLayout) findViewById(R.id.user_register_protocol);
        user_top_view_title.setText("注册登录");
        user_top_view_back.setOnClickListener(this);
        tv_get_sms.setOnClickListener(this);
        tv_voice_code.setOnClickListener(this);
        btn_user_register.setOnClickListener(this);
        user_register_protocol.setOnClickListener(this);
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, user_top_view_back, user_top_view_title);
        ed_sms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                code = s.toString().trim();
                if (!TextUtils.isEmpty(code)) {
                    btn_user_register.setEnabled(true);
                    btn_user_register.setBackgroundResource(R.drawable.rect_round_blue);
                } else {
                    btn_user_register.setEnabled(false);
                    btn_user_register.setBackgroundResource(R.drawable.rect_round_gray);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_get_sms:
                if (fastClick()) {
                    Map<String, String> stringMap = new HashMap<>();
                    stringMap.put("mobile", mobile);
                    TCAgent.onEvent(getApplicationContext(), "202006", "", stringMap);
                    newUserModel.getSmsCode(0, mobile, 0, 1, this);//找回密码获取短信验证码
                }
                break;
            case R.id.tv_voice_code:
                if (fastClick()) {
                    Map<String, String> stringMap = new HashMap<>();
                    stringMap.put("mobile", mobile);
                    TCAgent.onEvent(getApplicationContext(), "202008", "", stringMap);
                    newUserModel.getSmsCode(1, mobile, 0, 2, this);//找回密码获取语音验证码
                }
                break;
            case R.id.user_register_protocol:
                Intent webIntent = new Intent(this, WebViewActivity.class);
                webIntent.putExtra(WebViewActivity.WEBTITLE, "彩之云注册协议");
                webIntent.putExtra(WebViewActivity.WEBURL, "https://m.colourlife.com/xieyiApp");
                startActivity(webIntent);
                break;
            case R.id.btn_user_register: //注册
                if (fastClick()) {
                    newUserModel.userRegister(2, mobile, code, password, this);
                }
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    //提示短语验证码发送成功
                    ed_sms.setText("");
                    initTimeCount();
                    try {
                        SendCodeEntity sendCodeEntity = GsonUtils.gsonToBean(result, SendCodeEntity.class);
                        ToastUtil.toastShow(UserRegisterActivity.this, sendCodeEntity.getContent().getNotice());
                    } catch (Exception e) {
                        ToastUtil.toastShow(UserRegisterActivity.this, getResources().getString(R.string.user_code_send));
                    }
                } else {
                    tv_voice_code.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    //提示语音验证码获取成功
                    initTimeCount();
                    try {
                        SendCodeEntity sendCodeEntity = GsonUtils.gsonToBean(result, SendCodeEntity.class);
                        ToastUtil.toastShow(UserRegisterActivity.this, sendCodeEntity.getContent().getNotice());
                    } catch (Exception e) {
                        ToastUtil.toastShow(UserRegisterActivity.this, getResources().getString(R.string.user_hung_up));
                    }
                } else {
                    //显示发送短信到指定号码
                }
                break;
            case 2: //注册成功 获取access_token
                try {
                    if (!TextUtils.isEmpty(result)) {
                        BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                        int resultCode = baseContentEntity.getCode();
                        String message = baseContentEntity.getMessage();
                        switch (resultCode) {
                            case 0:
                                ToastUtil.toastShow(UserRegisterActivity.this, getResources().getString(R.string.user_register_success));
                                newUserModel.getAuthToken(4, mobile, password, "1", true, this);
                                break;
                            case 1:
                                ToastUtil.toastShow(UserRegisterActivity.this, message);
                                ed_sms.setText("");
                                break;
                            default:
                                ToastUtil.toastShow(UserRegisterActivity.this, message);
                                Message msg = new Message();
                                msg.what = UserMessageConstant.REGISTER_TYPE_FIAL;//注册失败的情况
                                msg.arg1 = resultCode;
                                EventBus.getDefault().post(msg);
                                finish();
                                break;
                        }
                    } else {
                        registerFailNumber++;
                        Map<String, String> stringMap = new HashMap<String, String>();
                        stringMap.put("mobile", mobile);
                        TCAgent.onEvent(getApplicationContext(), "200008", "", stringMap);
                        if (registerFailNumber == 2) {
                            finish();
                        }
                    }
                } catch (Exception e) {
                    ToastUtil.toastShow(UserRegisterActivity.this, getResources().getString(R.string.user_register_fail));
                }
                break;
            case 4:  //获取用户信息成功，到主页
                if (!TextUtils.isEmpty(result)) {
                    newUserModel.getUserInformation(8, true, this);
                } else {
                    Map<String, String> loginMap = new HashMap<String, String>();
                    loginMap.put("mobile", mobile);
                    TCAgent.onEvent(getApplicationContext(), "201006", "", loginMap);
                    Message msg = new Message();
                    msg.what = UserMessageConstant.SIGN_IN_FAIL;//登录成功之后，刷新各种数据
                    EventBus.getDefault().post(msg);
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
                sendNotification();
                finish();
                break;
            case 8:
                userInitImData(UserRegisterActivity.this, shared);
                mTokenModel.getToken(5, 1, true, this);
                break;
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimeCount();
    }

    private MyTimeCount myTimeCount = null;


    /***初始化计数器**/
    private void initTimeCount() {
        cancelTimeCount();
        tv_get_sms.setClickable(false);
        tv_get_sms.setTextColor(getResources().getColor(R.color.color_a3aaae));
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
            tv_get_sms.setTextColor(getResources().getColor(R.color.tv_blue_bg));
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
}
