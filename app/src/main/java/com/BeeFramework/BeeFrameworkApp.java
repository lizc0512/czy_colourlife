package com.BeeFramework;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Message;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;

import com.appsafekb.safekeyboard.NKeyBoardTextField;
import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.InitListener;
import com.external.eventbus.EventBus;
import com.im.activity.IMApplyFriendRecordActivity;
import com.im.entity.ApplyRecordEntity;
import com.im.greendao.IMGreenDaoManager;
import com.im.helper.CacheApplyRecorderHelper;
import com.nohttp.utils.SSLContextUtil;
import com.shell.SdkManager;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.Utils.TokenUtils;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.URLConnectionNetworkExecutor;
import com.youmai.hxsdk.HuxinSdkManager;
import com.youmai.hxsdk.proto.YouMaiBuddy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.net.ssl.SSLContext;

import cn.csh.colourful.life.utils.ColourLifeSDK;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
import cn.net.cyberway.utils.ActivityLifecycleListener;

public class BeeFrameworkApp extends MultiDexApplication {
    private static BeeFrameworkApp instance;


    public static BeeFrameworkApp getInstance() {
        if (instance == null) {
            instance = new BeeFrameworkApp();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SSLContext sslContext = SSLContextUtil.getDefaultSLLContext();
        InitializationConfig config = InitializationConfig.newBuilder(getApplicationContext())
                // 全局连接服务器超时时间，单位毫秒，默认10s。
                .connectionTimeout(20 * 1000)
                // 全局等待服务器响应超时时间，单位毫秒，默认10s。
                .readTimeout(20 * 1000)
                .networkExecutor(new URLConnectionNetworkExecutor())
                .sslSocketFactory(sslContext.getSocketFactory()) // 全局SSLSocketFactory。
                .retry(1)
                .build();
        NoHttp.initialize(config);
        try {
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext());
            HuxinSdkManager.instance().init(getApplicationContext());
            HuxinSdkManager.instance().setHomeAct(MainActivity.class);
            IMGreenDaoManager.instance(getApplicationContext());
            NKeyBoardTextField.setNlicenseKey(UserAppConst.IJIAMINLICENSEKEY);
            Intent initialIntent = new Intent(this, InitializeService.class);
            startService(initialIntent);
            ColourLifeSDK.init(getApplicationContext());
            HuxinSdkManager.instance().regeditCommonPushMsg(notify -> {
                String srcUuid = notify.getSrcUserId();
                String dstUuid = notify.getDestUserId();
                String optRemark = notify.getOptRemark();
                String nickName = notify.getNickname();
                String userName = notify.getUsername();
                String avatar = notify.getAvatar();
                YouMaiBuddy.BuddyOptType type = notify.getOptType();
                addFriendNotify(srcUuid, dstUuid, optRemark, nickName, userName, avatar, type);
            });
            OneKeyLoginManager.getInstance().init(getApplicationContext(), "DbBj26Nj", "DOMYqkZR", new InitListener() {
                @Override
                public void getInitStatus(int code, String result) {


                }
            });
        } catch (Exception e) {

        }
        registerActivityLifecycleCallbacks(new ActivityLifecycleListener());
        closeAndroidPDialog();
    }

