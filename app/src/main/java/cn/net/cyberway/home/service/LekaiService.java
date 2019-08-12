package cn.net.cyberway.home.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.BeeFramework.AppConst;
import com.BeeFramework.Utils.ToastUtil;
import com.intelspace.library.EdenApi;
import com.intelspace.library.ErrorConstants;
import com.intelspace.library.api.OnConnectCallback;
import com.intelspace.library.api.OnFoundDeviceListener;
import com.intelspace.library.api.OnSyncUserKeysCallback;
import com.intelspace.library.api.OnUserOptParkLockCallback;
import com.intelspace.library.module.Device;
import com.intelspace.library.module.LocalKey;
import com.youmai.hxsdk.view.camera.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;

import cn.net.cyberway.utils.ActivityLifecycleListener;
import cn.net.cyberway.utils.LekaiHelper;

/**
 * 乐开
 * <p>
 * Created by hxg on 2019/4/7 17:15
 */
public class LekaiService extends Service {

    private final String TAG = this.getClass().getSimpleName();

    private EdenApi mEdenApi;
    private LocalBinder mBinder = new LocalBinder();
    private BluetoothStateCallback mBluetoothStateCallback;
    private HashMap<String, Device> mParkMap = new HashMap<>(); // 所有的车位锁
    private LekaiParkLockController mParkLockController;

    private static String ACC = "";
    private static String TOK = "";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        LocalBinder() {
        }

        public LekaiService getService() {
            return LekaiService.this;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mParkLockController = new LekaiParkLockController(mParkMap);

        initEdenApi();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mEdenApi.unBindBleService();
    }

