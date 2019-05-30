package com.audio.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import cn.net.cyberway.R;

public class QNAppServer {
    /**
     * 设置推流画面尺寸，仅用于 Demo 测试，用户可以在创建七牛 APP 时设置该参数
     */
    public static final int STREAMING_WIDTH = 480;
    public static final int STREAMING_HEIGHT = 848;
    public static final String ADMIN_USER = "admin";

    private static final String TAG = "QNAppServer";
    private static final String APP_SERVER_ADDR = "https://api-demo.qnsdk.com";
    public static final String APP_ID = "d8lk7l4ed";
    public static final String TEST_MODE_APP_ID = "d8dre8w1p";

    private static class QNAppServerHolder {
        private static final QNAppServer instance = new QNAppServer();
    }

    private QNAppServer() {
    }

    public static QNAppServer getInstance() {
        return QNAppServerHolder.instance;
    }


    public String requestRoomToken(Context context, String userId, String roomName) {
        /**
         * 此处服务器 URL 仅用于 Demo 测试，随时可能修改/失效，请勿用于 App 线上环境！！
         * 此处服务器 URL 仅用于 Demo 测试，随时可能修改/失效，请勿用于 App 线上环境！！
         * 此处服务器 URL 仅用于 Demo 测试，随时可能修改/失效，请勿用于 App 线上环境！！
         */
        String url = APP_SERVER_ADDR + "/v1/rtc/token/admin/app/" + getAppId(context) + "/room/" + roomName + "/user/" + userId + "?bundleId=" + packageName(context);

        return null;
    }

    private String getAppId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        return sharedPreferences.getString(Config.APP_ID, APP_ID);
    }

    public static String packageName(Context context) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            // e.printStackTrace();
        }
        return "";
    }
}
