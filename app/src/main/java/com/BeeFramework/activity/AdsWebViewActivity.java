package com.BeeFramework.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.external.eventbus.EventBus;
import com.user.UserMessageConstant;

import cn.net.cyberway.R;

import static com.BeeFramework.activity.WebViewActivity.GUANGCAIPAY;
import static com.BeeFramework.activity.WebViewActivity.WEBTITLE;
import static com.BeeFramework.activity.WebViewActivity.WEBURL;
import static com.BeeFramework.activity.WebViewActivity.finishStatus;

/**
 * 进入首页展示活动的webview
 * Created by chenql on 16/1/25.
 */
public class AdsWebViewActivity extends BaseActivity implements View.OnClickListener {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ProgressBar progressBar;
    private WebView oauth_webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oauth_webview);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        oauth_webView = (WebView) findViewById(R.id.oauth_webView);
        user_top_view_back.setOnClickListener(this);
        WebSettings webSettings = oauth_webView.getSettings();
        webSettings.supportZoom();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        Intent intent = getIntent();
        String url = intent.getStringExtra(WEBURL);
        String webTitle = intent.getStringExtra(WEBTITLE);
        user_top_view_title.setText(webTitle);
        oauth_webView.setWebViewClient(new HtmlWebViewClient());
        oauth_webView.setWebChromeClient(new HtmlWebChromeClient());
        oauth_webView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                returnCallBack();
                break;
        }
    }


    private class HtmlWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (!TextUtils.isEmpty(title)) {
                if (title.length() > 8)
                    title = title.substring(0, 8) + "...";
                user_top_view_title.setText(title);
            }
        }
    }

    private class HtmlWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (parseScheme(url)) {
                jumpByUrls(url);
                return true;
            } else {
                return false;
            }
        }
    }

    private void jumpByUrls(String urls) {
        try {
            Intent intent = Intent.parseUri(urls,
                    Intent.URI_INTENT_SCHEME);
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setComponent(null);
            startActivityForResult(intent, GUANGCAIPAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***跳转到支付宝**/
    public boolean parseScheme(String url) {
        //alipays://platformapi
        if (url.contains("alipay")) {
            return true;
        } else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                && (url.contains("platformapi") && url.contains("startapp"))) {
            return true;
        } else {
            return false;
        }
        //  return startIntentUrl(var0, "intent://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2F{urlCode}%3F_s%3Dweb-other&_t=1472443966571#Intent;scheme=alipayqr;package=com.eg.android.AlipayGphone;end".replace("{urlCode}", var1));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GUANGCAIPAY:
                returnCallBack();
                break;
        }
    }


    private void returnCallBack() {
        Message message = Message.obtain();
        if ("2".equals(finishStatus)) {
            message.what = 101010;
        } else {
            message.what = UserMessageConstant.GUANGCAI_PAY_MSG;
        }
        EventBus.getDefault().post(message);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnCallBack();
    }
}
