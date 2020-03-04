/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.scanCode.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.UrlQuerySanitizer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.CompressHelper;
import com.BeeFramework.Utils.DecodeImage;
import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.Util;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.result.ResultParser;
import com.nohttp.utils.GsonUtils;
import com.permission.AndPermission;
import com.permission.PermissionListener;
import com.permission.Rationale;
import com.permission.RationaleListener;
import com.scanCode.entity.AnalysisQrCodeEntity;
import com.scanCode.model.ScanCodeModel;
import com.scanCode.zxing.BeepManager;
import com.scanCode.zxing.CaptureActivityHandler;
import com.scanCode.zxing.ViewfinderView;
import com.scanCode.zxing.camera.CameraManager;
import com.scanCode.zxing.result.URIResultHandler;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import cn.csh.colourful.life.utils.GlideImageLoader;
import cn.csh.colourful.life.view.imagepicker.ImagePicker;
import cn.csh.colourful.life.view.imagepicker.bean.ImageItem;
import cn.csh.colourful.life.view.imagepicker.ui.ImageGridActivity;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
import cn.net.cyberway.utils.LinkParseUtil;
import cn.net.cyberway.view.BottomShareDialogFragment;
import cn.net.cyberway.view.adapter.BaseRecyclerAdapter;


/**
 * This activity opens the camera and does the actual scanning on a background
 * thread. It draws a viewfinder to help the user place the barcode correctly,
 * shows feedback as the image processing is happening, and then overlays the
 * results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 * <p/>
 * 扫一扫
 */
