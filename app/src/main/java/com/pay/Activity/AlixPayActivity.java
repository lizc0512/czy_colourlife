package com.pay.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.pay.alipay.PartnerConfig;
import com.pay.alipay.Rsa;

import java.lang.ref.WeakReference;
import java.net.URLEncoder;

import cn.net.cyberway.R;

/**
 * 模拟商户应用的商品列表，交易步骤。
 * <p>
 * 1. 将商户ID，收款帐号，外部订单号，商品名称，商品介绍，价格，通知地址封装成订单信息 2. 对订单信息进行签名 3.
 * 将订单信息，签名，签名方式封装成请求参数 4. 调用pay方法
 */
public class AlixPayActivity extends Activity {
    public static final String ALIPAY_ORDER_INFOR = "alipay_order_infor";
    public static final int ALIPAY_PAYRESULT = 10000;
    private ProgressDialog mProgress = null;
    private PartnerConfig partnerConfig;
    private String alipay_order_infor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        partnerConfig = new PartnerConfig(this);
        Intent intent = getIntent();
        alipay_order_infor = intent.getStringExtra(ALIPAY_ORDER_INFOR);
        performPay();
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param signType 签名方式
     * @param content  待签名订单信息
     * @return
     */
    String sign(String signType, String content) {
        return Rsa.sign(content, partnerConfig.RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     * @return
     */
    String getSignType() {
        String getSignType = "sign_type=" + "\"" + "RSA" + "\"";
        return getSignType;
    }


    protected void performPay() {
        // 根据订单信息开始进行支付
        try {
            // prepare the order info.
            // 准备订单信息
            // 这里根据签名方式对订单信息进行签名
            String signType = getSignType();
            String strsign = sign(signType, alipay_order_infor);
            // 对签名进行编码
            strsign = URLEncoder.encode(strsign, "UTF-8");
            // 组装好参数
            final String info = alipay_order_infor + "&sign=" + "\"" + strsign + "\"" + "&"
                    + getSignType();
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    // 构造PayTask 对象
//                    PayTask alipay = new PayTask(AlixPayActivity.this);
//                    // 调用支付接口，获取支付结果
//                    String result = alipay.pay(info, true);
//                    Message msg = new Message();
//                    msg.what = ALIPAY_PAYRESULT;
//                    msg.obj = result;
//                    mHandler.sendMessage(msg);
                }
            };
            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        } catch (Exception ex) {
            Toast.makeText(AlixPayActivity.this, R.string.remote_call_failed,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {

        private final WeakReference<AlixPayActivity> demoActivityWeakReference;

        private MyHandler(AlixPayActivity activity) {
            this.demoActivityWeakReference = new WeakReference<AlixPayActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            AlixPayActivity activity = demoActivityWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case ALIPAY_PAYRESULT:
                        String ret = (String) msg.obj;
                        activity.closeProgress();
                        activity.payResult(ret);
                    default:
                        break;
                }
            }
        }
    }


    private void payResult(String resultInfo) {
        // 处理交易结果
        try {
            // 获取交易状态码，具体状态代码请参看文档
            String tradeStatus = "resultStatus={";
            int imemoStart = resultInfo.indexOf("resultStatus=");
            imemoStart += tradeStatus.length();
            int imemoEnd = resultInfo.indexOf("};memo=");
            tradeStatus = resultInfo.substring(imemoStart, imemoEnd);
            /*  9000——订单支付成功
    //	                8000——正在处理中
    //	                4000——订单支付失败
    //	                5000——重复请求
    //	                6001——用户中途取消
    //	                6002——网络连接出错*/
            if (tradeStatus.equals("9000")) {
                Intent data = new Intent();
                data.putExtra("pay_result", "success");
                setResult(Activity.RESULT_OK, data);
                finish();
            } else if (tradeStatus.equals("6001")) {
                Intent data = new Intent();
                data.putExtra("pay_result", "cancel");
                setResult(Activity.RESULT_OK, data);
                finish();
            } else {
                Intent data = new Intent();
                data.putExtra("pay_result", "fail");
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 关闭进度框
    public void closeProgress() {
        try {
            if (mProgress != null) {
                mProgress.dismiss();
                mProgress = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mProgress.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
