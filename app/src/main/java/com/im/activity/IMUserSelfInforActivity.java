package com.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.im.entity.MobileBookEntity;
import com.im.model.IMUploadPhoneModel;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.youmai.hxsdk.HuxinSdkManager;
import com.youmai.hxsdk.router.APath;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/20 16:57
 * @change
 * @chang time
 * @class describe  用户本人自己的用户信息
 */
@Route(path = APath.USER_INFO_ACT)
public class IMUserSelfInforActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ImageView user_photo;
    private TextView user_nickname;
    private ImageView user_sex;
    private TextView tv_community;
    private RelativeLayout details_community_layout;
    private Button btn_send_msg;
    private String mobilePhone;
    private String useruuid;
    private String userId;
    private String username;
    private String nickname;
    private String gender;
    private String communityname;
    private String portrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userself_information);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        user_photo = findViewById(R.id.user_photo);
        user_nickname = findViewById(R.id.user_nickname);
        tv_community = findViewById(R.id.tv_community);
        details_community_layout = findViewById(R.id.details_community_layout);
        user_sex = findViewById(R.id.user_sex);
        btn_send_msg = findViewById(R.id.btn_send_msg);
        user_top_view_back.setOnClickListener(this);
        details_community_layout.setOnClickListener(this);
        btn_send_msg.setOnClickListener(this);
        user_top_view_title.setText("详细资料");
        useruuid = getIntent().getStringExtra(IMFriendInforActivity.USERUUID);
        IMUploadPhoneModel imUploadPhoneModel = new IMUploadPhoneModel(IMUserSelfInforActivity.this);
        imUploadPhoneModel.getUserInforByUuid(0, useruuid, true, this);
    }

    private void setUserInfor() {
        GlideImageLoader.loadImageDefaultDisplay(IMUserSelfInforActivity.this, portrait, user_photo, R.drawable.im_icon_default_head, R.drawable.im_icon_default_head);
        if (TextUtils.isEmpty(username)) {
            user_nickname.setText(nickname);
        } else {
            user_nickname.setText(username);
        }
        if ("1".equals(gender)) {
            user_sex.setImageResource(R.drawable.im_icon_man);
        } else if ("2".equals(gender)) {
            user_sex.setImageResource(R.drawable.im_icon_woman);
        }
        if (TextUtils.isEmpty(communityname) || "null".equalsIgnoreCase(communityname)) {
            communityname = "";
        }
        tv_community.setText("小区        " + communityname);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_send_msg:  //发送消息
                HuxinSdkManager.instance().entryChatSingle(IMUserSelfInforActivity.this, useruuid, nickname, portrait, username, mobilePhone);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        MobileBookEntity mobileBookEntity = GsonUtils.gsonToBean(result, MobileBookEntity.class);
                        if (mobileBookEntity.getCode() == 0) {
                            MobileBookEntity.ContentBean contentBean = mobileBookEntity.getContent().get(0);
                            mobilePhone = contentBean.getMobile();
                            communityname = contentBean.getCommunity_name();
                            username = contentBean.getName();
                            userId = contentBean.getUser_id();
                            nickname = contentBean.getNick_name();
                            portrait = contentBean.getPortrait();
                            gender = contentBean.getGender();
                            setUserInfor();
                        }
                    } catch (Exception e) {

                    }
                }
                break;
        }
    }
}
