package com.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.user.model.NewUserModel;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/20 18:05
 * @change
 * @chang time
 * @class describe  搜索好友时用户未注册彩之云
 */

public class IMInviteRegisterActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String USERPHONE = "userphone";  //用户的手机号
    public static final String USERNAME = "username"; //用户的名字
    public static final String USERNICKNAME = "usernickname"; //用户的昵称
    public static final String USERGENDER = "usergender"; //用户的性别
    public static final String USERPORTRAIT = "userportrait"; //用户的头像
    public static final String USECOMMUNITYNAME = "usecommunityname";//用户的小区名
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView user_nickname;
    private TextView user_phone;
    private TextView tv_status;
    private Button btn_send_invite;
    private String phone = "";
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_information);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        user_nickname = findViewById(R.id.user_nickname);
        user_phone = findViewById(R.id.user_phone);
        tv_status = findViewById(R.id.tv_status);
        btn_send_invite = findViewById(R.id.btn_send_invite);
        user_top_view_title.setText("个人资料");
        user_top_view_back.setOnClickListener(this);
        btn_send_invite.setOnClickListener(this);
        Intent intent = getIntent();
        phone = intent.getStringExtra(USERPHONE);
        username = intent.getStringExtra(USERNAME);
        user_nickname.setText(username);
        user_phone.setText("手机号:" + phone);
        tv_status.setText(username + "未注册彩之云");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_send_invite:
                NewUserModel newUserModel = new NewUserModel(IMInviteRegisterActivity.this);
                newUserModel.inviteRegister(0, phone, IMInviteRegisterActivity.this);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                ToastUtil.toastShow(IMInviteRegisterActivity.this, "已发送邀请");
                break;
        }
    }
}
