package cn.net.cyberway.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by HX_CHEN on 2016/1/6.
 */
public class BroadcastReceiverActivity extends BroadcastReceiver {
    private Activity activity;

    public static final String FINISH_PAYMENT_ACTIVYTY = "FINISH_PAYMENT_ACTIVYTY"; // E缴费、E停车

    public static final String GESTURE = "FINISH_GESTURE";//手势密码退出登录


    public BroadcastReceiverActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        activity.finish();
    }

}