public final class CaptureActivity extends BaseActivity implements
        SurfaceHolder.Callback, View.OnClickListener, NewHttpResponse {
    private final static int INTENT_ACTION_OPEN_SCAN_RESULT = 6; // 弹出扫描结果
    public final static String QRCODE_SOURCE = "qrcode_source";// 第三方调用彩之云的扫码功能 回调将值给它
    private static final String TAG = CaptureActivity.class.getSimpleName();
    public static final int LIGHT_ON = 0;
    public static final int LIGHT_OFF = 1;

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private Result savedResultToShow;
    private ViewfinderView viewfinderView;
    private Result lastResult;
    private boolean hasSurface;
    private Collection<BarcodeFormat> decodeFormats;
    private String characterSet;
    private BeepManager beepManager;
    private TextView title;
    private ImageView back;
    private SurfaceHolder surfaceHolder;
    private ImageView mFlashLight;
    private ImageView open_picture;

    private int currentFlashState = LIGHT_ON;

    private ScanCodeModel scanCodeModel;

    private final int REQUEST_CAMERA = 2000;

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    private String qrSource;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.capture);
        qrSource = getIntent().getStringExtra(QRCODE_SOURCE);
        scanCodeModel = new ScanCodeModel(this);
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        title = (TextView) findViewById(R.id.user_top_view_title);
        title.setText(getResources().getString(R.string.title_scanner));
        back = (ImageView) findViewById(R.id.user_top_view_back);
        ImageView img_right = (ImageView) findViewById(R.id.img_right);
        img_right.setVisibility(View.GONE);
        img_right.setImageResource(R.drawable.img_home_more);
        mFlashLight = (ImageView) findViewById(R.id.flashlight);
        open_picture = (ImageView) findViewById(R.id.open_picture);
        back.setOnClickListener(this);
        img_right.setOnClickListener(this);
        hasSurface = false;
        beepManager = new BeepManager(this);
        mFlashLight.setOnClickListener(this);
        open_picture.setOnClickListener(this);
        initImagePicker();
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, back, title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AndPermission.hasPermission(CaptureActivity.this, Manifest.permission.CAMERA)) {
                initCamera();
            } else {
                ArrayList<String> permission = new ArrayList<>();
                permission.add(Manifest.permission.CAMERA);
                if (AndPermission.hasAlwaysDeniedPermission(CaptureActivity.this, permission)) {
                    ToastUtil.toastShow(getApplicationContext(), getResources().getString(R.string.user_camerapermission_notice));
                    finish();
                } else {
                    AndPermission.with(this)
                            .requestCode(REQUEST_CAMERA)
                            .permission(Manifest.permission.CAMERA)
                            .callback(permissionListener)
                            .rationale(rationaleListener)
                            .start();
                }
            }
        } else {
            initCamera();
        }
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(false);
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    private void initCamera() {
        cameraManager = new CameraManager(getApplication());

        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);

        handler = null;
        lastResult = null;

        resetStatusView();

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(surfaceHolder);
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        beepManager.updatePrefs(); // 设置震动

        decodeFormats = null;
        characterSet = null;

    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        if (null != cameraManager) {
            cameraManager.closeDriver();
        }
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (lastResult != null) {
                // restartPreviewAfterDelay(0L);
                finish();
                return true;
            }
        } else if (keyCode == KeyEvent.KEYCODE_FOCUS
                || keyCode == KeyEvent.KEYCODE_CAMERA) {
            // Handle these events so they don't launch the Camera app
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
        // Bitmap isn't used yet -- will be used soon
        if (handler == null) {
            savedResultToShow = result;
        } else {
            if (result != null) {
                savedResultToShow = result;
            }
            if (savedResultToShow != null) {
                Message message = Message.obtain(handler,
                        R.id.decode_succeeded, savedResultToShow);
                handler.sendMessage(message);
            }
            savedResultToShow = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG,
                    "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult The contents of the barcode.
     * @param barcode   A greyscale bitmap of the camera data which was decoded.
     */
    public void handleDecode(Result rawResult, Bitmap barcode) {
        lastResult = rawResult;

        URIResultHandler resultHandler = new URIResultHandler(this,
                ResultParser.parseResult(rawResult));

        String resultString = rawResult.getText();
        if (barcode == null) {
            // This is from history -- no saved barcode
            handleDecodeInternally(rawResult, resultHandler, null);
        } else {
            if ("colourlife".equals(qrSource)) {
                Intent intent = new Intent();
                intent.putExtra("qrcodeValue", resultString);
                setResult(200, intent);
                finish();
            } else {
                handleScan(resultString);
            }

        }
    }

    /**
     * 处理扫描结果
     *
     * @param result
     */
    private void handleScan(String result) {
        UrlQuerySanitizer sanitizer = new UrlQuerySanitizer();
        sanitizer.setAllowUnregisteredParamaters(true);
        sanitizer.parseUrl(result);
        if (result.contains("kakatool.com") == false) {
            // 非kakatool二维码规范
            if (result.contains("www.360wxq.com/")) {
                // 门禁 格式规范：www.360wxq.com/QR = xx.xx.xx.xx
                if (sanitizer.getValue("QR") != null) {
                    String qrcode = sanitizer.getValue("QR");
                    openDoorScanResult(qrcode, true, 1);
                } else {
                    unrecognizedScanResult(result);
                }
            } else if (result.contains("http://kkt.me/dr/")) {
                // 门禁 新的格式规范：http://kkt.me/dr/CSH000001
                String qrcode = result.replace("http://kkt.me/dr/", "");
                openDoorScanResult(qrcode, true, 2);
            } else if (result.contains("http://kkt.me/drb/")) {
                String qrcode = result.replace("http://kkt.me/drb/", "");
                openDoorScanResult(qrcode, true, 3);
            } else if (result.contains("http://kkt.me/w/")) {
                // 人传人规则
                String qrcode = result.replace("http://kkt.me/w/", "");
                if (qrcode.contains("/")) {
                    String[] array = qrcode.split("/");
                    String bid = array[0];
                    String cid = array[1];
                } else {
                    // 显示扫描信息
                    unrecognizedScanResult(result);
                }
            } else {
                // 显示扫描信息
                unrecognizedScanResult(result);
            }
            return;
        }
        unrecognizedScanResult(result);
    }

    // 判断CID是否为数字
    public static boolean isNumeric(String cid) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(cid).matches();
    }

    /**
     * 跳转到门禁
     *
     * @param qrcode
     * @param flag   false表示不缓存在本地（本地已经存在），true表示如果开门成功则允许缓存在本地
     */
    private void openDoorScanResult(String qrcode, boolean flag, int qrBle) {
        Intent intent = new Intent(CaptureActivity.this,
                MainActivity.class);
        intent.putExtra("shortcut", qrcode);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.door_push_bottom_out);
    }


    // 显示扫描的结果
    private void unrecognizedScanResult(String result) {
        scanCodeModel.analysisUrl(0, result, this);
    }


    private static void drawLine(Canvas canvas, Paint paint, ResultPoint a,
                                 ResultPoint b) {
        canvas.drawLine(a.getX(), a.getY(), b.getX(), b.getY(), paint);
    }

    private void showResult(Result rawResult, URIResultHandler resultHandler,
                            Bitmap barcode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence displayContents = resultHandler.getDisplayContents();
        if (barcode == null) {
            builder.setIcon(R.drawable.default_image);
        } else {

            Drawable drawable = new BitmapDrawable(barcode);
            builder.setIcon(drawable);
        }

        builder.setTitle("Result");
        builder.setMessage(displayContents);
        builder.setPositiveButton(getResources().getString(R.string.message_define), new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(CaptureActivity.this,
                        CaptureActivity.class);
                startActivity(intent);
                CaptureActivity.this.finish();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.message_cancel), new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                CaptureActivity.this.finish();

            }
        });
        builder.show();

    }

    /**
     * Put up our own UI for how to handle the decoded contents. 处理扫描结果
     *
     * @param rawResult
     * @param resultHandler
     * @param barcode       条码截图
     */
    private void handleDecodeInternally(Result rawResult,
                                        URIResultHandler resultHandler, Bitmap barcode) {
        viewfinderView.setVisibility(View.GONE);
        showResult(rawResult, resultHandler, barcode);// dialog展示简要信息

        // 二维码格式 例：QR_CODE
        String format = rawResult.getBarcodeFormat().toString();

        // 二维码扫描结果类型 例： URL
        String type = resultHandler.getType().toString();

        // time
        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT,
                DateFormat.SHORT);
        String formattedTime = formatter.format(new Date(rawResult
                .getTimestamp()));

        // url
        CharSequence displayContents = resultHandler.getDisplayContents();

        Log.d(TAG, String.format(
                "formatText=%s typeText=%s timeText=%s displayContents=%s",
                format, type, formattedTime, displayContents));

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats,
                        characterSet, cameraManager);
            }
            decodeOrStoreSavedBitmap(null, null);
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            // displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            ToastUtil.toastShow(getApplicationContext(), getResources().getString(R.string.user_camerapermission_notice));
        }
    }


    private void resetStatusView() {
        viewfinderView.setVisibility(View.VISIBLE);
        lastResult = null;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        setResult(resultCode, data);
        if (requestCode == 3000) {

        } else if (requestCode == 5000) {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String path = images.get(0).path;
                Bitmap bitmap = CompressHelper.getDefault(this).compressToBitmap(new File(path));
                Result result = DecodeImage.handleQRCodeFormBitmap(bitmap);
                if (result == null) {
                    ToastUtil.toastShow(CaptureActivity.this, "请选择是二维码的图片");
                } else {
                    handleDecode(result, bitmap);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.img_right:
                final BottomShareDialogFragment bottomShareDialogFragment = new BottomShareDialogFragment(CaptureActivity.this);
                if (null != bottomShareDialogFragment.adapter) {
                    bottomShareDialogFragment.adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int position) {
                            if (position == 0) {
                                Intent intent = new Intent(CaptureActivity.this, ImageGridActivity.class);
                                startActivityForResult(intent, 5000);
                            }
                            bottomShareDialogFragment.dismissDialog();
                        }
                    });
                }
                bottomShareDialogFragment.setWidth(Utils.getDeviceWith(getApplicationContext()));
                bottomShareDialogFragment.setHeight(Util.DensityUtil.dip2px(getApplicationContext(), 50));
                bottomShareDialogFragment.showAtLocation(findViewById(R.id.viewfinder_view), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.open_picture:
                Intent intent = new Intent(CaptureActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, 5000);
                break;
            case R.id.flashlight:
                if (currentFlashState == LIGHT_ON) {
                    if (cameraManager != null) {
                        cameraManager.openLight();
                        currentFlashState = LIGHT_OFF;
                        mFlashLight.setImageResource(R.drawable.b0_torch_on);
                    }
                } else {
                    if (cameraManager != null) {
                        cameraManager.offLight();
                        currentFlashState = LIGHT_ON;
                        mFlashLight.setImageResource(R.drawable.b0_torch_off);
                    }
                }
                break;
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_CAMERA: {
                    initCamera();
                    break;
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            if (AndPermission.hasAlwaysDeniedPermission(CaptureActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                ToastUtil.toastShow(getApplicationContext(), getResources().getString(R.string.user_camerapermission_notice));
            }
        }
    };


    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
            ToastUtil.toastShow(getApplicationContext(), getResources().getString(R.string.user_camerapermission_notice));
        }
    };

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        AnalysisQrCodeEntity analysisQrCodeEntity = GsonUtils.gsonToBean(result, AnalysisQrCodeEntity.class);
                        String linkUrl = analysisQrCodeEntity.getContent().getUrl();
                        if (!TextUtils.isEmpty(linkUrl)) {
                            if (linkUrl.contains("openYourDoor/")) {
                                String qrcode = linkUrl.replace("openYourDoor/", "");
                                openDoorScanResult(qrcode, true, 2);
                            } else {
                                LinkParseUtil.parse(CaptureActivity.this, linkUrl, "");
                            }
                        }
                        finish();
                    } catch (Exception e) {

                    }
                } else {
                    if (handler != null)
                        handler.restartPreviewAndDecode();
                }
                break;
        }
    }
}


