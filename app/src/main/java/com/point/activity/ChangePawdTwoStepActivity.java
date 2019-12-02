package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.external.eventbus.EventBus;
import com.external.gridpasswordview.GridPasswordView;
import com.external.gridpasswordview.PasswordType;
import com.user.UserMessageConstant;

import cn.net.cyberway.R;

import static com.point.activity.ChangePawdThreeStepActivity.PAWDTOEKN;
import static com.point.activity.ChangePawdThreeStepActivity.PAWDTYPE;

/***
 * 修改密码第二步
 */
public class ChangePawdTwoStepActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mTitle;
    private TextView tv_tips_content;
    private GridPasswordView gridPasswordView_cqb;
    private int passwordType = 0;
    private String passordToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        setContentView(R.layout.activity_password_change_layout);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        tv_tips_content = findViewById(R.id.tv_tips_content);
        gridPasswordView_cqb = findViewById(R.id.grid_pawd_view);
        gridPasswordView_cqb.setPasswordType(PasswordType.NUMBER);
        Intent intent = getIntent();
        passwordType = intent.getIntExtra(PAWDTYPE, 0);
        passordToken = intent.getStringExtra(PAWDTOEKN);
        gridPasswordView_cqb.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                Intent intent = new Intent(ChangePawdTwoStepActivity.this, ChangePawdThreeStepActivity.class);
                intent.putExtra(ChangePawdThreeStepActivity.NEWPAYPAWD, psw);
                intent.putExtra(PAWDTOEKN, passordToken);
                intent.putExtra(PAWDTYPE, passwordType);
                startActivity(intent);
            }
        });
        mBack.setOnClickListener(this);
        switch (passwordType) {
            case 1:
                mTitle.setText("修改支付密码");
                tv_tips_content.setText("请输入新支付密码");
                break;
            case 2:
                mTitle.setText("重置支付密码");
                tv_tips_content.setText("请设置支付密码");
                break;
            default:
                mTitle.setText("设置支付密码");
                tv_tips_content.setText("请输入支付密码");
                break;
        }
        if (!EventBus.getDefault().isregister(ChangePawdTwoStepActivity.this)) {
            EventBus.getDefault().register(ChangePawdTwoStepActivity.this);
        }
    }
    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.POINT_CHANGE_PAYPAWD:
            case UserMessageConstant.POINT_SET_PAYPAWD:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(ChangePawdTwoStepActivity.this)) {
            EventBus.getDefault().unregister(ChangePawdTwoStepActivity.this);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }

    }
}