    public void initEdenApi() {
        mEdenApi = EdenApi.getInstance(this, AppConst.APP_KEY, false);
        // 初始化（启动BleService）
        mEdenApi.init(() -> {
        });
        // 与设备断开连接的回调
        mEdenApi.setOnDisconnectCallback((device, state, newState) -> {
            // 开锁时SDK会停止扫描，开锁完成后需在断开连接的回调中重启扫描
            if (!ActivityLifecycleListener.isAppBackground()) {
                LogUtil.e("LekaiService ", "断开连接");
                mEdenApi.startScanDevice();
            }
        });
        // 手机蓝牙状态的监听
        mEdenApi.setOnBluetoothStateCallback((i, s) -> {
            try {
                if (i == BluetoothAdapter.STATE_ON) {
                    if (null != mBluetoothStateCallback) {
                        mBluetoothStateCallback.onBluetoothStateOn();
                    }
                    if (!ActivityLifecycleListener.isAppBackground()) {
                        ToastUtil.toastShow(getApplicationContext(), "蓝牙已开启");
                        mEdenApi.startScanDevice();  // 重启扫描
                    }
                } else if (i == BluetoothAdapter.STATE_OFF) {
                    if (null != mBluetoothStateCallback) {
                        mBluetoothStateCallback.onBluetoothStateOff();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        mEdenApi.setOnFoundDeviceListener(device -> {
            Log.d(TAG, device.getLockMac());
            unlockDevice(device);
        });
        foundDevice();
    }

    public void syncUserKeys(OnSyncUserKeysCallback callback, String accid, String token) {
        if (null != mEdenApi) {
            ACC = accid;
            TOK = token;
            mEdenApi.syncUserKeys(accid, token, callback);
        }
    }

    public void foundDevice() {
        if (mEdenApi != null) {
            mEdenApi.setOnFoundDeviceListener(device -> {
                Log.d(TAG, device.getLockMac());
                // 判断是否为车位锁
                if (Device.LOCK_VERSION_PARK_LOCK.equals(device.getLockVersion())) {
                    mParkLockController.putParkDevice(device);
                } else {
                    unlockDevice(device);
                }
            });
        }
    }

    public void unlockDevice(Device device) {
        if (null != mEdenApi) {
            mEdenApi.unlock(device, ACC, TOK, AppConst.CONNECT_TIME_OUT, (i, s, i1) -> {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    if (0 == i) {
                        ToastUtil.toastShow(getApplicationContext(), "开门成功");
                    } else if (ErrorConstants.IS_OPERATION_ERROR_TYPE_WRONG_TIME == i) {
                        ToastUtil.toastShow(getApplicationContext(), "钥匙过期，请联系管理员");
                    } else {
                        ToastUtil.toastShow(getApplicationContext(), "开门失败");
                    }
                });
            });
        }
    }

    public void setOnFoundDeviceListener(OnFoundDeviceListener listener) {
        if (null != mEdenApi) {
            mEdenApi.setOnFoundDeviceListener(listener);
        }
    }

    public ArrayList<LocalKey> getLocalKey() {
        return mEdenApi.getLocalKeys();
    }

    public int getLocalKeySize() {
        return null != mEdenApi ? mEdenApi.getKeySize() : 0;
    }

    public void startScanDevice() {
        if (null != mEdenApi) {
            LogUtil.e("LekaiService ", "重新扫描");
            mEdenApi.startScanDevice();
        }
    }

    public void stopScanDevice() {
        if (null != mEdenApi) {
            mEdenApi.stopScanDevice();
        }
    }

    public void clearLocalKey() {
        if (null != mEdenApi) {
            mEdenApi.clearLocalKey();
        }
    }

    public void setBluetoothStateCallback(BluetoothStateCallback bluetoothStateCallback) {
        mBluetoothStateCallback = bluetoothStateCallback;
    }

    public interface BluetoothStateCallback {
        void onBluetoothStateOn();

        void onBluetoothStateOff();
    }

    /**
     * 地锁放下（开锁）
     *
     * @param mac      地锁设备Mac地址  E07DEA3DD86A
     * @param callback 操作回调
     */
    public void parkUnlock(String mac, final OnUserOptParkLockCallback callback) {
        if (mEdenApi != null) {
            String deviceMac = LekaiHelper.formatMacAddress(mac);
            Device parkDevice = mParkLockController.getParkDeviceByMac(deviceMac);
            LogUtil.e("LekaiService", " =====================================");
            LogUtil.e("LekaiService 倒下", "mac地址：" + deviceMac);
            if (parkDevice != null) {
                mEdenApi.connectDevice(parkDevice, 5000, new OnConnectCallback() {
                    @Override
                    public void connectSuccess(Device device) {
                        LogUtil.e("LekaiService 倒下", "请求 ACC:" + ACC + "  TOK:" + TOK);

                        mEdenApi.parkUnlock(device, ACC, TOK, callback);
                    }

                    @Override
                    public void connectError(int error, String message) {
                        LogUtil.e("LekaiService 倒下", "失败：error：" + error + "  message：" + message);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(() -> ToastUtil.toastShow(getApplicationContext(), message));
                    }
                });
            }
        }
    }

    /**
     * 地锁抬起（关锁）
     *
     * @param mac      地锁设备Mac地址
     * @param callback 操作回调
     */
    public void parkLock(String mac, final OnUserOptParkLockCallback callback) {
        if (mEdenApi != null) {
            String deviceMac = LekaiHelper.formatMacAddress(mac);
            Device parkDevice = mParkLockController.getParkDeviceByMac(deviceMac);
            LogUtil.e("LekaiService", " =====================================");
            LogUtil.e("LekaiService 抬起", "mac地址：" + deviceMac);
            if (parkDevice != null) {
                mEdenApi.connectDevice(parkDevice, 5000, new OnConnectCallback() {
                    @Override
                    public void connectSuccess(Device device) {
                        LogUtil.e("LekaiService 抬起", "请求 ACC:" + ACC + "  TOK:" + TOK);

                        mEdenApi.parkLock(device, ACC, TOK, callback);
                    }

                    @Override
                    public void connectError(int error, String message) {
                        LogUtil.e("LekaiService 抬起", "失败：error：" + error + "  message：" + message);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(() -> ToastUtil.toastShow(getApplicationContext(), message));
                    }
                });
            }
        }
    }

    public void setScanParkLockChangeListener(LekaiParkLockController.OnScanParkLockChangeListener listener) {
        if (mParkLockController != null) {
            mParkLockController.setScanParkLockChangeListener(listener);
        }
    }

    public HashMap<String, Device> getParkMap() {
        return mParkMap;
    }

}
