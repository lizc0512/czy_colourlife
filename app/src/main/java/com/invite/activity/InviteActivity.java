package com.invite.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.mob.MobSDK;
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
 * 邀请好友
 * Created by chenql on 16/1/8.
 */
public class InviteActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

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
    private ImageView imgBack;
    private TextView tvRight;
    private TextView tvTitle;
    private TextView tv_invite_cancel;
    private PopupWindow popupWindow;
    private RelativeLayout rl_invite_wechat;
    private RelativeLayout rl_invite_pyq;
    private RelativeLayout rl_invite_qq;
    private OnekeyShare oks;
    private NewUserModel newUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        initTopView();
        MobSDK.init(this);
        initView();
        initData();
    }

    private void initData() {
        newUserModel = new NewUserModel(this);
        newUserModel.inviteCode(0, this);
    }

    private void initView() {
        edit_mobile = (EditText) findViewById(R.id.edit_mobile);
        img_contact = (ImageView) findViewById(R.id.img_contact);
        btn_invite = (Button) findViewById(R.id.btn_invite);
        tv_invite_record = (TextView) findViewById(R.id.tv_invite_record);
        tv_invite_success = (TextView) findViewById(R.id.tv_invite_success);
        imgBack = (ImageView) findViewById(R.id.user_top_view_back);
        tvRight = (TextView) findViewById(R.id.user_top_view_right);
        tvTitle = (TextView) findViewById(R.id.user_top_view_title);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(getString(R.string.share));
        tvRight.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        img_contact.setOnClickListener(this);
        btn_invite.setOnClickListener(this);
        tv_invite_record.setOnClickListener(this);
        tv_invite_success.setOnClickListener(this);
        tvTitle.setText(getString(R.string.invite_friends));
        ivGem = (ImageView) findViewById(R.id.iv_gem);
    }

    private void initTopView() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, imgBack, tvTitle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                initPopup();
                break;
            case R.id.img_contact:
                // 通讯录
                Intent intent = new Intent(InviteActivity.this, ContactsActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.btn_invite:
                // 邀请
                if (checkInput()) {
                    newUserModel.inviteRegister(1, edit_mobile.getText().toString(), this);
                }
                break;
            case R.id.tv_invite_record:
                // 邀请记录
                InviteRecordActivity.actionStart(InviteActivity.this, false);
                break;
            case R.id.tv_invite_success:
                // 邀请战绩
                InviteRecordActivity.actionStart(InviteActivity.this, true);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1002) {
            edit_mobile.setText(Utils.trimTelNum(data.getStringExtra("mobile")));
            edit_mobile.setSelection(edit_mobile.getText().toString().length());
        }
    }

    private boolean checkInput() {
        String input = edit_mobile.getText().toString();
        if (!TextUtils.isEmpty(input) && input.length() == 11) {
            return true;
        } else {
            ToastUtil.toastShow(InviteActivity.this, getResources().getString(R.string.invite_correct_phone));
            return false;
        }
    }


    private void initPopup() {
        View contentview = LayoutInflater.from(this).inflate(R.layout.activity_invite_share, null);
        popupWindow = new PopupWindow(contentview,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                true);
        tv_invite_cancel = (TextView) contentview.findViewById(R.id.tv_invite_cancel);
        rl_invite_wechat = (RelativeLayout) contentview.findViewById(R.id.rl_invite_wechat);
        rl_invite_pyq = (RelativeLayout) contentview.findViewById(R.id.rl_invite_pyq);
        rl_invite_qq = (RelativeLayout) contentview.findViewById(R.id.rl_invite_qq);
        rl_invite_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                showShare(InviteActivity.this, wechat.getName());
                popupWindow.dismiss();
            }
        });
        rl_invite_pyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform friend = ShareSDK.getPlatform(WechatMoments.NAME);
                showShare(InviteActivity.this, friend.getName());
                popupWindow.dismiss();
            }
        });
        rl_invite_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform friend = ShareSDK.getPlatform(QQ.NAME);
                showShare(InviteActivity.this, friend.getName());
                popupWindow.dismiss();
            }
        });
        tv_invite_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        setBackgroundAlpha(0.8f);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置位置
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_invite, null);
        popupWindow.showAtLocation(rootview, Gravity.TOP, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
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
                InviteActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.toastShow(InviteActivity.this, "分享成功");
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                InviteActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.toastShow(InviteActivity.this, "分享失败");
                    }
                });
            }

            @Override
            public void onCancel(Platform platform, int i) {
                InviteActivity.this.runOnUiThread(new Runnable() {
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
                    ToastUtil.toastShow(InviteActivity.this, inviteEntity.getContent().getInvite_message());
                } catch (Exception e) {

                }
                break;
        }
    }
}
