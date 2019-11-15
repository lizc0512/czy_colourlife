package com.door.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;

import cn.net.cyberway.R;


public class NoRightDoorActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_norightdoor_apply;
    private ImageView iv_doorright_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_right_door);
        tintManager.setStatusBarTintColor(Color.parseColor("#00000000"));
        initView();
    }


    private void initView() {
        tv_norightdoor_apply = (TextView) findViewById(R.id.tv_norightdoor_apply);
        iv_doorright_close = (ImageView) findViewById(R.id.iv_doorright_close);
        tv_norightdoor_apply.setOnClickListener(this);
        iv_doorright_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_norightdoor_apply:
                Intent applyIntent = new Intent(NoRightDoorActivity.this, NewDoorIndetifyActivity.class);
                startActivity(applyIntent);
                this.overridePendingTransition(0, R.anim.door_push_bottom_out);
                finish();
                break;
            case R.id.iv_doorright_close:
                NoRightDoorActivity.this.finish();
                this.overridePendingTransition(0, R.anim.door_push_bottom_out);
                break;
        }
    }
}
