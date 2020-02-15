package com.realaudit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.view.CircleImageView;
import com.external.eventbus.EventBus;
import com.nohttp.utils.GlideImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.point.activity.ChangePawdOneStepActivity;
import com.user.UserAppConst;
import com.user.UserMessageConstant;

import cn.net.cyberway.R;

import static cn.net.cyberway.utils.ConfigUtils.jumpContactService;

/**
 * 文件名:当前用户实名认证相关信息
 * 创建者:yuansongkai
 * 邮箱:827194927@qq.com
 * 创建日期:
 * 描述:
 **/
public class RealNameInforActivity extends BaseActivity implements View.OnClickListener {
    public static final String REALNAME = "realname";
    public static final String REALNUMBER = "realnumber";
    public static final String REALFACEIMAGE = "realfaceimage";
    private TextView tv_title;   //标题
    private ImageView imageView_back;//返回
    private CircleImageView iv_user_photo;
    private TextView tv_user_name;//用户的姓名
    private TextView tv_user_number;//用户的身份证号
    private TextView btn_apply;//重新更换
    private TextView tv_contact_service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_infor);
        tv_title = findViewById(R.id.user_top_view_title);
        imageView_back = findViewById(R.id.user_top_view_back);
        iv_user_photo = findViewById(R.id.iv_user_photo);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_number = findViewById(R.id.tv_user_number);
        btn_apply = findViewById(R.id.btn_apply);
        tv_contact_service = findViewById(R.id.tv_contact_service);
        imageView_back.setOnClickListener(this::onClick);
        btn_apply.setOnClickListener(this::onClick);
        tv_contact_service.setOnClickListener(this::onClick);
        tv_title.setText(getResources().getString(R.string.real_title_real_identify));
        Intent intent = getIntent();
        String realName = intent.getStringExtra(REALNAME);
        String realNumber = intent.getStringExtra(REALNUMBER);
        String headImgUrl = intent.getStringExtra(REALFACEIMAGE);
        if (TextUtils.isEmpty(headImgUrl)||"null".equalsIgnoreCase(headImgUrl)){
            headImgUrl= shared.getString(UserAppConst.Colour_head_img, "");
        }
        GlideImageLoader.loadImageDisplay(RealNameInforActivity.this,headImgUrl,iv_user_photo);
        tv_user_name.setText(realName);
        int  length=realNumber.length();
        if (realNumber.length()>0){
            tv_user_number.setText(getResources().getString(R.string.real_text_idcard)+realNumber.substring(0, 1) + "*** **** **** **** *" + realNumber.substring(length - 1));
        }
        if (!EventBus.getDefault().isregister(RealNameInforActivity.this)) {
            EventBus.getDefault().register(RealNameInforActivity.this);
        }
    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.REAL_CHANGE_STATE:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(RealNameInforActivity.this)) {
            EventBus.getDefault().unregister(RealNameInforActivity.this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_contact_service:
                jumpContactService(RealNameInforActivity.this);
                break;
            case R.id.btn_apply:
                Intent intent = new Intent(RealNameInforActivity.this, RealOriginUploadActivity.class);
                startActivity(intent);
                break;
        }

    }
}
