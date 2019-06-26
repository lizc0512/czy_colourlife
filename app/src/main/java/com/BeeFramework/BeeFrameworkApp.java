package com.BeeFramework;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Message;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.InitListener;
import com.external.eventbus.EventBus;
import com.facebook.stetho.Stetho;
import com.geetest.deepknow.DPAPI;
import com.im.activity.IMApplyFriendRecordActivity;
import com.im.entity.ApplyRecordEntity;
import com.im.greendao.IMGreenDaoManager;
import com.im.helper.CacheApplyRecorderHelper;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.shell.SdkManager;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.youmai.hxsdk.HuxinSdkManager;
import com.youmai.hxsdk.ProtoCallback;
import com.youmai.hxsdk.proto.YouMaiBuddy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.csh.colourful.life.utils.ColourLifeSDK;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
import cn.net.cyberway.utils.ActivityLifecycleListener;
import cn.net.cyberway.utils.ChangeLanguageHelper;

public class BeeFrameworkApp extends MultiDexApplication {
    private static BeeFrameworkApp instance;

    public static DisplayImageOptions optionsImage;        // DisplayImageOptions是用于设置图片显示的类

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
        ColourLifeSDK.init(getApplicationContext());
        DPAPI.getInstance(this, null);
        try {
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext());
            Intent initialIntent = new Intent(this, InitializeService.class);
            startService(initialIntent);
        } catch (Exception e) {

        }
        HuxinSdkManager.instance().init(this);
        HuxinSdkManager.instance().setHomeAct(MainActivity.class);
        Stetho.initializeWithDefaults(this);
        IMGreenDaoManager.instance(this);
        initImageLoader(getApplicationContext());
        initLoaderOptions();
        HuxinSdkManager.instance().regeditCommonPushMsg(new ProtoCallback.BuddyNotify() {
            @Override
            public void result(YouMaiBuddy.IMOptBuddyNotify notify) {
                String srcUuid = notify.getSrcUserId();
                String dstUuid = notify.getDestUserId();
                String optRemark = notify.getOptRemark();
                String nickName = notify.getNickname();
                String userName = notify.getUsername();
                String avatar = notify.getAvatar();
                YouMaiBuddy.BuddyOptType type = notify.getOptType();
                addFriendNotify(srcUuid, dstUuid, optRemark, nickName, userName, avatar, type);
            }
        });

        OneKeyLoginManager.getInstance().init(getApplicationContext(), "DbBj26Nj", "DOMYqkZR", new InitListener() {
            @Override
            public void getInitStatus(int code, String result) {


            }
        });
        registerActivityLifecycleCallbacks(new ActivityLifecycleListener());//乐开
        closeAndroidPDialog();
    }


    /**
     * 数位 保留 场景识别
     */
//    private void initSWLocation() {
//        SWLocationClient.initialization(this, true);
//    }
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
                sharedPreferences.edit().putBoolean(UserAppConst.IM_APPLY_FRIEND, true).commit();
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
                builder.setSmallIcon(R.drawable.img_msg);
                builder.setColor(getApplicationContext().getResources().getColor(R.color.white));
            } else {
                builder.setSmallIcon(R.drawable.img_msg);
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

    /***初始化imageloader的options***/
    private void initLoaderOptions() {
        optionsImage = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_image)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.default_image)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_image)        // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中*
                .cacheOnDisk(true)                            // 设置下载的图片是否缓存在SD卡中
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)//设置线程的优先级
                .threadPoolSize(3)
                .denyCacheImageMultipleSizesInMemory()//当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//设置缓存文件的名字
                .tasksProcessingOrder(QueueProcessingType.FIFO)// 设置图片下载和显示的工作队列排序
                .memoryCache(new WeakMemoryCache())
                .build();
        ImageLoader.getInstance().init(config);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            MultiDex.install(base); //解决差分包的问题
            SdkManager.initSdkManager(this);
            SharedPreferences sharedPreferences = getSharedPreferences(UserAppConst.USERINFO, 0);
            int currentLanguage = sharedPreferences.getInt(UserAppConst.CURRENTLANGUAGE, 0);
            ChangeLanguageHelper.changeLanguage(getApplicationContext(), currentLanguage);
            fix();
        } catch (Exception e) {

        }
    }

    //获取当前屏幕高度
    public static int getDeviceHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        return height;
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

    public void fix() {
        try {
            final Class clazz = Class.forName("java.lang.Daemons$FinalizerWatchdogDaemon");
            final Field field = clazz.getDeclaredField("INSTANCE");
            field.setAccessible(true);
            final Object watchdog = field.get(null);
            try {
                final Field thread = clazz.getSuperclass().getDeclaredField("thread");
                thread.setAccessible(true);
                thread.set(watchdog, null);
            } catch (final Throwable t) {
                t.printStackTrace();
                try {
                    // 直接调用stop方法，在Android 6.0之前会有线程安全问题
                    final Method method = clazz.getSuperclass().getDeclaredMethod("stop");
                    method.setAccessible(true);
                    method.invoke(watchdog);
                } catch (final Throwable e) {
                    t.printStackTrace();
                }
            }
        } catch (final Throwable t) {
            t.printStackTrace();
        }
    }
}
