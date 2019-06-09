package com.customerInfo.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.BeeFrameworkApp;
import com.BeeFramework.Utils.QRCodeUtil;
import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.external.maxwin.view.XListView;
import com.nohttp.utils.GsonUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.user.UserAppConst;
import com.user.entity.QrCodeEntity;
import com.user.model.NewUserModel;

import java.io.File;

import cn.net.cyberway.R;

/**
 * Created by liusw on 2016/12/25.
 * 我的二维码页面
 */

public class CustomerMakeZXingActivity extends BaseActivity implements NewHttpResponse {

    private ImageView userTopViewBack;
    private TextView userTopViewTitle;
    private XListView lv;
    private FrameLayout czyTitleLayout;
    private View line1;
    private View line2;
    private CircleImageView imgAvatar;
    private TextView tvName;
    private TextView tvCommunity;
    private ImageView ivQrCode;
    private SharedPreferences mShared;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_zxing);
        mShared = getSharedPreferences(UserAppConst.USERINFO, 0);
        user_id = mShared.getInt(UserAppConst.Colour_User_id, 0);
        userTopViewBack = (ImageView) findViewById(R.id.user_top_view_back);
        userTopViewTitle = (TextView) findViewById(R.id.user_top_view_title);
        lv = (XListView) findViewById(R.id.lv);
        czyTitleLayout = (FrameLayout) findViewById(R.id.czy_title_layout);
        initTop();
        initXView();
        String saveCache = mShared.getString(UserAppConst.ZXINGCODE + user_id, "");
        NewUserModel newUserModel = new NewUserModel(this);
        if (!TextUtils.isEmpty(saveCache)) {
            initQRcode(saveCache);
            newUserModel.getQrCode(0, false, this);
        } else {
            newUserModel.getQrCode(0, true, this);
        }
        initData();
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czyTitleLayout, userTopViewBack, userTopViewTitle);
    }

    private void initData() {
        String nickname = mShared.getString(UserAppConst.Colour_NIACKNAME, "");
        String name = mShared.getString(UserAppConst.Colour_NAME, "");
        if (!TextUtils.isEmpty(name)) {
            tvName.setText(name);
        } else if (!TextUtils.isEmpty(nickname)) {
            tvName.setText(nickname);
        } else {
            tvName.setText("彩多多");
        }
        tvCommunity.setText(mShared.getString(UserAppConst.Colour_login_community_name, ""));
        String headImgUrl = mShared.getString(UserAppConst.Colour_head_img, "");
        ImageLoader.getInstance().displayImage(headImgUrl, imgAvatar, BeeFrameworkApp.optionsImage);
    }

    private void initXView() {
        View view = LayoutInflater.from(this).inflate(R.layout.make_zxing_head, null);
        findHeadView(view);
        lv.addHeaderView(view);
        lv.setAdapter(null);
        lv.setDividerHeight(0);
        lv.setPullRefreshEnable(false);
    }

    private void findHeadView(View view) {
        imgAvatar = (CircleImageView) view.findViewById(R.id.img_avatar);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvCommunity = (TextView) view.findViewById(R.id.tv_community);
        ivQrCode = (ImageView) view.findViewById(R.id.iv_qr_code);
        line1 = view.findViewById(R.id.line1);
        line2 = view.findViewById(R.id.line2);
    }

    private void initTop() {
        userTopViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userTopViewTitle.setText(getResources().getString(R.string.title_my_qrcode));
    }

    boolean success = false;

    public void initQRcode(final String url) {
        final String filePath = getFileRoot(CustomerMakeZXingActivity.this) + File.separator
                + "qr_" + System.currentTimeMillis() + ".jpg";
        String headImgUrl = shared.getString(UserAppConst.Colour_head_img, "");

        Glide.with(CustomerMakeZXingActivity.this).load(headImgUrl).apply(new RequestOptions().placeholder(R.drawable.logo3x).error(R.drawable.logo3x))
                .into(new ImageViewTarget<Drawable>(ivQrCode) {

                    @Override
                    protected void setResource(@Nullable Drawable resource) {
                        try {
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;
                            Bitmap bitmap = bitmapDrawable.getBitmap();
                            success = QRCodeUtil.createQRImage(url,
                                    Utils.dip2px(CustomerMakeZXingActivity.this, 250),
                                    Utils.dip2px(CustomerMakeZXingActivity.this, 250),
                                    bitmap, filePath
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (!success) {
                                try {
                                    QRCodeUtil.createQRImage(url,
                                            Utils.dip2px(CustomerMakeZXingActivity.this, 250),
                                            Utils.dip2px(CustomerMakeZXingActivity.this, 250),
                                            BitmapFactory.decodeResource(getResources(), R.drawable.logo3x), filePath);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            ivQrCode.setImageBitmap(BitmapFactory.decodeFile(filePath));
                        }
                    }
                });
    }

    private String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }
        return context.getFilesDir().getAbsolutePath();
    }


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    QrCodeEntity qrCodeEntity = GsonUtils.gsonToBean(result, QrCodeEntity.class);
                    String QrUrl = qrCodeEntity.getContent().getQr_url();
                    if (!TextUtils.isEmpty(QrUrl)) {
                        initQRcode(QrUrl);
                        editor.putString(UserAppConst.ZXINGCODE + user_id, QrUrl).apply();
                    }
                } catch (Exception e) {

                }
                break;
        }
    }
}