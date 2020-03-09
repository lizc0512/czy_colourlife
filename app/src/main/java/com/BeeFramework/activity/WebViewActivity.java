package com.BeeFramework.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.JavaScriptMetod;
import com.BeeFramework.Utils.CompressHelper;
import com.BeeFramework.Utils.DecodeImage;
import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.Utils.WebViewUploadImageHelper;
import com.BeeFramework.model.Constants;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.protocol.JsAlertEntity;
import com.BeeFramework.view.CustomDialog;
import com.about.activity.FeedBackActivity;
import com.agentweb.AgentWeb;
import com.agentweb.AgentWebSettings;
import com.agentweb.ChromeClientCallbackManager;
import com.agentweb.ILoader;
import com.agentweb.PermissionInterceptor;
import com.agentweb.WebDefaultSettingsManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.cashier.activity.NewOrderPayActivity;
import com.customerInfo.activity.CustomerInfoActivity;
import com.customerInfo.activity.DeliveryAddressListActivity;
import com.customerInfo.protocol.RealNameTokenEntity;
import com.dashuview.library.keep.Cqb_PayUtil;
import com.external.eventbus.EventBus;
import com.google.zxing.Result;
import com.im.activity.IMCustomerInforActivity;
import com.im.activity.IMFriendInforActivity;
import com.im.activity.IMUserSelfInforActivity;
import com.im.entity.MobileBookEntity;
import com.im.helper.CacheFriendInforHelper;
import com.im.model.IMUploadPhoneModel;
import com.insthub.Config;
import com.mob.MobSDK;
import com.permission.AndPermission;
import com.permission.PermissionListener;
import com.scanCode.activity.CaptureActivity;
import com.setting.activity.DeliveryOauthDialog;
import com.tencent.authsdk.AuthConfig;
import com.tencent.authsdk.AuthSDKApi;
import com.tencent.authsdk.IDCardInfo;
import com.tencent.authsdk.callback.IdentityCallback;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.update.activity.UpdateVerSion;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.Utils.TokenUtils;
import com.user.model.NewUserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.csh.colourful.life.utils.GlideUtils;
import cn.csh.colourful.life.utils.GsonUtils;
import cn.csh.colourful.life.utils.JsonValidator;
import cn.csh.colourful.life.view.imagepicker.ImagePicker;
import cn.csh.colourful.life.view.imagepicker.bean.ImageItem;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
import cn.net.cyberway.home.service.LekaiParkLockController;
import cn.net.cyberway.sharesdk.onekeyshare.OnekeyShare;
import cn.net.cyberway.sharesdk.onekeyshare.js.ShareSDKUtils;
import cn.net.cyberway.utils.BuryingPointUtils;
import cn.net.cyberway.utils.ChangeLanguageHelper;
import cn.net.cyberway.utils.CityCustomConst;
import cn.net.cyberway.utils.CityManager;
import cn.net.cyberway.utils.FileUtils;
import cn.net.cyberway.utils.LekaiHelper;
import cn.net.cyberway.utils.LinkParseUtil;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import static cn.net.cyberway.utils.BuryingPointUtils.ENTER_TIME;
import static cn.net.cyberway.utils.BuryingPointUtils.LEAVE_TIME;
import static cn.net.cyberway.utils.BuryingPointUtils.UPLOAD_DETAILS;
import static com.BeeFramework.Utils.Utils.getAuthPublicParams;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends BaseActivity implements View.OnLongClickListener, OnClickListener, NewHttpResponse, LekaiParkLockController.OnScanParkLockChangeListener {
    public static final String WEBURL = "weburl";
    public static final String JUSHURL = "jushurl"; //极光推送过来的url
    public static final String JUSHRESOURCEID = "jushjushresourceidurl"; //极光推送的resourceId
    public static final String WEBTITLE = "webtitle";
    public static final String WEBDOMAIN = "webdomain";
    public static final String WEBDATA = "webdata";
    public static final int SHARELINLI = 10;
    public static final String THRIDSOURCE = "thridsource";
    private OnekeyShare oks;
    private String shareTitle;//分享标题
    private String shareUrl;//分享的连接
    private String shareImg;//分享照片的URL
    private String shareContent;//分享的内容
    private FrameLayout czy_title_layout;
    private TextView mTitle;
    public static final int PAY_FROM_HTML = 1002; // h5调用通用原生支付
    public static final int SCAN_GIVE_TICKET = 1;
    public static final int YUN_SHANG_SCANNERCODE = 1007;
    public static final int REQUEST_CALLPHONE = 2000;
    public static final int GUANGCAIPAY = 3000;
    public static final int DELIVERYADDRESS = 4000;
    public static final int REFRESH = 5000;
    private String webTitle = ""; // 传入的标题字符串
    private String appSectionCode = ""; // 传入的标题字符串
    private String url = "";
    private String domainName;
    private String sn; // 订单号
    private AgentWeb mAgentWeb;
    private HtmlWebChromeClient htmlWebChromeClient;
    private HtmlWebViewClient htmlWebViewClient;
    private ImageView img_right;
    private WebView webView;
    private ImageView imageClose;
    private FrameLayout frame_share;//分享
    private TextView tv_cacel;
    private boolean isHfiveShare = false;//是否是H5分享
    private RelativeLayout rl_sx;
    private RelativeLayout rl_llq;
    private RelativeLayout rl_wx;
    private RelativeLayout rl_pyq;
    private boolean isJumpThrid = false;
    private String hotLine = "1010-1778 ";
    private String testOauthUrl = "http://oauth2-czytest.colourlife.com";
    private String betaOauthUrl = "https://oauth2czy-czybeta.colourlife.com";
    private String officialOauthUrl = "https://oauth2czy.colourlife.com";
    private String awardState = "";
    public static String finishStatus = "1";
    private String photoType = "imagepath";
    private String otherMobile;
    private String otherUserId;
    private String biz_token;
    private int identitySource = 0;
    private int isClose = 0;


    @SuppressLint("AddJavascriptInterface")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.webview);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MobSDK.init(WebViewActivity.this);
            }
        });
        frame_share = (FrameLayout) findViewById(R.id.webview_frame_share);
        Intent intent = getIntent();
        url = intent.getStringExtra(WEBURL);
        domainName = intent.getStringExtra(WEBDOMAIN);
        shareUrl = url;
        webTitle = intent.getStringExtra(WEBTITLE);
        String webData = intent.getStringExtra(WEBDATA);
        initView();
        htmlWebViewClient = new HtmlWebViewClient();
        htmlWebChromeClient = new HtmlWebChromeClient();
        try {
            mAgentWeb = AgentWeb.with(this)//传入Activity
                    .setAgentWebParent((FrameLayout) findViewById(R.id.webview_layout), new RelativeLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，
                    // 如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                    .useDefaultIndicator()// 使用默认进度条
                    .setIndicatorColor(Color.parseColor("#01A7FF"))
                    .setReceivedTitleCallback(receivedTitleCallback) //设置 Web 页面的 title 回调
                    .setWebChromeClient(htmlWebChromeClient)
                    .setWebViewClient(htmlWebViewClient)
                    .setSecutityType(AgentWeb.SecurityType.strict)
                    .setAgentWebSettings(getSettings())
                    .setPermissionInterceptor(permissionInterceptor)
                    .additionalHttpHeader("Referer", domainName)
                    .createAgentWeb()
                    .ready().go(url);
            mAgentWeb.getJsInterfaceHolder().addJavaObject("jsObject", new JSObject());
            webView = mAgentWeb.getWebCreator().get();
        } catch (Exception e) {
            finish();
        }
        JavaScriptMetod javaScriptMetod = new JavaScriptMetod(this, webView, this);
        webView.addJavascriptInterface(javaScriptMetod, JavaScriptMetod.JAVAINTERFACE);
        String imei = TokenUtils.getImeiId(getApplicationContext());
        String imeis = Utils.setMD5(imei).toUpperCase();
        WebSettings webSettings = webView.getSettings();
        String ua = webSettings.getUserAgentString();
        webSettings.setUserAgentString(ua + "/" + imeis + "/colourlifeApp");
        ShareSDKUtils.prepare(webView, htmlWebViewClient);
        webView.setInitialScale(25);
        appSectionCode = webTitle;
        if (!TextUtils.isEmpty(webTitle) && !webTitle.contains(BuryingPointUtils.divisionSign)) {
            mTitle.setText(webTitle);
        }
        if (null == url && null != webData) {
            webView.loadData(webData, "text/html", "utf-8");
        }
        CityManager.getInstance(this).initLocation();
        webView.setOnLongClickListener(this);
        if (!EventBus.getDefault().isregister(WebViewActivity.this)) {
            EventBus.getDefault().register(WebViewActivity.this);
        }
    }

    private PermissionInterceptor permissionInterceptor = new PermissionInterceptor() {
        @Override
        public boolean intercept(String url, String[] permissions, String action) {
            if (permissions.length > 0) {
                String permission = permissions[0].replace("Manifest", "android");
                if (!AndPermission.hasPermission(WebViewActivity.this, permission)) {
                    if (permission.contains("CAMERA")) {
                        ToastUtil.toastShow(WebViewActivity.this, "未开启相机权限,请开启后再使用此功能");
                    } else {
                        ToastUtil.toastShow(WebViewActivity.this, "未开启" + action + "权限,请开启后再使用此功能");
                    }
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    };
    private ChromeClientCallbackManager.ReceivedTitleCallback receivedTitleCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            try {
                if (!TextUtils.isEmpty(title) && !Utils.isSpecialharacter(title)) {
                    webTitle = title;
                    if (webTitle.length() > 8) {
                        webTitle = title.substring(0, 8) + "...";
                    }
                }
                if (!TextUtils.isEmpty(webTitle) && !webTitle.contains(BuryingPointUtils.divisionSign)) {
                    mTitle.setText(webTitle);
                }
            } catch (Exception e) {
                mTitle.setText(title);
            }
        }
    };


    private void initView() {
        rl_sx = (RelativeLayout) findViewById(R.id.rl_sx);
        rl_llq = (RelativeLayout) findViewById(R.id.rl_llq);
        rl_wx = (RelativeLayout) findViewById(R.id.rl_wechat);
        rl_pyq = (RelativeLayout) findViewById(R.id.rl_pyq);
        frame_share.setOnClickListener(this);
        rl_sx.setOnClickListener(this);
        rl_llq.setOnClickListener(this);
        rl_wx.setOnClickListener(this);
        rl_pyq.setOnClickListener(this);
        findViewById(R.id.ll_no_data).setVisibility(View.GONE);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        ImageView imageback = (ImageView) findViewById(R.id.user_top_view_back);
        imageback.setOnClickListener(this);
        imageClose = (ImageView) findViewById(R.id.img_close);
        mTitle = (TextView) findViewById(R.id.user_top_view_title);
        img_right = (ImageView) findViewById(R.id.img_right);
        img_right.setVisibility(View.VISIBLE);
        img_right.setImageResource(R.drawable.img_home_more);
        img_right.setOnClickListener(this);
        imageClose.setOnClickListener(this);
        imageClose.setVisibility(View.VISIBLE);
        tv_cacel = (TextView) findViewById(R.id.tv_web_cancel);
        tv_cacel.setOnClickListener(this);
        ThemeStyleHelper.webviewTitileBar(getApplicationContext(), czy_title_layout, imageback, imageClose, mTitle, img_right);
    }

    /**
     * 打开WebView
     *
     * @param context context
     * @param url     网址
     * @param title   标题
     * @param data    用WebView查看的数据
     */
    public static void actionStart(Context context, String url, String title, String data) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WEBURL, url);
        intent.putExtra(WEBTITLE, title);
        intent.putExtra(WEBDATA, data);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    private void finishActivity() {
        Intent intent = new Intent();
        intent.putExtra("state", awardState);
        setResult(200, intent);
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.user_top_view_back:
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finishActivity();
                }
                break;
            case R.id.img_close:
                finishActivity();
                break;
            case R.id.img_right:
                frame_share.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_web_cancel:
                closeShareLayout();
                break;
            case R.id.rl_sx:
                webView.reload();
                closeShareLayout();
                break;
            case R.id.rl_wechat:
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                showShare(this, wechat.getName());
                closeShareLayout();
                break;
            case R.id.rl_pyq:
                Platform friend = ShareSDK.getPlatform(WechatMoments.NAME);
                showShare(this, friend.getName());
                closeShareLayout();
                break;
        }
    }

    /**
     * 关闭分享布局
     */
    private void closeShareLayout() {
        frame_share.setVisibility(View.GONE);
    }


    /**
     * 调用ShareSDK执行分享
     *
     * @param context
     * @param platformToShare 指定直接分享平台名称（一旦设置了平台名称，则九宫格将不会显示）
     */
    public void showShare(Context context, String platformToShare) {
        oks = new OnekeyShare();
        oks.setSilent(true);
        //隐藏自带的分享列表
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        if (isHfiveShare == true) {
            oks.setTitle(shareTitle);
            oks.setImageUrl(shareImg);
            oks.setText(shareContent);
        } else {
            oks.setTitle(webTitle);
            oks.setText(webTitle);
            oks.setImageUrl("https://cc.colourlife.com/common/v30/logo/app_logo_v30.png");
        }
        oks.setUrl(shareUrl);
        // 将快捷分享的操作结果将通过OneKeyShareCallback回调
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(final Platform platform, int i, HashMap<String, Object> hashMap) {
                WebViewActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("shareType", platform.getName());
                            jsonObject.put("state", "1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        webView.loadUrl("javascript:ColourlifeShareHandler('" + jsonObject.toString() + "')");
                        ToastUtil.toastShow(WebViewActivity.this, "分享成功");
                    }
                });
            }

            @Override
            public void onError(final Platform platform, int i, final Throwable throwable) {
                WebViewActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("shareType", platform.getName());
                            jsonObject.put("state", "0");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        webView.loadUrl("javascript:ColourlifeShareHandler('" + jsonObject.toString() + "')");
                        ToastUtil.toastShow(WebViewActivity.this, "分享失败" + throwable.getMessage());
                    }
                });
            }

            @Override
            public void onCancel(final Platform platform, int i) {
                WebViewActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("shareType", platform.getName());
                            jsonObject.put("state", "3");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        webView.loadUrl("javascript:ColourlifeShareHandler('" + jsonObject.toString() + "')");
                        ToastUtil.toastShow(WebViewActivity.this, "关闭分享");
                    }
                });
            }
        });
        // 启动分享
        oks.show(context);
        isHfiveShare = false;
    }

    /**
     * 分享窗口
     */
    public void showShareLayou(Boolean ishtml) {
        //因为js调用本地方法是在子线程中，更新页面得在在UI线程
        WebViewActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                frame_share.setVisibility(View.VISIBLE);
                rl_sx.setVisibility(View.GONE);
                rl_llq.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        MobileBookEntity mobileBookEntity = com.nohttp.utils.GsonUtils.gsonToBean(result, MobileBookEntity.class);
                        if (mobileBookEntity.getCode() == 0) {
                            MobileBookEntity.ContentBean listBean = mobileBookEntity.getContent().get(0);
                            List<String> friendUUidList = CacheFriendInforHelper.instance().toQueryFriendUUIdList(WebViewActivity.this);
                            String uuid = listBean.getUuid();
                            if (friendUUidList.contains(uuid)) {
                                if (shared.getString(UserAppConst.Colour_User_uuid, "").equals(listBean.getUuid())) {
                                    Intent intent = new Intent(WebViewActivity.this, IMUserSelfInforActivity.class);
                                    intent.putExtra(IMFriendInforActivity.USERUUID, listBean.getUuid());
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(WebViewActivity.this, IMFriendInforActivity.class);
                                    intent.putExtra(IMFriendInforActivity.USERUUID, listBean.getUuid());
                                    startActivity(intent);
                                }
                            } else {
                                Intent intent = new Intent(WebViewActivity.this, IMCustomerInforActivity.class);
                                intent.putExtra(IMFriendInforActivity.USERUUID, listBean.getUuid());
                                startActivity(intent);
                            }
                        }
                    } catch (Exception e) {

                    }
                    finish();
                    break;
                }
            case 2://获取实名认证token
                try {
                    RealNameTokenEntity entity = com.nohttp.utils.GsonUtils.gsonToBean(result, RealNameTokenEntity.class);
                    RealNameTokenEntity.ContentBean bean = entity.getContent();
                    biz_token = bean.getBizToken();
                    AuthConfig.Builder configBuilder = new AuthConfig.Builder(biz_token, R.class.getPackage().getName());
                    AuthSDKApi.startMainPage(this, configBuilder.build(), mListener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3://实名认证成功
                String state = "0";//1 成功；0失败
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String code = jsonObject.getString("code");
                    if ("0".equals(code)) {
                        String content = jsonObject.getString("content");
                        if ("1".equals(content)) {
                            state = "1";
                            ToastUtil.toastShow(this, "认证成功");
                            if (identitySource == 1 || identitySource == 0) {
                                editor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + shared.getInt(UserAppConst.Colour_User_id, 0), realName).commit();
                                newUserModel.finishTask(4, "2", "task_web", this);
                            } else if (identitySource == 2) {
                                finish();
                            }
                        } else {
                            ToastUtil.toastShow(this, "认证失败");
                        }
                    } else {
                        String message = jsonObject.getString("message");
                        ToastUtil.toastShow(this, message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (identitySource == 0) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("state", state);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    webView.loadUrl("javascript:window.CLColourlifeIdentifyAuthHandler('" + jsonObject.toString() + "')");
                }
                break;
            case 4://实名认成功刷新
                webView.reload();
                break;
        }
    }


    /**
     * 注入的js类
     */
    public class JSObject {

        @JavascriptInterface
        public void galleryActivity(String photo) {  //拍照和相册
            String choiceMode = "";
            try {
                JSONObject jsonObject = new JSONObject(photo);
                WebViewActivity.this.photoType = jsonObject.optString("photoType");
                choiceMode = jsonObject.optString("choiceMode");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new WebViewUploadImageHelper(WebViewActivity.this).selectImage(choiceMode);
        }

        @JavascriptInterface
        public void scanActivity() {
            //扫码
            Intent intent = new Intent(WebViewActivity.this, CaptureActivity.class);
            intent.putExtra(CaptureActivity.QRCODE_SOURCE, "default");
            startActivity(intent);
        }

        @JavascriptInterface
        public void scanActivity(String valueStr) {
            //扫码
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(valueStr);
                if (!jsonObject.isNull("value") && "colourlife".equals(jsonObject.optString("value"))) {
                    Intent intent = new Intent(WebViewActivity.this, CaptureActivity.class);
                    intent.putExtra(CaptureActivity.QRCODE_SOURCE, jsonObject.optString("value"));
                    startActivityForResult(intent, YUN_SHANG_SCANNERCODE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public String getLocationActivity() {
            //获取经纬度
            SharedPreferences mShared = getSharedPreferences(UserAppConst.USERINFO, 0);
            String latitude = mShared.getString(CityCustomConst.LOCATION_LATITUDE, "");
            String longitude = mShared.getString(CityCustomConst.LOCATION_LONGITUDE, "");
            String province = mShared.getString(CityCustomConst.LOCATION_PROVINCE, "");
            String city = mShared.getString(CityCustomConst.LOCATION_CITY, "");
            String district = mShared.getString(CityCustomConst.LOCATION_DISTRICT, "");
            String address = mShared.getString(CityCustomConst.LOCATION_ADDRESS, "");
            String buildName = mShared.getString(CityCustomConst.LOCATION_BUILDNAME, "");
            String floor = mShared.getString(CityCustomConst.LOCATION_FLOOR, "");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("longitude", longitude);
                jsonObject.put("latitude", latitude);
                jsonObject.put("province", province);
                jsonObject.put("city", city);
                jsonObject.put("district", district);
                jsonObject.put("address", address);
                jsonObject.put("buildname", buildName);
                jsonObject.put("floor", floor);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject.toString();
        }

        @JavascriptInterface
        public void payFromHtml(String sn, String url) {
            // h5页面调通用支付,更多：京东特供，手机充值,饭票商城-
            if (TextUtils.isEmpty(sn)) {
                ToastUtil.toastShow(WebViewActivity.this, "订单号为空");
            } else {
                Intent intent = new Intent(WebViewActivity.this, NewOrderPayActivity.class);
                intent.putExtra(NewOrderPayActivity.ORDER_SN, sn);
                WebViewActivity.this.sn = sn;
                Constants.isFromHtml = true;
                Constants.payResultUrl = url;
                startActivityForResult(intent, PAY_FROM_HTML);
            }
        }


        @JavascriptInterface
        public void ColourlifeTicket(String mobile) {
            // h5页面调通用赠送，勿动！！
            String mobilePhone = "";
            try {
                JSONObject jsonObject = new JSONObject(mobile);
                mobilePhone = jsonObject.getString("mobile");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(mobilePhone)) {
                IMUploadPhoneModel imUploadPhoneModel = new IMUploadPhoneModel(WebViewActivity.this);
                imUploadPhoneModel.getUserInforByMobile(0, mobilePhone, WebViewActivity.this);
            } else {
                ToastUtil.toastShow(WebViewActivity.this, "当前用户手机号码为空");
            }
        }


        /****扫一扫 根据用户的uuid去好友页面***/
        @JavascriptInterface
        public void ColourlifeFriend(String uuid) {
            List<String> friendUUidList = CacheFriendInforHelper.instance().toQueryFriendUUIdList(WebViewActivity.this);
            Intent intent = null;
            if (friendUUidList.contains(uuid)) {
                intent = new Intent(WebViewActivity.this, IMFriendInforActivity.class);
            } else {
                intent = new Intent(WebViewActivity.this, IMCustomerInforActivity.class);
            }
            intent.putExtra(IMFriendInforActivity.USERUUID, uuid);
            startActivity(intent);
            finish();
        }

        //隐藏标题栏
        @JavascriptInterface
        public void hideTitleLayout() {
            czy_title_layout.setVisibility(View.GONE);
        }

        @JavascriptInterface
        // h5调此方法关闭当前webview窗口
        public void closeBrowserHandler() {
            finish();
        }

        @JavascriptInterface
        public void colourlifeWebLogout() {
            boolean isLogin = shared.getBoolean(UserAppConst.IS_LOGIN, false);
            if (isLogin) {
                SharedPreferences.Editor editor = shared.edit();
                editor.putString(UserAppConst.Colour_login_mobile, "");
                editor.putBoolean(UserAppConst.IS_LOGIN, false);
                editor.apply();
                Message msg = new Message();
                msg.what = UserMessageConstant.WEB_OUT;//更换手机号,证件审核完成，跳转到登录页面
                EventBus.getDefault().post(msg);
                finish();
            } else {
                finish();
            }
        }

        //门禁开门
        public void openDoorActivity(String qrCode) {
            try {
                JSONObject jsonObject = new JSONObject(qrCode);
                String qrcode = jsonObject.optString("qrCode");
                Intent intent = new Intent(WebViewActivity.this,
                        MainActivity.class);
                intent.putExtra("shortcut", qrcode);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.door_push_bottom_out);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**
         * h5任意跳原生界面
         */
        @JavascriptInterface
        public void jumpPrototype(String proto) {
            LinkParseUtil.parse(WebViewActivity.this, proto, "");
        }

        /**
         * 车位锁
         */
        @JavascriptInterface
        public void CLColourlifeBloothLockOperation(String result) {
            LekaiHelper.setScanParkLockChangeListener(WebViewActivity.this);
            try {
                JSONObject jsonObject = new JSONObject(result);
                String actionType = jsonObject.optString("actionType");
                String mac = jsonObject.optString("mac");

                mac = mac.replaceAll(":", "");
                if ("1".equals(actionType)) {//1 抬起 2 倒下
                    parkUp(mac);
                } else {
                    parkDown(mac);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**
         * 前往实名认证
         */
        @JavascriptInterface
        public void CLColourlifeIdentifyAuth() {
            identitySource = 0;
            toRealName();
        }

        /**
         * 帮助他人实名认证
         */
        @JavascriptInterface
        public void CLColourlifeIdentifyAuth(String jsonStr) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                otherMobile = jsonObject.optString("user_mobile");//他人的手机号码(帮助他人传就行)
                identitySource = jsonObject.optInt("user_type"); //1表示自己实名 2表示帮他们实名(必传)
                otherUserId = jsonObject.optString("user_id");//他人的用户id(帮助他人传就行)
                isClose = jsonObject.optInt("is_close");//用户进入实名后点击返回是否关闭h5页面
            } catch (JSONException e) {
                e.printStackTrace();
            }
            toRealName();
        }

        /**
         * 获取当前应用版本名
         */
        @JavascriptInterface
        public String getAppVersion() {
            String versionName = "";
            try {
                PackageManager pm = WebViewActivity.this.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(
                        WebViewActivity.this.getPackageName(), 0);
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
         * 获取当前应用本地显示版本号
         */
        @JavascriptInterface
        public String getAppLocalVersion() {
            String versionName = "";
            versionName = UpdateVerSion.showVersionName(UpdateVerSion.getVersionName(WebViewActivity.this));
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
                versionCode = WebViewActivity.this.getPackageManager().getPackageInfo(
                        WebViewActivity.this.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return versionCode;
        }

        /**
         * H5调原生分享页面
         *
         * @param data
         */
        @JavascriptInterface
        public void ColourlifeShareCallBack(String data) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                shareTitle = jsonObject.optString("title");
                if (!jsonObject.isNull("url")) {
                    shareUrl = jsonObject.optString("url");
                }
                shareImg = jsonObject.optString("image");
                shareContent = jsonObject.optString("content");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            isHfiveShare = true;
            showShareLayou(true);
        }

        /**
         * h5调用手机号码的功能
         */
        @JavascriptInterface
        public void colourlifeDialNumber(String data) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                hotLine = jsonObject.optString("hotLine");
            } catch (JSONException e) {
                e.printStackTrace();
                hotLine = "1010-1778";
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (AndPermission.hasPermission(WebViewActivity.this, Manifest.permission.CALL_PHONE)) {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hotLine));//跳转到拨号界面，同时传递电话号码
                    startActivity(dialIntent);
                } else {
                    ArrayList<String> permission = new ArrayList<>();
                    permission.add(Manifest.permission.CALL_PHONE);
                    if (AndPermission.hasAlwaysDeniedPermission(WebViewActivity.this, permission)) {
                        ToastUtil.toastShow(getApplicationContext(), "拨号权限被禁止，请去开启该权限");
                    } else {
                        AndPermission.with(WebViewActivity.this)
                                .requestCode(REQUEST_CALLPHONE)
                                .permission(Manifest.permission.CALL_PHONE)
                                .callback(permissionListener)
                                .start();
                    }
                }
            } else {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hotLine));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
            }
        }


        /**
         * h5调个人信息页面
         */
        @JavascriptInterface
        public void Information() {
            Intent intent = new Intent(WebViewActivity.this, CustomerInfoActivity.class);
            startActivity(intent);
        }

        /**
         * h5点意见反馈页面
         */
        @JavascriptInterface
        public void FeedBack() {
            Intent intent = new Intent(WebViewActivity.this, FeedBackActivity.class);
            startActivity(intent);
        }

        /***
         *
         *  摇一摇获取交易订单号
         * @return
         */
        @JavascriptInterface
        public String getTransactionNo() {
            String colourSn = shared.getString(UserAppConst.COLOURLIFE_TRADE_NO, "");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("transaction_no", colourSn);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject.toString();
        }

        /***
         *
         *  抽奖的结果
         * @return
         */
        @JavascriptInterface
        public void shakeLotteryCallBack(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                awardState = jsonObject.optString("state");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**
         * h5调用弹窗信息
         */
        @JavascriptInterface
        public void alertActivity(final String data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final JsAlertEntity jsAlertEntity = GsonUtils.gsonToBean(data, JsAlertEntity.class);
                    if (null != jsAlertEntity) {
                        if (jsAlertEntity.getButtons().size() > 0) {
                            if (jsAlertEntity.getButtons().size() == 1) {
                                new AlertDialog.Builder(WebViewActivity.this)
                                        .setTitle(jsAlertEntity.getTitle())
                                        .setMessage(jsAlertEntity.getContent())
                                        .setNegativeButton(jsAlertEntity.getButtons().get(0).getName(), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (!TextUtils.isEmpty(jsAlertEntity.getButtons().get(0).getUrl())) {
                                                    sendJsalert(jsAlertEntity.getButtons().get(0).getUrl());
                                                }
                                            }
                                        })
                                        .show();
                            } else if (jsAlertEntity.getButtons().size() == 2) {
                                new AlertDialog.Builder(WebViewActivity.this)
                                        .setTitle(jsAlertEntity.getTitle())
                                        .setMessage(jsAlertEntity.getContent())
                                        .setNegativeButton(jsAlertEntity.getButtons().get(0).getName(), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (!TextUtils.isEmpty(jsAlertEntity.getButtons().get(0).getUrl())) {
                                                    sendJsalert(jsAlertEntity.getButtons().get(0).getUrl());
                                                }
                                            }
                                        })
                                        .setPositiveButton(jsAlertEntity.getButtons().get(1).getName(), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (!TextUtils.isEmpty(jsAlertEntity.getButtons().get(1).getUrl())) {
                                                    sendJsalert(jsAlertEntity.getButtons().get(1).getUrl());
                                                }
                                            }
                                        })
                                        .show();
                            }
                        }

                    }
                }
            });


        }

        /**
         * 跳转到第三方页面
         */
        @JavascriptInterface
        public String jumpToThirdApp(String packgeInfo) {
            JSONObject result = null;
            String name = "";
            try {
                result = new JSONObject();
                if (new JsonValidator().validate(packgeInfo)) {
                    JSONObject jsonObject = new JSONObject(packgeInfo);
                    String packgeName = jsonObject.optString("identity");
                    String parameter = "";
                    String accessToken = jsonObject.optString("access_token");
                    String refresh_token = jsonObject.optString("refresh_token");
                    String expires_in = jsonObject.optString("expires_in");
                    String className = jsonObject.optString("view");
                    name = jsonObject.optString("name");
                    String supportVersion = "";
                    if (!jsonObject.isNull("support_version")) {
                        supportVersion = jsonObject.optString("support_version");
                    }
                    if (!jsonObject.isNull("parameter")) {
                        parameter = jsonObject.optString("parameter");
                    }
                    isJumpThrid = true;
                    if (checkApkExist(packgeName)) {
                        int installVersionCode = getApplicationContext().getPackageManager().getPackageInfo(packgeName, 0).versionCode;
                        if (!TextUtils.isEmpty(supportVersion)) {
                            int compareResult = installVersionCode - Integer.valueOf(supportVersion);
                            if (compareResult >= 0) {
                                result.put("result", true);
                                Intent intent = new Intent();
                                //知道要跳转应用的包名、类名
                                ComponentName componentName = new ComponentName(packgeName, className);
                                intent.setComponent(componentName);
                                Bundle bundle = new Bundle();
                                bundle.putString("access_token", accessToken);
                                bundle.putString("refresh_token", refresh_token);
                                bundle.putString("parameter", parameter);
                                bundle.putString("expires_in", expires_in);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                result.put("result", false);
                            }
                        } else {
                            result.put("result", true);
                            Intent intent = new Intent();
                            //知道要跳转应用的包名、类名
                            ComponentName componentName = new ComponentName(packgeName, className);
                            intent.setComponent(componentName);
                            Bundle bundle = new Bundle();
                            bundle.putString("access_token", accessToken);
                            bundle.putString("refresh_token", refresh_token);
                            bundle.putString("parameter", parameter);
                            bundle.putString("expires_in", expires_in);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    } else {
                        result.put("result", false);
                    }
                } else {
                    if (!packgeInfo.startsWith("http") || !packgeInfo.startsWith("https") || !packgeInfo.startsWith("ftp")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        if (isInstall(intent)) {
                            result.put("result", true);
                            getApplicationContext().startActivity(intent);
                        } else {
                            result.put("result", false);
                        }
                    } else {
                        result.put("result", false);
                    }
                }
            } catch (Exception e) {
                try {
                    result.put("result", false);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
            return result.toString();
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
                jsonObject.put("device", TokenUtils.getUUID(getApplicationContext()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject.toString();
        }

        @JavascriptInterface
        public String getCurrentLang() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("lang", ChangeLanguageHelper.getLanguageType(WebViewActivity.this));
            } catch (JSONException e) {  //当前app的语言
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
            String community_uuid = shared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da"); //小区uuid
            String community_name = shared.getString(UserAppConst.Colour_login_community_name, ""); //小区名
            String name = shared.getString(UserAppConst.Colour_NAME, ""); //真实姓名
            String nickname = shared.getString(UserAppConst.Colour_NIACKNAME, ""); //昵称
            String room = shared.getString(UserAppConst.Colour_Room_name, ""); //房间号
            String portraitUrl = shared.getString(UserAppConst.Colour_head_img, ""); //用户头像
            String gender = shared.getString(UserAppConst.Colour_GENDER, ""); //性别
            String userUUid = shared.getString(UserAppConst.Colour_User_uuid, "");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("uid", uid);
                jsonObject.put("username", username);
                jsonObject.put("build_id", build_id);
                jsonObject.put("build_name", build_name);
                jsonObject.put("community_id", community_id);
                jsonObject.put("community_uuid", community_uuid);
                jsonObject.put("community_name", community_name);
                jsonObject.put("name", name);
                jsonObject.put("nickname", nickname);
                jsonObject.put("portraitUrl", portraitUrl);
                jsonObject.put("gender", gender);
                jsonObject.put("mobile", mobile);
                jsonObject.put("room", room);
                jsonObject.put("user_uuid", userUUid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String json = jsonObject.toString();
            return json;
        }


        @JavascriptInterface
        public void colourlifeDeliveryAddress(String result) {
            String appName = "";
            String appLogo = "";
            String appId = "";
            try {
                JSONObject jsonObject = new JSONObject(result);
                appName = jsonObject.optString("appname");
                appLogo = jsonObject.optString("applogo");
                appId = jsonObject.optString("appid");
                String oauthUnquiue = appId + shared.getInt(UserAppConst.Colour_User_id, 0);
                String deliveryOauthCache = shared.getString(UserAppConst.DELIVERYOAUTHCACHE, "");
                if (deliveryOauthCache.contains(oauthUnquiue)) {
                    Intent intent = new Intent(WebViewActivity.this, DeliveryAddressListActivity.class);
                    startActivityForResult(intent, DELIVERYADDRESS);
                } else {
                    showOauthDialog(appName, appLogo, oauthUnquiue, deliveryOauthCache);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @JavascriptInterface
        public void WXMiniProgramActivity(String json) {
            if (!TextUtils.isEmpty(json)) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String appid = jsonObject.getString("userName");
                    String path = jsonObject.getString("path");
                    if (!TextUtils.isEmpty(appid)) {
                        IWXAPI api = WXAPIFactory.createWXAPI(WebViewActivity.this, Config.WEIXIN_APP_ID);//微信APPID
                        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                        req.userName = appid; // 小程序原始id
                        req.path = URLEncoder.encode(path);
                        ;//拉起小程序页面的可带参路径，不填默认拉起小程序首页
                        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
                        api.sendReq(req);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @JavascriptInterface
        public void ColourlifeWalletAuth(String authJson) {//彩钱包实名认证

        }


        @JavascriptInterface
        public void ColourlifeSmartService(String goodsJson) {
            LinkParseUtil.sendGoodsInfor(WebViewActivity.this, goodsJson);
        }

        @JavascriptInterface
        public void colourlifePayFinishStatus(String payStatus) {
            if (!TextUtils.isEmpty(payStatus)) {
                try {
                    JSONObject jsonObject = new JSONObject(payStatus);
                    finishStatus = jsonObject.optString("finishStatus");//1表示关闭 2表示不关闭
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @JavascriptInterface
    public void ColourlifeWalletAuth(String authJson) {
        Cqb_PayUtil.getInstance(WebViewActivity.this).openCertification(getAuthPublicParams(WebViewActivity.this, authJson), Constants.CAIWALLET_ENVIRONMENT, "CertificationFlag");
    }

    private void showOauthDialog(String appName, String appLogo, final String oauthUnquiue, final String deliveryOauthCache) {
        final DeliveryOauthDialog deliveryOauthDialog = new DeliveryOauthDialog(WebViewActivity.this, R.style.dialog);
        deliveryOauthDialog.show();
        deliveryOauthDialog.tv_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                deliveryOauthDialog.dismiss();
            }
        });
        deliveryOauthDialog.tv_define.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryOauthDialog.dismiss();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(oauthUnquiue);
                stringBuffer.append(",");
                stringBuffer.append(deliveryOauthCache);
                editor.putString(UserAppConst.DELIVERYOAUTHCACHE, stringBuffer.toString()).apply();
                Intent intent = new Intent(WebViewActivity.this, DeliveryAddressListActivity.class);
                startActivityForResult(intent, DELIVERYADDRESS);
            }
        });
        deliveryOauthDialog.tv_app_name.setText(appName + "申请获得以下权限:");
        GlideUtils.loadImageView(WebViewActivity.this, appLogo, deliveryOauthDialog.iv_app_logo);

    }


    /**
     * android 向H5交互
     *
     * @param message
     */
    public void sendJsalert(final String message) {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:colourlifeAlertHandler('" + message + "')");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PAY_FROM_HTML:
                // 支付回调
                if (resultCode == 200) {
                    // 不是在收银台界面直接点击返回的，就调用回调链接
                    if (Constants.payResultUrl.startsWith("http")) {
                        String url = Constants.payResultUrl + "?sn=" + sn;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                            CookieManager.getInstance().flush();
                        } else {
                            CookieSyncManager.getInstance().sync();
                        }
                        webView.loadUrl(url);
                        Constants.isFromHtml = false; // 恢复默认值
                    } else {
                        String linkUrl = Constants.payResultUrl;
                        LinkParseUtil.parse(WebViewActivity.this, linkUrl, "");
                        finish();
                    }
                } else if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    webView.reload();
                }
                break;
            case SCAN_GIVE_TICKET:
                finish();
                break;
            case YUN_SHANG_SCANNERCODE:
                if (data != null) {
                    final String qrcode = data.getStringExtra("qrcodeValue");
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("qrCode", qrcode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    webView.loadUrl("javascript:colourlifeScanCodeHandler('" + jsonObject.toString() + "')");
                }
                break;
            case SHARELINLI:
                String stas = null;
                if (resultCode == RESULT_OK) {//邻里分享成功
                    ToastUtil.toastShow(this, "分享成功");
                    stas = "1";
                } else if (resultCode == RESULT_CANCELED) {//邻里分享失败或者取消分享
                    ToastUtil.toastShow(this, "分享取消");
                    stas = "3";
                }
                JSONObject json = new JSONObject();
                try {
                    json.put("shareType", "邻里");
                    json.put("state", stas);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                webView.loadUrl("javascript:ColourlifeShareHandler('" + json.toString() + "')");
                break;
            case WebViewUploadImageHelper.REQ_CAMERA:
            case WebViewUploadImageHelper.REQ_CHOOSE:
                if (resultCode == ImagePicker.RESULT_CODE_ITEMS && null != data) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    ImageItem imageItem = images.get(0);
                    String imagePath = imageItem.path;
                    String mimeType = imageItem.mimeType;
                    String imageName = imageItem.name;
                    long size = imageItem.size;
                    File imageFile = new File(imagePath);
                    if (size == 0) {
                        size = imageFile.length();
                        mimeType = "image/jpeg";
                        imageName = imageFile.getName();
                    }
                    JSONObject jsonObject = new JSONObject();
                    File newFile = CompressHelper.getDefault(this).compressToFile(imageFile);
                    String base64Path = FileUtils.fileToBase64(newFile.getPath());
                    try {
                        jsonObject.put(photoType, base64Path);
                        jsonObject.put("size", size);
                        jsonObject.put("type", mimeType);
                        jsonObject.put("name", imageName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = jsonObject.toString();
                    handler.sendMessage(msg);
                }
                break;
            case GUANGCAIPAY:
                if ("2".equals(finishStatus)) {
                    webView.reload();
                } else {
                    Message message = Message.obtain();
                    message.what = UserMessageConstant.GUANGCAI_PAY_MSG;
                    EventBus.getDefault().post(message);
                    finish();
                }
                break;
            case DELIVERYADDRESS:
                if (resultCode == 200) {
                    String deliverAddresInfor = data.getStringExtra(DeliveryAddressListActivity.DELIVERYINFOR);
                    //加载js
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("addressDetail", new JSONObject(deliverAddresInfor));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    webView.loadUrl("javascript:window.colourlifeDeliveryAddressHandler('" + jsonObject.toString() + "')");
                }
                break;
            case REFRESH: //回调刷新h5
                if (resultCode == 200) {
                    webView.reload();
                }
                break;
            default:
                mAgentWeb.uploadFileResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
        if (isJumpThrid) {
            ILoader iLoader = mAgentWeb.getLoader();
            iLoader.loadUrl(url);
        }
        showMillions = System.currentTimeMillis();
    }

    private long showMillions = 0;//可见时的秒数

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
        Message message = Message.obtain();
        message.what = UserMessageConstant.UPLOAD_PAGE_TIME;
        Bundle bundle = new Bundle();
        bundle.putLong(ENTER_TIME, showMillions);
        bundle.putLong(LEAVE_TIME, System.currentTimeMillis());
        bundle.putString(UPLOAD_DETAILS, appSectionCode);
        message.setData(bundle);
        EventBus.getDefault().post(message);
    }


    @Override
    protected void onDestroy() {
        mAgentWeb.clearWebCache();
        mAgentWeb.getWebLifeCycle().onDestroy();
        if (EventBus.getDefault().isregister(WebViewActivity.this)) {
            EventBus.getDefault().unregister(WebViewActivity.this);
        }
        super.onDestroy();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int code = msg.what;
            if (code == 404 || code == 400 || code == 500 || code == -2) {
                String errorDesc = msg.obj.toString();
                showError(msg.what, errorDesc);
            } else if (code == 0) {  //图片是二维码
                if (isQR) {
                    adapter.add("识别图中二维码");
                }
                adapter.notifyDataSetChanged();
            } else if (code == 1) {
                webView.loadUrl("javascript:window.galleryActivityHandler('" + msg.obj.toString() + "')");
            }
        }
    };

    private void showError(int errorCode, String errorDesc) {
        webView.setVisibility(View.GONE);
        findViewById(R.id.ll_no_data).setVisibility(View.VISIBLE);
        ImageView img_empty = (ImageView) findViewById(R.id.img_empty);
        TextView tv_tips = (TextView) findViewById(R.id.tv_tips);
        TextView tv_description = (TextView) findViewById(R.id.tv_description);
        Button btn_operation = (Button) findViewById(R.id.btn_operation);
        img_empty.setImageResource(R.drawable.error);
        tv_tips.setText("加载失败(" + errorCode + ":" + errorDesc + ")");
        tv_description.setText("刷新一下试试");
        btn_operation.setText("刷新");
        btn_operation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl(url);
                findViewById(R.id.ll_no_data).setVisibility(View.GONE);
            }
        });
    }


    private class HtmlWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String urls, Bitmap favicon) {
            if (urls.equals(getUrl())) {
                imageClose.setVisibility(View.VISIBLE);
            } else {
                imageClose.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String urls) {
            shareUrl = urls;
            super.onPageFinished(view, urls);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String urls) {
            WebView.HitTestResult hitTestResult = view.getHitTestResult();
            if (urls.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(urls));
                startActivity(intent);
                return true;
            } else if (urls.startsWith("androidamap") || urls.startsWith("baidumap")) {
                openGaodeMapToGuide(urls);
                return true;
            } else if (urls.startsWith("intent://") && urls.contains("com.youku.phone")) {
                return true;
            } else if (urls.startsWith("weixin://wap/pay?")) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urls));
                startActivityForResult(intent, GUANGCAIPAY);
                return true;
            } else if (urls.startsWith(testOauthUrl) || urls.startsWith(betaOauthUrl) || urls.startsWith(officialOauthUrl)) {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("color-token", shared.getString(UserAppConst.Colour_access_token, ""));
                webView.loadUrl(urls, headerMap);
                return false;
            } else if (parseScheme(urls)) {
                goAlipayPay(urls);
                return true;
            } else if (null == hitTestResult || WebView.HitTestResult.UNKNOWN_TYPE == hitTestResult.getType()) {
                return false;
            }
            return false;
        }


        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            view.stopLoading();
            view.clearView();
            Message msg = new Message();
            msg.what = errorCode;
            msg.obj = description;
            handler.sendMessage(msg);
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // 接受网站证书
        }
    }

    private String getUrl() {
        return url;
    }


    /***跳转到支付宝**/
    public boolean parseScheme(String url) {
        if (url.contains("alipays://platformapi")) {
            return true;
        } else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                && (url.contains("platformapi") && url.contains("startapp"))) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 跳转到高德地图
     ***/
    private void openGaodeMapToGuide(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri = Uri.parse(url);
        //将功能Scheme以URI的方式传入data
        intent.setData(uri);
        //启动该页面即可
        startActivity(intent);
    }


    private void goAlipayPay(String urls) {
        jumpByUrls(urls);
    }

    private void jumpByUrls(String urls) {
        urls+="&fromAppUrlScheme=colourlifePay";
        try {
            Intent intent = Intent.parseUri(urls, Intent.URI_INTENT_SCHEME);
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.setComponent(null);
            startActivityForResult(intent, GUANGCAIPAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AgentWebSettings getSettings() {
        return WebDefaultSettingsManager.getInstance();
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


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finishActivity();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean checkApkExist(String packageName) {
        PackageManager packageManager = getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (packageName.equals(pn)) {
                    return true;
                }
            }
        }
        return false;
    }

    //判断app是否安装
    private boolean isInstall(Intent intent) {
        return getApplicationContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }


    /***识别webview图片中的二维码****/
    private CustomDialog mCustomDialog;
    private ArrayAdapter<String> adapter;
    private boolean isQR;//判断是否为二维码
    private Result result;//二维码解析结果
    private File file;

    @Override
    public boolean onLongClick(View v) {
        final WebView.HitTestResult htr = webView.getHitTestResult();//获取所点击的内容
        if (htr.getType() == WebView.HitTestResult.IMAGE_TYPE
                || htr.getType() == WebView.HitTestResult.IMAGE_ANCHOR_TYPE
                || htr.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
            // 获取到图片地址后做相应的处理
            MyAsyncTask mTask = new MyAsyncTask();
            mTask.execute(htr.getExtra());
            showDialog();
        }
        return false;
    }

    /**
     * 判断是否为二维码
     * param url 图片地址
     * return
     */
    private boolean decodeImage(String sUrl) {
        try {
            result = DecodeImage.handleQRCodeFormBitmap(getBitmap(sUrl));
        } catch (Exception e) {
        }
        if (result == null) {
            isQR = false;
        } else {
            isQR = true;
        }
        return isQR;
    }

    /***加载二维码的Url 扫码***/
    private void loadQrCodeUrl(final String loadUrl) {
        Glide.with(WebViewActivity.this).load(loadUrl).into(new ImageViewTarget<Drawable>(null) {
            @Override
            protected void setResource(@Nullable Drawable resource) {
                BitmapDrawable bd = (BitmapDrawable) resource;
                Result loadResult = DecodeImage.handleQRCodeFormBitmap(bd.getBitmap());
                if (null != loadResult) {
                    webView.loadUrl(loadResult.getText());
                } else {
                    webView.loadUrl(loadUrl);
                }
            }
        });
    }


    public class MyAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (isQR) {
                handler.sendEmptyMessage(0);
            }


        }

        @Override
        protected String doInBackground(String... params) {
            decodeImage(params[0]);
            return null;
        }

    }

    /**
     * 根据地址获取网络图片
     *
     * @param sUrl 图片地址
     * @return
     * @throws IOException
     */
    public Bitmap getBitmap(String sUrl) {
        try {
            URL url = new URL(sUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                saveMyBitmap(bitmap, "code");//先把bitmap生成jpg图片
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 显示Dialog
     * param v
     */
    private void showDialog() {
        initAdapter();
        mCustomDialog = new CustomDialog(this) {
            @Override
            public void initViews() {
                // 初始CustomDialog化控件
                ListView mListView = (ListView) findViewById(R.id.lv_dialog);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // 点击事件
                        switch (position) {
                            case 0:
                                sendToFriends("com.tencent.mm.ui.tools.ShareImgUI");
                                closeDialog();
                                break;
                            case 1:
                                sendToFriends("com.tencent.mm.ui.tools.ShareToTimeLineUI");
                                closeDialog();
                                break;
                            case 2:
                                saveImageToGallery(WebViewActivity.this);
                                closeDialog();
                                break;
                            case 3:
                                goIntent();
                                closeDialog();
                                break;
                        }

                    }
                });
            }
        };
        mCustomDialog.show();
    }

    /**
     * 初始化数据
     */
    private void initAdapter() {
        adapter = new ArrayAdapter<String>(this, R.layout.item_dialog);
        adapter.add("发送给好友");
        adapter.add("分享到朋友圈");
        adapter.add("保存到手机");
    }


    /**
     * 发送给好友或朋友圈
     */
    private void sendToFriends(String className) {
        try {
            Uri uriToImage = null;
            if (Build.VERSION.SDK_INT >= 24) {
                uriToImage = FileProvider.getUriForFile(WebViewActivity.this, "cn.net.cyberway.fileprovider", file);
            } else {
                uriToImage = Uri.fromFile(file);
            }
            Intent shareIntent = new Intent();
            ComponentName comp = new ComponentName("com.tencent.mm", className);
            shareIntent.setComponent(comp);
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, getTitle()));
        } catch (Exception e) {
            ToastUtil.toastShow(WebViewActivity.this, "分享失败");
        }
    }

    /**
     * bitmap 保存为jpg 图片
     *
     * @param mBitmap 图片源
     * @param bitName 图片名
     */
    public void saveMyBitmap(Bitmap mBitmap, String bitName) {
        file = new File(Environment.getExternalStorageDirectory() + "/" + bitName + ".jpg");
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 先保存到本地再广播到图库
     */
    public void saveImageToGallery(Context context) {

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), "code", null);
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"
                    + file)));
            ToastUtil.toastShow(WebViewActivity.this, "已成功保存到手机");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void goIntent() {
        webView.loadUrl(result.getText());
    }

    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_CALLPHONE: {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hotLine));//跳转到拨号界面，同时传递电话号码
                    startActivity(dialIntent);
                    break;
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            if (AndPermission.hasAlwaysDeniedPermission(WebViewActivity.this, deniedPermissions)) {
                ToastUtil.toastShow(getApplicationContext(), "拨号权限被禁止，请去开启该权限");
            }
        }
    };

    /**
     * 停车 下降
     */
    public void parkDown(String mac) {
        if (LekaiHelper.isFastClick()) {
            boolean openBluetooth = false;
            BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
            if (null != blueAdapter) {
                openBluetooth = blueAdapter.isEnabled();
            }
            //支持蓝牙模块
            if (openBluetooth) {
                ToastUtil.toastShow(getApplicationContext(), "正在下降车位锁");
                LekaiHelper.parkUnlock(mac);
            } else {
                ToastUtil.toastShow(getApplicationContext(), "请打开蓝牙");
            }
        }
    }

    /**
     * 停车 抬起
     */
    public void parkUp(String mac) {
        if (LekaiHelper.isFastClick()) {
            boolean openBluetooth = false;
            BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
            if (null != blueAdapter) {
                openBluetooth = blueAdapter.isEnabled();
            }
            //支持蓝牙模块
            if (openBluetooth) {
                ToastUtil.toastShow(getApplicationContext(), "正在升起位锁");
                LekaiHelper.parkLock(mac);
            } else {
                ToastUtil.toastShow(getApplicationContext(), "请打开蓝牙");
            }
        }
    }

    @Override
    public void onScanParkLockChanged(String mac) {

    }

    private NewUserModel newUserModel;
    private String realName;

    private void toRealName() {
        if (null == newUserModel) {
            newUserModel = new NewUserModel(WebViewActivity.this);
        }
        newUserModel.getRealNameToken(2, WebViewActivity.this, true);
    }

    /**
     * 监听实名认证返回
     */
    private IdentityCallback mListener = data -> {
        boolean identityStatus = data.getBooleanExtra(AuthSDKApi.EXTRA_IDENTITY_STATUS, false);
        if (identityStatus) {//true 已实名
            IDCardInfo idCardInfo = data.getExtras().getParcelable(AuthSDKApi.EXTRA_IDCARD_INFO);
            if (idCardInfo != null) {
                realName = idCardInfo.getName();
                if (null == newUserModel) {
                    newUserModel = new NewUserModel(WebViewActivity.this);
                }
                newUserModel.submitRealName(3, biz_token, otherMobile, otherUserId, this);
            }
        } else {
            if (isClose == 1) {
                finish();
            }
        }
    };

    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case 101010:
                webView.reload();
                break;
        }
    }

}