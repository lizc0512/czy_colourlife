package cn.net.cyberway.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import com.BeeFramework.Utils.ToastUtil;
import com.external.eventbus.EventBus;
import com.insthub.Config;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.user.UserMessageConstant;

/**
 * 微信支付回调
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Config.WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            //TODO 微信小程序支付反馈
            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) resp;
            String extraData = launchMiniProResp.extMsg; //对应小程序组件 <button ope n-type="launchApp"> 中的 app-parameter 属性
            ToastUtil.toastShow(WXPayEntryActivity.this, "小程序支付回调");
            if (extraData.equals("ok") || extraData.equals("success")) {
                Message message = Message.obtain();
                message.what = UserMessageConstant.GUANGCAI_PAY_MSG;
                EventBus.getDefault().post(message);
            } else if (extraData.equals("fail")) {
                Message message = Message.obtain();
                message.what = UserMessageConstant.GUANGCAI_PAY_MSG;
                EventBus.getDefault().post(message);
            } else if (extraData.equals("cancel")) {
                Message message = Message.obtain();
                message.what = UserMessageConstant.GUANGCAI_PAY_MSG;
                EventBus.getDefault().post(message);
            } else if (extraData.equals("no")) {
                Message message = Message.obtain();
                message.what = UserMessageConstant.GUANGCAI_PAY_MSG;
                EventBus.getDefault().post(message);
            }
        }
    }
}