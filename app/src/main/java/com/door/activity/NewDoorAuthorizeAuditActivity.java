package com.door.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.activity.BaseFragmentActivity;

import cn.net.cyberway.R;
/*
 * 授权待批复页面
 *
 * */

public class NewDoorAuthorizeAuditActivity extends BaseActivity implements View.OnClickListener {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView tv_apply_name;
    private TextView tv_apply_identify;
    private TextView tv_apply_room;
    private TextView tv_apply_time;
    private RecyclerView rv_apply_duration;
    private Button btn_agree_authorize;
    private Button btn_refuse_authorize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_authorize_auditing);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        tv_apply_name = findViewById(R.id.tv_apply_name);
        tv_apply_identify = findViewById(R.id.tv_apply_identify);
        tv_apply_room = findViewById(R.id.tv_apply_room);
        tv_apply_time = findViewById(R.id.tv_apply_time);
        rv_apply_duration = findViewById(R.id.rv_apply_duration);
        btn_agree_authorize = findViewById(R.id.btn_agree_authorize);
        btn_refuse_authorize = findViewById(R.id.btn_refuse_authorize);
        user_top_view_back.setOnClickListener(this::onClick);
        btn_agree_authorize.setOnClickListener(this::onClick);
        btn_refuse_authorize.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_agree_authorize:

                break;
            case R.id.btn_refuse_authorize:

                break;
        }
    }
}
