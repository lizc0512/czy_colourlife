package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.view.ClearEditText;
import com.user.UserAppConst;

import cn.net.cyberway.R;

import static com.point.activity.PointTransactionListActivity.POINTTITLE;

/***
 * 赠送积分输入手机号
 */
public class GivenPointMobileActivity extends BaseActivity implements View.OnClickListener, TextWatcher {


    private ImageView mBack;
    private TextView mTitle;
    private TextView user_top_view_right;
    private ClearEditText input_given_mobile;
    private Button btn_next_step;
    private TextView tv_remain_notice;
    private String givePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_given_mobile);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        user_top_view_right = findViewById(R.id.user_top_view_right);
        input_given_mobile = findViewById(R.id.input_given_mobile);
        btn_next_step = findViewById(R.id.btn_next_step);
        tv_remain_notice = findViewById(R.id.tv_remain_notice);
        mBack.setOnClickListener(this);
        btn_next_step.setEnabled(false);
        btn_next_step.setOnClickListener(this);
        mTitle.setText(shared.getString(UserAppConst.COLOUR_WALLET_KEYWORD_SIGN, "") + "赠送");
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setText("记录");
        user_top_view_right.setOnClickListener(this::onClick);
        input_given_mobile.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                Intent intent = new Intent(GivenPointMobileActivity.this, GivenPointHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_next_step:
                Intent amount_Intent = new Intent(GivenPointMobileActivity.this, GivenPointAmountActivity.class);
                startActivity(amount_Intent);
                break;
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
        givePhone = s.toString().trim();
        if (11 != givePhone.length()) {
            btn_next_step.setEnabled(false);
            btn_next_step.setBackgroundResource(R.drawable.point_password_default_bg);
        } else {
            btn_next_step.setEnabled(true);
            btn_next_step.setBackgroundResource(R.drawable.point_password_click_bg);
        }
    }
}
