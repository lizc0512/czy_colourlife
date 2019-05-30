package com.BeeFramework.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;

import com.BeeFramework.view.ToastView;

public class ToastUtil {
	private static boolean isToast=true;
	private static boolean isInterToast=true;
	/**
	 * 弹出toast
	 * @param context
	 * @param text
	 */
	public static void toastShow(Context context, String text) {
		if(null!=context&& !TextUtils.isEmpty(text) && isToast==true) {
			ToastView toast = new ToastView(context, text);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}
	/**
	 * 弹出toast:专为网络连接情况提示语
	 * @param context
	 * @param text
	 */
	public static void toastInterShow(Context context, String text) {
		if(null!=context&& !TextUtils.isEmpty(text) && isInterToast==true) {
			ToastView toast = new ToastView(context, text);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}
	/**
	 * 弹出toast
	 * @param context
	 * @param text
	 */
	public static void toastShow(Context context, int text) {
		if (null!=context && isToast==true){
			ToastView toast = new ToastView(context, text);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}
	public static void toastTime(Context context, String text,int duration) {
		if(null!=context&& !TextUtils.isEmpty(text) && isToast==true) {
			ToastView toast = new ToastView(context, text);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setDuration(duration);
			toast.show();
		}
	}
}
