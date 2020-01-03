package com.realaudit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;

import cn.net.cyberway.R;

/**
 * 文件名:实名信息待审核页面
 * 创建者:yuansongkai
 * 邮箱:827194927@qq.com
 * 创建日期:
 * 描述:
 **/
public class RealCheckWaitingActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_title;   //标题
    private ImageView imageView_back;//返回
    private ImageView iv_check_result;
    private TextView tv_check_result;
    private TextView tv_check_reason;
    private Button btn_apply;
    private TextView tv_contact_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_check_waiting);
        tv_title = findViewById(R.id.user_top_view_title);
        imageView_back = findViewById(R.id.user_top_view_back);

        iv_check_result = findViewById(R.id.iv_check_result);
        tv_check_result = findViewById(R.id.tv_check_result);
        tv_check_reason = findViewById(R.id.tv_check_reason);
        btn_apply = findViewById(R.id.btn_apply);
        tv_contact_service = findViewById(R.id.tv_contact_service);
        imageView_back.setOnClickListener(this::onClick);
        btn_apply.setOnClickListener(this::onClick);
        tv_contact_service.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_apply:

                break;
            case R.id.tv_contact_service:


                break;
        }

    }
}
