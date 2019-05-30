package com.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.view.ClearEditText;
import com.im.entity.FriendInforEntity;
import com.im.helper.CacheFriendInforHelper;
import com.im.utils.BaseUtil;
import com.im.utils.CharacterParser;
import com.nohttp.utils.GlideImageLoader;
import com.user.UserAppConst;
import com.youmai.hxsdk.HuxinSdkManager;
import com.youmai.hxsdk.ProtoCallback;
import com.youmai.hxsdk.proto.YouMaiBuddy;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/15 9:55
 * @change
 * @chang time
 * @class describe   申请添加好友页面
 */

public class IMApplyFriendActivity extends BaseActivity implements View.OnClickListener {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView user_top_view_right;

    private ImageView user_photo;
    private TextView user_nickname;
    private TextView user_community;
    private ImageView user_sex;
    private ClearEditText ed_verify_infor;
    private ClearEditText ed_set_notes;
    private String mobilePhone;
    private String userRemark;
    private String useruuid;
    private String username;
    private String nickname;
    private String gender;
    private String communityname;
    private String portrait;
    private String verifyInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend_apply);
        initView();
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        user_top_view_right = findViewById(R.id.user_top_view_right);
        user_photo = findViewById(R.id.user_photo);
        user_nickname = findViewById(R.id.user_nickname);
        user_community = findViewById(R.id.user_community);
        user_sex = findViewById(R.id.user_sex);
        ed_verify_infor = findViewById(R.id.ed_verify_infor);
        ed_set_notes = findViewById(R.id.ed_set_notes);
        user_top_view_title.setText(getResources().getString(R.string.instant_verify_apply));
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setText(getResources().getString(R.string.instant_send));
        user_top_view_right.setTextColor(getResources().getColor(R.color.color_45adff));
        user_top_view_back.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        Intent intent = getIntent();
        mobilePhone = intent.getStringExtra(IMInviteRegisterActivity.USERPHONE);
        username = intent.getStringExtra(IMInviteRegisterActivity.USERNAME);
        nickname = intent.getStringExtra(IMInviteRegisterActivity.USERNICKNAME);
        gender = intent.getStringExtra(IMInviteRegisterActivity.USERGENDER);
        portrait = intent.getStringExtra(IMInviteRegisterActivity.USERPORTRAIT);
        useruuid = intent.getStringExtra(IMFriendInforActivity.USERUUID);
        communityname = intent.getStringExtra(IMInviteRegisterActivity.USECOMMUNITYNAME);
        GlideImageLoader.loadImageDefaultDisplay(IMApplyFriendActivity.this, portrait, user_photo, R.drawable.im_icon_default_head, R.drawable.im_icon_default_head);
        user_nickname.setText(username);
        if ("1".equals(gender)) {
            user_sex.setImageResource(R.drawable.im_icon_man);
        } else if ("2".equals(gender)) {
            user_sex.setImageResource(R.drawable.im_icon_woman);
        }
        user_community.setText(communityname);
        if (!TextUtils.isEmpty(nickname)) {
            ed_verify_infor.setText("我是" + shared.getString(UserAppConst.Colour_NIACKNAME, ""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:// IM调用发送添加好友验证申请的
                verifyInfor = ed_verify_infor.getText().toString().trim();
                userRemark = ed_set_notes.getText().toString().trim();
                if (TextUtils.isEmpty(verifyInfor)) {
                    verifyInfor = "";
                }
                addFriend();
                break;
        }
    }

    public void addFriend() {
        if (TextUtils.isEmpty(useruuid)) {
            ToastUtil.toastShow(IMApplyFriendActivity.this, "添加好友失败,好友的uui为空");
        } else {
            HuxinSdkManager.instance().addFriend(useruuid, YouMaiBuddy.BuddyOptType.BUDDY_OPT_ADD_REQ,
                    verifyInfor, new ProtoCallback.AddFriendListener() {
                        @Override
                        public void result(YouMaiBuddy.IMOptBuddyRsp ack) {
                            if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_OK) {
                                ToastUtil.toastShow(IMApplyFriendActivity.this, "已发送");
                                finish();
                            } else if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_BUDDY_REQUESTING) {
                                ToastUtil.toastShow(IMApplyFriendActivity.this, "重复添加好友请求成功");
                            } else if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_BUDDY_BUILT) {
                                ToastUtil.toastShow(IMApplyFriendActivity.this, "已经是好友了");
                            } else if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_BLACKLIST) {
                                ToastUtil.toastShow(IMApplyFriendActivity.this, "黑名单");
                            } else if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_BUDDY_READD) {
                                addFriendList();
                                ToastUtil.toastShow(IMApplyFriendActivity.this, "添加好友成功");
                                finish();
                                HuxinSdkManager.instance().entryChatSingle(IMApplyFriendActivity.this, useruuid, nickname, portrait, username, mobilePhone);
                            } else {
                                ToastUtil.toastShow(IMApplyFriendActivity.this, "出现错误");
                            }
                        }
                    });
        }
    }

    private void addFriendList() {
        FriendInforEntity friendInforEntity = new FriendInforEntity();
        friendInforEntity.setUuid(useruuid);
        friendInforEntity.setPortrait(portrait);
        friendInforEntity.setGender(gender);
        friendInforEntity.setNickname(nickname);
        friendInforEntity.setRealName(username);
        friendInforEntity.setUsername(username);
        friendInforEntity.setCommunityName(communityname);
        friendInforEntity.setMobile(mobilePhone);
        String sortStr = "";
        if (TextUtils.isEmpty(sortStr)) {
            sortStr = BaseUtil.formatString(username);
        }
        if (TextUtils.isEmpty(sortStr)) {
            sortStr = BaseUtil.formatString(nickname);
        }
        if (!TextUtils.isEmpty(sortStr)) {
            CharacterParser characterParser = CharacterParser.getInstance();
            String pinyin = characterParser.getSelling(sortStr);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                friendInforEntity.setSortLetters(sortString.toUpperCase());
            } else {
                friendInforEntity.setSortLetters("#");
            }
        } else {
            friendInforEntity.setSortLetters("#");
        }
        CacheFriendInforHelper.instance().insertOrUpdate(getApplicationContext(), friendInforEntity);
    }
}
