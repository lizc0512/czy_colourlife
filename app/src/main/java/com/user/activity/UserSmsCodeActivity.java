package com.user.activity;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.GsonUtils;
import com.tendcloud.tenddata.TCAgent;
import com.user.UserAppConst;
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
 * @time 2018/2/27 15:18
 * @change
 * @chang time
 * @class describe 三个月未登录获取验证码的接口
 */

public class UserSmsCodeActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
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
    private String code;
    private int smsType = 0;
    private String workType = "verifyLogin";
    private TextView tv_sub_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        Intent intent = getIntent();
        mobile = intent.getStringExtra(UserRegisterAndLoginActivity.MOBILE);
        smsType = intent.getIntExtra(UserRegisterAndLoginActivity.SMSTYPE, 0);
        initView();
        newUserModel = new NewUserModel(this);
        if (smsType == 4) {
            workType = "voiceLogin"; //语音验证的类型
            tv_get_sms.setText(getResources().getString(R.string.user_get_voice));
            newUserModel.getSmsCode(0, mobile, smsType, 2, this);
        } else {
            workType = "verifyLogin";
            newUserModel.getSmsCode(0, mobile, smsType, 1, this);
        }
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
        tv_sub_title = findViewById(R.id.tv_sub_title);
        user_register_protocol = (LinearLayout) findViewById(R.id.user_register_protocol);
        user_register_protocol.setVisibility(View.GONE);
        findViewById(R.id.line).setVisibility(View.GONE);
        tv_sub_title.setText(getResources().getString(R.string.title_safty_verify));
        btn_user_register.setText(getResources().getString(R.string.user_finish));
        tv_register_number.setText(mobile);
        user_top_view_back.setOnClickListener(this);
        tv_get_sms.setOnClickListener(this);
        tv_voice_code.setOnClickListener(this);
        btn_user_register.setOnClickListener(this);
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
                    if (smsType == 4) {
                        newUserModel.getSmsCode(0, mobile, smsType, 2, this);//找回密码获取短信验证码
                    } else {
                        newUserModel.getSmsCode(0, mobile, smsType, 1, this);//找回密码获取短信验证码
                    }
                }
                break;
            case R.id.tv_voice_code:
                if (fastClick()) {
                    Map<String, String> stringMap = new HashMap<>();
                    stringMap.put("mobile", mobile);
                    TCAgent.onEvent(getApplicationContext(), "202008", "", stringMap);
                    newUserModel.getSmsCode(1, mobile, smsType, 2, this);//找回密码获取语音验证码
                }
                break;
            case R.id.btn_user_register:
                if (fastClick()) {
                    newUserModel.checkSMSCode(2, mobile, code, workType, this);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimeCount();
    }

    @Override
    public void OnHttpResponse(int what, String result) {

        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    //提示短语验证码发送成功
                    ed_sms.setText("");
                    if (smsType != 4) {
                        initTimeCount();
                    }
                    try {
                        SendCodeEntity sendCodeEntity = GsonUtils.gsonToBean(result, SendCodeEntity.class);
                        ToastUtil.toastShow(UserSmsCodeActivity.this, sendCodeEntity.getContent().getNotice());
                    } catch (Exception e) {
                        if (smsType == 4) {
                            ToastUtil.toastShow(UserSmsCodeActivity.this, getResources().getString(R.string.user_hung_up));
                        } else {
                            ToastUtil.toastShow(UserSmsCodeActivity.this, getResources().getString(R.string.user_code_send));
                        }
                    }
                } else {
                    //显示获取语音验证码的按钮
                    if (smsType == 3) {
                        tv_voice_code.setVisibility(View.GONE);
                    } else {
                        tv_voice_code.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    //提示语音验证码获取成功
                    initTimeCount();
                    try {
                        SendCodeEntity sendCodeEntity = GsonUtils.gsonToBean(result, SendCodeEntity.class);
                        ToastUtil.toastShow(UserSmsCodeActivity.this, sendCodeEntity.getContent().getNotice());
                    } catch (Exception e) {
                        ToastUtil.toastShow(UserSmsCodeActivity.this, getResources().getString(R.string.user_hung_up));
                    }
                } else {
                    //显示发送短信到指定号码
                }
                break;
            case 2:
                Intent intent = new Intent();
                intent.putExtra(UserRegisterAndLoginActivity.SMSCODE, code);
                setResult(200, intent);
                finish();
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
            tv_get_sms.setText(getResources().getString(R.string.user_already_send) + "(" + currentSecond + "S)");
            if (smsType != 4) {
                if (currentSecond <= 20) {
                    tv_voice_code.setVisibility(View.VISIBLE);
                } else {
                    tv_voice_code.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 跳转到本地发送短信
     */
    private void sendSMS() {
        Uri smsToUri = Uri.parse("smsto:1000456");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", mobile + "+czy");
        startActivity(intent);
    }

}
