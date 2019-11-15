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
/***
 * 修改密码第一步
 */
public class ChangePawdOneStepActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mTitle;
    private GridPasswordView gridPasswordView_cqb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        setContentView(R.layout.activity_password_change_layout);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        gridPasswordView_cqb = findViewById(R.id.grid_pawd_view);
        gridPasswordView_cqb.setPasswordType(PasswordType.NUMBER);
        gridPasswordView_cqb.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                Intent intent=new Intent(ChangePawdOneStepActivity.this,ChangePawdTwoStepActivity.class);
                intent.putExtra(ChangePawdThreeStepActivity.OLDPAYPAWD,psw);
                startActivity(intent);
            }
        });
        mBack.setOnClickListener(this);
        mTitle.setText("修改支付密码");

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
