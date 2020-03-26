package com.BeeFramework.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.view.ToastView;

import cn.net.cyberway.R;

public class ToastUtil {
    private static boolean isToast = true;
    private static boolean isInterToast = true;

    /**
     * 弹出toast
     *
     * @param context
     * @param text
     */
    public static void toastShow(Context context, String text) {
        if (null != context && !TextUtils.isEmpty(text) && isToast == true) {
            ToastView toast = new ToastView(context, text);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 弹出toast:专为网络连接情况提示语
     *
     * @param context
     * @param text
     */
    public static void toastInterShow(Context context, String text) {
        if (null != context && !TextUtils.isEmpty(text) && isInterToast == true) {
            ToastView toast = new ToastView(context, text);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 弹出toast
     *
     * @param context
     * @param text
     */
    public static void toastShow(Context context, int text) {
        if (null != context && isToast == true) {
            ToastView toast = new ToastView(context, text);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public static void toastTime(Context context, String text, int duration) {
        if (null != context && !TextUtils.isEmpty(text) && isToast == true) {
            ToastView toast = new ToastView(context, text);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(duration);
            toast.show();
        }
    }

    public static void toastBottomTime(Context context, String text, int duration) {
        if (null != context && !TextUtils.isEmpty(text) && isToast == true) {
            ToastView toast = new ToastView(context, text);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.setDuration(duration);
            toast.show();
        }
    }

    public static void toastJoinActivity(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.community_activity_toast, null);
        TextView t = (TextView) view.findViewById(R.id.toast_text);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
