package cn.net.cyberway.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.BeeFramework.Utils.ToastUtil;
import com.intelspace.library.api.OnSyncUserKeysCallback;
import com.intelspace.library.api.OnUserOptParkLockCallback;
import com.intelspace.library.module.Device;
import com.intelspace.library.module.LocalKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.net.cyberway.activity.MainActivity;
import cn.net.cyberway.home.service.LekaiParkLockController;
import cn.net.cyberway.home.service.LekaiService;

/**
 * 乐开
 * hxg 2019/06/19
 */
public class LekaiHelper {
    private static String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION};

    private static LekaiService mLekaiService;
    private static ServiceConnection mConn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof LekaiService.LocalBinder) {
                LekaiService.LocalBinder binder = (LekaiService.LocalBinder) service;
                mLekaiService = binder.getService();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mLekaiService = null;
        }
    };

    /**
     * Android 6.0后扫描Ble设备需开启位置权限
     */
    public static void init(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(activity, permissions[0]);
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission(activity);
            } else {
                startOperationService(activity);
            }
        } else {
            startOperationService(activity);
        }
    }

    public static void noticeOpenPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(activity, permissions[0]);
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission(activity);
            }
        }
    }

    private static void showDialogTipUserRequestPermission(Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("定位权限不可用")
                .setMessage("由于蓝牙扫描需要定位权限，所以在使用前请授予定位权限；\n否则，蓝牙开门无法正常使用")
                .setPositiveButton("立即开启", (dialog, which) -> startRequestPermission(activity))
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
    }

    /**
     * 开始提交请求权限
     */
    private static void startRequestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, permissions, 100);
    }

    /**
     * 启动开锁服务
     */
    public static void startOperationService(Activity activity) {
        try {
            Intent intent = new Intent(activity, LekaiService.class);
            activity.startService(intent);
            activity.bindService(intent, mConn, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {

        }
    }

    /**
     * 关闭服务
     */
    public static void stop(Activity activity) {
        try {
            if (null != mConn && activity != null) {
                activity.unbindService(mConn);
                activity.stopService(new Intent(activity, LekaiService.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startService(Activity activity, String result) {
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String code = jsonObject.getString("code");
                if ("0".equals(code)) {
                    String content = jsonObject.getString("content");
                    JSONObject data = new JSONObject(content);
                    String accid = data.getString("accid");
                    String token = data.getString("token");
                    start(activity, accid, token);
//                    start(activity, "huangxiaoguang", "huangxiaoguang");//测试
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static void start(Activity activity, String accid, String token) {
        if (null != mLekaiService) {
            mLekaiService.syncUserKeys(new OnSyncUserKeysCallback() {
                @Override
                public void syncResponse(int code, String msg) {
                    if (403 == code) {
                        if (null != activity) {
                            ((MainActivity) activity).regetLekaiToken();
                        }
                    }
                }

                @Override
                public void syncFailed(Throwable throwable) {
                    ToastUtil.toastShow(activity, throwable.getMessage());
                }
            }, accid, token);
        }
    }

    /**
     * 获取本地钥匙
     */
    public static ArrayList<LocalKey> getLocalKey() {
        if (null != mLekaiService) {
            return mLekaiService.getLocalKey();
        } else {
            return null;
        }
    }

    /**
     * 获取钥匙数量
     */
    public static int getKeySize() {
        if (null != mLekaiService) {
            return mLekaiService.getLocalKeySize();
        } else {
            return 0;
        }
    }

    /**
     * 开始扫描设备
     */
    public static void startScanDevice() {
        if (null != mLekaiService) {
            mLekaiService.startScanDevice();
        }
    }

    /**
     * 停止扫描设备
     */
    public static void stopScanDevice() {
        if (null != mLekaiService) {
            mLekaiService.stopScanDevice();
        }
    }

    /**
     * 清除本地钥匙
     */
    public static void clearLocalKey() {
        if (null != mLekaiService) {
            mLekaiService.clearLocalKey();
        }
    }

    /**
     * 停车监听
     */
    public static void setScanParkLockChangeListener(LekaiParkLockController.OnScanParkLockChangeListener listener) {
        if (null != mLekaiService) {
            mLekaiService.setScanParkLockChangeListener(listener);
        }
    }

    /**
     * 获取停车列表
     */
    public static HashMap<String, Device> getParkMap() {
        if (null != mLekaiService) {
            return mLekaiService.getParkMap();
        } else {
            return null;
        }
    }

    /**
     * 下降
     */
    public static void parkUnlock(String mac) {
        if (null != mLekaiService) {
            mLekaiService.parkUnlock(mac);
        }
    }

    /**
     * 下降
     */
    public static void parkUnlock(String mac, final OnUserOptParkLockCallback callback) {
        if (null != mLekaiService) {
            mLekaiService.parkUnlock(mac, callback);
        }
    }

    /**
     * 升起
     */
    public static void parkLock(String mac) {
        if (null != mLekaiService) {
            mLekaiService.parkLock(mac);
        }
    }

    /**
     * 升起
     */
    public static void parkLock(String mac, final OnUserOptParkLockCallback callback) {
        if (null != mLekaiService) {
            mLekaiService.parkLock(mac, callback);
        }
    }

    /**
     * 校验mac地址
     */
    public static String formatMacAddress(String mac) {
        if (TextUtils.isEmpty(mac) || mac.length() < 12) {
            return "";
        }
        StringBuilder builder = new StringBuilder(mac);
        for (int i = mac.length() - 2; i > 0; i -= 2) {
            builder.insert(i, ":");
        }
        return builder.toString();
    }

    /**
     * 防止快速点击 true 超过1秒点击
     */
    private static final int MIN_CLICK_DELAY_TIME = 1000;// 两次点击按钮之间的点击间隔不能少于500毫秒
    private static long lastClickTime = 0;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

}
