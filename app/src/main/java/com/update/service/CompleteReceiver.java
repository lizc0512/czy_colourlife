package com.update.service;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.user.UserAppConst;

import java.io.File;
import java.util.List;

class CompleteReceiver extends BroadcastReceiver {
    private Context context;


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            installAPP(context, intent);
        }
    }

    private void installAPP(Context context, Intent intent) {
        long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        SharedPreferences sPreferences = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        long refernece = sPreferences.getLong(UserAppConst.UPDATE, 0);
        String apkName = sPreferences.getString(UserAppConst.APKNAME, "");
        if (refernece == myDwonloadID) {
            if (!TextUtils.isEmpty(apkName)) {
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory("android.intent.category.DEFAULT");
                File file = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + apkName);
                Uri apkUri = null;
                if (Build.VERSION.SDK_INT >= 24) {
                    apkUri = FileProvider.getUriForFile(context, "cn.net.cyberway.fileprovider", file);
                    install.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                } else {
                    apkUri = Uri.fromFile(file);
                }
                install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                //查询所有符合 intent 跳转目标应用类型的应用，注意此方法必须放置setDataAndType的方法之后
                List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                //然后全部授权
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    context.grantUriPermission(packageName, apkUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                context.startActivity(intent);
            }
        }
    }
}