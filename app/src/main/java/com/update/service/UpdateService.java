package com.update.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;

import com.BeeFramework.Utils.ToastUtil;
import com.permission.AndPermission;
import com.user.UserAppConst;
import com.user.Utils.TokenUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;


@SuppressLint("NewApi")
public class UpdateService extends Service {
    public static final String DOWNLOAD_URL = "download_url";
    public static final String VERSIONNAME = "versionname";
    private String url;
    private String version;
    private String apk_name;
    /**
     * 广播接受者
     */
    private BroadcastReceiver receiver;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        url = intent.getStringExtra(DOWNLOAD_URL);
        version = intent.getStringExtra(VERSIONNAME);
        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();
        apk_name = "colourlife" + "_" + version + "_" + currentTimeStamp + ".apk";
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                install(context);
                //销毁当前的Service
                stopSelf();
            }
        };
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        //下载需要写SD卡权限, targetSdkVersion>=23 需要动态申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!AndPermission.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //申请WRITE_EXTERNAL_STORAGE权限
                AndPermission.with(this).permission(Manifest.permission.WRITE_EXTERNAL_STORAGE).start();
            }
        }
        if (canDownloadState()) {
            downloadApk();
        } else {
            ToastUtil.toastShow(getApplication(), "请到应用管理打开下载管理程序");
            String packageName = "com.android.providers.downloads";
            Intent down = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            down.setData(Uri.parse("package:" + packageName));
            startActivity(down);
        }
    }

    private void downloadApk() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File apkFile= new File(Environment.DIRECTORY_DOWNLOADS + "/" + apk_name);
            if (apkFile.exists()) {
                apkFile.delete();
            }
            SharedPreferences sPreferences = getSharedPreferences(UserAppConst.USERINFO, 0);
            DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            startDownLoading(manager, sPreferences);
        }
    }

    /**
     * 通过query查询下载状态，包括已下载数据大小，总大小，下载状态
     *
     * @param downloadId
     * @return
     */
    public static String getBytesAndStatus(DownloadManager manager, long downloadId) {
        int totalSize = 0;
        int dowmLoadSize = 0;
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = null;
        try {
            cursor = manager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                //已经下载文件大小
                dowmLoadSize = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                //下载文件的总大小
                totalSize = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (dowmLoadSize == 0 || totalSize == 0) {
            return "彩之云已在下载更新中...";
        } else {
            return "彩之云已下载:" + Math.abs(dowmLoadSize * 100 / totalSize) + "%,详细信息可在通知栏查看哟!";
        }
    }

    private void startDownLoading(DownloadManager manager, SharedPreferences sPreferences) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.allowScanningByMediaScanner();// 设置可以被扫描到
        request.setVisibleInDownloadsUi(true);// 设置下载可见
        request.setTitle("彩之云版本更新");//设置下载中通知栏提示的标题
        request.setDescription("程序正在下载中...");//设置下载中通知栏提示的介绍
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //TODO 有些手机放在其他路径会崩溃
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, apk_name);// 这个地址肯定不会错
        request.setMimeType("application/vnd.android.package-archive");
        long refernece = manager.enqueue(request);// 加入下载并取得下载ID
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong(UserAppConst.UPDATE, refernece);//保存此次下载ID
        editor.putString(UserAppConst.APKNAME, apk_name);//保存此次下载的apk名字
        editor.putString(UserAppConst.DOWNLOADERVERSION, version);//保存此次下载的version
        editor.apply();
    }

    /**
     * 通过隐式意图调用系统安装程序安装APK
     */
    private void install(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            boolean hasInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (hasInstallPermission) {
                //安装应用
                installApp(context);
            } else {
                //跳转至“安装未知应用”权限界面，引导用户开启权限
                ToastUtil.toastShow(context, "请开启安装未知应用的权限");
                Uri selfPackageUri = Uri.parse("package:" + this.getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, selfPackageUri);
                if (context instanceof Activity) {
                    ((Activity) context).startActivityForResult(intent, 3000);
                } else {
                    context.startActivity(intent);
                }
            }
        } else {
            //安装应用
            installApp(context);
        }
    }


    private void installApp(Context context) {
        try {
            File file = null;
            if ("huawei".equalsIgnoreCase(TokenUtils.getDeviceBrand()) && Build.VERSION.SDK_INT >= 26) {
                file = new File(context.getCacheDir() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + apk_name);
            } else {
                file = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + apk_name);
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT > 23) { //判读版本是否在7.0含以上
                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                Uri apkUri = FileProvider.getUriForFile(context, "cn.net.cyberway.fileprovider", file);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        } catch (Exception e) {//跳转到应用程序的列表
            ToastUtil.toastShow(context, "请在应用程序的列表中找到彩之云并进行安装");
            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);
            startActivity(intent);
        }
    }

    /**
     * 通过隐式意图调用系统安装程序安装APK
     */
    public static void installCzyAPP(Context context, String appName) {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + appName);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT > 23) { //判读版本是否在7.0含以上
                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                Uri apkUri = FileProvider.getUriForFile(context, "cn.net.cyberway.fileprovider", file);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        } catch (Exception e) {//跳转到应用程序的列表
            ToastUtil.toastShow(context, "请在应用程序的列表中找到彩之云并进行安装");
            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);
            context.startActivity(intent);
        }

    }

    private boolean canDownloadState() {
        try {
            int state = this.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static int getDownloadStatus(DownloadManager downloadManager, long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                }
            } finally {
                c.close();
            }
        }
        return -1;
    }

    @Override
    public void onDestroy() {
        //服务销毁的时候 反注册广播
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
