package com.realaudit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;

import cn.net.cyberway.R;

/**
 * 文件名:上传之前绑定用户的旧证件信息
 * 创建者:yuansongkai
 * 邮箱:827194927@qq.com
 * 创建日期:
 * 描述:
 **/
public class RealOriginUploadActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_title;   //标题
    private ImageView imageView_back;//返回

    private ImageView iv_idcard_back;
    private ImageView iv_del_back;

    private ImageView iv_idcard_front;
    private ImageView iv_del_front;


    private ImageView iv_idcard_hand;
    private ImageView iv_del_hand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_orgin_idcard);
        tv_title = findViewById(R.id.user_top_view_title);
        imageView_back = findViewById(R.id.user_top_view_back);
        iv_idcard_back = findViewById(R.id.iv_idcard_back);
        iv_del_back = findViewById(R.id.iv_del_back);
        iv_idcard_front = findViewById(R.id.iv_idcard_front);
        iv_del_front = findViewById(R.id.iv_del_front);
        iv_idcard_hand = findViewById(R.id.iv_idcard_hand);
        iv_del_hand = findViewById(R.id.iv_del_hand);

        imageView_back.setOnClickListener(this::onClick);
        iv_idcard_back.setOnClickListener(this::onClick);
        iv_del_back.setOnClickListener(this::onClick);
        iv_idcard_front.setOnClickListener(this::onClick);
        iv_del_front.setOnClickListener(this::onClick);
        iv_idcard_hand.setOnClickListener(this::onClick);
        iv_del_hand.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.iv_idcard_back:


                break;
            case R.id.iv_del_back:


                break;

            case R.id.iv_idcard_front:


                break;
            case R.id.iv_del_front:


                break;

            case R.id.iv_idcard_hand:


                break;
            case R.id.iv_del_hand:


                break;
        }

    }
}
