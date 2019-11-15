package com.pay.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.pay.alipay.AlixId;
import com.pay.alipay.PartnerConfig;
import com.pay.alipay.Rsa;

import java.lang.ref.WeakReference;
import java.net.URLEncoder;

import cn.net.cyberway.R;

/**
 * 模拟商户应用的商品列表，交易步骤。
 * 
 * 1. 将商户ID，收款帐号，外部订单号，商品名称，商品介绍，价格，通知地址封装成订单信息 2. 对订单信息进行签名 3.
 * 将订单信息，签名，签名方式封装成请求参数 4. 调用pay方法
 *
 */
public class AlixPayActivity extends Activity  {
    private ProgressDialog mProgress = null;
    private PartnerConfig partnerConfig;
    private static final int SDK_PAY_FLAG = 1;

    public static final String ALIPAY_OUT_TRADE_NO="alipay_out_trade_no";
    public static final String ALIPAY_SUBJECT="alipay_subject";
    public static final String ALIPAY_BODY="alipay_body";
    public static final String ALIPAY_TOTAL_FEE="alipay_total_fee";

    private String out_trade_no;
    private String subject;
    private String body;
    private String total_fee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        partnerConfig = new PartnerConfig(this);
        Intent intent=getIntent();
        out_trade_no=intent.getStringExtra(ALIPAY_OUT_TRADE_NO);
        subject=intent.getStringExtra(ALIPAY_SUBJECT);
        body=intent.getStringExtra(ALIPAY_BODY);
        total_fee=intent.getStringExtra(ALIPAY_TOTAL_FEE);
        performPay();

    }



    /**
     * get the selected order info for pay. 获取商品订单信息
     *
     * @param
     * @return
     */

    String getOrderInfo() {
        String strOrderInfo = "partner=" + "\"" + partnerConfig.PARTNER + "\"";
        strOrderInfo += "&";
        strOrderInfo += "seller_id=" + "\"" + partnerConfig.SELLER + "\"";
        strOrderInfo += "&";
        strOrderInfo += "out_trade_no=" + "\"" +out_trade_no + "\"";
        strOrderInfo += "&";
        strOrderInfo += "subject=" + "\"" + subject
                + "\"";
        strOrderInfo += "&";
        strOrderInfo += "body=" + "\"" +body + "\"";
        strOrderInfo += "&";
        strOrderInfo += "total_fee=" + "\""
                + total_fee + "\"";
        strOrderInfo += "&";
        strOrderInfo += "notify_url=" + "\""
                + partnerConfig.ALIPAY_CALLBACK + "\"";

        // 服务接口名称， 固定值
        strOrderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        strOrderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        strOrderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        strOrderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        //strOrderInfo += "&return_url=\"m.alipay.com\"";

        return strOrderInfo;
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
            String orderInfo = getOrderInfo();
            // 这里根据签名方式对订单信息进行签名
            String signType = getSignType();
            String strsign = sign(signType, orderInfo);
            // 对签名进行编码
            strsign = URLEncoder.encode(strsign, "UTF-8");
            // 组装好参数
            final String info = orderInfo + "&sign=" + "\"" + strsign + "\"" + "&"
                    + getSignType();
            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask(AlixPayActivity.this);
                    // 调用支付接口，获取支付结果
                    String result = alipay.pay(info,true);
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
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
                    case AlixId.RQF_PAY: {
                        String ret = (String) msg.obj;
                        activity.closeProgress();
                        activity.payResult(ret);
                    }
                    default:
                        break;
                }


            }
        }
    }



    private void  payResult(String resultInfo ){
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
            if (tradeStatus.equals("9000"))
            {
                Intent data=new Intent();
                data.putExtra("pay_result", "success");
                setResult(Activity.RESULT_OK, data);
                finish();
            }else if(tradeStatus.equals("6001")){
                Intent data=new Intent();
                data.putExtra("pay_result", "cancel");
                setResult(Activity.RESULT_OK, data);
                finish();
            } else {
                Intent data=new Intent();
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

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent,requestCode);
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }
}
