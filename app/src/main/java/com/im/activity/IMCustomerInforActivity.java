package com.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.Constants;
import com.BeeFramework.model.NewHttpResponse;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.dashuview.library.keep.Cqb_PayUtil;
import com.dashuview.library.keep.ListenerUtils;
import com.dashuview.library.keep.MyListener;
import com.feed.activity.PersonalFeedActivity;
import com.im.entity.MobileBookEntity;
import com.im.model.IMUploadPhoneModel;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.youmai.hxsdk.HuxinSdkManager;
import com.youmai.hxsdk.router.APath;

import cn.net.cyberway.R;

import static com.BeeFramework.activity.WebViewActivity.SCAN_GIVE_TICKET;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/20 16:57
 * @change
 * @chang time
 * @class describe  彩之云注册用户但不是好友的资料
 */
@Route(path = APath.BUDDY_NOT_FRIEND)
public class IMCustomerInforActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse, MyListener {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ImageView user_photo;
    private TextView user_nickname;
    private ImageView user_sex;
    private TextView tv_community;
    private RelativeLayout details_community_layout;
    private Button btn_add_friend;
    private Button btn_transfer_accounts;
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
        setContentView(R.layout.activity_customer_information);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        user_photo = findViewById(R.id.user_photo);
        user_nickname = findViewById(R.id.user_nickname);
        user_sex = findViewById(R.id.user_sex);
        details_community_layout = findViewById(R.id.details_community_layout);
        tv_community = findViewById(R.id.tv_community);
        btn_add_friend = findViewById(R.id.btn_add_friend);
        btn_transfer_accounts = findViewById(R.id.btn_transfer_accounts);
        user_top_view_back.setOnClickListener(this);
        details_community_layout.setOnClickListener(this);
        btn_add_friend.setOnClickListener(this);
        btn_transfer_accounts.setOnClickListener(this);
        user_top_view_title.setText(getResources().getString(R.string.instant_detail_infor));
        Intent intent = getIntent();
        useruuid = intent.getStringExtra(IMFriendInforActivity.USERUUID);
        IMUploadPhoneModel imUploadPhoneModel = new IMUploadPhoneModel(IMCustomerInforActivity.this);
        imUploadPhoneModel.getUserInforByUuid(0, useruuid, true, this);
        ListenerUtils.setCallBack(this);
        HuxinSdkManager.instance().getStackAct().addActivity(this);
    }

    private void setUserInfor() {
        GlideImageLoader.loadImageDefaultDisplay(IMCustomerInforActivity.this, portrait, user_photo, R.drawable.im_icon_default_head, R.drawable.im_icon_default_head);
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
            case R.id.details_community_layout:
                Intent LiLinIntent = new Intent(this, PersonalFeedActivity.class);
                LiLinIntent.putExtra(PersonalFeedActivity.USERID,userId);
                LiLinIntent.putExtra(IMInviteRegisterActivity.USERNAME,username);
                startActivity(LiLinIntent);
                break;
            case R.id.btn_add_friend:  //调用IM的接口
                Intent intent = new Intent(IMCustomerInforActivity.this, IMApplyFriendActivity.class);
                intent.putExtra(IMInviteRegisterActivity.USERPHONE, mobilePhone);
                intent.putExtra(IMInviteRegisterActivity.USERNAME, username);
                intent.putExtra(IMInviteRegisterActivity.USERNICKNAME, nickname);
                intent.putExtra(IMInviteRegisterActivity.USERGENDER, gender);
                intent.putExtra(IMInviteRegisterActivity.USERPORTRAIT, portrait);
                intent.putExtra(IMFriendInforActivity.USERUUID, useruuid);
                intent.putExtra(IMInviteRegisterActivity.USECOMMUNITYNAME, communityname);
                startActivity(intent);
                break;
            case R.id.btn_transfer_accounts:
                if (TextUtils.isEmpty(mobilePhone)) {
                    ToastUtil.toastShow(IMCustomerInforActivity.this, "被转账人手机号码为空");
                } else {
                    Cqb_PayUtil.getInstance(IMCustomerInforActivity.this).skipActivityForFlag(Utils.getPublicParams(IMCustomerInforActivity.this), mobilePhone, SCAN_GIVE_TICKET, Constants.CAIWALLET_ENVIRONMENT);
                }
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
                            details_community_layout.setVisibility(View.VISIBLE);
                            MobileBookEntity.ContentBean contentBean = mobileBookEntity.getContent().get(0);
                            mobilePhone = contentBean.getMobile();
                            userId=contentBean.getUser_id();
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
                            setUserInfor();
                        }
                    } catch (Exception e) {

                    }
                }
                break;
        }
    }

    @Override
    public void authenticationFeedback(String s, int i) {
        ToastUtil.toastShow(IMCustomerInforActivity.this, s);
    }

    @Override
    public void toCFRS(String s) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HuxinSdkManager.instance().getStackAct().removeActivity(this);
    }
}
