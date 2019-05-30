package com.invite.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.user.model.NewUserModel;

import cn.net.cyberway.R;

/**
 * 兑换
 * Created by hxg on 19/5/16.
 */
public class InviteChangeActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView imgBack;
    private TextView tvTitle;

    private EditText et_change_num;
    private TextView tv_change_all, tv_avail;
    private ImageView iv_change;

    private NewUserModel newUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_change);
        getWindow().setBackgroundDrawable(null);
        initView();
        initData();
    }

    private void initView() {
        imgBack = findViewById(R.id.user_top_view_back);
        tvTitle = findViewById(R.id.user_top_view_title);

        et_change_num = findViewById(R.id.et_change_num);
        tv_change_all = findViewById(R.id.tv_change_all);
        tv_avail = findViewById(R.id.tv_avail);
        iv_change = findViewById(R.id.iv_change);

        tv_change_all.setOnClickListener(this);
        iv_change.setOnClickListener(this);
    }

    private void initData() {
        tvTitle.setText("兑换");
        newUserModel = new NewUserModel(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_change_all:
                String num = tv_avail.getText().toString().trim();
                if (!"0".equals(num)) {
                    et_change_num.setText(num);
                } else {
                    ToastUtil.toastShow(this, "没有可用收益啦~");
                }
                break;
            case R.id.iv_change:
                String sum = tv_avail.getText().toString().trim();
                String input = et_change_num.getText().toString().trim();
                try {
                    float avial = Float.parseFloat(sum);
                    float in = Float.parseFloat(input);
                    if (avial < in) {
                        ToastUtil.toastShow(this, "兑换金额超过可用收益啦~");
                    } else {
                        ToastUtil.toastShow(this, "输入：" + in);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:

                break;
        }
    }
}
