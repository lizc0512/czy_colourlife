package cn.net.cyberway.home.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.BeeFramework.AppConst;
import com.BeeFramework.BeeFrameworkApp;
import com.BeeFramework.Utils.ToastUtil;
import com.intelspace.library.EdenApi;
import com.intelspace.library.api.OnSyncUserKeysCallback;
import com.intelspace.library.module.Device;

import cn.net.cyberway.R;


/**
 * 乐开 有待优化 乐开
 * <p>
 * Created by hxg on 2019/4/7 17:15
 */
public class OperationService extends Service {
    private final String TAG = this.getClass().getSimpleName();

    private EdenApi mEdenApi;
    private LocalBinder mBinder = new LocalBinder();

    private String acc = "";
    private String tok = "";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        LocalBinder() {

        }

        public OperationService getService() {
            return OperationService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mEdenApi = BeeFrameworkApp.getEdenApi();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? createNotificationChannel() : "";
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.drawable.img_msg)
                    .setContentTitle("彩之云")
                    .setContentText("彩之云正在运行")
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setCategory(NotificationCompat.CATEGORY_SERVICE)
                    .build();
            startForeground(1, notification);
        } catch (Exception e) {

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel() {
        String channelId = "channel_id";
        try {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            String channelName = "彩之云乐开服务";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setImportance(NotificationManager.IMPORTANCE_NONE);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channelId;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    public void initEdenApi(final Activity activity) {
        //申请打开蓝牙
//        Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//        activity.startActivityForResult(enabler, 103);

        mEdenApi = BeeFrameworkApp.getEdenApi();
        // 初始化（启动BleService）
        mEdenApi.init(() -> {
        });

        // 与设备断开连接的回调
        mEdenApi.setOnDisconnectCallback((device, state, newState) -> {
            // 开锁时SDK会停止扫描，开锁完成后需在断开连接的回调中重启扫描
            mEdenApi.startScanDevice();
        });

        // 手机蓝牙状态的监听
        mEdenApi.setOnBluetoothStateCallback((i, s) -> {
            if (i == BluetoothAdapter.STATE_ON) {
                ToastUtil.toastShow(this, "蓝牙已开启");
                mEdenApi.startScanDevice();  // 重启扫描
            }/* else if (i == BluetoothAdapter.STATE_OFF) {
                ToastUtil.toastShow(this, "蓝牙已关闭");
            }*/
        });

        foundDevice();
    }

    public void syncUserKeys(OnSyncUserKeysCallback callback, String accit, String token) {
        if (mEdenApi != null) {
            acc = accit;
            tok = token;
            mEdenApi.syncUserKeys(accit, token, callback);
        }
    }

    public void foundDevice() {
        mEdenApi.setOnFoundDeviceListener(device -> {
            Log.d(TAG, device.getLockMac());
            unlockDevice(device);
        });
    }

    public void unlockDevice(Device device) {
        mEdenApi.unlock(device, acc, tok, AppConst.CONNECT_TIME_OUT, (i, s, i1) -> {
            //开锁成功的回调
            Log.d(TAG, "开锁成功");

            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone rt = RingtoneManager.getRingtone(getApplicationContext(), uri);
            rt.play();
        });
    }

    public int getLocalKeySize() {
        return mEdenApi.getKeySize();
    }

    /**
     * 在切换账号调用，本次不使用
     */
    public void unBindService() {
        mEdenApi.unBindBleService();
    }

    /**
     * 清除key
     */
    public void clearLocalKey() {
        mEdenApi.clearLocalKey();
    }
}
