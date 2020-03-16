package com.BeeFramework;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.lhqpay.ewallet.keepIntact.PayUtil;

/**
 * @author zhao.yi
 * @version 1.0
 * @ClassName: CaiQianB
 * @Desc: TODO
 * @date 2018-7-7 18:53
 * @history v1.0
 */
public class JavaScriptMetod {
    public static final String JAVAINTERFACE = "javaInterface";
    private Context mContext;
    private WebView mWebView;
    private Activity ac;

    public JavaScriptMetod(Context context, WebView webView, Activity activity) {
        mContext = context;
        mWebView = webView;
        this.ac = activity;
    }

    @JavascriptInterface
    public void isFinishOrder(boolean bl) {
        //TODO 这是我捕捉到h5端的点击事件后进行处理
        PayUtil.getInstance(ac).quitPaySDK(mContext);
        ac.finish();
    }
}
