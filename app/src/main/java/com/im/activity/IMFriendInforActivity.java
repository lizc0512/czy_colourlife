package com.im.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.Util;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.feed.activity.PersonalFeedActivity;
import com.im.entity.FriendInforEntity;
import com.im.entity.MobileBookEntity;
import com.im.helper.CacheApplyRecorderHelper;
import com.im.helper.CacheFriendInforHelper;
import com.im.model.IMUploadPhoneModel;
import com.im.view.CommunityOperationDialog;
import com.im.view.FriendOperationDialogFragment;
import com.im.view.UploadPhoneDialog;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.youmai.hxsdk.HuxinSdkManager;
import com.youmai.hxsdk.ProtoCallback;
import com.youmai.hxsdk.chatgroup.IMGroupActivity;
import com.youmai.hxsdk.chatsingle.IMConnectionActivity;
import com.youmai.hxsdk.group.ChatGroupDetailsActivity;
import com.youmai.hxsdk.module.groupchat.ChatDetailsActivity;
import com.youmai.hxsdk.proto.YouMaiBuddy;
import com.youmai.hxsdk.router.APath;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.view.adapter.BaseRecyclerAdapter;

import static com.youmai.hxsdk.proto.YouMaiBuddy.BuddyOptType.BUDDY_OPT_ADD_BLACKLIST;
import static com.youmai.hxsdk.proto.YouMaiBuddy.BuddyOptType.BUDDY_OPT_DEL;
import static com.youmai.hxsdk.proto.YouMaiBuddy.BuddyOptType.BUDDY_OPT_REMOVE_BLACKLIST;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/20 16:57
 * @change
 * @chang time
 * @class describe  好友的资料  通过用户的uuid
 */
