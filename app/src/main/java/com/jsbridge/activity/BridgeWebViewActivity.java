package com.jsbridge.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.BeeFramework.AppConst;
import com.BeeFramework.Utils.ImageUtil;
import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.activity.BaseActivity;
import com.external.eventbus.EventBus;
import com.insthub.CustomMessageConstant;
import com.jsbridge.base.BridgeHandler;
import com.jsbridge.base.BridgeWebView;
import com.jsbridge.base.CallBackFunction;
import com.jsbridge.base.DefaultHandler;
import com.scanCode.activity.CaptureActivity;
import com.user.UserAppConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cn.net.cyberway.R;


public class BridgeWebViewActivity extends BaseActivity implements OnClickListener {

    private final String TAG = "MainActivity";

    BridgeWebView webView;

    Button button;

    public static final String WEBURL = "weburl";
    public static final String WEBTITLE = "webtitle";
    private FrameLayout czyTitleLayout;
    private ImageView web_back;
    private ImageView goForward;
    private ImageView reload;
    private ImageView mBack;
    private TextView mTitle;
    private ProgressBar pb;

    ValueCallback<Uri> mUploadMessage;


    CallBackFunction mScanCodeCallback;
    CallBackFunction mChooseImageCallback;
    int mScanCodeNeedResult = 0;

    int RESULT_CODE = 0;
    public static int REQUEST_SCAN_CODE = 1;
    public static int REQUEST_ALBUM = 2;
    public static int REQUEST_PHOTO = 3;


    private File mPhotographFile;
    private String mPhotographPath;
    private SharedPreferences mShared;
    public SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bridge_webview_activity);
        mShared = getSharedPreferences(UserAppConst.USERINFO ,0);
        mEditor = mShared.edit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webView = (BridgeWebView) findViewById(R.id.webView);

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(this);

        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setMax(100);
        czyTitleLayout= (FrameLayout) findViewById(R.id.czy_title_layout);
        mBack = (ImageView) findViewById(R.id.user_top_view_back);
        mTitle = (TextView) findViewById(R.id.user_top_view_title);

        Intent intent = getIntent();
        String url = intent.getStringExtra(WEBURL);
        String title = intent.getStringExtra(WEBTITLE);
        if (title != null) {
            mTitle.setText(title);
        }

        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTitle.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb.setProgress(newProgress);
                if (newProgress == 100) {
                    pb.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                pickFile();
            }

        };
        // 设置setWebChromeClient对象
        webView.setWebChromeClient(wvcc);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        mBack.setOnClickListener(new OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        web_back = (ImageView) findViewById(R.id.web_back);
        web_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });


        goForward = (ImageView) findViewById(R.id.goForward);
        goForward.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                webView.goForward();
            }
        });

        reload = (ImageView) findViewById(R.id.reload);
        reload.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                webView.reload();
                pb.setVisibility(View.VISIBLE);
            }
        });


        webView.setDefaultHandler(new DefaultHandler());

