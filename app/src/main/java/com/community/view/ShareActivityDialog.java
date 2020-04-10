package com.community.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mob.MobSDK;

import java.util.HashMap;

import cn.net.cyberway.R;
import cn.net.cyberway.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * author:yuansk
 * cretetime:2020/3/19
 * desc:分享活动的对话框
 **/
public class ShareActivityDialog extends Dialog {

    Context context;
    public TextView tv_share_friend;
    public TextView tv_share_circle;
    public TextView tv_cancel_share;

    public ShareActivityDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
        MobSDK.init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_share_activity);
        tv_share_friend = findViewById(R.id.tv_share_friend);
        tv_share_circle = findViewById(R.id.tv_share_circle);
        tv_cancel_share = findViewById(R.id.tv_cancel_share);
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        int density = getWidthPixels(context);
        params.width = density;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        this.setCanceledOnTouchOutside(true);
        tv_cancel_share.setOnClickListener(v -> dismiss());
        tv_share_friend.setOnClickListener(v -> {
            Platform friend = ShareSDK.getPlatform(Wechat.NAME);
            showShare(friend.getName());
            dismiss();
        });
        tv_share_circle.setOnClickListener(v -> {
            Platform friend = ShareSDK.getPlatform(WechatMoments.NAME);
            showShare(friend.getName());
            dismiss();
        });
    }


    private String shareTitle;
    private String shareText;
    private String shareUrl;

    public void setShareContent(String shareTitle, String shareText, String shareUrl) {
        this.shareTitle = shareTitle;
        this.shareText = shareText;
        this.shareUrl = shareUrl;
    }

    private void showShare(String platformToShare) {
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(true);
        //隐藏自带的分享列表
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        oks.disableSSOWhenAuthorize();
        oks.setTitle(shareTitle);
        oks.setText("快来参加活动吧".trim());
        oks.setImageData(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.share_default_logo));
        if (TextUtils.isEmpty(shareUrl)) {
            shareUrl = "http://m.colourlife.com/doubleCode?code=1390620762&sign=553FCC9A69A55F1BE686F6AF85E42154";
        }
        oks.setUrl(shareUrl);
        // 将快捷分享的操作结果将通过OneKeyShareCallback回调
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        // 启动分享
        oks.show(context);
    }

    private int getWidthPixels(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
}
