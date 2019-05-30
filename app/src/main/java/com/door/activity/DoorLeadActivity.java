package com.door.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.BeeFramework.activity.BaseActivity;

import cn.net.cyberway.R;

public class DoorLeadActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_doorlead_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_lead);
        initView();
    }

    private void initView() {
        iv_doorlead_close = (ImageView) findViewById(R.id.iv_doorlead_close);
        iv_doorlead_close.setOnClickListener(DoorLeadActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_doorlead_close:
                DoorLeadActivity.this.finish();
                break;
        }
    }
}
