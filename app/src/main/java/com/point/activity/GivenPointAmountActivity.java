package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.view.ClearEditText;
import com.nohttp.utils.CashierInputFilter;
import com.user.UserAppConst;

import cn.net.cyberway.R;

/***
 * 赠送积分输入金额
 */
public class GivenPointAmountActivity extends BaseActivity implements View.OnClickListener, TextWatcher {


    private ImageView mBack;
    private TextView mTitle;
    private TextView user_top_view_right;
    private ImageView iv_given_photo;
    private TextView tv_given_username;
    private ClearEditText ed_given_amount;
    private ClearEditText ed_given_remark;
    private TextView tv_remain_amount;
    private TextView tv_remain_notice;
    private Button btn_given;
    private String giveAmount;
    private int digits = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_given_amount);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        user_top_view_right = findViewById(R.id.user_top_view_right);
        iv_given_photo = findViewById(R.id.iv_given_photo);
        tv_given_username = findViewById(R.id.tv_given_username);
        ed_given_amount = findViewById(R.id.ed_given_amount);
        ed_given_remark = findViewById(R.id.ed_given_remark);
        tv_remain_amount = findViewById(R.id.tv_remain_amount);
        tv_remain_notice = findViewById(R.id.tv_remain_notice);
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setText("记录");
        user_top_view_right.setOnClickListener(this::onClick);
        btn_given = findViewById(R.id.btn_given);
        mBack.setOnClickListener(this);
        btn_given.setOnClickListener(this);
        mTitle.setText(shared.getString(UserAppConst.COLOUR_WALLET_KEYWORD_SIGN, "") + "赠送");
        ed_given_amount.addTextChangedListener(this);
        CashierInputFilter cashierInputFilter = new CashierInputFilter(1000);
        ed_given_amount.setFilters(new InputFilter[]{cashierInputFilter});
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                Intent historyIntent = new Intent(GivenPointAmountActivity.this, GivenPointHistoryActivity.class);
                startActivity(historyIntent);
                break;
            case R.id.btn_given:
                String giveAmount = ed_given_amount.getText().toString().trim();
                if (giveAmount.endsWith(".")) {
                    giveAmount = giveAmount.substring(0, giveAmount.length() - 1);
                } else if (giveAmount.startsWith("0") && !giveAmount.contains(".")) {
                    int pos = giveAmount.lastIndexOf('0');
                    giveAmount = giveAmount.substring(pos + 1, giveAmount.length());
                }
                ToastUtil.toastShow(GivenPointAmountActivity.this, giveAmount);
                Intent intent = new Intent(GivenPointAmountActivity.this, GivenPointResultActivity.class);
                startActivity(intent);
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
        giveAmount = s.toString().trim();
        if (TextUtils.isEmpty(giveAmount) || giveAmount.equals("0") || giveAmount.equals("0.")
                || giveAmount.equals("0.0") || giveAmount.equals("0.00")) {
            btn_given.setEnabled(false);
            btn_given.setBackgroundResource(R.drawable.point_password_default_bg);
        } else {
            btn_given.setEnabled(true);
            btn_given.setBackgroundResource(R.drawable.point_password_click_bg);
        }
    }
}
