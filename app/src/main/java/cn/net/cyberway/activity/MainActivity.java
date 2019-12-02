package cn.net.cyberway.activity;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseFragmentActivity;
import com.BeeFramework.model.Constants;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.BeeFramework.model.NewHttpResponse;
import com.ScreenManager;
import com.audio.activity.RoomActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.door.activity.NoRightDoorActivity;
import com.door.entity.OpenDoorResultEntity;
import com.door.model.NewDoorModel;
import com.door.view.ShowOpenDoorDialog;
import com.external.eventbus.EventBus;
import com.feed.FeedConstant;
import com.im.model.IMUploadPhoneModel;
import com.jpush.Constant;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.RequestEncryptionUtils;
import com.popupScreen.PopupScUtils;
import com.popupScreen.model.PopupModel;
import com.scanCode.activity.CaptureActivity;
import com.sobot.chat.SobotApi;
import com.sobot.chat.utils.ScreenUtils;
import com.umeng.analytics.MobclickAgent;
import com.update.activity.UpdateVerSion;
import com.update.service.UpdateService;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.entity.CheckRegisterEntity;
import com.user.model.NewUserModel;
import com.user.model.RequestFailModel;
import com.user.model.TokenModel;
import com.user.protocol.CheckDeviceLoginApi;
import com.user.protocol.CheckDeviceLoginResponse;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.youmai.hxsdk.HuxinSdkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.service.PluginOppoPushService;
import cn.net.cyberway.R;
import cn.net.cyberway.fagment.BenefitFragment;
import cn.net.cyberway.fagment.InstantMessageFragment;
import cn.net.cyberway.fagment.ProfileFragment;
import cn.net.cyberway.home.entity.PushNotificationEntity;
import cn.net.cyberway.home.fragment.MainHomeFragmentNew;
import cn.net.cyberway.home.fragment.NologinHomeFragment;
import cn.net.cyberway.model.ThemeModel;
import cn.net.cyberway.protocol.ThemeEntity;
import cn.net.cyberway.utils.BuryingPointUtils;
import cn.net.cyberway.utils.LekaiHelper;
import cn.net.cyberway.utils.LinkParseUtil;
import cn.net.cyberway.view.BenefitActivityDialog;
import q.rorbin.badgeview.QBadgeView;

import static cn.net.cyberway.utils.BuryingPointUtils.UPLOAD_DETAILS;
import static cn.net.cyberway.utils.IMFriendDataUtils.getFriendListData;
import static cn.net.cyberway.utils.IMFriendDataUtils.saveFriendData;
import static cn.net.cyberway.utils.IMFriendDataUtils.userInitImData;
import static cn.net.cyberway.utils.TableLayoutUtils.addSelectorFromDrawable;
import static cn.net.cyberway.utils.TableLayoutUtils.addSeletorFromNet;
import static cn.net.cyberway.utils.TableLayoutUtils.addTVSeletor;
import static cn.net.cyberway.utils.TableLayoutUtils.jumpLoginPage;
import static cn.net.cyberway.utils.TableLayoutUtils.shortEnter;
import static cn.net.cyberway.utils.TableLayoutUtils.showOpenDoorResultDialog;
import static com.user.Utils.TokenUtils.clearUserCache;


