package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.external.gridpasswordview.GridPasswordView;
import com.external.gridpasswordview.PasswordType;

import cn.net.cyberway.R;

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
    private String oldPawd = "";

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
        oldPawd = intent.getStringExtra(ChangePawdThreeStepActivity.OLDPAYPAWD);
        gridPasswordView_cqb.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                Intent intent = new Intent(ChangePawdTwoStepActivity.this, ChangePawdThreeStepActivity.class);
                intent.putExtra(ChangePawdThreeStepActivity.NEWPAYPAWD, psw);
                intent.putExtra(PAWDTYPE, passwordType);
                intent.putExtra(ChangePawdThreeStepActivity.OLDPAYPAWD, oldPawd);
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
