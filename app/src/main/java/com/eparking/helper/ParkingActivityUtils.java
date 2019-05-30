package com.eparking.helper;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.helper
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/28 10:14
 * @change
 * @chang time
 * @class describe
 */
public class ParkingActivityUtils {
    private List<Activity> activityList = new LinkedList<Activity>();
    public static ParkingActivityUtils instance;

    // 单例模式中获取唯一的MyApplication实例
    public static ParkingActivityUtils getInstance() {
        if (null == instance) {
            instance = new ParkingActivityUtils();
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (null != activity) {
            activityList.add(activity);
        }
    }

    // 遍历Activity并finish
    public void exit() {
        if (null != activityList) {
            for (Activity activity : activityList) {
                activity.finish();
            }
        }
    }
}
