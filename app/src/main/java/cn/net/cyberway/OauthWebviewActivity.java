package cn.net.cyberway;


import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.Constants;
import com.user.UserAppConst;
import com.user.Utils.TokenUtils;
import com.user.activity.UserRegisterAndLoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 创建时间 : 2017/9/4.
 * 编写人 :  ${yuansk}
 * 功能描述: oauth2.0授权登录
 * 版本:
 */

public class OauthWebviewActivity extends BaseActivity implements View.OnClickListener {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ProgressBar progressBar;
    private WebView oauth_webView;
    private String loadUrl = "";
    private String baseUrl = Constants.OAUTH_ADDRESS;
    private String applicationId = "";
    private String packgeName = "";
    private boolean isReload = false;

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
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        oauth_webView.setWebViewClient(new HtmlWebViewClient());
        oauth_webView.setWebChromeClient(new HtmlWebChromeClient());
        oauth_webView.addJavascriptInterface(new JSObject(), "jsObject");
        Bundle bundle = getIntent().getExtras();
        applicationId = bundle.getString("applicationId", "");
        packgeName = bundle.getString("packgename", "");
        if (!shared.getBoolean(UserAppConst.IS_LOGIN, false)) {
            isReload = true;
            Intent intent = new Intent(getApplicationContext(), UserRegisterAndLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle1 = new Bundle();
            bundle1.putBoolean(UserRegisterAndLoginActivity.FORMSOURCE, true);
            bundle1.putString("packgename", packgeName);
            bundle1.putString("applicationId", applicationId);
            intent.putExtras(bundle1);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        if (shared.getBoolean(UserAppConst.IS_LOGIN, false)) {
            loadUrl = baseUrl + "authorize?application_id=" + applicationId + ""
                    + "&redirect_uri=" + packgeName + "&response_type=code&scope=[]&state=STATE#colourlife_redirect";
            oauth_webView.loadUrl(loadUrl);
        } else {
            if (isReload) {
                isReload = false;
                finish();
            }
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                cancelOauth();
                break;
        }
    }


    private void cancelOauth() {
        try {
            ComponentName componentName = new ComponentName(packgeName, packgeName + ".czy.ColourLifeEntryActivity");
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("code", "-1");
            intent.setComponent(componentName);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

        }
        ToastUtil.toastShow(OauthWebviewActivity.this, "用户取消授权");
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelOauth();
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
            if (url.contains("code") && url.contains(packgeName) && !url.contains("authorize")) {
                int index = url.indexOf("?");
                String code = "";
                int endPos = 0;
                if (url.contains("&")) {
                    endPos = url.indexOf("&");
                } else {
                    endPos = url.length();
                }
                code = url.substring(index + 1, endPos);
                try {
                    ComponentName componentName = new ComponentName(packgeName, packgeName + ".czy.ColourLifeEntryActivity");
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putString("code", code);
                    intent.putExtras(bundle);
                    intent.setComponent(componentName);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    ToastUtil.toastShow(OauthWebviewActivity.this, "请检查你的app是否配置了" + packgeName + ".czy.ColourLifeEntryActivity");
                }
                finish();
                return false;
            } else {
                view.loadUrl(url);
                return true;
            }
        }
    }


    public class JSObject {
        /**
         * 获取设备号唯一信息
         *
         * @return
         */
        @JavascriptInterface
        public String getDeviceHandler() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("device", TokenUtils.getUUID(getApplicationContext()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject.toString();
        }

        @JavascriptInterface
        public String getUserInfoHandler() {
            int uid = shared.getInt(UserAppConst.Colour_User_id, 0); //登录成功的业主用户 ID
            String username = shared.getString(UserAppConst.Colour_NAME, ""); //登录成功的业主用户账号
            String mobile = shared.getString(UserAppConst.Colour_login_mobile, ""); //手机号码
            String build_id = shared.getString(UserAppConst.Colour_Build_id, ""); //楼栋id
            String build_name = shared.getString(UserAppConst.Colour_Build_name, ""); //楼栋名
            String community_id = shared.getString(UserAppConst.Colour_login_community_uuid, ""); //小区id
            String community_name = shared.getString(UserAppConst.Colour_login_community_name, ""); //小区名
            String name = shared.getString(UserAppConst.Colour_NAME, ""); //真实姓名
            String nickname = shared.getString(UserAppConst.Colour_NIACKNAME, ""); //昵称
            String room = shared.getString(UserAppConst.Colour_Room_name, ""); //房间号
            String portraitUrl = shared.getString(UserAppConst.Colour_head_img, ""); //用户头像
            String gender = shared.getString(UserAppConst.Colour_GENDER, ""); //性别
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("uid", uid);
                jsonObject.put("username", username);
                jsonObject.put("build_id", build_id);
                jsonObject.put("build_name", build_name);
                jsonObject.put("community_id", community_id);
                jsonObject.put("community_name", community_name);
                jsonObject.put("name", name);
                jsonObject.put("nickname", nickname);
                jsonObject.put("portraitUrl", portraitUrl);
                jsonObject.put("gender", gender);
                jsonObject.put("mobile", mobile);
                jsonObject.put("room", room);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String json = jsonObject.toString();
            return json;
        }
    }
}