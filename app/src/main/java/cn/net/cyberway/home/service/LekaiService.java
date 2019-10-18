package cn.net.cyberway.home.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.BeeFramework.AppConst;
import com.BeeFramework.Utils.ToastUtil;
import com.external.eventbus.EventBus;
import com.intelspace.library.EdenApi;
import com.intelspace.library.ErrorConstants;
import com.intelspace.library.api.OnConnectCallback;
import com.intelspace.library.api.OnFoundDeviceListener;
import com.intelspace.library.api.OnSyncUserKeysCallback;
import com.intelspace.library.api.OnUserOptParkLockCallback;
import com.intelspace.library.module.Device;
import com.intelspace.library.module.LocalKey;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.model.NewUserModel;

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
    private static String err = "操作失败，请重试";

    private EdenApi mEdenApi;
    private LocalBinder mBinder = new LocalBinder();
    private BluetoothStateCallback mBluetoothStateCallback;
    private HashMap<String, Device> mParkMap = new HashMap<>(); // 所有的车位锁
    private LekaiParkLockController mParkLockController;
    private NewUserModel newUserModel;
    private Handler mHandler;

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
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
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
//                LogUtil.e("LekaiService ", "断开连接");
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

    private String deviceCipherId;

    /**
     * 开门禁锁
     */
    public void unlockDevice(Device device) {
        if (null != mEdenApi) {
            mEdenApi.unlock(device, ACC, TOK, AppConst.CONNECT_TIME_OUT, (code, message, battery) -> {
//                LogUtil.e("LekaiService", " =====================================");
//                LogUtil.e("LekaiService 蓝牙门禁", " code：" + (i == 0 ? "开门成功" : i) + "    message:" + s + "   电量：" + i1);
                if (null == mHandler) {
                    mHandler = new Handler(Looper.getMainLooper());
                }
                mHandler.post(() -> {
                    String openCipherId = device.getCipherId();
                    if (0 == code) {
                        SharedPreferences mShared = getApplicationContext().getSharedPreferences(UserAppConst.USERINFO, 0);
                        long currentTimeMillis = System.currentTimeMillis();
                        if (!openCipherId.equals(deviceCipherId)) {
                            deviceCipherId = openCipherId;
                            Message msg = Message.obtain();
                            msg.what = UserMessageConstant.BLUETOOTH_OPEN_DOOR;
                            mShared.edit().putLong("saveTime", currentTimeMillis).apply();
                            EventBus.getDefault().post(msg);

                            uploadOpenDoor(openCipherId, "door", code, message);
                        } else {//开门成功一次后 20s再弹出
                            long saveTime = mShared.getLong("saveTime", currentTimeMillis - 20000);
                            long distanceTime = currentTimeMillis - saveTime;
                            if (distanceTime >= 20000) {
                                mShared.edit().putLong("saveTime", currentTimeMillis).apply();
                                Message msg = Message.obtain();
                                msg.what = UserMessageConstant.BLUETOOTH_OPEN_DOOR;
                                EventBus.getDefault().post(msg);

                                uploadOpenDoor(openCipherId, "door", code, message);
                            }
                        }
                    } else if (ErrorConstants.IS_OPERATION_ERROR_TYPE_WRONG_TIME == code) {
                        ToastUtil.toastShow(getApplicationContext(), "钥匙过期，请联系管理员");
                        uploadOpenDoor(openCipherId, "door", code, message);
                    } else {
                        if (code == -87) {
                            ToastUtil.toastShow(getApplicationContext(), message + ",请重启一下手机蓝牙重试");
                        } else {
                            ToastUtil.toastShow(getApplicationContext(), "开门失败");
                        }
                        uploadOpenDoor(openCipherId, "door", code, message);
                    }
                });
            });
        }
    }

    /**
     * 上传门禁记录
     */
    private void uploadOpenDoor(String openCipherId, String type, int code, String message) {
        boolean success = 0 == code;
        if (null == newUserModel) {
            newUserModel = new NewUserModel(this);
        }
        if (!TextUtils.isEmpty(openCipherId)) {
            newUserModel.uploadOpenDoor(0, openCipherId, type, success ? 1 : 2, success ? code + "" : code + "," + message);
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
//            LogUtil.e("LekaiService ", "重新扫描");
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
     * 地锁 下降
     *
     * @param mac 地锁设备Mac地址  E07DEA3DD86A
     */
    public void parkUnlock(String mac) {
        if (mEdenApi != null) {
            String deviceMac = LekaiHelper.formatMacAddress(mac);
            Device parkDevice = mParkLockController.getParkDeviceByMac(deviceMac);
//            LogUtil.e("LekaiService", " =====================================");
//            LogUtil.e("LekaiService 下降", "mac地址：" + deviceMac);
            if (parkDevice != null) {
                mEdenApi.connectDevice(parkDevice, 5000, new OnConnectCallback() {
                    @Override
                    public void connectSuccess(Device device) {
//                        LogUtil.e("LekaiService 下降", "请求 ACC:" + ACC + "  TOK:" + TOK);
                        mEdenApi.parkUnlock(device, ACC, TOK, (status, message, battery) -> {
//                            LogUtil.e("LekaiService 下降", "status:" + status + "  message:" + message + "  battery:" + battery);
                            if (null == mHandler) {
                                mHandler = new Handler(Looper.getMainLooper());
                            }
                            mHandler.post(() -> {
                                try {
                                    ToastUtil.toastShow(getApplicationContext(), 0 == status ? ("操作成功,电量：" + battery) : err);
                                    uploadOpenDoor(device.getCipherId(), "car", status, message);
//                                    if (null == newUserModel) {
//                                        newUserModel = new NewUserModel(LekaiService.this);
//                                    }
//                                    if (!TextUtils.isEmpty(device.getCipherId())) {
//                                        newUserModel.uploadOpenDoor(0, device.getCipherId(), "car", 0 == status ? 1 : 2, 0 == status ? status + "" : status + "," + message);
//                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        });
                    }

                    @Override
                    public void connectError(int error, String message) {
//                        LogUtil.e("LekaiService 下降", "m失败：mac: " + parkDevice.getCipherId() + "error：" + error + "  message：" + message);
                        if (null == mHandler) {
                            mHandler = new Handler(Looper.getMainLooper());
                        }
                        mHandler.post(() -> {
                            try {
                                ToastUtil.toastShow(getApplicationContext(), message);
                                if (null == newUserModel) {
                                    newUserModel = new NewUserModel(LekaiService.this);
                                }
                                if (!TextUtils.isEmpty(parkDevice.getCipherId())) {
                                    newUserModel.uploadOpenDoor(0, parkDevice.getCipherId(), "car", 2, 0 == error ? error + "" : error + "," + message);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
            }
        }
    }

    /**
     * 地锁 下降
     *
     * @param mac      地锁设备Mac地址  E07DEA3DD86A
     * @param callback 操作回调
     */
    public void parkUnlock(String mac, final OnUserOptParkLockCallback callback) {
        if (mEdenApi != null) {
            String deviceMac = LekaiHelper.formatMacAddress(mac);
            Device parkDevice = mParkLockController.getParkDeviceByMac(deviceMac);
//            LogUtil.e("LekaiService", " =====================================");
//            LogUtil.e("LekaiService 下降", "mac地址：" + deviceMac);
            if (parkDevice != null) {
                mEdenApi.connectDevice(parkDevice, 5000, new OnConnectCallback() {
                    @Override
                    public void connectSuccess(Device device) {
//                        LogUtil.e("LekaiService 下降", "请求 ACC:" + ACC + "  TOK:" + TOK);

                        mEdenApi.parkUnlock(device, ACC, TOK, callback);
                    }

                    @Override
                    public void connectError(int error, String message) {
//                        LogUtil.e("LekaiService 下降", "失败：error：" + error + "  message：" + message);
                        if (null == mHandler) {
                            mHandler = new Handler(Looper.getMainLooper());
                        }
                        mHandler.post(() -> ToastUtil.toastShow(getApplicationContext(), message));
                    }
                });
            }
        }
    }

    /**
     * 地锁 升起
     *
     * @param mac 地锁设备Mac地址
     */
    public void parkLock(String mac) {
        if (mEdenApi != null) {
            String deviceMac = LekaiHelper.formatMacAddress(mac);
            Device parkDevice = mParkLockController.getParkDeviceByMac(deviceMac);
//            LogUtil.e("LekaiService", " =====================================");
//            LogUtil.e("LekaiService 升起", "mac地址：" + deviceMac);
            if (parkDevice != null) {
                mEdenApi.connectDevice(parkDevice, 5000, new OnConnectCallback() {
                    @Override
                    public void connectSuccess(Device device) {
//                        LogUtil.e("LekaiService 升起", "请求 ACC:" + ACC + "  TOK:" + TOK);
                        mEdenApi.parkLock(device, ACC, TOK, new OnUserOptParkLockCallback() {
                            @Override
                            public void userOptParkLockCallback(int status, String message, int battery) {
                                try {
//                                    LogUtil.e("LekaiService 升起", "status:" + status + "  message:" + message + "  battery:" + battery);
                                    if (null == mHandler) {
                                        mHandler = new Handler(Looper.getMainLooper());
                                    }
                                    mHandler.post(() -> {
                                        ToastUtil.toastShow(getApplicationContext(), 0 == status ? ("操作成功,电量：" + battery) : err);
                                        uploadOpenDoor(device.getCipherId(), "car", status, message);

//                                        if (null == newUserModel) {
//                                            newUserModel = new NewUserModel(LekaiService.this);
//                                        }
//                                        if (!TextUtils.isEmpty(device.getCipherId())) {
//                                            newUserModel.uploadOpenDoor(0, device.getCipherId(), "car", 0 == status ? 1 : 2, 0 == status ? status + "" : status + "," + message);
//                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    @Override
                    public void connectError(int error, String message) {
//                        LogUtil.e("LekaiService 升起", "失败：error：" + error + "  message：" + message);
                        if (null == mHandler) {
                            mHandler = new Handler(Looper.getMainLooper());
                        }
                        mHandler.post(() -> {
                            try {
                                ToastUtil.toastShow(getApplicationContext(), message);
                                if (null == newUserModel) {
                                    newUserModel = new NewUserModel(LekaiService.this);
                                }
                                if (!TextUtils.isEmpty(parkDevice.getCipherId())) {
                                    newUserModel.uploadOpenDoor(0, parkDevice.getCipherId(), "car", 2, 0 == error ? error + "" : error + "," + message);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
            }
        }
    }

    /**
     * 地锁 升起
     *
     * @param mac      地锁设备Mac地址
     * @param callback 操作回调
     */
    public void parkLock(String mac, final OnUserOptParkLockCallback callback) {
        if (mEdenApi != null) {
            String deviceMac = LekaiHelper.formatMacAddress(mac);
            Device parkDevice = mParkLockController.getParkDeviceByMac(deviceMac);
//            LogUtil.e("LekaiService", " =====================================");
//            LogUtil.e("LekaiService 升起", "mac地址：" + deviceMac);
            if (parkDevice != null) {
                mEdenApi.connectDevice(parkDevice, 5000, new OnConnectCallback() {
                    @Override
                    public void connectSuccess(Device device) {
//                        LogUtil.e("LekaiService 升起", "请求 ACC:" + ACC + "  TOK:" + TOK);

                        mEdenApi.parkLock(device, ACC, TOK, callback);
                    }

                    @Override
                    public void connectError(int error, String message) {
//                        LogUtil.e("LekaiService 升起", "失败：error：" + error + "  message：" + message);
                        if (null == mHandler) {
                            mHandler = new Handler(Looper.getMainLooper());
                        }
                        mHandler.post(() -> ToastUtil.toastShow(getApplicationContext(), message));
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
