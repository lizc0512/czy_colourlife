package com.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.nohttp.utils.GlideImageLoader;
import com.youmai.hxsdk.HuxinSdkManager;
import com.youmai.hxsdk.ProtoCallback;
import com.youmai.hxsdk.proto.YouMaiBuddy;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/20 16:57
 * @change
 * @chang time
 * @class describe  申请添加为好友的用户的资料
 */

public class IMApplyFriendInforActivity extends BaseActivity implements View.OnClickListener {

    public static final String USERREMARK = "userremark";
    public static final String POSITION = "position";
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ImageView user_photo;
    private TextView user_nickname;
    private ImageView user_sex;
    private TextView tv_community;
    private TextView tv_remarks;
    private Button btn_pass_verify;
    private String useruuid;
    private String userRemark;
    private String username;
    private String gender;
    private String communityname;
    private String portrait;
    private String nickName;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyfriend_infor);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        user_photo = findViewById(R.id.user_photo);
        user_nickname = findViewById(R.id.user_nickname);
        user_sex = findViewById(R.id.user_sex);
        tv_community = findViewById(R.id.tv_community);
        tv_remarks = findViewById(R.id.tv_remarks);
        btn_pass_verify = findViewById(R.id.btn_pass_verify);
        user_top_view_back.setOnClickListener(this);
        btn_pass_verify.setOnClickListener(this);
        user_top_view_title.setText(getResources().getString(R.string.instant_detail_infor));
        Intent intent = getIntent();
        useruuid = intent.getStringExtra(IMFriendInforActivity.USERUUID);
        userRemark = intent.getStringExtra(USERREMARK);
        username = intent.getStringExtra(IMInviteRegisterActivity.USERNAME);
        nickName = intent.getStringExtra(IMInviteRegisterActivity.USERNICKNAME);
        portrait = intent.getStringExtra(IMInviteRegisterActivity.USERPORTRAIT);
        gender = intent.getStringExtra(IMInviteRegisterActivity.USERGENDER);
        communityname = intent.getStringExtra(IMInviteRegisterActivity.USECOMMUNITYNAME);
        position = intent.getIntExtra(POSITION, 0);
        setUserInfor();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_pass_verify:  // 通过验证,调用IM的接口
                agreeFriendApply();
                break;

        }
    }

    public void agreeFriendApply() {

        ProtoCallback.AddFriendListener listener = new ProtoCallback.AddFriendListener() {
            @Override
            public void result(YouMaiBuddy.IMOptBuddyRsp ack) {
                if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_OK) {
                    ToastUtil.toastShow(getApplicationContext(), "好友验证成功");
                    Intent intent = new Intent();
                    intent.putExtra(POSITION, position);
                    setResult(200, intent);
                    finish();
                } else if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_BUDDY_REQUESTING) {
                    ToastUtil.toastShow(getApplicationContext(), "重复添加好友请求成功");
                } else if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_BUDDY_BUILT) {
                    Intent intent = new Intent();
                    intent.putExtra(POSITION, position);
                    setResult(200, intent);
                    finish();
                } else if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_BLACKLIST) {
                    ToastUtil.toastShow(getApplicationContext(), "黑名单");
                } else {
                    ToastUtil.toastShow(getApplicationContext(), "出现错误");
                }
            }
        };
        HuxinSdkManager.instance().addFriend(useruuid,
                YouMaiBuddy.BuddyOptType.BUDDY_OPT_ADD_AGREE,
                userRemark, listener);
    }


    private void setUserInfor() {
        if (TextUtils.isEmpty(userRemark)) {
            tv_remarks.setText("");
        } else {
            tv_remarks.setText(userRemark);
        }
        GlideImageLoader.loadImageDefaultDisplay(IMApplyFriendInforActivity.this, portrait, user_photo, R.drawable.im_icon_default_head, R.drawable.im_icon_default_head);
        String showUserName = username.replace(" ", "");
        if (!TextUtils.isEmpty(showUserName)) {
            user_nickname.setText(username);
        } else {
            user_nickname.setText(nickName);
        }
        if ("1".equals(gender)) {
            user_sex.setImageResource(R.drawable.im_icon_man);
        } else if ("2".equals(gender)) {
            user_sex.setImageResource(R.drawable.im_icon_woman);
        }
        tv_community.setText("小区        " + communityname);
    }

}
