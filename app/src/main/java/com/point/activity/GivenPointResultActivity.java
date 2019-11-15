package com.point.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.view.ClearEditText;
import com.user.UserAppConst;

import cn.net.cyberway.R;
/***
 * 积分赠送的结果
 */
public class GivenPointResultActivity extends BaseActivity implements View.OnClickListener{


    private ImageView mBack;
    private TextView mTitle;
    private TextView tv_given_amount;
    private TextView tv_given_username;
    private TextView tv_continue_given;
    private TextView tv_return;
    private Button btn_next_step;
    private TextView tv_remain_notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_given_result);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        tv_given_amount = findViewById(R.id.tv_given_amount);
        tv_given_username = findViewById(R.id.tv_given_username);
        tv_continue_given = findViewById(R.id.tv_continue_given);
        tv_return = findViewById(R.id.tv_return);
        mBack.setOnClickListener(this);
        tv_continue_given.setOnClickListener(this);
        tv_return.setOnClickListener(this);
        mTitle.setText(shared.getString(UserAppConst.COLOUR_WALLET_KEYWORD_SIGN, "") + "赠送");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_continue_given:
                 setResult(200);
                 finish();
                break;
            case R.id.tv_return:


                break;
        }
    }
}
