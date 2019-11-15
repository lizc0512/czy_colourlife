package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;

import cn.net.cyberway.R;

public class ChangePawdStyleActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mTitle;
    private RelativeLayout change_paypawd_layout;
    private RelativeLayout forget_paypawd_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_style_layout);
        change_paypawd_layout = findViewById(R.id.change_paypawd_layout);
        forget_paypawd_layout = findViewById(R.id.forget_paypawd_layout);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        mBack.setOnClickListener(this);
        mTitle.setText("支付密码");
        change_paypawd_layout.setOnClickListener(this::onClick);
        forget_paypawd_layout.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:

                finish();
                break;
            case R.id.change_paypawd_layout:
                Intent intent = new Intent(ChangePawdStyleActivity.this, ChangePawdOneStepActivity.class);
                startActivity(intent);
                break;
            case R.id.forget_paypawd_layout:
                Intent forget_intent = new Intent(ChangePawdStyleActivity.this, ForgetPayPawdActivity.class);
                startActivity(forget_intent);
                break;
        }

    }
}
