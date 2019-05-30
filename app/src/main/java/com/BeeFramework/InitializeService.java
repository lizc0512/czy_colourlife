package com.BeeFramework;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import com.mob.MobSDK;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.commonsdk.UMConfigure;

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
            MobSDK.init(getApplicationContext());
            JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志
            JPushInterface.init(this);
            //开始定位
            CityManager.getInstance(getApplicationContext()).initLocation();
            TCAgent.init(this.getApplicationContext(), "08184D5951FD5E1F2000152A3006060A", "colourlife");
            TCAgent.setReportUncaughtExceptions(false);
            // 友盟统计数据加密
            // enable为true，SDK会对日志进行加密。加密模式可以有效防止网络攻击，提高数据安全性
            // enable为false，SDK将按照非加密的方式来传输日志
            UMConfigure.setEncryptEnabled(true);
            UMConfigure.setLogEnabled(false);// 设置开启日志,发布时请关闭日志
        } catch (Exception e) {

        }
    }
}
