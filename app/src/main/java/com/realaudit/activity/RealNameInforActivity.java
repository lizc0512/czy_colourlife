package com.realaudit.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.view.CircleImageView;

import cn.net.cyberway.R;

/**
 * 文件名:当前用户实名认证相关信息
 * 创建者:yuansongkai
 * 邮箱:827194927@qq.com
 * 创建日期:
 * 描述:
 **/
public class RealNameInforActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_title;   //标题
    private ImageView imageView_back;//返回
    private CircleImageView iv_user_photo;
    private TextView tv_user_name;
    private TextView tv_user_number;
    private TextView tv_user_status;
    private TextView btn_apply;
    private TextView tv_contact_service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_infor);
        tv_title = findViewById(R.id.user_top_view_title);
        imageView_back = findViewById(R.id.user_top_view_back);
        iv_user_photo=findViewById(R.id.iv_user_photo);
        tv_user_number=findViewById(R.id.tv_user_number);
        tv_user_status=findViewById(R.id.tv_user_status);
        btn_apply=findViewById(R.id.btn_apply);
        tv_contact_service=findViewById(R.id.tv_contact_service);

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
            case R.id.tv_contact_service:


                break;
            case R.id.btn_apply:


                break;
        }

    }
}
