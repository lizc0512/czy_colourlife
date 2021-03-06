package com.jpush.reciever;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
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
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.audio.activity.RoomActivity.COMMUNITY_NAME;
import static com.audio.activity.RoomActivity.COMMUNITY_UUID;
import static com.audio.activity.RoomActivity.DOOR_ID;
import static com.audio.activity.RoomActivity.EXTRA_ROOM_ID;
import static com.audio.activity.RoomActivity.EXTRA_ROOM_TOKEN;
import static com.audio.activity.RoomActivity.EXTRA_USER_ID;
import static com.audio.activity.RoomActivity.ROOM_NAME;

public class MyReceiver extends JPushMessageReceiver {
    private static final String TAG = "PushMessageReceiver";
    private LocalBroadcastManager mLocalBroadcastManager;
    private SharedPreferences shared;
    private NotificationManager mNotifMan;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(Context context, String message) {
        int notifysound = 0;
        if (mLocalBroadcastManager == null) {
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(context);
        }
        int icon = R.drawable.img_icon;
        long when = System.currentTimeMillis();
        NotificationCompat.Builder builder;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "jpush_login";
            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, message, importance);
            channel.setDescription(message);
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);
            channel.setShowBadge(false);
            notificationManager.createNotificationChannel(channel);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        builder.setContentTitle(message);
        builder.setContentText(message);
        builder.setSmallIcon(icon);
        builder.setWhen(when);
        builder.setAutoCancel(true);
        Notification notification = new Notification.Builder(context).build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        if (notifysound == 0) {
            shared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
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
        mNotifMan = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        try {
            intent = new Intent(context, MyReceiver.class);
            intent.setAction(JPushInterface.ACTION_NOTIFICATION_OPENED);
            pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pi);
            Notification notification1 = builder.build();
            mNotifMan.notify(0, notification1);
            Intent data6 = new Intent();
            data6.setAction(Constant.ACTION_C6);
            mLocalBroadcastManager.sendBroadcast(data6);
            mNotifMan.notify(message, ++Constant.pushId, notification);
        } catch (Exception e) {

        }
    }

    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        String contentType = customMessage.contentType;
        String message = customMessage.message;
        SharedPreferences mShared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        boolean isLogin = mShared.getBoolean(UserAppConst.IS_LOGIN, false);
        if (!TextUtils.isEmpty(contentType)) {
            if ("c6".equalsIgnoreCase(contentType) && !TextUtils.isEmpty(message)) { //单设备挤出登录
                sendNotification(context, message);
            } else {
                if ("c7".equalsIgnoreCase(contentType)) { //更换手机号挤出登录
                    Message msg = new Message();
                    msg.what = UserMessageConstant.AUDIT_PASS_OUT;
                    msg.obj = message;
                    EventBus.getDefault().post(msg);
                } else if ("colourlifeVideoOpenDoor".equalsIgnoreCase(contentType)) {//门禁音视频
                    if (isLogin) {
                        String extraContent = customMessage.extra;
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
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        String command = message.notificationContent;
        if (isRunningForeground(context)) {
            if (TextUtils.isEmpty(command)) {
                String extraExtra = message.notificationExtras;
                JSONObject jsonObject = null;
                try
                {
                    jsonObject = new JSONObject(extraExtra);
                    String resourceId = "";
                    if (!jsonObject.isNull("resource_id")) {
                        resourceId = jsonObject.getString("resource_id");
                    }
                    String url = jsonObject.getString("url");
                    if (TextUtils.isEmpty(url)) {
                        jumpMainPage(context);
                    } else {
                        if (url.startsWith("http") || url.startsWith("https")) {
                            Intent intent = new Intent();
                            intent.setClass(context, WebViewActivity.class);
                            intent.putExtra(WebViewActivity.JUSHURL, url);
                            intent.putExtra(WebViewActivity.JUSHRESOURCEID, resourceId);
                            intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                                    | Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式
                            context.startActivity(intent);
                        } else {
                            Intent intent = new Intent();
                            Intent jumpIntent = new Intent(context, MainActivity.class);
                            intent.putExtra(MainActivity.JUMPOTHERURL, url);
                            context.startActivity(jumpIntent);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    jumpMainPage(context);
                }
            } else {
                jumpMainPage(context);
            }
        }
    }

    private void jumpMainPage(Context context){
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式*
        context.startActivity(intent);
    }


    @Override
    public void onMultiActionClicked(Context context, Intent intent) {

    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Map<String, String> valueMap = new HashMap<String, String>();
        valueMap.put("cn.jpush.android.MSG_ID", String.valueOf(message.notificationId));
        valueMap.put("cn.jpush.android.ALERT", message.notificationExtras);
        valueMap.put("cn.jpush.android.NOTIFICATION_SMALL_ICON", message.notificationSmallIcon);
        valueMap.put("cn.jpush.android.NOTIFICATION_LARGE_ICON", message.notificationLargeIcon);
        valueMap.put("cn.jpush.android.NOTIFICATION_CONTENT_TITLE", message.notificationTitle);
        valueMap.put("cn.jpush.android.ALERT_TYPE", String.valueOf(message.notificationAlertType));
        BasicPushNotificationBuilder basicPushNotificationBuilder = new BasicPushNotificationBuilder(context);
        basicPushNotificationBuilder.statusBarDrawable = R.drawable.img_icon;
        basicPushNotificationBuilder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        basicPushNotificationBuilder.notificationDefaults = Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 震动、呼吸灯闪烁都要
        basicPushNotificationBuilder.buildNotification(valueMap);
        JPushInterface.setPushNotificationBuilder(1, basicPushNotificationBuilder);
        String extraAlert = message.notificationExtras;
        if (extraAlert.contains("IMCommunityManager")) {
            shared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
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
    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {

    }

    @Override
    public void onRegister(Context context, String registrationId) {
        SharedPreferences shared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("UniqueID", registrationId);
        editor.apply();
        if (mLocalBroadcastManager == null) {
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(context);
        }
        Intent data1 = new Intent();
        data1.setAction(Constant.ACTION_PUSHMESSAGE_REG);
        mLocalBroadcastManager.sendBroadcast(new Intent(data1));
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        Log.e(TAG, "[onConnected] " + isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        Log.e(TAG, "[onCommandResult] " + cmdMessage);
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context, jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context, jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context, jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onMobileNumberOperatorResult(context, jPushMessage);
        TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context, jPushMessage);

    }

    // 判断本应用是否正在前台运行（前台运行的activity以此应用的包名开头）
    private boolean isRunningForeground(Context context) {
        String packageName = getPackageName(context);
        String topActivityClassName = getTopActivityName(context);
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
                .getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager
                .getRunningTasks(1);
        if (runningTaskInfos != null) {
            ComponentName f = runningTaskInfos.get(0).topActivity;
            topActivityClassName = f.getClassName();
        }
        return topActivityClassName;
    }
}