/**
 * 主界面
 */

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener, HttpApiResponse, NewHttpResponse {
    public static final int FLAG_TAB_ONE = 1;
    public static final int FLAG_TAB_TWO = 2;
    public static final int FLAG_TAB_THREE = 3;
    public static final int FLAG_TAB_FOUR = 4;
    public static final String CALLSETTTING = "callsettting";
    public static final String JUMPOTHERURL = "jumpotherurl";
    private MainHomeFragmentNew mHomeFragment;
    private NologinHomeFragment nologinHomeFragment;
    private InstantMessageFragment instantMessageFragment;//消息
    private BenefitFragment benefitFragment;
    private ProfileFragment profileFragment;
    private LinearLayout mHome;
    private LinearLayout mCommunity;
    private LinearLayout mDiscovery;
    private LinearLayout mProfile;
    private RelativeLayout mToolBar;
    private LinearLayout main_tool_scan;
    private ImageView circle_scanner_image;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private TokenModel mTokenModel;
    private UpdateVerSion mUpdateVerSion;
    private boolean mIsExit = false;
    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;
    private int userId;//用户的ID
    // app内通知
    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mReceiver;
    private String mPhoneStr;
    private PopupModel popupModel;
    public static NotificationManager myManager = null;
    public static final int NOTIFICATION_ID_1 = 1;
    private boolean slienceLogin = true;
    public int choiceType = 0;  //判断是否在我的页面
    private ImageView home_btn;
    private ImageView life_btn;
    private ImageView Scande_btn;
    private ImageView linli_btn;
    private ImageView myinfo_btn;
    private TextView tv_home;
    private TextView tv_life;
    private TextView tv_linli;
    private TextView tv_myinfo;
    private ThemeModel themeModel;
    private ThemeEntity.ContentBean.DefaultThemeBean.TabbarBean themeBean;
    private ThemeEntity themeEntity;
    private ShowOpenDoorDialog showOpenDoorDialog;
    private NewDoorModel openModel;
    private String door_code;
    private NewUserModel newUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        ScreenManager.getScreenManager().pushActivity(this);
        registerNotice();
        mShared = getSharedPreferences(UserAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        mUpdateVerSion = new UpdateVerSion();
        openModel = new NewDoorModel(this);
        Intent intentMessage = getIntent();
        if (intentMessage != null) {
            String doorid = intentMessage.getStringExtra("shortcut");
            if (!TextUtils.isEmpty(doorid)) {
                openModel.openDoor(2, doorid, true, MainActivity.this);
            }
            String jumpUrl = intentMessage.getStringExtra(JUMPOTHERURL);
            if (!TextUtils.isEmpty(jumpUrl)) {
                LinkParseUtil.parse(MainActivity.this, jumpUrl, "");
            }
        }
        initView();
        userId = mShared.getInt(UserAppConst.Colour_User_id, 0);
        mPhoneStr = mShared.getString(UserAppConst.Colour_login_mobile, "");
        themeModel = new ThemeModel(this);
        String themeCache = mShared.getString(UserAppConst.THEME, "");
        themeAdapter(themeCache);
        fragmentManager = getSupportFragmentManager();
        onTabSelected(FLAG_TAB_ONE);
        if (mShared.getBoolean(UserAppConst.IS_CHECK_UPDATE, false)) {//为TURE，说明第二次进入才检测更新
//            mUpdateVerSion.getNewVerSion("1", true, MainActivity.this);
            mUpdateVerSion.getNewVerSion(MainActivity.this, true,false);
        }
        mEditor.putInt(UpdateVerSion.SAVEVERSIONCODE, UpdateVerSion.getVersionCode(MainActivity.this));//保存版本号
        mEditor.putBoolean(UserAppConst.IS_CHECK_UPDATE, true);
        mEditor.apply();
        shortEnter(MainActivity.this, getIntent(), mShared);
        int totalUnRead = HuxinSdkManager.instance().unreadBuddyAndCommMessage();
        showUnReadMsg(totalUnRead);
        HuxinSdkManager.instance().getStackAct().addActivity(this);
        newUserModel = new NewUserModel(MainActivity.this);
        tintManager.setStatusBarTintColor(Color.TRANSPARENT);
        GlideImageLoader.initImageLoader(getApplicationContext());
        ToastUtil.toastShow(MainActivity.this, JPushInterface.getRegistrationID(getApplicationContext()));

    }


    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (slienceLogin) {
                backendBecomeActive();
                slienceLogin = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isregister(MainActivity.this)) {
            EventBus.getDefault().register(MainActivity.this);
        }
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isregister(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (mReceiver != null) {
            mLocalBroadcastManager.unregisterReceiver(mReceiver);
        }
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
        HuxinSdkManager.instance().getStackAct().finishActivity(this);
        super.onDestroy();
    }

    private QBadgeView badgeView;

    public void showUnReadMsg(int totalUnRead) {
        if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
            if (null == badgeView) {
                badgeView = new QBadgeView(MainActivity.this);
                badgeView.bindTarget(mDiscovery);
                badgeView.setBadgeGravity(Gravity.TOP | Gravity.END);
                badgeView.setGravityOffset(12f, -2.5f, true);
                badgeView.setBadgeTextSize(10f, true);
                badgeView.setBadgeBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.hx_color_red_tag));
                badgeView.setShowShadow(false);
            }
            badgeView.setBadgeNumber(totalUnRead);
        } else {
            if (null != badgeView) {
                badgeView.setBadgeNumber(0);
            }
        }
    }

    private void initView() {
        mHome = findViewById(R.id.main_tool_home);
        mCommunity = findViewById(R.id.main_tool_community);
        mDiscovery = findViewById(R.id.main_tool_discover);
        mProfile = findViewById(R.id.main_tool_profile);
        mToolBar = findViewById(R.id.toolbar);
        main_tool_scan = findViewById(R.id.main_tool_scan);//扫一扫
        circle_scanner_image = findViewById(R.id.circle_scanner_image);//扫一扫
        //底部4个图标适配
        home_btn = findViewById(R.id.img_home_1);
        life_btn = findViewById(R.id.img_home_2);
        linli_btn = findViewById(R.id.img_home_3);
        myinfo_btn = findViewById(R.id.img_home_4);
        Scande_btn = findViewById(R.id.img_home_5);
        tv_home = findViewById(R.id.tex_1);
        tv_life = findViewById(R.id.tex_2);
        tv_linli = findViewById(R.id.tex_3);
        tv_myinfo = findViewById(R.id.tex_4);
        setLayoutPramas(home_btn, 11);
        setLayoutPramas(myinfo_btn, 11);
        setLayoutPramas(linli_btn, 11);
        setLayoutPramas(life_btn, 11);
        setLayoutPramas(Scande_btn, 8);
        mHome.setOnClickListener(this);
        mCommunity.setOnClickListener(this);
        mDiscovery.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        main_tool_scan.setOnClickListener(this);
        circle_scanner_image.setOnClickListener(this);
    }


    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            intent = getIntent();
            if (null != intent) {
                String doorid = intent.getStringExtra("shortcut");
                String qrcode = intent.getStringExtra("qrcode");
                if (!TextUtils.isEmpty(qrcode)) {
                    openModel.openDoor(2, qrcode, true, MainActivity.this);
                }
                if (!TextUtils.isEmpty(doorid)) {
                    openDoor(doorid);
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shortEnter(MainActivity.this, intent, mShared);
        String doorid = intent.getStringExtra("shortcut");
        if (!TextUtils.isEmpty(doorid)) {
            openDoor(doorid);
        }
        String linkURl = intent.getStringExtra(JUMPOTHERURL);
        if (!TextUtils.isEmpty(linkURl)) {
            if ("colourlifeCaiHui".equals(linkURl)) {
                onTabSelected(FLAG_TAB_TWO);
            } else if ("ColourlifeBackHome".equals(linkURl)) {
                onTabSelected(FLAG_TAB_ONE);
                Message message = Message.obtain();
                message.what = UserMessageConstant.CHANGE_COMMUNITY;
                EventBus.getDefault().post(message);
            } else {
                LinkParseUtil.parse(MainActivity.this, linkURl, "");
            }
        }
    }

    public void openDoor(String door_id) {
        openModel.openDoor(2, door_id, true, MainActivity.this);
    }

    /**
     * APP从后台进入前台，进行账号验证和同步数据
     */
    public void backendBecomeActive() {
        if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) { //未登录
            if (null == mTokenModel) {
                mTokenModel = new TokenModel(MainActivity.this);
            }
            mTokenModel.checkDeviceLogin(this);
        }
    }

    /**
     * 注册通知
     */
    private void registerNotice() {
        mLocalBroadcastManager = LocalBroadcastManager
                .getInstance(MainActivity.this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_C6);// 重复登录
        filter.addAction(Constant.ACTION_OUT);//设置里的退出登录
        // 登录界面退出应用
        filter.addAction(Constant.ACTION_LOGIN_FINISH_COMPLETED);
        // 注册设备，用于推送
        filter.addAction(Constant.ACTION_PUSHMESSAGE_REG);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String getAction = intent.getAction();
                switch (getAction) {
                    case Constant.ACTION_C6:
                        if (ScreenManager.getScreenManager().currentActivity() != null) {
                            if (!ScreenManager.getScreenManager().currentActivity()
                                    .equals(MainActivity.this)) {
                                ScreenManager.getScreenManager()
                                        .popAllActivityExceptOne(MainActivity.class);
                            }
                            Message message = new Message();
                            message.what = UserMessageConstant.SQUEEZE_OUT;
                            message.obj = getResources().getString(R.string.account_extrude_login);
                            EventBus.getDefault().post(message);
                        }
                        break;
                    case Constant.ACTION_PUSHMESSAGE_REG:
                        configPushMessage();
                        break;
                    case Constant.ACTION_LOGIN_FINISH_COMPLETED:
                        slienceLogin = false;
                        configPushMessage();
                        if (null == mTokenModel) {
                            mTokenModel = new TokenModel(MainActivity.this);
                        }
                        mTokenModel.checkDeviceLogin(MainActivity.this);
                        getChangeCommunityTheme();
                        break;
                    case Constant.ACTION_OUT:
                        onTabSelected(FLAG_TAB_ONE);
                        break;
                }
            }
        };
        mLocalBroadcastManager.registerReceiver(mReceiver, filter);
    }

    /**
     * 清除用户数据
     */
    public void clearUserData(String notice) {
        SobotApi.exitSobotChat(MainActivity.this);//退出智齿客服
        showUnReadMsg(0);
        onTabSelected(FLAG_TAB_ONE);
        clearUserCache(MainActivity.this);
        myManager.cancel(NOTIFICATION_ID_1);
        Constants.NOTIFICATION_BTN = false;
        JPushInterface.stopPush(getApplicationContext());
        JPushInterface.cleanTags(getApplicationContext(), userId);
        if (!TextUtils.isEmpty(notice)) {
            ToastUtil.toastShow(getApplicationContext(), notice);
        }
    }


    /**
     * 配置推送信息,注册客户端，用于推送服务
     */
    public void configPushMessage() {
        if (JPushInterface.isPushStopped(MainActivity.this)) {
            JPushInterface.resumePush(MainActivity.this);
        }
        String mDeviceID = JPushInterface.getRegistrationID(getApplicationContext());// 获取设备推送的设备码
        mPhoneStr = mShared.getString(UserAppConst.Colour_login_mobile, "");
        String community_uuid = mShared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da");
        Set<String> tags = new HashSet<>();
        if (community_uuid.contains("-")) {
            tags.add(community_uuid.replace("-", "_"));
        }
        tags.add(mShared.getString(UserAppConst.Colour_User_uuid, "03b98def-b5bd-400b-995f-a9af82be01da"));
        userId = mShared.getInt(UserAppConst.Colour_User_id, 0);
        JPushInterface.setAlias(getApplicationContext(), userId, mPhoneStr);
        JPushInterface.setTags(getApplicationContext(), userId, tags);
        JPushInterface.setMobileNumber(getApplicationContext(), userId, mPhoneStr);
        mEditor.putString(UserAppConst.Colour_mDeviceID, mDeviceID);
        mEditor.commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 6) {
            ScreenManager.getScreenManager().popAllActivityExceptOne(MainActivity.class);
            onTabSelected(FLAG_TAB_ONE);
        } else if (requestCode == 1000) {
            onTabSelected(FLAG_TAB_ONE);
        } else if (requestCode == 2000) {
            if (resultCode == 2001) {
                String json = data.getStringExtra("doorData");
                door_code = data.getStringExtra("door_code");
                initDealDoorReault(json);
            }
        } else if (requestCode == 3000) {
            boolean hasInstallPermission;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                hasInstallPermission = getPackageManager().canRequestPackageInstalls();
                if (hasInstallPermission) {
                    String appName = shared.getString(UserAppConst.APKNAME, "");
                    UpdateService.installCzyAPP(MainActivity.this, appName);
                } else {
                    ToastUtil.toastShow(MainActivity.this, "未知应用程序安装权限未开启");
                }
            }
        } else if (requestCode == 4000) {
            intoPopup();
        }
    }

    /**
     * 展示开门结果
     */
    public void showOpenDoorDialog(String json) {
        if (choiceType == 0) {
            try {
                if (null == showOpenDoorDialog) {
                    showOpenDoorDialog = new ShowOpenDoorDialog(MainActivity.this, R.style.opendoor_dialog_theme);
                }
                if (showOpenDoorDialog.isShowing()) {
                    showOpenDoorDialog.dismiss();
                }
                showOpenDoorDialog.show();
                final OpenDoorResultEntity openDoorResultEntity = GsonUtils.gsonToBean(json, OpenDoorResultEntity.class);
                OpenDoorResultEntity.ContentBean contentBean = openDoorResultEntity.getContent();
                final int result = contentBean.getOpen_result();
                showOpenDoorResultDialog(MainActivity.this, showOpenDoorDialog, contentBean);
                showOpenDoorDialog.tv_opendoor_cqb_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showOpenDoorDialog.dismiss();
                        if (result == 0) {//重新开门
                            NewDoorModel openModel = new NewDoorModel(MainActivity.this);
                            openModel.openDoor(2, door_code, true, MainActivity.this);
                        } else {//跳转到彩钱包
                            LinkParseUtil.parse(MainActivity.this, "colourlife://proto?type=NewTicket", "");
                        }
                    }
                });
            } catch (Exception e) {

            }
        }
    }

    /**
     * 处理开门返回信息
     *
     * @param result
     */
    private void initDealDoorReault(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            String reason = jsonObject.getString("message");
            if (code == 0) {
                showOpenDoorDialog(result);
            } else if (code == 2304) {
                Intent intent = new Intent(MainActivity.this, NoRightDoorActivity.class);
                intent.putExtra("dataToast", reason);
                startActivity(intent);
                this.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            } else {
                ToastUtil.toastShow(MainActivity.this, reason);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_tool_home:
                onTabSelected(FLAG_TAB_ONE);
                break;
            case R.id.main_tool_community://生活
                onTabSelected(FLAG_TAB_TWO);
                break;
            case R.id.main_tool_discover://邻里
                onTabSelected(FLAG_TAB_THREE);
                break;
            case R.id.main_tool_profile:
                onTabSelected(FLAG_TAB_FOUR);
                break;
            case R.id.main_tool_scan:
            case R.id.circle_scanner_image:
                if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                    intent.putExtra(CaptureActivity.QRCODE_SOURCE, "default");
                    startActivity(intent);
                } else {
                    LinkParseUtil.parse(MainActivity.this, "", "");
                }
                break;
        }
    }

    // 退出操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!mIsExit) {
                mIsExit = true;
                ToastUtil.toastShow(getApplicationContext(), getResources().getString(R.string.app_exit_notice));
                handler.sendEmptyMessageDelayed(0, 3000);
                return true;
            } else {
                //重置埋点活动
                Constants.ISSHOWGEM = 1;
                ScreenManager.getScreenManager().removeActivity(this);
                ScreenManager.getScreenManager().amForceAppProcess(getApplicationContext());
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mIsExit = false;
        }
    };

    /**
     * 选择tab
     */
    public void onTabSelected(int flagTab) {
        if (flagTab == FLAG_TAB_ONE) {//首页
            choiceType = 0;
            transaction = fragmentManager.beginTransaction();
            hideFragments(transaction);
            boolean isLogin = mShared.getBoolean(UserAppConst.IS_LOGIN, false);
            if (isLogin) {
                if (mHomeFragment == null) {
                    mHomeFragment = new MainHomeFragmentNew();
                    transaction.add(R.id.main_fragment_container, mHomeFragment);
                } else {
                    transaction.show(mHomeFragment);
                }
            } else {
                if (null == nologinHomeFragment) {
                    nologinHomeFragment = new NologinHomeFragment();
                    transaction.add(R.id.main_fragment_container, nologinHomeFragment);
                } else {
                    transaction.show(nologinHomeFragment);
                }
            }
            transaction.commitAllowingStateLoss();
            mHome.setSelected(true);
            mCommunity.setSelected(false);
            mDiscovery.setSelected(false);
            mProfile.setSelected(false);
            showPopupScreen();
            dismissBenefitDialog();
        } else if (flagTab == FLAG_TAB_TWO) { //彩惠人生
            if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                choiceType = 1;
                transaction = fragmentManager.beginTransaction();
                hideFragments(transaction);
                if (null == benefitFragment) {
                    benefitFragment = new BenefitFragment();
                    transaction.add(R.id.main_fragment_container, benefitFragment);
                } else {
                    transaction.show(benefitFragment);
                }
                transaction.commitAllowingStateLoss();
                mHome.setSelected(false);
                mCommunity.setSelected(true);
                mDiscovery.setSelected(false);
                mProfile.setSelected(false);
            } else {
                LinkParseUtil.parse(MainActivity.this, "", "");
            }
        } else if (flagTab == FLAG_TAB_THREE) {//邻里
            if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                choiceType = 1;
                transaction = fragmentManager.beginTransaction();
                hideFragments(transaction);
                if (instantMessageFragment == null) {
                    instantMessageFragment = new InstantMessageFragment();
                    transaction.add(R.id.main_fragment_container, instantMessageFragment);
                } else {
                    transaction.show(instantMessageFragment);
                }
                transaction.commitAllowingStateLoss();
                mHome.setSelected(false);
                mCommunity.setSelected(false);
                mDiscovery.setSelected(true);
                mProfile.setSelected(false);
            } else {
                LinkParseUtil.parse(MainActivity.this, "", "");
            }
            dismissBenefitDialog();
        } else if (flagTab == FLAG_TAB_FOUR) {
            if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                choiceType = 2;
                transaction = fragmentManager.beginTransaction();
                hideFragments(transaction);
                if (profileFragment == null) {
                    profileFragment = new ProfileFragment();
                    transaction.add(R.id.main_fragment_container, profileFragment);
                } else {
                    transaction.show(profileFragment);
                }
                transaction.commitAllowingStateLoss();
                mHome.setSelected(false);
                mCommunity.setSelected(false);
                mDiscovery.setSelected(false);
                mProfile.setSelected(true);
            } else {
                LinkParseUtil.parse(MainActivity.this, "", "");
            }
            dismissBenefitDialog();
        }
    }

    /**
     * 隐藏页面
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (nologinHomeFragment != null) {
            transaction.hide(nologinHomeFragment);
        }
        if (instantMessageFragment != null) {
            transaction.hide(instantMessageFragment);
        }
        if (benefitFragment != null) {
            transaction.hide(benefitFragment);
        }
        if (profileFragment != null) {
            transaction.hide(profileFragment);
        }
    }


    /***获取key和secert***/
    private void updateAccessToken() {
        newUserModel.getUserInformation(5, false, this);
    }

    private void checkMobileStatus() {
        String mobile = mShared.getString(UserAppConst.Colour_login_mobile, "");
        newUserModel.getCheckRegister(20, mobile, this);
        updateAccessToken();
    }


    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(CheckDeviceLoginApi.class)) {
            CheckDeviceLoginResponse  checkDeviceLoginResponse=((CheckDeviceLoginApi) api).response;
            int login_state = checkDeviceLoginResponse.login_state;
            if (login_state == 1) {
                Message message = new Message();
                message.what = UserMessageConstant.SQUEEZE_OUT;
                message.obj = getResources().getString(R.string.account_extrude_login);
                EventBus.getDefault().post(message);
            } else {
                boolean isLoin = mShared.getBoolean(UserAppConst.IS_LOGIN, false);
                boolean userLogin = mShared.getBoolean(UserAppConst.Colour_user_login, false);
                if (isLoin) { //用户是登录状态
                    if (!userLogin) {  //如果用户是自己登录
                        checkMobileStatus();
                    } else { //改为用户静默登录
                        mEditor.putBoolean(UserAppConst.Colour_user_login, false);
                        mEditor.commit();
                    }
                }
            }
        }
    }

    private ArrayList<String> imageList = new ArrayList<>();
    private ArrayList<String> urlList = new ArrayList<>();
    private ArrayList<String> descList = new ArrayList<>();

    private void setPopData() {
        imageList.clear();
        urlList.clear();
        descList.clear();
        int newSize = newPopList.size();
        for (int i = 0; i < newSize; i++) {
            PushNotificationEntity.ContentBean contentBean = newPopList.get(i);
            imageList.add(contentBean.getImg_url());
            urlList.add(contentBean.getLink_url());
            descList.add(contentBean.getMsg_title());
        }
        intoPopup();
    }


    public void intoPopup() {
        try {
            PopupScUtils.getInstance().jump(this, urlList, imageList, descList);
        } catch (Exception e) {

        }
    }


    public void onEvent(Object event) {
        Message message = (Message) event;
        switch (message.what) {
            case FeedConstant.FEED_SHOW_INPUT:
                mToolBar.setVisibility(View.GONE);
                circle_scanner_image.setVisibility(View.GONE);
                break;
            case FeedConstant.FEED_CLOSE_INPUT:
                mToolBar.setVisibility(View.VISIBLE);
                circle_scanner_image.setVisibility(View.VISIBLE);
                break;
            case UserMessageConstant.LOGOUT:
                myManager.cancel(NOTIFICATION_ID_1);
                onTabSelected(FLAG_TAB_ONE);
                break;
            case UserMessageConstant.SIGN_IN_SUCCESS:
                onTabSelected(FLAG_TAB_ONE);
                getContactList();
                break;
            case UserMessageConstant.CHANGE_COMMUNITY:
                showPopupScreen();
                break;
            case UserMessageConstant.GET_SINGLE_FRIINFOR:
                String useruuid = (String) message.obj;
                IMUploadPhoneModel imUploadPhoneModel = new IMUploadPhoneModel(MainActivity.this);
                imUploadPhoneModel.getUserInforByUuid(30, useruuid, false, this);
                break;
            case UserMessageConstant.CHANGE_DIFF_LANG:
                getChangeCommunityTheme();
                onTabSelected(FLAG_TAB_ONE);
                break;
            case UserMessageConstant.UPLOAD_PAGE_TIME:
                Bundle bundle = message.getData();
                long startTime = bundle.getLong(BuryingPointUtils.ENTER_TIME, 0) / 1000;
                long leaveTime = bundle.getLong(BuryingPointUtils.LEAVE_TIME, 0) / 1000;
                String functionSectionId = bundle.getString(UPLOAD_DETAILS, "");
                uploadPageStayTime(startTime, leaveTime, functionSectionId);
                break;
            case UserMessageConstant.BLUETOOTH_CLOSE_DIALOG:
                if (null != showOpenDoorDialog && showOpenDoorDialog.isShowing()) {
                    showOpenDoorDialog.dismiss();
                }
                break;
            case 10000:
                final String token = "";
                final String mUserName = "";
                final String roomName = "";
                Intent intent = new Intent(MainActivity.this, RoomActivity.class);
                intent.putExtra(RoomActivity.EXTRA_ROOM_ID, roomName.trim());
                intent.putExtra(RoomActivity.EXTRA_ROOM_TOKEN, token);
                intent.putExtra(RoomActivity.EXTRA_USER_ID, mUserName);
                startActivity(intent);
                break;
        }
    }

    public void uploadPageStayTime(long startTime, long leaveTime, String functionSectionId) {
        if (leaveTime - startTime >= 1) { //大于1s才上传
            if (functionSectionId.contains(BuryingPointUtils.divisionSign)) {
                String[] funArr = functionSectionId.split(BuryingPointUtils.divisionSign);
                String functionCode = funArr[0];
                String functionName = funArr[1];
                String sectionCode = funArr[2];
                BuryingPointUtils.uploadPageStayTime(MainActivity.this, BuryingPointUtils.homePageName, sectionCode, functionCode, functionName, startTime, leaveTime);
            }
        }
    }

    /**
     * 请求好友列表接口(好友列表）
     */
    private void getContactList() {
        getFriendListData(MainActivity.this);
    }

    private boolean isPopupRequest = true;

    private void showPopupScreen() {
        if (mShared.getBoolean(UserAppConst.IS_LOGIN, false) && isPopupRequest) {
            isPopupRequest = false;
            if (null == popupModel) {
                popupModel = new PopupModel(this);
            }
            userId = mShared.getInt(UserAppConst.Colour_User_id, 0);
            popupModel.getNewHomePopupMsg(11, this);
        }
    }

    /**
     * 底部四个home图标适配
     */
    private void setLayoutPramas(View view, int value) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Utils.getDeviceWith(this) / value, Utils.getDeviceWith(this) / value);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 主题接口返回数据
     *
     * @param what
     * @param result
     */
    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        themeEntity = GsonUtils.gsonToBean(result, ThemeEntity.class);
                        long oldUpdateTime = Long.valueOf(mShared.getString(UserAppConst.THEMEUPDATETIME, "0"));
                        long nowUpdateTime = themeEntity.getContent().getDefault_theme().getTabbar().getUpdated_at();
                        if (nowUpdateTime >= oldUpdateTime) {//当前接口为最新主题，使用该接口数据
                            themeAdapter(result);
                        }
                    } catch (Exception e) {

                    }
                }
                break;
            case 5:
                if (!TextUtils.isEmpty(result)) {
                    userInitImData(MainActivity.this, mShared);
                    getContactList();
                    if (null == mTokenModel) {
                        mTokenModel = new TokenModel(MainActivity.this);
                    }
                    mTokenModel.getToken(1, false, this);
                }
                break;
            case 11:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        PushNotificationEntity pushNotificationEntity = GsonUtils.gsonToBean(result, PushNotificationEntity.class);
                        newPopList.clear();
                        newPopList.addAll(pushNotificationEntity.getContent());
                        setPopData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 20:
                int isRegister;
                if (!TextUtils.isEmpty(result)) {
                    try {
                        CheckRegisterEntity checkRegisterEntity = GsonUtils.gsonToBean(result, CheckRegisterEntity.class);
                        isRegister = checkRegisterEntity.getContent().getIs_register();
                    } catch (Exception e) {
                        isRegister = 1;
                    }
                    if (isRegister == 0) {
                        clearUserData("");
                        ToastUtil.toastTime(getApplicationContext(), getResources().getString(R.string.use_newaccount__login), 5000);
                        jumpLoginPage(MainActivity.this, mShared, 3000);
                    }
                }
                break;
            case 30:
                if (!TextUtils.isEmpty(result)) {
                    saveFriendData(MainActivity.this, result);
                }
                break;
            case 40:
                if (!TextUtils.isEmpty(result)) {
                    newUserModel.getUserInformation(5, false, this);
                }
                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {
                    initDealDoorReault(result);
                }
                break;
            case 50://刷新乐开token
                LekaiHelper.startService(this, result);
                break;
        }
    }

    private List<PushNotificationEntity.ContentBean> newPopList = new ArrayList<>();

    /**
     * 主题适配
     */
    private void themeAdapter(String themeCache) {
        if (!TextUtils.isEmpty(themeCache)) {
            try {
                themeEntity = GsonUtils.gsonToBean(themeCache, ThemeEntity.class);
                themeBean = themeEntity.getContent().getDefault_theme().getTabbar();
                tv_home.setText(themeBean.getHome_tabbar_title());
                tv_life.setText(themeBean.getLife_tabbar_title());
                tv_linli.setText(themeBean.getLinli_tabbar_title());
                tv_myinfo.setText(themeBean.getProfile_tabbar_title());
                addSeletorFromNet(MainActivity.this, themeBean.getHome_tabbar_image(),
                        themeBean.getHome_tabbar_select_image(), home_btn);
                addSeletorFromNet(MainActivity.this, themeBean.getLife_tabbar_image(),
                        themeBean.getLife_tabbar_select_image(), life_btn);
                addSeletorFromNet(MainActivity.this, themeBean.getLinli_tabbar_image(),
                        themeBean.getLinli_tabbar_select_image(), linli_btn);
                addSeletorFromNet(MainActivity.this, themeBean.getProfile_tabbar_image(),
                        themeBean.getProfile_tabbar_select_image(), myinfo_btn);
                Glide.with(MainActivity.this)
                        .load(themeBean.getScan_tabbar_image())
                        .apply(new RequestOptions().dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(circle_scanner_image);
                addTVSeletor(MainActivity.class, themeBean.getTabbar_title_color(),
                        themeBean.getTabbar_title_select_color(), tv_home);
                addTVSeletor(MainActivity.class, themeBean.getTabbar_title_color(),
                        themeBean.getTabbar_title_select_color(), tv_life);
                addTVSeletor(MainActivity.class, themeBean.getTabbar_title_color(),
                        themeBean.getTabbar_title_select_color(), tv_linli);
                addTVSeletor(MainActivity.class, themeBean.getTabbar_title_color(),
                        themeBean.getTabbar_title_select_color(), tv_myinfo);
            } catch (Exception e) {
                defaultTheme();
            }
        } else {//缓存为空则请求数据
            getChangeCommunityTheme();
            defaultTheme();
        }
    }

    private void defaultTheme() {
        addSelectorFromDrawable(MainActivity.this, R.drawable.icon_bar_home_n, R.drawable.icon_bar_home_s, home_btn);
        addSelectorFromDrawable(MainActivity.this, R.drawable.icon_bar_life_n, R.drawable.icon_bar_life_s, life_btn);
        addSelectorFromDrawable(MainActivity.this, R.drawable.icon_bar_lil_n, R.drawable.icon_bar_lil_s, linli_btn);
        addSelectorFromDrawable(MainActivity.this, R.drawable.icon_bar_my_n, R.drawable.icon_bar_my_s, myinfo_btn);
        circle_scanner_image.setImageResource(R.drawable.icon_home_bar_sic);
        addTVSeletor(MainActivity.class, "#97a0ab", "#2E7BEF", tv_home);
        addTVSeletor(MainActivity.class, "#97a0ab", "#2E7BEF", tv_life);
        addTVSeletor(MainActivity.class, "#97a0ab", "#2E7BEF", tv_linli);
        addTVSeletor(MainActivity.class, "#97a0ab", "#2E7BEF", tv_myinfo);
    }

    public void getChangeCommunityTheme() {
        if (themeModel == null) {
            themeModel = new ThemeModel(MainActivity.this);
        }
        themeModel.getTheme(0, this);
    }

    /**
     * 乐开403重新获取
     */
    public void regetLekaiToken() {
        newUserModel.regetLekaiDoor(50, this);
    }

    private BenefitActivityDialog activityDialog;

    /**
     * 彩惠活动弹窗
     */
    public void showBenefitDialog(String imgPath, String url, String title) {
        if (choiceType == 1) {
            if (null == activityDialog) {
                activityDialog = new BenefitActivityDialog(this, ScreenUtils.getScreenWidth(this));
            }
            activityDialog.show();
            GlideImageLoader.loadImageDefaultDisplay(this, imgPath, activityDialog.iv_activity, R.drawable.bg_benefit_act_default, R.drawable.bg_benefit_act_default);
            activityDialog.iv_close.setOnClickListener(v -> activityDialog.dismiss());
            activityDialog.iv_activity.setOnClickListener(v -> {
                activityDialog.dismiss();
                LinkParseUtil.parse(this, url, title);
            });
        }
    }

    /**
     * 隐藏活动弹窗
     */
    public void dismissBenefitDialog() {
        if (null != activityDialog) {
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    }

    private InterHandler mHandler = new InterHandler(this);

    private static class InterHandler extends Handler {
        private WeakReference<MainActivity> mActivity;

        InterHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == 1) {
                    if (null != activity.activityDialog) {
                        activity.activityDialog.dismiss();
                    }
                }
            } else {
                super.handleMessage(msg);
            }
        }
    }

}