@Route(path = APath.BUDDY_FRIEND)
public class IMFriendInforActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String USERUUID = "useruuid";  //用户的uuid
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ImageView img_right;
    private ImageView user_photo;
    private TextView user_name;
    private TextView user_nickname;
    private ImageView user_sex;
    private TextView tv_community;
    private RelativeLayout details_community_layout;
    private TextView tv_source;
    private TextView tv_name;
    private LinearLayout set_remark_layout;
    private LinearLayout share_card_layout;
    private Button btn_send_msg;
    private Button btn_transfer_accounts;
    private String mobilePhone;
    private String userRemark;
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
        setContentView(R.layout.activity_friend_information);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        img_right = findViewById(R.id.img_right);
        img_right.setVisibility(View.VISIBLE);
        user_photo = findViewById(R.id.user_photo);
        user_name = findViewById(R.id.user_name);
        user_nickname = findViewById(R.id.user_nickname);
        user_sex = findViewById(R.id.user_sex);
        tv_community = findViewById(R.id.tv_community);
        details_community_layout = findViewById(R.id.details_community_layout);
        tv_source = findViewById(R.id.tv_source);
        tv_name = findViewById(R.id.tv_name);
        set_remark_layout = findViewById(R.id.set_remark_layout);
        share_card_layout = findViewById(R.id.share_card_layout);
        btn_send_msg = findViewById(R.id.btn_send_msg);
        btn_transfer_accounts = findViewById(R.id.btn_transfer_accounts);
        user_top_view_back.setOnClickListener(this);
        img_right.setOnClickListener(this);
        set_remark_layout.setOnClickListener(this);
        share_card_layout.setOnClickListener(this);
        btn_send_msg.setOnClickListener(this);
        btn_transfer_accounts.setOnClickListener(this);
        details_community_layout.setOnClickListener(this);
        user_top_view_title.setText(getResources().getString(R.string.instant_detail_infor));
        Intent intent = getIntent();
        useruuid = intent.getStringExtra(IMFriendInforActivity.USERUUID);
        IMUploadPhoneModel imUploadPhoneModel = new IMUploadPhoneModel(IMFriendInforActivity.this);
        imUploadPhoneModel.getUserInforByUuid(0, useruuid, true, this);
        HuxinSdkManager.instance().getStackAct().addActivity(this);
    }

    private void setUserInfor() {
        try {
            if (TextUtils.isEmpty(userRemark)) {
                tv_name.setText("");
            } else {
                tv_name.setText(userRemark);
            }
            GlideImageLoader.loadImageDefaultDisplay(IMFriendInforActivity.this, portrait, user_photo, R.drawable.im_icon_default_head, R.drawable.im_icon_default_head);
            if (TextUtils.isEmpty(username)) {
                user_nickname.setText(nickname);
            } else {
                user_name.setText(username);
            }
            if (TextUtils.isEmpty(nickname)) {
                user_nickname.setText("");
            } else {
                user_nickname.setText("昵称:" + nickname);
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
        } catch (Exception e) {

        }

    }

    private List<String> textList = new ArrayList<>();
    private List<Integer> imgList = new ArrayList<>();

    private void showBottomDialog() {
        textList.clear();
        imgList.clear();
        final FriendInforEntity friendInforEntity = CacheFriendInforHelper.instance().toQueryFriendnforById(IMFriendInforActivity.this, useruuid);
        final int status = friendInforEntity.getStatus();
        if (status == 1) {
            textList.add("加入黑名单");
            imgList.add(R.drawable.icon_wechat_share);
        } else {
            textList.add("移除黑名单");
            imgList.add(R.drawable.icon_wechat_share);
        }
        textList.add("删除好友");
        imgList.add(R.drawable.icon_wechat_share);
        final FriendOperationDialogFragment bottomShareDialogFragment = new FriendOperationDialogFragment(IMFriendInforActivity.this, textList, imgList);
        if (null != bottomShareDialogFragment.adapter) {
            bottomShareDialogFragment.adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    if (position == 0) {
                        if (status == 1) {
                            addBlackListNotice(friendInforEntity);
                        } else {
                            removeBlackList(friendInforEntity);
                        }
                    } else { //删除好友
                        delFriendNotice();
                    }
                    bottomShareDialogFragment.dismissDialog();
                }
            });
        }
        bottomShareDialogFragment.setWidth(Utils.getDeviceWith(getApplicationContext()));
        bottomShareDialogFragment.setHeight(Util.DensityUtil.dip2px(getApplicationContext(), 100));
        bottomShareDialogFragment.showAtLocation(findViewById(R.id.friend_infor_layout), Gravity.BOTTOM, 0, 0);
    }

    private CommunityOperationDialog communityOperationDialog;

    private void delBottomDialog() {
        if (null == communityOperationDialog) {
            communityOperationDialog = new CommunityOperationDialog(IMFriendInforActivity.this, R.style.custom_dialog_theme);
        }
        communityOperationDialog.show();
        communityOperationDialog.setShowHide(4);
        communityOperationDialog.tv_again_submit.setText("删除");
        communityOperationDialog.tv_again_submit.setTextColor(Color.parseColor("#FD3D41"));
        communityOperationDialog.tv_cancel.setOnClickListener(this);
        communityOperationDialog.tv_again_submit.setOnClickListener(this);
        communityOperationDialog.tv_revoke.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.img_right:
                delBottomDialog();
                break;
            case R.id.details_community_layout:
                Intent LiLinIntent = new Intent(this, PersonalFeedActivity.class);
                LiLinIntent.putExtra(PersonalFeedActivity.USERID, userId);
                LiLinIntent.putExtra(IMInviteRegisterActivity.USERNAME, username);
                startActivity(LiLinIntent);
                break;
            case R.id.tv_cancel:
                if (null != communityOperationDialog) {
                    communityOperationDialog.dismiss();
                }
                break;
            case R.id.tv_again_submit:
                if (null != communityOperationDialog) {
                    communityOperationDialog.dismiss();
                }
                delFriendNotice();
                break;
            case R.id.set_remark_layout:
                Intent intent = new Intent(IMFriendInforActivity.this, IMModifyGroupNameActivity.class);
                if (TextUtils.isEmpty(userRemark)) {
                    intent.putExtra(IMModifyGroupNameActivity.REMARKNAME, "");
                } else {
                    intent.putExtra(IMModifyGroupNameActivity.REMARKNAME, userRemark);
                }
                startActivity(intent);
                break;
            case R.id.share_card_layout:

                break;
            case R.id.btn_send_msg:
                if (TextUtils.isEmpty(nickname)) {
                    nickname = username;
                }
                HuxinSdkManager.instance().entryChatSingle(IMFriendInforActivity.this, useruuid, nickname, portrait, username, mobilePhone);
                break;
            case R.id.btn_transfer_accounts:
                if (TextUtils.isEmpty(mobilePhone)) {
                    ToastUtil.toastShow(IMFriendInforActivity.this, "被转账人手机号码为空");
                } else {

                }
                break;

        }
    }

    private void delFriendNotice() {
        final UploadPhoneDialog delFriendDialog = new UploadPhoneDialog(IMFriendInforActivity.this, R.style.custom_dialog_theme);
        delFriendDialog.show();
        delFriendDialog.setCancelable(true);
        delFriendDialog.setCanceledOnTouchOutside(true);
        delFriendDialog.dialog_title.setText("删除好友");
        if (TextUtils.isEmpty(username)) {
            delFriendDialog.dialog_content.setText("删除好友" + username + ",同时删除与该好友的聊天记录");
        } else {
            delFriendDialog.dialog_content.setText("删除好友" + nickname + ",同时删除与该好友的聊天记录");
        }
        delFriendDialog.btn_open.setText("删除");
        delFriendDialog.btn_open.setTextColor(Color.parseColor("#FD3D41"));
        delFriendDialog.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HuxinSdkManager.instance().addFriend(useruuid, BUDDY_OPT_DEL, "", new ProtoCallback.AddFriendListener() {
                    @Override
                    public void result(YouMaiBuddy.IMOptBuddyRsp ack) {
                        if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_OK) {
                            CacheFriendInforHelper.instance().delFriendInfor(IMFriendInforActivity.this, useruuid);
                            CacheApplyRecorderHelper.instance().delApplyRecord(IMFriendInforActivity.this, useruuid);
                            HuxinSdkManager.instance().delMsgChat(useruuid);
                            HuxinSdkManager.instance().getStackAct().finishActivity(ChatGroupDetailsActivity.class);
                            HuxinSdkManager.instance().getStackAct().finishActivity(IMGroupActivity.class);
                            HuxinSdkManager.instance().getStackAct().finishActivity(ChatDetailsActivity.class);
                            HuxinSdkManager.instance().getStackAct().finishActivity(IMConnectionActivity.class);
                            ToastUtil.toastShow(IMFriendInforActivity.this, "删除好友成功");
                        } else {
                            ToastUtil.toastShow(IMFriendInforActivity.this, "删除好友失败");
                        }
                        finish();
                    }
                });
                delFriendDialog.dismiss();
            }
        });
        delFriendDialog.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delFriendDialog.dismiss();
            }
        });
    }

    private void removeBlackList(final FriendInforEntity friendInforEntity) {
        HuxinSdkManager.instance().addFriend(useruuid, BUDDY_OPT_REMOVE_BLACKLIST, "", new ProtoCallback.AddFriendListener() {
            @Override
            public void result(YouMaiBuddy.IMOptBuddyRsp ack) {
                if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_OK) {
                    friendInforEntity.setStatus(1);
                    CacheFriendInforHelper.instance().insertOrUpdate(IMFriendInforActivity.this, friendInforEntity);
                    ToastUtil.toastShow(IMFriendInforActivity.this, "移除黑名单成功");
                } else {
                    ToastUtil.toastShow(IMFriendInforActivity.this, "移除黑名单失败");
                }
            }
        });
    }


    private void addBlackListNotice(final FriendInforEntity friendInforEntity) {
        final UploadPhoneDialog addBlackDialog = new UploadPhoneDialog(IMFriendInforActivity.this, R.style.custom_dialog_theme);
        addBlackDialog.setCancelable(true);
        addBlackDialog.setCanceledOnTouchOutside(true);
        addBlackDialog.show();
        addBlackDialog.dialog_title.setText("加入黑名单");
        addBlackDialog.dialog_content.setText("加入黑名单,你将不再收到对方的消息");
        addBlackDialog.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HuxinSdkManager.instance().addFriend(useruuid, BUDDY_OPT_ADD_BLACKLIST, "", new ProtoCallback.AddFriendListener() {
                    @Override
                    public void result(YouMaiBuddy.IMOptBuddyRsp ack) {
                        if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_OK) {
                            friendInforEntity.setStatus(2);
                            CacheFriendInforHelper.instance().insertOrUpdate(IMFriendInforActivity.this, friendInforEntity);
                            ToastUtil.toastShow(IMFriendInforActivity.this, "加入黑名单成功");
                        } else {
                            ToastUtil.toastShow(IMFriendInforActivity.this, "加入黑名单失败");
                        }
                    }
                });
                addBlackDialog.dismiss();

            }
        });
        addBlackDialog.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBlackDialog.dismiss();
            }
        });
    }


    @Override
    public void OnHttpResponse(int what, String result) {

        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        MobileBookEntity mobileBookEntity = GsonUtils.gsonToBean(result, MobileBookEntity.class);
                        if (mobileBookEntity.getCode() == 0) {
                            details_community_layout.setVisibility(View.VISIBLE);
                            MobileBookEntity.ContentBean contentBean = mobileBookEntity.getContent().get(0);
                            mobilePhone = contentBean.getMobile();
                            communityname = contentBean.getCommunity_name();
                            String realName = contentBean.getReal_name();
                            if (TextUtils.isEmpty(realName)) {
                                username = contentBean.getName();
                            } else {
                                username = realName;
                            }
                            nickname = contentBean.getNick_name();
                            portrait = contentBean.getPortrait();
                            gender = contentBean.getGender();
                            userId = contentBean.getUser_id();

                            setUserInfor();
                        }
                    } catch (Exception e) {
                        getUserInfor();
                    }
                } else {
                    getUserInfor();
                }
                break;
        }
    }

    private void getUserInfor() {
        FriendInforEntity friendInforEntity = CacheFriendInforHelper.instance().toQueryFriendnforById(IMFriendInforActivity.this, useruuid);
        if (null != friendInforEntity) {
            mobilePhone = friendInforEntity.getMobile();
            communityname = friendInforEntity.getCommunityName();
            username = friendInforEntity.getUsername();
            if (TextUtils.isEmpty(username)) {
                username = friendInforEntity.getRealName();
            }
            nickname = friendInforEntity.getNickname();
            portrait = friendInforEntity.getPortrait();
            gender = friendInforEntity.getGender();
            setUserInfor();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HuxinSdkManager.instance().getStackAct().removeActivity(this);
    }
}
