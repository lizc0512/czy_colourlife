package com.customerInfo.activity;

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

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseFragmentActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.gesturepwd.activity.CreateGesturePasswordActivity;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.entity.SendCodeEntity;
import com.user.model.NewUserModel;

import cn.net.cyberway.R;

/**
 * Created by liusw on 2016/1/14.
 */
public class CustomerCheckCodeActivity extends BaseFragmentActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czyTitleLayout;
    private ImageView mBack;
    private TextView mTitle;
    private TextView user_check_phone;
    private EditText ed_sms;
    private TextView tv_get_sms;
    private TextView tv_voice_code;
    private Button btn_change_pawd;
    private NewUserModel newUserModel;
    private String mobile;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pawd_layout);
        czyTitleLayout = findViewById(R.id.czy_title_layout);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        user_check_phone = findViewById(R.id.user_check_phone);
        ed_sms = findViewById(R.id.ed_sms);
        tv_get_sms = findViewById(R.id.tv_get_sms);
        tv_voice_code = findViewById(R.id.tv_voice_code);
        btn_change_pawd = findViewById(R.id.btn_change_pawd);
        mBack.setOnClickListener(this);
        tv_get_sms.setOnClickListener(this);
        tv_voice_code.setOnClickListener(this);
        btn_change_pawd.setOnClickListener(this);
        mTitle.setText("手机验证码验证");
        mobile = shared.getString(UserAppConst.Colour_login_mobile, "");
        user_check_phone.setText(mobile);
        newUserModel = new NewUserModel(this);
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
                    btn_change_pawd.setEnabled(true);
                    btn_change_pawd.setBackgroundResource(R.drawable.rect_round_blue);
                } else {
                    btn_change_pawd.setEnabled(false);
                    btn_change_pawd.setBackgroundResource(R.drawable.rect_round_gray);
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
                    newUserModel.getSmsCode(0, mobile, 6, 1, this);
                }
                break;
            case R.id.tv_voice_code:
                if (fastClick()) {
                    newUserModel.getSmsCode(1, mobile, 6, 2, this);//找回密码获取语音验证码
                }
                break;
            case R.id.btn_change_pawd:
                if (fastClick()) {
                    newUserModel.checkSMSCode(2, mobile, code, "gestureSet", this);
                }
                break;
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

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    //提示语音验证码获取成功
                    initTimeCount();
                    try {
                        SendCodeEntity sendCodeEntity = GsonUtils.gsonToBean(result, SendCodeEntity.class);
                        ToastUtil.toastShow(CustomerCheckCodeActivity.this, sendCodeEntity.getContent().getNotice());
                    } catch (Exception e) {
                        ToastUtil.toastShow(CustomerCheckCodeActivity.this, getResources().getString(R.string.user_hung_up));
                    }
                } else {
                    //显示发送短信到指定号码
                }
                break;
            case 2:
                cancelTimeCount();
                Intent intent = new Intent();
                intent.setClass(CustomerCheckCodeActivity.this, CreateGesturePasswordActivity.class);
                intent.putExtra("isChangePwd", false);// 是修改手势密码
                startActivity(intent);
                finish();
                break;
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
