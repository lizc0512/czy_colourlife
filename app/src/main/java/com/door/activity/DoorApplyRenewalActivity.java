package com.door.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;

import cn.net.cyberway.R;

/**
 * 申请续期
 * hxg 2019.9.26
 */
public class DoorApplyRenewalActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;

//    iv_id_card"tv_community"tv_identity"tv_to_real_name

    private ImageView iv_id_card;
    private TextView tv_explain;
    private TextView tv_name;
    private TextView tv_community;
    private TextView tv_identity;
    private TextView tv_to_real_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_apply_renewal);
        initView();
        initData();
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);

        iv_id_card = findViewById(R.id.iv_id_card);
        tv_explain = findViewById(R.id.tv_explain);
        tv_name = findViewById(R.id.tv_name);
        tv_community = findViewById(R.id.tv_community);
        tv_identity = findViewById(R.id.tv_identity);
        tv_to_real_name = findViewById(R.id.tv_to_real_name);

        user_top_view_back.setOnClickListener(this);
        tv_identity.setOnClickListener(this);
        tv_to_real_name.setOnClickListener(this);
    }

    private void initData() {
        user_top_view_title.setText("申请续期");

//1、
//        tv_explain.setText("您已实名认证，已获得当前小区的两个月开门权限");
//        tv_to_real_name.setVisibility(View.GONE);


//2、
//        tv_explain.setText("您已实名认证，再次申请即可获得权限");
//        tv_to_real_name.setText("申请");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_community:
                break;
            case R.id.tv_identity:
                break;
            case R.id.tv_apply_one:
                break;
            case R.id.tv_apply_forever:
                break;
        }
    }

    private void canSubmit() {


    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 1:
                if (!TextUtils.isEmpty(result)) {

                }
                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {

                }
                break;
        }
    }
}
