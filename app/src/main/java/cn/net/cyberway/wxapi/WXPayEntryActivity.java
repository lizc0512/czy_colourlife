package cn.net.cyberway.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;

import com.BeeFramework.view.ToastView;
import com.external.eventbus.EventBus;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信支付回调
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    public static String APPID = "appid";
    public static String PREPAYID = "prepayid";
    public static String NONCESTR = "noncestr";
    public static String TIMESTAMP = "timestamp";
    public static String PACKAGEVALUE = "wx_package";
    public static String SIGN = "sign";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, "wx86965bc046c2fac8");
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
            //成功
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            if (resp.errCode == 0) {
                this.finish();
                ToastView toast = new ToastView(this, "支付成功");
                toast.setGravity(Gravity.CENTER, 0, 0);
            //失败
            } else if (resp.errCode == -1) {
                this.finish();
                ToastView toast = new ToastView(this, "支付失败");
                toast.setGravity(Gravity.CENTER, 0, 0);
            }
            //取消
            else if (resp.errCode == -2){
                this.finish();
                ToastView toast = new ToastView(this, "支付取消");
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(2000);
            }
        }
    }
}