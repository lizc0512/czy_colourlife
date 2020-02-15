package com.community.activity;

import android.os.Bundle;
import android.view.View;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;

import cn.net.cyberway.R;
/**
 * @name ${yuansk}
 * @class name：com.cashier.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/7 10:35
 * @change
 * @chang time
 * @class describe   动态提醒
 */

public class DynamicNoticeActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamics_details);
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void OnHttpResponse(int what, String result) {

    }
}
