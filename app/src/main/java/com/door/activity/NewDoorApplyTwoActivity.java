package com.door.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.view.ClearEditText;

import cn.net.cyberway.R;
/*
 * 业主申请门禁
 *
 * */

public class NewDoorApplyTwoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ClearEditText ed_real_name;
    private RelativeLayout choice_room_layout;
    private TextView tv_apply_room;
    private TextView tv_apply_identify;
    private ClearEditText ed_authorize_phone;
    private Button btn_submit_infor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_applyaccess_two);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        ed_real_name = findViewById(R.id.ed_real_name);
        choice_room_layout = findViewById(R.id.choice_room_layout);
        tv_apply_room = findViewById(R.id.tv_apply_room);
        tv_apply_identify = findViewById(R.id.tv_apply_identify);
        ed_authorize_phone = findViewById(R.id.ed_authorize_phone);
        btn_submit_infor = findViewById(R.id.btn_submit_infor);
        choice_room_layout.setOnClickListener(this::onClick);
        btn_submit_infor.setOnClickListener(this::onClick);
        user_top_view_back.setOnClickListener(this::onClick);
        user_top_view_title.setText("申请开门");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.choice_room_layout:

                break;
            case R.id.btn_submit_infor:

                break;
        }
    }
}
