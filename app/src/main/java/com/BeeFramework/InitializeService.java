package com.BeeFramework;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.BeeFramework.model.Constants;
import com.geetest.deepknow.DPAPI;
import com.mob.MobSDK;
import com.qiniu.droid.rtc.QNRTCEnv;
import com.sobot.chat.SobotApi;
import com.tencent.bugly.Bugly;

import cn.jpush.android.api.JPushInterface;
import cn.net.cyberway.utils.CityManager;

/**
 * 创建时间 : 2017/7/6.
 * 编写人 :  ${yuansk}
 * 功能描述:
 * 版本:
 */

public class InitializeService extends IntentService {

    public InitializeService() {
        super("InitializeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            DPAPI.getInstance(getApplicationContext(), null);
//            Stetho.initializeWithDefaults(getApplicationContext());  实时查看数据库
            MobSDK.init(getApplicationContext());
            SobotApi.initSobotSDK(getApplicationContext(), Constants.SMART_SERVICE_KEY, "");
            JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志
            JPushInterface.init(this);
            //开始定位
            CityManager.getInstance(getApplicationContext()).initLocation();
            QNRTCEnv.init(getApplicationContext());

//        LeakCanary.install(this);
//        initSWLocation();//数位
            initBugly();
        } catch (Exception e) {

        }
    }

    /**
     * 腾讯热更新 bugly
     */
    private void initBugly() {
        Bugly.init(this, Constants.BUGLY_KEY, false);
    }
}
