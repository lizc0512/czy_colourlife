package com.setting.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.NetworkUtil;
import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.BeeFramework.model.NewHttpResponse;
import com.about.activity.AboutActivity;
import com.external.eventbus.EventBus;
import com.mob.MobSDK;
import com.setting.switchButton.SwitchButton;
import com.tendcloud.tenddata.TCAgent;
import com.update.activity.UpdateVerSion;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.model.NewUserModel;
import com.user.model.TokenModel;
import com.user.protocol.SingleDeviceLoginOutApi;
import com.youmai.hxsdk.HuxinSdkManager;
import com.youmai.hxsdk.im.IMMsgManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.jpush.android.api.JPushInterface;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.BroadcastReceiverActivity;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 设置
 * Created by liusw on 2016/1/8.
 */
public class SettingActivity extends BaseActivity implements HttpApiResponse, View.OnClickListener, NewHttpResponse {

    public static final String FROM_COLOUR_BEAN = "from_colour_bean";

    private ImageView mBack;
    private TextView mTitle;
    private RelativeLayout account_safty;
    private RelativeLayout user_conceal;
    private LinearLayout rl_clear_cache;
    private RelativeLayout rl_about;
    private Button exit_btn;

    private SwitchButton voice_sb;
    private SwitchButton sb_notification;
    private TokenModel mTokenModel;

    private EditDialog mEditDialog;
    private BroadcastReceiverActivity broadcast;

    private TextView tv_current_language;
    private TextView tv_safe;
    private RelativeLayout rl_choice_language;

