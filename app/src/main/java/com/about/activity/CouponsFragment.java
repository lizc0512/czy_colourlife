package com.about.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.BeeFramework.Utils.Utils;
import com.agentweb.AgentWeb;
import com.agentweb.AgentWebSettings;
import com.agentweb.WebDefaultSettingsManager;
import com.update.activity.UpdateVerSion;
import com.user.UserAppConst;
import com.user.Utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;

public class CouponsFragment extends Fragment {
    private String testOauthUrl = "http://oauth2-czytest.colourlife.com";
    private String betaOauthUrl = "https://oauth2czy-czybeta.colourlife.com";
    private String officialOauthUrl = "https://oauth2czy.colourlife.com";
    private View rootView;
    public String WEBURL = "";
    private AgentWeb mAgentWeb;
    private HtmlWebChromeClient htmlWebChromeClient;
    private HtmlWebViewClient htmlWebViewClient;
    private WebView webView;
    private SharedPreferences shared;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shared = getActivity().getSharedPreferences(UserAppConst.USERINFO, 0);
        rootView = inflater.inflate(R.layout.coupons_fragment, container, false);
        prepareView();
        prepareData();
        return rootView;
    }


    private void prepareView() {
        htmlWebViewClient = new HtmlWebViewClient();
        htmlWebChromeClient = new HtmlWebChromeClient();
    }

    public AgentWebSettings getSettings() {
        return WebDefaultSettingsManager.getInstance();
    }

    public void prepareData() {
        if (!TextUtils.isEmpty(WEBURL)) {
            mAgentWeb = AgentWeb.with(this)//传入Activity
                    .setAgentWebParent((FrameLayout) rootView.findViewById(R.id.webview_layout), new RelativeLayout.LayoutParams(-1, -1))
                    .setIndicatorColorWithHeight(Color.parseColor("#01A7FF"), 2)
                    .setWebViewClient(htmlWebViewClient)
                    .setWebChromeClient(htmlWebChromeClient)
                    .setSecurityType(AgentWeb.SecurityType.strict)
                    .setAgentWebWebSettings(getSettings())
                    .createAgentWeb().ready().go(WEBURL);
            mAgentWeb.getJsInterfaceHolder().addJavaObject("jsObject", new JSObject());
            webView = mAgentWeb.getWebCreator().get();
            webView.setWebChromeClient(htmlWebChromeClient);
            webView.setWebViewClient(htmlWebViewClient);
            webView.loadUrl(WEBURL);
            String imei = TokenUtils.getImeiId(getActivity());
            String imeis = Utils.setMD5(imei).toUpperCase();
            WebSettings webSettings = webView.getSettings();
            String ua = webSettings.getUserAgentString();
            webSettings.setUserAgentString(ua + "/" + imeis + "/colourlifeApp");
        }
    }

    public void goBack() {
        if (webView != null) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                try {
                    Objects.requireNonNull(getActivity()).setResult(200, new Intent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getActivity().finish();
            }
        }
    }

    public class JSObject {
        /**
         * 获取当前应用版本名
         */
        @JavascriptInterface
        public String getAppVersion() {
            String versionName = "";
            try {
                PackageManager pm = getActivity().getPackageManager();
                PackageInfo pi = pm.getPackageInfo(
                        getActivity().getPackageName(), 0);
                versionName = pi.versionName;
                if (versionName == null || versionName.length() <= 0) {
                    return "-1";
                } else {
                    return versionName;
                }
            } catch (Exception e) {
                return "-1";
            }
        }

        /**
         * 获取设备号唯一信息
         *
         * @return
         */
        @JavascriptInterface
        public String getDeviceHandler() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("device", TokenUtils.getUUID(getActivity().getApplicationContext()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject.toString();
        }

        /**
         * 获取当前应用本地显示版本号
         */
        @JavascriptInterface
        public String getAppLocalVersion() {
            String versionName = "";
            versionName = UpdateVerSion.showVersionName(UpdateVerSion.getVersionName(getActivity()));
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("version", versionName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject.toString();
        }

        /**
         * 获取软件版本号
         *
         * @return
         */
        @JavascriptInterface
        public int getVersionCode() {
            int versionCode = 1;
            try {
                // 获取软件版本号，
                versionCode = getActivity().getPackageManager().getPackageInfo(
                        getActivity().getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return versionCode;
        }

        @JavascriptInterface
        public String getNativeType() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("native_type", 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject.toString();
        }
    }

    private class HtmlWebChromeClient extends WebChromeClient {
        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
//            屏蔽下句达到禁止Webview页面自带定位功能  和使用缓存定位
            callback.invoke(origin, true, true);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }

        @Override
        public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
            //设置数据库存储大小，最大是5*1024*1024，也就是5MB
            quotaUpdater.updateQuota(estimatedSize * 2);
        }

    }


    private class HtmlWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String urls) {
            WebView.HitTestResult hitTestResult = view.getHitTestResult();
            if (urls.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(urls));
                startActivity(intent);
                return true;
            } else if (urls.startsWith(testOauthUrl) || urls.startsWith(betaOauthUrl) || urls.startsWith(officialOauthUrl)) {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("color-token", shared.getString(UserAppConst.Colour_access_token, ""));
                webView.loadUrl(urls, headerMap);
                return false;
            } else if (null == hitTestResult || WebView.HitTestResult.UNKNOWN_TYPE == hitTestResult.getType()) {
                return false;
            }
            return false;
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // 接受网站证书
        }


        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if (null != getActivity()) {
                LinkParseUtil.parse(getActivity(), WEBURL, "");
            } else {
                webView.loadUrl(failingUrl);
            }
        }
    }
}
