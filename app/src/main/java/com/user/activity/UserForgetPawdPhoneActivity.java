package com.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.activity.WebViewActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.ClearEditText;
import com.nohttp.utils.GsonUtils;
import com.tendcloud.tenddata.TCAgent;
import com.user.UserAppConst;
import com.user.entity.ChangeMobileEntity;
import com.user.entity.SendCodeEntity;
import com.user.model.NewUserModel;

import java.util.HashMap;
import java.util.Map;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.user.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/3/1 14:57
 * @change
 * @chang time
 * @class describe  忘记密码，用户输入手机号码和获取验证码的页面
 */

public class UserForgetPawdPhoneActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ClearEditText user_forget_phone;
    private Button btn_next;
    private String mobile;
    private String code;
    private EditText ed_sms;
    private TextView tv_get_sms;
    private TextView tv_voice_code;
    private TextView tv_change_mobile;
    private NewUserModel newUserModel;
    private String enterMobile;
    private boolean isShowLoading = false;
    private String changeMobileUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetphone_layout);
        newUserModel = new NewUserModel(this);
        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimeCount();
    }

    private void initView() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        user_forget_phone = (ClearEditText) findViewById(R.id.user_forget_phone);
        tv_voice_code = (TextView) findViewById(R.id.tv_voice_code);
        ed_sms = (EditText) findViewById(R.id.ed_sms);
        tv_get_sms = (TextView) findViewById(R.id.tv_get_sms);
        btn_next = (Button) findViewById(R.id.btn_next);
        tv_change_mobile = (TextView) findViewById(R.id.tv_change_mobile);
        findViewById(R.id.line).setVisibility(View.GONE);
        user_top_view_back.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        tv_get_sms.setOnClickListener(this);
        tv_voice_code.setOnClickListener(this);
        tv_change_mobile.setOnClickListener(this);
        mobile = getIntent().getStringExtra(UserRegisterAndLoginActivity.MOBILE);
        enterMobile = mobile;
        user_forget_phone.setText(mobile);
        user_forget_phone.setSelection(mobile.length());
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, user_top_view_back, user_top_view_title);
        changeBtnStatus();
        user_forget_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mobile = s.toString().trim();
                changeBtnStatus();
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
                changeBtnStatus();
            }
        });
        newUserModel.getChangeMobileEnter(3, mobile,1, isShowLoading, UserForgetPawdPhoneActivity.this);
    }

    private void changeBtnStatus() {
        if (!TextUtils.isEmpty(mobile) && 11 == mobile.length() && !TextUtils.isEmpty(code)) {
            btn_next.setEnabled(true);
            btn_next.setBackgroundResource(R.drawable.onekey_login_bg);
        } else {
            btn_next.setEnabled(false);
            btn_next.setBackgroundResource(R.drawable.onekey_login_default_bg);
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
                    if (mobile.length() < 11) {
                        ToastUtil.toastShow(this, getResources().getString(R.string.user_errorphone_notice));
                        user_forget_phone.requestFocus();
                    } else {
                        Map<String, String> stringMap = new HashMap<>();
                        stringMap.put("mobile", mobile);
                        TCAgent.onEvent(getApplicationContext(), "202006", "", stringMap);
                        newUserModel.getSmsCode(0, mobile, 1, 1, this);//找回密码获取短信验证码
                    }
                }
                break;
            case R.id.tv_voice_code:
                if (fastClick()) {
                    if (mobile.length() < 11) {
                        ToastUtil.toastShow(this, getResources().getString(R.string.user_errorphone_notice));
                        user_forget_phone.requestFocus();
                    } else {
                        Map<String, String> stringMap = new HashMap<>();
                        stringMap.put("mobile", mobile);
                        TCAgent.onEvent(getApplicationContext(), "202008", "", stringMap);
                        newUserModel.getSmsCode(1, mobile, 1, 2, this);//找回密码获取语音验证码
                    }
                }
                break;

            case R.id.btn_next:
                if (fastClick()) {
                    newUserModel.checkSMSCode(2, mobile, code, "resetPassword", this);
                }
                break;
            case R.id.tv_change_mobile://跳转到h5更换手机号的页面
                if (mobile.equals(enterMobile)) {
                    Intent webIntent = new Intent(UserForgetPawdPhoneActivity.this, WebViewActivity.class);
                    webIntent.putExtra(WebViewActivity.WEBURL, changeMobileUrl);
                    webIntent.putExtra(WebViewActivity.WEBTITLE, "");
                    startActivity(webIntent);
                    finish();
                } else {
                    isShowLoading = true;
                    newUserModel.getChangeMobileEnter(3, mobile,1, isShowLoading, UserForgetPawdPhoneActivity.this);
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
                        ToastUtil.toastShow(UserForgetPawdPhoneActivity.this, sendCodeEntity.getContent().getNotice());
                    } catch (Exception e) {
                        ToastUtil.toastShow(UserForgetPawdPhoneActivity.this, getResources().getString(R.string.user_code_send));
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
                        ToastUtil.toastShow(UserForgetPawdPhoneActivity.this, sendCodeEntity.getContent().getNotice());
                    } catch (Exception e) {
                        ToastUtil.toastShow(UserForgetPawdPhoneActivity.this, getResources().getString(R.string.user_hung_up));
                    }
                } else {
                    //显示发送短信到指定号码
                }
                break;
            case 2:
                Intent intent = new Intent(UserForgetPawdPhoneActivity.this, UserForgetPasswordActivity.class);
                intent.putExtra(UserRegisterAndLoginActivity.MOBILE, mobile);
                intent.putExtra(UserRegisterAndLoginActivity.SMSCODE, code);
                startActivity(intent);
                break;
            case 3:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        ChangeMobileEntity changeMobileEntity = GsonUtils.gsonToBean(result, ChangeMobileEntity.class);
                        ChangeMobileEntity.ContentBean contentBean = changeMobileEntity.getContent();
                        if (null != contentBean) {
                            if (!isShowLoading) {
                                if (contentBean.getShow() == 1 && !TextUtils.isEmpty(contentBean.getUrl())) {
                                    changeMobileUrl = contentBean.getUrl();
                                    tv_change_mobile.setVisibility(View.VISIBLE);
                                } else {
                                    tv_change_mobile.setVisibility(View.GONE);
                                }
                            } else {
                                if (!TextUtils.isEmpty(contentBean.getUrl())) {
                                    changeMobileUrl = contentBean.getUrl();
                                }
                                Intent webIntent = new Intent(UserForgetPawdPhoneActivity.this, WebViewActivity.class);
                                webIntent.putExtra(WebViewActivity.WEBURL, changeMobileUrl);
                                webIntent.putExtra(WebViewActivity.WEBTITLE, "");
                                startActivity(webIntent);
                                finish();
                            }
                        }
                    } catch (Exception e) {

                    }
                } else {
                    if (isShowLoading) {
                        Intent webIntent = new Intent(UserForgetPawdPhoneActivity.this, WebViewActivity.class);
                        webIntent.putExtra(WebViewActivity.WEBURL, changeMobileUrl);
                        webIntent.putExtra(WebViewActivity.WEBTITLE, "");
                        startActivity(webIntent);
                        finish();
                    }
                }
                break;
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
            tv_get_sms.setText(getResources().getString(R.string.user_already_send)+"(" + currentSecond + "S)");
            if (currentSecond <= 20) {
                tv_voice_code.setVisibility(View.VISIBLE);
            } else {
                tv_voice_code.setVisibility(View.GONE);
            }
        }
    }
}