    private boolean isNotify = false;
    private boolean isVoice = false;
    private List<String> languageList = new ArrayList<>();
    private NewUserModel newUserModel;
    private boolean fromColourToast = false;//是否从彩豆进入 和控制只toast一次

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initPublic();
        initView();
        initData();
        sendMsgColourBean();//发送设置消息的彩豆
    }

    private void initPublic() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        account_safty = findViewById(R.id.account_safty);
        tv_safe = findViewById(R.id.tv_safe);
        user_conceal = (RelativeLayout) findViewById(R.id.user_conceal);
        rl_clear_cache = findViewById(R.id.rl_clear_cache);
        rl_about = (RelativeLayout) findViewById(R.id.rl_about);
        account_safty.setOnClickListener(this);
        user_conceal.setOnClickListener(this);
        rl_clear_cache.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        mBack = (ImageView) findViewById(R.id.user_top_view_back);
        mTitle = (TextView) findViewById(R.id.user_top_view_title);
        tv_current_language = findViewById(R.id.tv_current_language);
        rl_choice_language = findViewById(R.id.rl_choice_language);
        mBack.setOnClickListener(this);
        rl_choice_language.setOnClickListener(this);
        mTitle.setText(getResources().getString(R.string.title_setting));
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, mBack, mTitle);
        String[] languageArr = getResources().getStringArray(R.array.language_list);
        for (int j = 0; j < languageArr.length; j++) {
            languageList.add(languageArr[j]);
        }
        int choiceLanguage = shared.getInt(UserAppConst.CURRENTLANGUAGE, 0);
        if (choiceLanguage > 0) {
            tv_current_language.setText(languageList.get(choiceLanguage - 1));
        } else {
            tv_current_language.setText(Locale.getDefault().getDisplayLanguage());
        }
        UpdateVerSion mUpdateVerSion = new UpdateVerSion();
        mUpdateVerSion.getNewVerSion("2", false, SettingActivity.this);
    }

    private void initView() {
        voice_sb = (SwitchButton) findViewById(R.id.voice_sb);
        sb_notification = (SwitchButton) findViewById(R.id.sb_notifi);
        isNotify = HuxinSdkManager.instance().isNotify();
        isVoice = shared.getBoolean("VOICE", true);

        voice_sb.setOpened(isNotify);
        sb_notification.setOpened(isVoice);

        voice_sb.setOnStateChangedListener(new SwitchButton.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                voice_sb.setOpened(true);
                HuxinSdkManager.instance().setNotify(true);
            }

            @Override
            public void toggleToOff(View view) {
                voice_sb.setOpened(false);
                HuxinSdkManager.instance().setNotify(false);
            }
        });
        sb_notification.setOnStateChangedListener(new SwitchButton.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                editor.putBoolean("VOICE", true);
                editor.commit();
                sb_notification.setOpened(true);
                // 点击恢复按钮后，极光推送服务会恢复正常工作
                JPushInterface.resumePush(getApplicationContext());
            }

            @Override
            public void toggleToOff(View view) {
                editor.putBoolean("VOICE", false);
                editor.commit();
                sb_notification.setOpened(false);
                // 点击停止按钮后，极光推送服务会被停止掉
                JPushInterface.stopPush(getApplicationContext());
            }
        });
        exit_btn = (Button) findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(this);
        mTokenModel = new TokenModel(this);
        mEditDialog = new EditDialog(this);
    }

    private void initData() {
        newUserModel = new NewUserModel(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.account_safty:
                intent = new Intent(this, UserAccountSaftyActivity.class);
                startActivity(intent);
                break;
            case R.id.user_conceal:
                intent = new Intent(this, UserConcealActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_clear_cache:
                intent = new Intent(this, UserClearCacheActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_choice_language:
                intent = new Intent(SettingActivity.this, ChoiceLanguageActivity.class);
                startActivityForResult(intent, 1000);
                break;
            case R.id.rl_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.exit_btn:
                // 退出登录
                mEditDialog.show();
                mEditDialog.left_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEditDialog.dismiss();
                    }
                });
                mEditDialog.right_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEditDialog.dismiss();
                        if (NetworkUtil.isConnect(SettingActivity.this)) {
                            mTokenModel.deviceLogout(SettingActivity.this);
                            HuxinSdkManager.instance().loginOut();
                            IMMsgManager.instance().cancelPushMsg();
                        } else {
                            ToastUtil.toastShow(SettingActivity.this, getResources().getString(R.string.app_network_error));
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(SingleDeviceLoginOutApi.class)) {
            editor.putBoolean(UserAppConst.IS_LOGIN, false).apply();
            JPushInterface.stopPush(SettingActivity.this);
            ToastUtil.toastShow(this, getResources().getString(R.string.login_off_success));
            cleanThirdData(this);
            Message msg = new Message();
            msg.what = UserMessageConstant.LOGOUT;//退出登录之后
            EventBus.getDefault().post(msg);
            finish();
        }
    }

    /**
     * 发送设置消息的彩豆  保留
     */
    private void sendMsgColourBean() {
//        fromColourToast = getIntent().getBooleanExtra(FROM_COLOUR_BEAN, false);//是否从彩豆页面进入
//        if (isNotify) {//有新消息提醒
//            mHandler.sendEmptyMessageDelayed(0, 0);
//        }
//        if (isVoice) {//有消息推送
//            mHandler.sendEmptyMessageDelayed(1, 300);
//        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 1://设置消息的彩豆
                if (fromColourToast) {
                    fromColourToast = false;
                    ToastUtil.toastShow(this, "您已获得彩豆啦~");
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        //注册广播
        broadcast = new BroadcastReceiverActivity(this);
        IntentFilter intentFilter = new IntentFilter(BroadcastReceiverActivity.GESTURE);
        registerReceiver(broadcast, intentFilter);
        super.onResume();
        TCAgent.onPageStart(getApplicationContext(), "设置");
    }

    @Override
    protected void onDestroy() {
        //销毁广播注册
        if (broadcast != null) {
            unregisterReceiver(broadcast);
            broadcast = null;
        }
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 清空第三方登录数据
     */
    public static void cleanThirdData(Context context) {
        MobSDK.init(context);
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        if (qq.isAuthValid()) {
            qq.removeAccount(true);
        }
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        if (wechat.isAuthValid()) {
            wechat.removeAccount(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        TCAgent.onPageEnd(getApplicationContext(), "设置");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 200) {
            finish();
        }
    }

    private InterHandler mHandler = new InterHandler(this);

    private static class InterHandler extends Handler {
        private WeakReference<SettingActivity> mActivity;

        InterHandler(SettingActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SettingActivity activity = mActivity.get();
            String type;
            if (activity != null) {
                switch (msg.what) {
                    case 0:
                        type = "notice";
                        activity.newUserModel.sendMsgColourBean(1, type, activity);
                        break;
                    case 1:
                        type = "push";
                        activity.newUserModel.sendMsgColourBean(1, type, activity);
                        break;
                }
            } else {
                super.handleMessage(msg);
            }
        }
    }
}
