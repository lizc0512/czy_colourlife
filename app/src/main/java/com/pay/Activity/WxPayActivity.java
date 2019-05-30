package com.pay.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import com.BeeFramework.activity.BaseActivity;
import com.cashier.modelnew.WXBean;
import com.cashier.protocolnew.PAYINFODATA;
import com.external.eventbus.EventBus;
import com.insthub.Config;
import com.pay.WxPayModel;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/*
 *微信支付
 */
public class WxPayActivity extends BaseActivity {
    private IWXAPI mWeixinAPI = null;
    private WxPayModel mWxPayModel;
    public static String PAY_INFOMATION = "PAY_INFOMATION";
    private WXBean wxBean;

    //T+0饭票改造
    public static String TAGFP = "TAGFPZF";
    public static String PAY_FPINFOMESSGE = "PAY_FPINFOMESSGE";
    private String fp_come;
    private PAYINFODATA mFpInfomation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWxPayModel = new WxPayModel(this);
        //T+0
        fp_come = getIntent().getStringExtra(TAGFP);
        if (!TextUtils.isEmpty(fp_come)) {
            mFpInfomation = (PAYINFODATA) getIntent().getSerializableExtra(PAY_FPINFOMESSGE);

            if (null != mFpInfomation) {
                wxpayfpInfo();
            } else {
                if (mWeixinAPI == null) {
                    mWeixinAPI = WXAPIFactory.createWXAPI(this, Config.WEIXIN_APP_ID);
                    // 将该app注册到微信
                    mWeixinAPI.registerApp(Config.WEIXIN_APP_ID);
                }
            }
        }

        Intent intent = this.getIntent();
        //订单传过来的数据
        wxBean = (WXBean) intent.getSerializableExtra(PAY_INFOMATION);

        if (null != wxBean) {
            wxpayInfo();
        } else {
            if (mWeixinAPI == null) {
                mWeixinAPI = WXAPIFactory.createWXAPI(this, Config.WEIXIN_APP_ID);
                // 将该app注册到微信
                mWeixinAPI.registerApp(Config.WEIXIN_APP_ID);
            }
        }

        EventBus.getDefault().register(this);

    }

    private void wxpayInfo() {

        mWeixinAPI = WXAPIFactory.createWXAPI(this, wxBean.getAppid());
        // 将该app注册到微信
        mWeixinAPI.registerApp(wxBean.getAppid());
        PayReq req = new PayReq();
        req.appId = wxBean.getAppid();
        req.partnerId = wxBean.getPartnerid();
        req.prepayId = wxBean.getPrepayid();
        req.nonceStr = wxBean.getNoncestr();
        req.timeStamp = String.valueOf(wxBean.getTimestamp());
        req.packageValue = wxBean.getPackageStr();//"Sign=" + packageValue;
        req.sign = wxBean.getSign();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        mWeixinAPI.sendReq(req);
    }

    /**
     * 改版后的
     */
    private void wxpayfpInfo() {
        mWeixinAPI = WXAPIFactory.createWXAPI(this, wxBean.getAppid());
        // 将该app注册到微信
        mWeixinAPI.registerApp(wxBean.getAppid());
        PayReq req = new PayReq();
        req.appId = wxBean.getAppid();
        req.partnerId = wxBean.getPartnerid();
        req.prepayId = wxBean.getPrepayid();
        req.nonceStr = wxBean.getNoncestr();
        req.timeStamp = String.valueOf(wxBean.getTimestamp());
        req.packageValue = wxBean.getPackageStr();//"Sign=" + packageValue;
        req.sign = wxBean.getSign();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        mWeixinAPI.sendReq(req);
    }

    public void onEvent(Object event) {
        Message message = (Message) event;
        Intent data = null;
        setResult(Activity.RESULT_OK, data);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
