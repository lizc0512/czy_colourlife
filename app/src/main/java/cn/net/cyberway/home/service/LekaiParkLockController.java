package cn.net.cyberway.home.service;

import android.os.Handler;

import com.intelspace.library.module.Device;

import java.util.HashMap;

/**
 * 保存扫描到的地锁设备，若干时间内没有再次扫描到，则移除
 * hxg 2019-07-29.
 */
public class LekaiParkLockController {

    public static final long REMOVE_PARK_LOCK_DELAY = 5000;

    private HashMap<String, Device> mParkMap;
    private HashMap<String, ParkLockRemoveRunnable> mRunnableMap = new HashMap<>();

    private Handler mHandler = new Handler();
    private OnScanParkLockChangeListener mScanParkLockChangeListener;

    public LekaiParkLockController(HashMap<String, Device> parkMap) {
        mParkMap = parkMap;
    }

    public void setScanParkLockChangeListener(OnScanParkLockChangeListener listener) {
        mScanParkLockChangeListener = listener;
    }

    public void putParkDevice(Device device) {
        if (!mParkMap.containsKey(device.getLockMac())) {
            mParkMap.put(device.getLockMac(), device);
            if (mScanParkLockChangeListener != null) {
                mScanParkLockChangeListener.onScanParkLockChanged(device.getLockMac());
            }
        } else {
            mHandler.removeCallbacks(mRunnableMap.get(device.getLockMac()));
        }

        ParkLockRemoveRunnable runnable = new ParkLockRemoveRunnable(device.getLockMac());
        mRunnableMap.put(device.getLockMac(), runnable);
        mHandler.postDelayed(runnable, REMOVE_PARK_LOCK_DELAY);
    }

    public Device getParkDeviceByMac(String mac) {
        if (mParkMap.containsKey(mac)) {
            return mParkMap.get(mac);
        }
        return null;
    }

    class ParkLockRemoveRunnable implements Runnable {

        private String mac;

        public ParkLockRemoveRunnable(String mac) {
            this.mac = mac;
        }

        @Override
        public void run() {
            mParkMap.remove(mac);
            if (mScanParkLockChangeListener != null) {
                mScanParkLockChangeListener.onScanParkLockChanged(mac);
            }
        }
    }

    public interface OnScanParkLockChangeListener {
        void onScanParkLockChanged(String mac);
    }
}
