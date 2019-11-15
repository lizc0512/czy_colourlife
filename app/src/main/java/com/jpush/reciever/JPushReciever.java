package com.jpush.reciever;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.BeeFramework.activity.WebViewActivity;
import com.audio.activity.RoomActivity;
import com.audio.entity.RoomTokenEntity;
import com.external.eventbus.EventBus;
import com.jpush.Constant;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.UserMessageConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;

import static com.audio.activity.RoomActivity.COMMUNITY_NAME;
import static com.audio.activity.RoomActivity.COMMUNITY_UUID;
import static com.audio.activity.RoomActivity.DOOR_ID;
import static com.audio.activity.RoomActivity.EXTRA_ROOM_ID;
import static com.audio.activity.RoomActivity.EXTRA_ROOM_TOKEN;
import static com.audio.activity.RoomActivity.EXTRA_USER_ID;
import static com.audio.activity.RoomActivity.ROOM_NAME;


public class JPushReciever extends BroadcastReceiver {

    private static final String TAG = "JPushReciever";

    private Context aContext;

    NotificationManager mNotifMan;

    LocalBroadcastManager mLocalBroadcastManager;

    public SharedPreferences shared;

    @Override
    public void onReceive(final Context context, Intent intent) {
        aContext = context;
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return;
        }
        // 注册
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "接收Registration Id : " + regId);
            getPushRegId(context);
            // 取消注册
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "接收UnRegistration Id : " + regId);
            // 自定义信息
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            String cmd = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            SharedPreferences mShared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
            boolean isLogin = mShared.getBoolean(UserAppConst.IS_LOGIN, false);
            if (!TextUtils.isEmpty(cmd)) {
                if (cmd.equalsIgnoreCase("c6") && !TextUtils.isEmpty(message)) { //单设备挤出登录
                    sendNotification(context, bundle);
                } else {
                    if (cmd.equalsIgnoreCase("c7")) { //更换手机号挤出登录
                        Message msg = new Message();
                        msg.what = UserMessageConstant.AUDIT_PASS_OUT;
                        msg.obj = message;
                        EventBus.getDefault().post(msg);
                    } else if (cmd.equalsIgnoreCase("colourlifeVideoOpenDoor")) {//门禁音视频
                        if (isLogin) {
                            String extraContent = bundle.getString(JPushInterface.EXTRA_EXTRA);
                            try {
                                RoomTokenEntity roomTokenEntity = GsonUtils.gsonToBean(extraContent, RoomTokenEntity.class);
                                long pushTime = roomTokenEntity.getSendTime();
                                long currentTime = System.currentTimeMillis() / 1000;
                                if (currentTime - pushTime <= 3 * 60) {
                                    Intent roomIntent = new Intent(context, RoomActivity.class);
                                    roomIntent.putExtra(EXTRA_ROOM_ID, roomTokenEntity.getRoomName());
                                    roomIntent.putExtra(COMMUNITY_NAME, roomTokenEntity.getCommunityName());
                                    roomIntent.putExtra(COMMUNITY_UUID, roomTokenEntity.getCommunityID());
                                    roomIntent.putExtra(ROOM_NAME, roomTokenEntity.getDoorName());
                                    roomIntent.putExtra(DOOR_ID, roomTokenEntity.getDoorID());
                                    roomIntent.putExtra(EXTRA_ROOM_TOKEN, roomTokenEntity.getRoomToken());
                                    roomIntent.putExtra(EXTRA_USER_ID, String.valueOf(context.getSharedPreferences(UserAppConst.USERINFO, 0).getInt(UserAppConst.Colour_User_id, 0)));
                                    context.startActivity(roomIntent);
                                }
                            } catch (Exception e) {

                            }
                        }
                    }
                }
            }
            // 极光通知
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Map<String, String> valueMap = new HashMap<String, String>();
            Set<String> keySet = bundle.keySet();
            Iterator<String> iter = keySet.iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                valueMap.put(key, bundle.getString(key));
            }
            BasicPushNotificationBuilder basicPushNotificationBuilder = new BasicPushNotificationBuilder(context);
            basicPushNotificationBuilder.statusBarDrawable = R.drawable.img_icon;
            basicPushNotificationBuilder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                    | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
            basicPushNotificationBuilder.notificationDefaults = Notification.DEFAULT_VIBRATE
                    | Notification.DEFAULT_LIGHTS;  // 震动、呼吸灯闪烁都要
            basicPushNotificationBuilder.buildNotification(valueMap);
            JPushInterface.setPushNotificationBuilder(1, basicPushNotificationBuilder);
            String extraAlert = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (extraAlert.contains("IMCommunityManager")) {
                shared = aContext.getSharedPreferences(UserAppConst.USERINFO, 0);
                String userUUid = shared.getString(UserAppConst.Colour_User_uuid, "");
                shared.edit().putBoolean(UserAppConst.IM_CMANGAG_ERHELPER + userUUid, true).commit();
                Message msg = new Message();
                msg.what = UserMessageConstant.COMMUNITY_MANAGER_NOTICE;
                EventBus.getDefault().post(msg);
            }
            try {
                JSONObject jsonObject = new JSONObject(extraAlert);
                if (!jsonObject.isNull("content_type")) {
                    String contentType = jsonObject.optString("content_type");
                    if ("colourlifeInstant".equals(contentType)) {
                        Message msg = new Message();
                        msg.what = UserMessageConstant.APP_INSTANCE_MSG;
                        msg.obj = jsonObject.optString("single_message");
                        EventBus.getDefault().post(msg);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // 点击通知
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");
            String command = intent.getStringExtra("command");
            if (isRunningForeground()) {
                if (TextUtils.isEmpty(command)) {
                    String extraExtra = bundle.getString(JPushInterface.EXTRA_EXTRA);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(extraExtra);
                        String resourceId = "";
                        if (!jsonObject.isNull("resource_id")) {
                            resourceId = jsonObject.getString("resource_id");
                        }
                        String url = jsonObject.getString("url");
                        if (TextUtils.isEmpty(url)) {
                            intent.setClass(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                                    | Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式*
                            context.startActivity(intent);
                        } else {
                            if (url.startsWith("http") || url.startsWith("https")) {
                                intent.setClass(context, WebViewActivity.class);
                                intent.putExtra(WebViewActivity.JUSHURL, url);
                                intent.putExtra(WebViewActivity.JUSHRESOURCEID, resourceId);
                                intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                                        | Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式
                                context.startActivity(intent);
                            } else {
                                Intent jumpIntent = new Intent(context, MainActivity.class);
                                intent.putExtra(MainActivity.JUMPOTHERURL, url);
                                context.startActivity(jumpIntent);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        intent.setClass(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式*
                        context.startActivity(intent);
                    }
                } else {
                    intent.setClass(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式*
                    context.startActivity(intent);
                }
            }
        }

    }

    // 获取推送通知注册码

    private void getPushRegId(Context ctx) {
        if (mLocalBroadcastManager == null) {
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(ctx);
        }
        Intent data1 = new Intent();
        data1.setAction(Constant.ACTION_PUSHMESSAGE_REG);
        mLocalBroadcastManager.sendBroadcast(new Intent(data1));

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(Context ctx, Bundle bundle) {
        int notifysound = 0;
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String cmd = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
        if (mLocalBroadcastManager == null) {
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(ctx);
        }
        int icon = R.drawable.img_icon;
        long when = System.currentTimeMillis();
        NotificationCompat.Builder builder;
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "jpush_login";
            builder = new NotificationCompat.Builder(ctx, CHANNEL_ID);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, message, importance);
            channel.setDescription(message);
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);
            channel.setShowBadge(false);
            notificationManager.createNotificationChannel(channel);
        } else {
            builder = new NotificationCompat.Builder(ctx);
        }
        builder.setContentTitle(message);
        builder.setContentText(message);
        builder.setSmallIcon(icon);
        builder.setWhen(when);
        builder.setAutoCancel(true);
        Notification notification = new Notification.Builder(aContext).build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        if (notifysound == 0) {
            shared = aContext.getSharedPreferences(UserAppConst.USERINFO, 0);
            if (shared.getBoolean("VOICE", true)) {
                notification.defaults = Notification.DEFAULT_ALL;
                builder.setDefaults(Notification.DEFAULT_ALL);
            }
        } else {
            notification.sound = null;
            notification.vibrate = null;
        }
        Intent intent;
        PendingIntent pi;
        try {
            intent = new Intent(ctx, JPushReciever.class);
            intent.setAction(JPushInterface.ACTION_NOTIFICATION_OPENED);
            intent.putExtra("command", cmd);
            pi = PendingIntent.getBroadcast(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pi);
            mNotifMan= (NotificationManager) aContext.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification1 = builder.build();
            mNotifMan.notify(0, notification1);
            Intent data6 = new Intent();
            data6.setAction(Constant.ACTION_C6);
            mLocalBroadcastManager.sendBroadcast(data6);
            mNotifMan.notify(message, ++Constant.pushId, notification);
        } catch (Exception e) {

        }
    }

    // 判断本应用是否正在前台运行（前台运行的activity以此应用的包名开头）
    private boolean isRunningForeground() {
        String packageName = getPackageName(aContext);
        String topActivityClassName = getTopActivityName(aContext);
        if (packageName != null && topActivityClassName != null) {
            return true;
        } else {
            return false;
        }
    }

    // 获取本应用的包名
    public String getPackageName(Context context) {
        String packageName = context.getPackageName();
        return packageName;
    }

    // 获取正在前台运行中的activity名字
    public String getTopActivityName(Context context) {
        String topActivityClassName = null;
        ActivityManager activityManager = (ActivityManager) (context
                .getSystemService(android.content.Context.ACTIVITY_SERVICE));
        List<RunningTaskInfo> runningTaskInfos = activityManager
                .getRunningTasks(1);
        if (runningTaskInfos != null) {
            ComponentName f = runningTaskInfos.get(0).topActivity;
            topActivityClassName = f.getClassName();
        }
        return topActivityClassName;
    }
}
