package com.BeeFramework.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.reflect.Method;

/**
 * 网络工具类
 *
 * @author ender
 */
public class NetworkUtil {

    /**
     * 判断网络是否可用
     *
     * @return -1：网络不可用 0：移动网络 1：wifi网络 2：未知网络
     */
    public static int isNetworkEnabled(Context context) {
        int status = -1;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            switch (networkInfo.getType()) {
                case ConnectivityManager.TYPE_MOBILE: { // 移动网络
                    status = 0;
                    break;
                }
                case ConnectivityManager.TYPE_WIFI: { // wifi网络
                    status = 1;
                    break;
                }
                default: {
                    status = -1;
                }
            }
        }

        return status;
    }

    /**
     * 是否有网络
     *
     * @param context
     * @return
     */
    public static boolean isConnect(Context context) {
        return isNetworkAvailable(context);
    }


    /***判断当前的网络是否可用***/
    public static boolean isNetworkAvailable(Context context) {
        if (null == context) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        } else {
            //如果仅仅是用来判断网络连接
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null && info.length > 0) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断数据网络是否开启
     *
     * @param context
     * @return
     */
    public static boolean haveIntent(Context context) {
        boolean mobileDataEnabled = false; // Assume disabled
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean) method.invoke(cm);
        } catch (Exception e) {
            // Some problem accessible private API
            // TODO do whatever error handling you want here
        }
        return mobileDataEnabled;
    }
}
