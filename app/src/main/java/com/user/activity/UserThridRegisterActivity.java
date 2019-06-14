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
import com.BeeFramework.model.NewHttpResponse;
import com.external.eventbus.EventBus;
import com.jpush.Constant;
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

import static cn.net.cyberway.utils.IMFriendDataUtils.userInitImData;

/**
 * @name ${yuansk}
 * @class name：com.user.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/3/2 14:34
 * @change
 * @chang time
 * @class describe 第三方注册的
 */

public class UserThridRegisterActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String OPENCODE = "opencode";
    public static final String GENDER = "gender";
    public static final String NICKNAME = "nickname";
    public static final String PORTRAIT = "portrait";
    public static final String SOURCE = "source";
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private EditText user_register_phone;
    private EditText ed_sms;
    private TextView tv_get_sms;
    private TextView tv_voice_code;
    private TextView user_get_login;
    private Button btn_next_step;
    private NewUserModel newUserModel;
    private String code = "";
    private String mobile;
    private String openCode;
    private String source;
    private String portrait;
    private String nick_name;
    private String gender;
    private TokenModel mTokenModel;
    private LinearLayout thrid_register_layout;
    private LinearLayout bind_success_layout;
    private String loginType = "4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thridregister_layout);
        initView();
        Intent intent = getIntent();
        openCode = intent.getStringExtra(OPENCODE);
        gender = intent.getStringExtra(GENDER);
        nick_name = intent.getStringExtra(NICKNAME);
        portrait = intent.getStringExtra(PORTRAIT);
        source = intent.getStringExtra(SOURCE);
        if ("qq".equals(source)) {
            loginType = "5";
        } else if ("wechat".equals(source)) {
            loginType = "4";
        } else {
            loginType = "6";
        }
        newUserModel = new NewUserModel(this);
        mTokenModel = new TokenModel(this);
    }

    private void initView() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        user_register_phone = (EditText) findViewById(R.id.user_register_phone);
        bind_success_layout = (LinearLayout) findViewById(R.id.bind_success_layout);
        thrid_register_layout = (LinearLayout) findViewById(R.id.thrid_register_layout);
        ed_sms = (EditText) findViewById(R.id.ed_sms);
        tv_get_sms = (TextView) findViewById(R.id.tv_get_sms);
        tv_voice_code = (TextView) findViewById(R.id.tv_voice_code);
        user_get_login = (TextView) findViewById(R.id.user_get_login);
        btn_next_step = (Button) findViewById(R.id.btn_next_step);
        findViewById(R.id.line).setVisibility(View.GONE);
        user_top_view_back.setOnClickListener(this);
        tv_get_sms.setOnClickListener(this);
        tv_voice_code.setOnClickListener(this);
        user_get_login.setOnClickListener(this);
        btn_next_step.setOnClickListener(this);
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, user_top_view_back, user_top_view_title);
        user_register_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mobile = s.toString().trim();
                changeRegisterBtnStatus();
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
                code = s.toString().trim();
                changeRegisterBtnStatus();
            }
        });
        thrid_register_layout.setVisibility(View.VISIBLE);
        bind_success_layout.setVisibility(View.GONE);
        changeRegisterBtnStatus();
    }

    private void changeRegisterBtnStatus() {
        if (!TextUtils.isEmpty(mobile) && 11 == mobile.length() && !TextUtils.isEmpty(code)) {
            btn_next_step.setEnabled(true);
            btn_next_step.setBackgroundResource(R.drawable.rect_round_blue);
        } else {
            btn_next_step.setEnabled(false);
            btn_next_step.setBackgroundResource(R.drawable.rect_round_gray);
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
                    if (TextUtils.isEmpty(mobile) || mobile.length() < 11) {
                        ToastUtil.toastShow(this, getResources().getString(R.string.user_errorphone_notice));
                        user_register_phone.requestFocus();
                    } else {
                        Map<String, String> stringMap = new HashMap<>();
                        stringMap.put("mobile", mobile);
                        TCAgent.onEvent(getApplicationContext(), "202006", "", stringMap);
                        newUserModel.getSmsCode(0, mobile, 0, 1, this);//找回密码获取短信验证码
                    }
                }
                break;
            case R.id.tv_voice_code:
                if (fastClick()) {
                    if (TextUtils.isEmpty(mobile) || mobile.length() < 11) {
                        ToastUtil.toastShow(this, getResources().getString(R.string.user_errorphone_notice));
                        user_register_phone.requestFocus();
                    } else {
                        Map<String, String> stringMap = new HashMap<>();
                        stringMap.put("mobile", mobile);
                        TCAgent.onEvent(getApplicationContext(), "202008", "", stringMap);
                        newUserModel.getSmsCode(1, mobile, 0, 2, this);//找回密码获取语音验证码
                    }
                }
                break;
            case R.id.btn_next_step:
                if (fastClick()) {
                    newUserModel.thirdRegister(2, mobile, code, source, openCode, portrait, gender, nick_name, this);
                }
                break;
            case R.id.user_get_login:
                finish();
                break;
            case R.id.user_login_btn:
                if (fastClick()) {
                    newUserModel.getAuthToken(4, mobile, openCode, loginType, true, this);
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
                        ToastUtil.toastShow(UserThridRegisterActivity.this, sendCodeEntity.getContent().getNotice());
                    } catch (Exception e) {
                        ToastUtil.toastShow(UserThridRegisterActivity.this, getResources().getString(R.string.user_code_send));
                    }
                } else {
                    //显示获取语音验证码的按钮
                    tv_voice_code.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    //提示语音验证码获取成功
                    initTimeCount();
                    try {
                        SendCodeEntity sendCodeEntity = GsonUtils.gsonToBean(result, SendCodeEntity.class);
                        ToastUtil.toastShow(UserThridRegisterActivity.this, sendCodeEntity.getContent().getNotice());
                    } catch (Exception e) {
                        ToastUtil.toastShow(UserThridRegisterActivity.this, getResources().getString(R.string.user_hung_up));
                    }
                } else {
                    //显示发送短信到指定号码
                }
                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {
                    setSucceedView();
                }
                break;
            case 4:  //获取用户信息成功，到主页
                if (!TextUtils.isEmpty(result)) {
                    newUserModel.getUserInformation(7, true, this);
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
                Map<String, String> mainParams = new HashMap<String, String>();
                mainParams.put("customer_id", shared.getInt(UserAppConst.Colour_User_id, 0) + "");
                mainParams.put("mobile", mobile);
                TCAgent.onEvent(getApplicationContext(), "200007", "", mainParams);
                ToastUtil.toastShow(this, getResources().getString(R.string.user_login_success));
                Message msg = new Message();
                msg.what = UserMessageConstant.SIGN_IN_SUCCESS;//登录成功之后，刷新各种数据
                EventBus.getDefault().post(msg);
                sendNotification();
                finish();
                break;
            case 7:
                if (!TextUtils.isEmpty(result)) {
                    userInitImData(UserThridRegisterActivity.this, shared);
                    mTokenModel.getToken(5, Integer.valueOf(loginType), true, this);
                }
                break;
        }
    }

    /**
     * 绑定成功现实布局
     */
    protected void setSucceedView() {
        thrid_register_layout.setVisibility(View.GONE);
        bind_success_layout.setVisibility(View.VISIBLE);
        String mPhoneStr = mobile.replace(mobile.substring(3, 7), "****");
        TextView tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_phone.setText(mPhoneStr);
        TextView title = (TextView) findViewById(R.id.user_top_view_title);
        title.setText(getResources().getString(R.string.user_bind_success));
        ImageView back = (ImageView) findViewById(R.id.user_top_view_back);
        back.setVisibility(View.INVISIBLE);
        Button user_login_btn = (Button) findViewById(R.id.user_login_btn);
        user_login_btn.setOnClickListener(this);
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
}