//
//        if (null != url) {
//            webView.loadUrl(url);
//        }

        webView.loadUrl("file:///android_asset/demo.html");


        webView.registerHandler("scanCode", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {

                mScanCodeCallback = function;

                try {
                    JSONObject jsonObject = new JSONObject(data);
                    mScanCodeNeedResult = jsonObject.optInt("needResult", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent it = new Intent(BridgeWebViewActivity.this, CaptureActivity.class);
                it.putExtra(CaptureActivity.QRCODE_SOURCE,"default");
                BridgeWebViewActivity.this.startActivityForResult(it, REQUEST_SCAN_CODE);
            }

        });

        webView.registerHandler("getUserInfo", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {


//                try {
//                    JSONObject jo = new JSONObject();
//                    jo.put("uid", AppConst.SESSION.getInstance().uid);
//                    jo.put("sid", AppConst.SESSION.getInstance().sid);
//                    function.onCallBack(jo.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
				function.onCallBack("{\"uid\":\"xxxxxx\",\"sid\":\"xxxxxxxx\"}");
            }

        });
        webView.registerHandler("getLocation", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
				function.onCallBack("{\"lat\":\"xxxxxx\",\"lon\":\"xxxxxxxx\"}");
            }

        });


        webView.registerHandler("chooseImage", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                mChooseImageCallback = function;
                choosePicture();
            }

        });

        webView.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {


                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String str1 = jsonObject.optString("param", null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent it = new Intent(BridgeWebViewActivity.this, CaptureActivity.class);
                it.putExtra(CaptureActivity.QRCODE_SOURCE,"default");
                BridgeWebViewActivity.this.startActivityForResult(it, REQUEST_SCAN_CODE);
            }

        });
        webView.callHandler("functionInJs", "user", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });

        webView.send("hello");
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(),czyTitleLayout,mBack,mTitle);
    }

    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }

    /**
     * 用户上传头像
     */
    private void choosePicture() {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View contentView = getLayoutInflater().inflate(R.layout.user_avatar_dialog, null);
        dialog.setContentView(contentView);

        android.view.ViewGroup.LayoutParams cursorParams = contentView.getLayoutParams();
        cursorParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(cursorParams);
        dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
        dialog.show();

        TextView album = (TextView) dialog.findViewById(R.id.avatar_album);
        TextView photograph = (TextView) dialog.findViewById(R.id.avatar_photograph);
        TextView cancel = (TextView) dialog.findViewById(R.id.avatar_cancel);

        //从相册选择上传
        album.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                {
//                    Intent intent = new Intent(BridgeWebViewActivity.this, PhotoWallActivity.class);
//                    intent.putExtra(PhotoWallActivity.CODE, 200); // 进入最近照片
//                    startActivityForResult(intent, REQUEST_ALBUM);
                }
            }
        });

        //拍照上传
        photograph.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                {
                    if (mPhotographFile == null) {
                        mPhotographFile = new File(UserAppConst.FILEPATH + "/img/");
                        if (!mPhotographFile.exists()) {
                            mPhotographFile.mkdirs();
                        }
                    }
                    mPhotographPath = mPhotographFile + AppConst.imageName();
                    mEditor.putString(UserAppConst.PHOTO_PATH, mPhotographPath);
                    mEditor.commit();
                    File file = new File(mPhotographPath);
                    Uri imageuri = Uri.fromFile(file);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, REQUEST_PHOTO);
                }

            }
        });
        //取消
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RESULT_CODE) {
            if (null == mUploadMessage) {
                return;
            }
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == REQUEST_SCAN_CODE) {
            if (resultCode == RESULT_OK) {
                if (null != intent) {
                    String scanResult = intent.getStringExtra("scanResult");
                    if (mScanCodeNeedResult == 1) {
                        mScanCodeCallback.onCallBack(scanResult);
                    } else {
                        webView.loadUrl(scanResult);
                    }

                }

            }
        } else if (requestCode == REQUEST_PHOTO) {
//            if (intent != null) {
//                ArrayList<String> paths = new ArrayList<String>();
//                paths = intent.getStringArrayListExtra("paths");
//                mChooseImageCallback.onCallBack(paths.toString());
//
//            }
            mPhotographPath = mShared.getString(UserAppConst.PHOTO_PATH, null);
            if(mPhotographPath != null) {
                File files = new File(mPhotographPath);
                if (files.exists()) {
                   String  mImagePath = ImageUtil.zoomImage(mPhotographPath, 1024);
                    ArrayList<String> paths = new ArrayList<String>();
                    paths.add(mImagePath);
                    mChooseImageCallback.onCallBack(paths.toString());
                }
            }

        } else if (requestCode == REQUEST_ALBUM) {
            if (intent != null) {
                ArrayList<String> paths = new ArrayList<String>();
                paths = intent.getStringArrayListExtra("paths");
                mChooseImageCallback.onCallBack(paths.toString());
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (button.equals(v)) {
            webView.callHandler("functionInJs", "data from Java", new CallBackFunction() {

                @Override
                public void onCallBack(String data) {
                    // TODO Auto-generated method stub
                    Log.i(TAG, "reponse data from js " + data);
                }

            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isregister(this)) {
            EventBus.getDefault().register(this);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onEvent(Object event) {
        Message msg = (Message) event;
        if(msg.what == CustomMessageConstant.LABUM_BACK){
            int code = msg.arg1;
            if (code != 100) {
                return;
            }
            ArrayList<String> paths = new ArrayList<String>();
            paths = (ArrayList<String>) msg.obj;
            mChooseImageCallback.onCallBack(paths.toString());

        }
    }
}
