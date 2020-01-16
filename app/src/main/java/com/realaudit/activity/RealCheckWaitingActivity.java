package com.realaudit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.activity.BaseActivity;

import cn.net.cyberway.R;

import static cn.net.cyberway.utils.ConfigUtils.jumpContactService;

/**
 * 文件名:实名信息待审核页面
 * 创建者:yuansongkai
 * 邮箱:827194927@qq.com
 * 创建日期:
 * 描述:
 **/
public class RealCheckWaitingActivity extends BaseActivity implements View.OnClickListener {
    public static final String CHECKTIME = "checktime";
    private TextView tv_title;   //标题
    private ImageView imageView_back;//返回
    private TextView tv_contact_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_check_waiting);
        tv_title = findViewById(R.id.user_top_view_title);
        imageView_back = findViewById(R.id.user_top_view_back);
        TextView tv_check_time = findViewById(R.id.tv_check_time);
        long time = getIntent().getLongExtra(CHECKTIME, System.currentTimeMillis() / 1000);
        tv_check_time.setText(TimeUtil.getDateToString(time));
        tv_contact_service = findViewById(R.id.tv_contact_service);
        imageView_back.setOnClickListener(this::onClick);
        tv_contact_service.setOnClickListener(this::onClick);
        tv_title.setText(getResources().getString(R.string.real_title_change_realname));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_contact_service:
                jumpContactService(RealCheckWaitingActivity.this);
                break;
        }

    }
}
