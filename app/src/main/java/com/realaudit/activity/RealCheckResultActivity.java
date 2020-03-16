package com.realaudit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.external.eventbus.EventBus;
import com.user.UserMessageConstant;

import cn.net.cyberway.R;

import static cn.net.cyberway.utils.ConfigUtils.jumpContactService;

/**
 * 文件名:实名信息审核结果页面
 * 创建者:yuansongkai
 * 邮箱:827194927@qq.com
 * 创建日期:
 * 描述:
 **/
public class RealCheckResultActivity extends BaseActivity implements View.OnClickListener {

    public static final String CHECKSTATE = "checkstate";
    public static final String CHECKREASON = "checkreason";

    private TextView tv_title;   //标题
    private ImageView imageView_back;//返回


    private ImageView iv_check_result;//认证的结果
    private TextView tv_contact_service;
    private Button btn_apply;//重新实名
    private String checkState;//实名认证的状态
    private String realToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_check_result);
        tv_title = findViewById(R.id.user_top_view_title);
        imageView_back = findViewById(R.id.user_top_view_back);
        iv_check_result = findViewById(R.id.iv_check_result);
        tv_contact_service = findViewById(R.id.tv_contact_service);
        btn_apply = findViewById(R.id.btn_apply);
        Intent intent = getIntent();
        checkState = intent.getStringExtra(CHECKSTATE);
        String checkReason = intent.getStringExtra(CHECKREASON);

        TextView tv_check_reason = findViewById(R.id.tv_check_reason);
        TextView tv_check_result = findViewById(R.id.tv_check_result);
        if ("2".equals(checkState)) {
            tv_check_reason.setText(getResources().getString(R.string.real_check_success));
        } else {
            btn_apply.setText(getResources().getString(R.string.real_upload_again));
            iv_check_result.setImageResource(R.drawable.real_check_fail);
            tv_check_result.setText(getResources().getString(R.string.real_status_fail));
            if (!TextUtils.isEmpty(checkReason)) {
                tv_check_reason.setText(checkReason);
            } else {
                tv_check_reason.setText("");
            }
        }
        imageView_back.setOnClickListener(this::onClick);
        tv_contact_service.setOnClickListener(this::onClick);
        btn_apply.setOnClickListener(this::onClick);
        tv_title.setText(getResources().getString(R.string.real_title_change_realname));
        if (!EventBus.getDefault().isregister(RealCheckResultActivity.this)) {
            EventBus.getDefault().register(RealCheckResultActivity.this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_contact_service:
                jumpContactService(RealCheckResultActivity.this);
                break;
            case R.id.btn_apply:
                if ("2".equals(checkState)) {
                    Intent intent = new Intent(RealCheckResultActivity.this, RealCommonSubmitActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(RealCheckResultActivity.this, RealOriginUploadActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }

    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.REAL_SUCCESS_STATE:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(RealCheckResultActivity.this)) {
            EventBus.getDefault().unregister(RealCheckResultActivity.this);
        }
    }
}
