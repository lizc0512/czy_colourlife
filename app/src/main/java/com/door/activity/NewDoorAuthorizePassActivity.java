package com.door.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.activity.BaseFragmentActivity;

import cn.net.cyberway.R;
/*
 * 授权通过或拒绝的页面
 *
 * */

public class NewDoorAuthorizePassActivity extends BaseActivity implements View.OnClickListener {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView tv_apply_name;
    private TextView tv_apply_identify;
    private TextView tv_apply_room;
    private TextView tv_apply_time;
    private TextView tv_apply_status;
    private TextView tv_apply_duration;
    private TextView tv_pass_time;
    private Button btn_cancel_authorize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_authorize_pass);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        tv_apply_name = findViewById(R.id.tv_apply_name);
        tv_apply_identify = findViewById(R.id.tv_apply_identify);
        tv_apply_room = findViewById(R.id.tv_apply_room);
        tv_apply_time = findViewById(R.id.tv_apply_time);
        tv_apply_status = findViewById(R.id.tv_apply_status);
        tv_apply_duration = findViewById(R.id.tv_apply_duration);
        tv_pass_time = findViewById(R.id.tv_pass_time);
        btn_cancel_authorize = findViewById(R.id.btn_cancel_authorize);
        user_top_view_back.setOnClickListener(this::onClick);
        btn_cancel_authorize.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_cancel_authorize:

                break;
        }
    }
}