    private void addFriendNotify(String srcUuid, String dstUuid, String optRemark, String nickName, String userName, String avatar, YouMaiBuddy.BuddyOptType type) {
        if (type == YouMaiBuddy.BuddyOptType.BUDDY_OPT_ADD_REQ) { //请求添加好友
            if (!srcUuid.equals(dstUuid)) {  //过滤自己申请添加自己为好友的记录
                ApplyRecordEntity recordEntity = CacheApplyRecorderHelper.instance().toQueryApplyRecordById(getApplicationContext(), srcUuid);
                if (null == recordEntity) {
                    recordEntity = new ApplyRecordEntity();
                    recordEntity.setUuid(srcUuid); //请求人的
                    recordEntity.setComment(optRemark);
                    recordEntity.setNewComment(optRemark);
                    recordEntity.setState("0");
                    recordEntity.setMobile("");
                    recordEntity.setGender("");
                    recordEntity.setCommunityName("");
                    if (!TextUtils.isEmpty(nickName)) {
                        recordEntity.setNickName(nickName);
                    } else {
                        recordEntity.setNickName("");
                    }
                    if (TextUtils.isEmpty(userName)) {
                        recordEntity.setName("");
                    } else {
                        recordEntity.setName(userName);
                    }
                    recordEntity.setPortrait(avatar);
                } else { //多次请求备注进行添加
                    String comment = recordEntity.getComment();
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(comment);
                    stringBuffer.append("\n");
                    stringBuffer.append(optRemark);
                    recordEntity.setComment(stringBuffer.toString());
                    recordEntity.setNewComment(optRemark);
                }
                CacheApplyRecorderHelper.instance().insertOrUpdate(this, recordEntity);
                SharedPreferences sharedPreferences = getSharedPreferences(UserAppConst.USERINFO, 0);
                sharedPreferences.edit().putBoolean(UserAppConst.IM_APPLY_FRIEND, true).apply();
                Message msg = new Message();
                msg.what = UserMessageConstant.GET_APPLY_NUMBER;
                EventBus.getDefault().post(msg);
                if (!TextUtils.isEmpty(userName)) {
                    notifyMsg(userName, optRemark);//请求添加好友的弹窗
                } else {
                    notifyMsg(nickName, optRemark);//请求添加好友的弹窗
                }
            }
        } else if (type == YouMaiBuddy.BuddyOptType.BUDDY_OPT_ADD_AGREE) {  //好友验证通过的回调
            Message msg = new Message();
            msg.obj = srcUuid;
            msg.what = UserMessageConstant.GET_SINGLE_FRIINFOR;
            EventBus.getDefault().post(msg);
        }
    }

    private void notifyMsg(String nickName, String optRemark) {
        try {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder;
            if (Build.VERSION.SDK_INT >= 26) {
                String CHANNEL_ID = "im_chat";
                if (TextUtils.isEmpty(nickName)) {
                    nickName = "";
                }
                if (TextUtils.isEmpty(optRemark)) {
                    optRemark = "";
                }
                CharSequence name = nickName;
                String Description = optRemark;
                builder = new NotificationCompat.Builder(this, CHANNEL_ID);
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(Description);
                channel.enableLights(true);
                channel.setLightColor(Color.GREEN);
                channel.enableVibration(true);
                channel.setShowBadge(false);
                notificationManager.createNotificationChannel(channel);
            } else {
                builder = new NotificationCompat.Builder(this);
            }
            builder.setContentTitle(getString(R.string.from) + nickName)
                    .setContentText(getString(R.string.add_friend))
                    .setTicker(getString(R.string.add_friend))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setSmallIcon(R.drawable.img_icon); //img_msg
                builder.setColor(getApplicationContext().getResources().getColor(R.color.white));
            } else {
                builder.setSmallIcon(R.drawable.img_icon);
            }
            Intent resultIntent = new Intent(this, IMApplyFriendRecordActivity.class);  //点击打开的activity
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class); //点击打开的activity后，返回的activity
            stackBuilder.addNextIntentWithParentStack(intent);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(nickName.hashCode(),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(resultPendingIntent);
            notificationManager.notify(nickName.hashCode(), builder.build());
        } catch (Exception e) {

        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            MultiDex.install(base); //解决差分包的问题
            SdkManager.initSdkManager(this);
            fixOppoAssetManager();
        } catch (Exception e) {

        }
    }


    private void closeAndroidPDialog() {
        if (Build.VERSION.SDK_INT < 28) return;
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);
            Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fixOppoAssetManager() {
        String device = TokenUtils.getDeviceBrand().toLowerCase();
        if (!TextUtils.isEmpty(device)) {
            if (device.contains("oppo")) {
                try {
                    // 关闭掉FinalizerWatchdogDaemon
                    Class clazz = Class.forName("java.lang.Daemons$FinalizerWatchdogDaemon");
                    Method method = clazz.getSuperclass().getDeclaredMethod("stop");
                    method.setAccessible(true);
                    Field field = clazz.getDeclaredField("INSTANCE");
                    field.setAccessible(true);
                    method.invoke(field.get(null));
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
