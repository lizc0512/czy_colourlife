package com.user.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.ClearEditText;
import com.external.eventbus.EventBus;
import com.mob.MobSDK;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.entity.SendCodeEntity;
import com.user.model.NewUserModel;
import com.user.model.TokenModel;

import cn.net.cyberway.R;
import cn.net.cyberway.home.model.NewHomeModel;

import static cn.net.cyberway.utils.IMFriendDataUtils.userInitImData;
import static com.user.activity.UserRegisterAndLoginActivity.MOBILE;

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
public class UserIdentityLoginActivity extends BaseActivity implements OnClickListener, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ClearEditText user_login_phone;
    private EditText ed_sms;
    private TextView tv_get_sms;
    private TextView tv_voice_code;
    private Button user_login_btn;
    private NewUserModel newUserModel;
    private String mobile;
    private String smsCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identitycode_login_layout);
        MobSDK.init(getApplicationContext());
        initView();
        newUserModel = new NewUserModel(this);
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        user_login_phone = findViewById(R.id.user_login_phone);
        ed_sms = findViewById(R.id.ed_sms);
        tv_get_sms = findViewById(R.id.tv_get_sms);
        tv_voice_code = findViewById(R.id.tv_voice_code);
        user_login_btn = findViewById(R.id.user_login_btn);
        user_top_view_title.setText(getResources().getString(R.string.title_login_verify));
        user_top_view_back.setOnClickListener(this);
        user_login_btn.setOnClickListener(this);
        tv_get_sms.setOnClickListener(this);
        tv_voice_code.setOnClickListener(this);
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
            user_login_btn.setBackgroundResource(R.drawable.rect_round_blue);
        } else {
            user_login_btn.setEnabled(false);
            user_login_btn.setBackgroundResource(R.drawable.rect_round_gray);
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

