package com.setting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.activity.CustomerPwdActivity;
import com.mob.MobSDK;
import com.mob.tools.utils.UIHandler;
import com.nohttp.utils.GsonUtils;
import com.setting.switchButton.SwitchButton;
import com.user.UserAppConst;
import com.user.entity.ChangeMobileEntity;
import com.user.entity.ThridBindStatusEntity;
import com.user.model.NewUserModel;

import java.util.HashMap;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import static com.setting.activity.VerifyLoginPwdActivity.PAWDTYPE;

/**
 * @name ${yuansk}
 * @class name：com.setting.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/13 9:28
 * @change
 * @chang time
 * @class describe
 */

public class UserAccountSaftyActivity extends BaseActivity implements View.OnClickListener, Handler.Callback, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private RelativeLayout gesture_pwd_layout;
    private RelativeLayout pay_pswd_layout;
    private RelativeLayout change_mobile_layout;
    private RelativeLayout logout_phone_layout;
    private String changeMobileUrl;
    private int is_show = 0;
    private int loginout_show = 0;
    private String loginoutUrl = "";
    private LinearLayout set_pwd_layout;
    private LinearLayout ll_auth_manege;
    private TextView tv_login_pwd;
    private TextView tv_is_setpawd;
    private SwitchButton sb_open_qq;
    private SwitchButton sb_open_wechat;
    private TextView tv_qq_status;
    private TextView tv_wechat_status;
    private String isSetPawd;
    private String source;
    private NewUserModel newUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safty);
        MobSDK.init(getApplicationContext());
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        tv_login_pwd = findViewById(R.id.tv_login_pwd);
        tv_is_setpawd = findViewById(R.id.tv_is_setpawd);
        gesture_pwd_layout = findViewById(R.id.gesture_pwd_layout);
        pay_pswd_layout = findViewById(R.id.pay_pswd_layout);
        change_mobile_layout = findViewById(R.id.change_mobile_layout);
        logout_phone_layout = findViewById(R.id.logout_phone_layout);
        set_pwd_layout = findViewById(R.id.set_pwd_layout);  //为设置密码显示
        sb_open_qq = findViewById(R.id.sb_open_qq);
        sb_open_wechat = findViewById(R.id.sb_open_wechat);
        tv_qq_status = findViewById(R.id.tv_qq_status);
        ll_auth_manege = findViewById(R.id.ll_auth_manege);
        tv_wechat_status = findViewById(R.id.tv_wechat_status);
        user_top_view_back.setOnClickListener(this);
        gesture_pwd_layout.setOnClickListener(this);
        pay_pswd_layout.setOnClickListener(this);
        change_mobile_layout.setOnClickListener(this);
        logout_phone_layout.setOnClickListener(this);
        ll_auth_manege.setOnClickListener(this);
        set_pwd_layout.setOnClickListener(this);
        //忘记手势密码  有登录密码验证登录密码   没有就通过短信验证码的方式进行
        sb_open_qq.setOnStateChangedListener(new SwitchButton.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                source = "qq";
                authorize(qq);
                sb_open_qq.setOpened(true);
            }

            @Override
            public void toggleToOff(View view) {
                source = "qq";
                newUserModel.unbindThridSource(4, source, UserAccountSaftyActivity.this);
                sb_open_qq.setOpened(false);
            }
        });

        sb_open_wechat.setOnStateChangedListener(new SwitchButton.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                source = "wechat";
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                authorize(wechat);
                sb_open_wechat.setOpened(true);
            }

            @Override
            public void toggleToOff(View view) {
                source = "wechat";
                newUserModel.unbindThridSource(4, source, UserAccountSaftyActivity.this);
                sb_open_wechat.setOpened(false);
            }
        });
        user_top_view_title.setText(getResources().getString(R.string.account_safty));
        isSetPawd = shared.getString(UserAppConst.Colour_set_password, "1");
        if ("1".equals(isSetPawd)) {
            tv_is_setpawd.setVisibility(View.INVISIBLE);
            tv_login_pwd.setText("修改登录密码");
        } else {
            tv_is_setpawd.setVisibility(View.VISIBLE);
            tv_login_pwd.setText("登录密码");
        }
        String mobile = shared.getString(UserAppConst.Colour_login_mobile, "");
        newUserModel = new NewUserModel(this);
        newUserModel.getChangeMobileEnter(1, mobile, 2, false, this);
        newUserModel.getThridBindStatus(2, this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.set_pwd_layout:
                String isSetPawd = shared.getString(UserAppConst.Colour_set_password, "1");
                if ("1".equals(isSetPawd)) {
                    intent = new Intent(this, CustomerPwdActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(UserAccountSaftyActivity.this, VerifyLoginPwdActivity.class);
                    intent.putExtra(PAWDTYPE, 0);
                    startActivityForResult(intent, 2000);
                }
                break;
            case R.id.ll_auth_manege:
                intent = new Intent(this, AuthManegeListActivity.class);
                startActivity(intent);
                break;
            case R.id.gesture_pwd_layout:
                intent = new Intent(this, GesturePwdMainActivity.class);
                startActivity(intent);
                break;
            case R.id.change_mobile_layout:
                if (!TextUtils.isEmpty(changeMobileUrl)) {
                    LinkParseUtil.parse(UserAccountSaftyActivity.this, changeMobileUrl, "");
                }
                break;
            case R.id.logout_phone_layout:
                if (!TextUtils.isEmpty(loginoutUrl)) {
                    LinkParseUtil.parse(UserAccountSaftyActivity.this, loginoutUrl, "");
                }
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        ChangeMobileEntity changeMobileEntity = com.nohttp.utils.GsonUtils.gsonToBean(result, ChangeMobileEntity.class);
                        ChangeMobileEntity.ContentBean contentBean = changeMobileEntity.getContent();
                        if (null != contentBean) {
                            is_show = contentBean.getShow();
                            changeMobileUrl = contentBean.getUrl();
                            loginout_show = contentBean.getIs_logout();
                            loginoutUrl = contentBean.getLogout_url();
                        }
                        if (is_show == 1) {
                            change_mobile_layout.setVisibility(View.VISIBLE);
                        } else {
                            change_mobile_layout.setVisibility(View.GONE);
                        }
                        if (loginout_show == 1) {
                            logout_phone_layout.setVisibility(View.VISIBLE);
                        } else {
                            logout_phone_layout.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {

                    }
                }
                break;
            case 2:
                try {
                    ThridBindStatusEntity thridBindStatusEntity = GsonUtils.gsonToBean(result, ThridBindStatusEntity.class);
                    ThridBindStatusEntity.ContentBean contentBean = thridBindStatusEntity.getContent();
                    if (contentBean.getBind_qq() == 1) {
                        sb_open_qq.setOpened(true);
                    } else {
                        sb_open_qq.setOpened(false);
                    }
                    if (contentBean.getBind_weixin() == 1) {
                        sb_open_wechat.setOpened(true);
                    } else {
                        sb_open_wechat.setOpened(false);
                    }
                } catch (Exception e) {

                }
                break;
            case 3:
                if (TextUtils.isEmpty(result)) {
                    if ("qq".equals(source)) {
                        sb_open_qq.setOpened(false);
                    } else {
                        sb_open_wechat.setOpened(false);
                    }
                } else {
                    ToastUtil.toastShow(UserAccountSaftyActivity.this, "绑定成功");
                }
                break;
            case 4:
                if (TextUtils.isEmpty(result)) {
                    if ("qq".equals(source)) {
                        sb_open_qq.setOpened(true);
                    } else {
                        sb_open_wechat.setOpened(true);
                    }
                } else {
                    ToastUtil.toastShow(UserAccountSaftyActivity.this, "已解绑");
                }
                break;
        }
    }


    private void authorize(final Platform plat) {  //1是绑定qq 0是解绑
        if (plat == null) {
            return;
        }
        //判断指定平台是否已经完成授权
        if (plat.isAuthValid()) {
            //如果已经授权，直接调用第三方登录接口
            String userId = plat.getDb().getUserId();
            if (userId != null) {
                Message msg = new Message();
                msg.what = 200;
                msg.obj = plat;
                UIHandler.sendMessage(msg, this);
                return;
            }
        }
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //判断当前的opencode是否注册
                PlatformDb loginPlatformDb = platform.getDb();
                String openCode = loginPlatformDb.getUserId();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newUserModel.bindThridSource(3, source, openCode, UserAccountSaftyActivity.this);
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                if ("qq".equals(source)) {
                    sb_open_qq.setOpened(false);
                } else {
                    sb_open_wechat.setOpened(false);
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {
                if ("qq".equals(source)) {
                    sb_open_qq.setOpened(false);
                } else {
                    sb_open_wechat.setOpened(false);
                }
            }
        });
        // true不使用SSO授权，false使用SSO授权(调用客户端)
        plat.SSOSetting(false);
        //获取用户资料
        plat.authorize();
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 200:
                Platform platform = (Platform) msg.obj;
                PlatformDb loginPlatformDb = platform.getDb();
                String userId = loginPlatformDb.getUserId(); //用户在微信或qq对应的唯一id
                newUserModel.bindThridSource(3, source, userId, this);
                break;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            finish();
        }
    }
}
