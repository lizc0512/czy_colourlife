package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.ClearEditText;
import com.external.eventbus.EventBus;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.model.NewUserModel;

import cn.net.cyberway.R;

import static cn.net.cyberway.utils.ConfigUtils.jumpContactService;

/***
 * 忘记密码填写资料
 */
public class ForgetPayPawdActivity extends BaseActivity implements View.OnClickListener, TextWatcher, NewHttpResponse {
    private ImageView mBack;
    private TextView mTitle;
    private TextView tv_user_phone;
    private ClearEditText input_pawd_code;
    private TextView tv_get_code;
    private TextView tv_user_realname;
    private ClearEditText input_pawd_idcard;
    private Button btn_define;
    private TextView tv_contact_service;
    private MyTimeCount myTimeCount = null;
    private String idCardNumber;
    private String mobile;
    private String smsCode;
    private NewUserModel newUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forget_layout);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        tv_user_phone = findViewById(R.id.tv_user_phone);
        input_pawd_code = findViewById(R.id.input_pawd_code);
        tv_get_code = findViewById(R.id.tv_get_code);
        tv_user_realname = findViewById(R.id.tv_user_realname);
        input_pawd_idcard = findViewById(R.id.input_pawd_idcard);
        btn_define = findViewById(R.id.btn_define);
        btn_define.setEnabled(false);
        tv_contact_service = findViewById(R.id.tv_contact_service);
        mBack.setOnClickListener(this::onClick);
        tv_get_code.setOnClickListener(this::onClick);
        btn_define.setOnClickListener(this::onClick);
        tv_contact_service.setOnClickListener(this::onClick);
        mTitle.setText("支付密码");
        input_pawd_code.addTextChangedListener(this);
        input_pawd_idcard.addTextChangedListener(this);
        newUserModel=new NewUserModel(ForgetPayPawdActivity.this);
        if (!EventBus.getDefault().isregister(ForgetPayPawdActivity.this)) {
            EventBus.getDefault().register(ForgetPayPawdActivity.this);
        }
    }
    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.POINT_CHANGE_PAYPAWD:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(ForgetPayPawdActivity.this)) {
            EventBus.getDefault().unregister(ForgetPayPawdActivity.this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_get_code:
                if (fastClick()){
                    newUserModel.getSmsCode(0,mobile,7,1,ForgetPayPawdActivity.this);
                }
                break;
            case R.id.btn_define:
                Intent intent = new Intent(ForgetPayPawdActivity.this, ChangePawdTwoStepActivity.class);
                intent.putExtra(ChangePawdThreeStepActivity.PAWDTYPE, 2);
                startActivity(intent);
                break;
            case R.id.tv_contact_service:
                jumpContactService(ForgetPayPawdActivity.this);
                break;
        }

    }


    /***初始化计数器**/
    private void initTimeCount() {
        cancelTimeCount();
        tv_get_code.setClickable(false);
        tv_get_code.setTextColor(getResources().getColor(R.color.color_8d9299));
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        smsCode = input_pawd_code.getText().toString().trim();
        idCardNumber = input_pawd_idcard.getText().toString().trim();
        if (TextUtils.isEmpty(smsCode) || TextUtils.isEmpty(idCardNumber)) {
            btn_define.setEnabled(false);
            btn_define.setBackgroundResource(R.drawable.point_password_default_bg);
        } else {
            btn_define.setEnabled(true);
            btn_define.setBackgroundResource(R.drawable.point_password_click_bg);
        }

    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what){
            case 0:
                initTimeCount();
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
            tv_get_code.setText(getResources().getString(R.string.user_again_getcode));
            tv_get_code.setTextColor(getResources().getColor(R.color.color_329dfa));
            tv_get_code.setClickable(true);
            tv_get_code.requestFocus();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            long currentSecond = millisUntilFinished / UserAppConst.INTERVAL;
            tv_get_code.setText(getResources().getString(R.string.user_already_send) + "(" + currentSecond + "S)");
        }
    }
}
