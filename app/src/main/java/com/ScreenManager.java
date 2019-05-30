package com;

import android.app.Activity;
import android.content.Context;

import java.io.DataOutputStream;
import java.util.Stack;

public class ScreenManager {
    private static Stack<Activity> activityStack;
    private static ScreenManager instance;

    private ScreenManager() {
    }

    public static ScreenManager getScreenManager() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public boolean hasActivity() {
        if (activityStack != null && activityStack.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isTop() {
        if (activityStack == null || activityStack.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void removeActivity(Activity activity) {
        if (activityStack != null) {
            activityStack.remove(activity);
        }

    }

    public void removeAllActivity() {
        if (activityStack != null) {
            activityStack.clear();
        }

    }

    public void popActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activity.finish();
            activity = null;
        }
    }

    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    public Activity currentActivity() {

        if (activityStack != null && activityStack.size() > 0) {
            Activity activity = activityStack.lastElement();
            return activity;
        }
        return null;
    }

    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        if (!activityStack.contains(activity)) {
            activityStack.add(activity);
        }

    }

    public void popAllActivityExceptOne(Class<?> cls) {
        while (true) {

            Activity activity = currentActivity();
//			Log.e("pop", "pop:"+activity.getLocalClassName());
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
//				Log.e("pop", "break");
                break;
            }
            popActivity(activity);
        }
    }

    public void amForceAppProcess(Context ctx) {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(" am force-stop " + ctx.getPackageName() + " \n ");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
