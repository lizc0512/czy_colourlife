package com.invite.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.gem.GemConstant;
import com.gem.util.GemDialogUtil;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.entity.InviteCodeEntity;
import com.user.entity.InviteEntity;
import com.user.model.NewUserModel;

import java.util.HashMap;

import cn.net.cyberway.R;
import cn.net.cyberway.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 新邀请好友
 * Created by hxg on 19/5/10.
 */
public class InviteFriendActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private EditText edit_mobile;        // 手机号码
    private ImageView img_contact;        // 通讯录
    private Button btn_invite;         // 立即邀请
    private TextView tv_invite_record;   // 邀请记录
    private TextView tv_invite_success;  // 邀请战绩
    private String inviteTitle;
    private String inviteContent;
    private String inviteUrl;
    private String inviteIcon;
    private ImageView ivGem;
    private TextView tvRight;
    private TextView tvTitle;
    private TextView tv_invite_cancel;
    private PopupWindow popupWindow;
    private RelativeLayout rl_invite_wechat;
    private RelativeLayout rl_invite_pyq;
    private RelativeLayout rl_invite_qq;

    private TextView tv_activity, tv_detail, tv_save_poster, tv_save_face_invite;
    private TextView tv_avail_profit, tv_all_profit, tv_invite_num;
    private CardView cv_invite, cv_change;
    private ImageView imgBack, iv_share;
    private LinearLayout ll_all_profit, ll_invite_num;


    private OnekeyShare oks;
    private NewUserModel newUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        getWindow().setBackgroundDrawable(null);
        initView();
        initData();
    }

    private void initView() {
        tvTitle = findViewById(R.id.user_top_view_title);
        imgBack = findViewById(R.id.user_top_view_back);
        tv_activity = findViewById(R.id.tv_activity);
        tv_detail = findViewById(R.id.tv_detail);
        cv_invite = findViewById(R.id.cv_invite);
        cv_change = findViewById(R.id.cv_change);
        iv_share = findViewById(R.id.iv_share);
        tv_save_poster = findViewById(R.id.tv_save_poster);
        tv_save_face_invite = findViewById(R.id.tv_save_face_invite);
        tv_avail_profit = findViewById(R.id.tv_avail_profit);
        tv_all_profit = findViewById(R.id.tv_all_profit);
        tv_invite_num = findViewById(R.id.tv_invite_num);
//        ll_all_profit = findViewById(R.id.ll_all_profit);
//        ll_invite_num = findViewById(R.id.ll_invite_num);

        imgBack.setOnClickListener(this);
        tv_activity.setOnClickListener(this);
        tv_detail.setOnClickListener(this);
        cv_invite.setOnClickListener(this);
        cv_change.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        tv_save_poster.setOnClickListener(this);
        tv_save_face_invite.setOnClickListener(this);
        tv_all_profit.setOnClickListener(this);
        tv_avail_profit.setOnClickListener(this);
//        ll_all_profit.setOnClickListener(this);
//        ll_invite_num.setOnClickListener(this);
    }

    private void initData() {
        tvTitle.setText(getString(R.string.invite_title));
        newUserModel = new NewUserModel(this);
        newUserModel.inviteCode(0, this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_activity://活动
                intent = new Intent(this, InviteActivityActivity.class);
                intent.putExtra(InviteActivityActivity.ENTER_TYPE, "act");
                startActivity(intent);
                break;
            case R.id.tv_detail://明细
                intent = new Intent(this, InviteActivityActivity.class);
                intent.putExtra(InviteActivityActivity.ENTER_TYPE, "detail");
                startActivity(intent);
                break;
            case R.id.cv_invite://我的邀请
                intent = new Intent(this, InviteMyActivity.class);
                startActivity(intent);
                break;
            case R.id.cv_change://收益兑换
                intent = new Intent(this, InviteChangeActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_share://分享
                initPopup();
                break;
            case R.id.tv_save_poster://保存海报
                break;
            case R.id.tv_save_face_invite://当面邀请
                break;
            case R.id.tv_all_profit://累计收益
            case R.id.tv_avail_profit://累计收益
                intent = new Intent(this, InviteProfitActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 邀请好友
     */
    private void initPopup() {
        View contentview = LayoutInflater.from(this).inflate(R.layout.activity_invite_share, null);
        popupWindow = new PopupWindow(contentview,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                true);
        tv_invite_cancel = contentview.findViewById(R.id.tv_invite_cancel);
        rl_invite_wechat = contentview.findViewById(R.id.rl_invite_wechat);
        rl_invite_pyq = contentview.findViewById(R.id.rl_invite_pyq);
        rl_invite_qq = contentview.findViewById(R.id.rl_invite_qq);
        rl_invite_wechat.setOnClickListener(v -> {
            Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
            showShare(InviteFriendActivity.this, wechat.getName());
            popupWindow.dismiss();
        });
        rl_invite_pyq.setOnClickListener(v -> {
            Platform friend = ShareSDK.getPlatform(WechatMoments.NAME);
            showShare(InviteFriendActivity.this, friend.getName());
            popupWindow.dismiss();
        });
        rl_invite_qq.setOnClickListener(v -> {
            Platform friend = ShareSDK.getPlatform(QQ.NAME);
            showShare(InviteFriendActivity.this, friend.getName());
            popupWindow.dismiss();
        });
        tv_invite_cancel.setOnClickListener(v -> popupWindow.dismiss());
        setBackgroundAlpha(0.8f);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置位置
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_invite_friend, null);
        popupWindow.showAtLocation(rootview, Gravity.TOP, 0, 0);
        popupWindow.setOnDismissListener(() -> setBackgroundAlpha(1.0f));
        editor.putBoolean(UserAppConst.LINLISHOWPOP, true);
        editor.commit();
    }

    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    /**
     * 调用ShareSDK执行分享
     *
     * @param context
     * @param platformToShare 指定直接分享平台名称（一旦设置了平台名称，则九宫格将不会显示）
     */
    public void showShare(Context context, String platformToShare) {
        oks = new OnekeyShare();
        oks.setSilent(true);
        //隐藏自带的分享列表
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        oks.setTitle(inviteTitle);
        oks.setTitleUrl(inviteUrl);
        oks.setText(inviteContent);
        oks.setImageUrl(inviteIcon);
        oks.setUrl(inviteUrl);
        // 将快捷分享的操作结果将通过OneKeyShareCallback回调
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                InviteFriendActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.toastShow(InviteFriendActivity.this, "分享成功");
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                InviteFriendActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.toastShow(InviteFriendActivity.this, "分享失败");
                    }
                });
            }

            @Override
            public void onCancel(Platform platform, int i) {
                InviteFriendActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
        // 启动分享
        oks.show(context);
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    InviteCodeEntity inviteCodeEntity = GsonUtils.gsonToBean(result, InviteCodeEntity.class);
                    InviteCodeEntity.ContentBean contentBean = inviteCodeEntity.getContent();
                    inviteContent = contentBean.getContent();
                    inviteIcon = contentBean.getIcon();
                    inviteUrl = contentBean.getRedirect_url();
                    inviteTitle = contentBean.getTitle();
                } catch (Exception e) {

                }
                break;
            case 1:
                try {
                    InviteEntity inviteEntity = GsonUtils.gsonToBean(result, InviteEntity.class);
                    ToastUtil.toastShow(InviteFriendActivity.this, inviteEntity.getContent().getInvite_message());
                    GemDialogUtil.showGemDialog(ivGem, this, GemConstant.mineInvite, "");
                } catch (Exception e) {

                }
                break;
        }
    }
}
