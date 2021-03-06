package com.update.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.GsonUtils;
import com.update.adapter.UpdateAdapter;
import com.update.entity.UpdateVersionEntity;
import com.update.model.UpdateModel;
import com.update.service.UpdateService;

import java.util.Arrays;
import java.util.List;

/**
 * 版本更新
 * 大版本检测更新
 */
public class UpdateVerSion implements NewHttpResponse {
    public static final String SAVEVERSION = "SAVEVERSION";//保存的彩之云版本名
    public static final String SAVEVERSIONCODE = "SAVEVERSIONCODE";//保存的彩之云版本号
    public static final String HASNEWCODE = "HASNEWCODE";//有新版本

    private int code;
    private String newversion;
    private String downurl;
    private UpdateVerSionDialog updateDialog;
    private UpdateAdapter updateAdapter;
    private List<String> updateList;
    private Context contexts;
    private boolean isShowDialog;//true弹出更新 false不弹出

    /**
     * 获取软件版本号
     *
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 1;
        try {
            // 获取软件版本号，
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取软件版本名
     *
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "1.0.0";
        try {
            // 获取软件版本号，
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     *版本控制的版本号
     *
     * @return
     */
    public static String handleVersionName(String versionName) {

        String result = "";
        if (versionName.contains(".")) {
            StringBuffer stringBuffer = new StringBuffer(versionName);

            if (versionName.length() > 0) {

                int mainVersion = Integer.valueOf(versionName.substring(0, 1));
                //获取的版本号比要显示的版本号大2
                if (mainVersion > 2) {
                    mainVersion -= 2;
                }
                stringBuffer.replace(0, 1, mainVersion + "");
                result = stringBuffer.toString();
            }
        }

        return result;
    }

    public static String showVersionName(String versionName) {
        String result = "";
        if (versionName.contains(".")) {
            StringBuffer stringBuffer = new StringBuffer();
            if (versionName.length() > 0) {  //6.6.5  -5.0.0
                String[] versionArr = versionName.split("\\.");
                int length = versionArr.length;
                if (length >= 3) {
                    stringBuffer.append(Integer.valueOf(versionArr[0]) - 1);
                    stringBuffer.append(".");
                    stringBuffer.append(Integer.valueOf(versionArr[1]) - 5);
                    stringBuffer.append(".");
                    stringBuffer.append(Integer.valueOf(versionArr[2]) - 18);
                    if (length == 4) {
                        stringBuffer.append(".");
                        stringBuffer.append(versionArr[3]);
                    }
                    result = stringBuffer.toString();
                } else {
                    result = versionName;
                }
            }
        }
        return result;
    }


    public void getNewVerSion(Activity context, boolean showDialog, boolean slient) {
        isShowDialog = showDialog;
        contexts = context;
        UpdateModel updateModel = new UpdateModel(context);
        updateModel.checkVersion(getVersionName(context), !showDialog, slient, this);
    }


    public void showNewUpdateDialog() {
        updateDialog = new UpdateVerSionDialog(contexts);
        switch (code) {
            case 1://最新版本
                if (!isShowDialog) {
                    ToastUtil.toastShow(contexts, "彩之云已经是最新版本！");
                }
                break;
            case 2://可选更新  新的版本控制平台
                updateDialog.ok.setText("更新至V" + newversion + "版本");
                updateDialog.cancel.setVisibility(View.VISIBLE);
                updateAdapter = new UpdateAdapter(contexts, updateList);
                updateDialog.listView.setAdapter(updateAdapter);
                updateDialog.show();
                break;
            case 3://新的版本控制平台
                updateDialog.cancel.setVisibility(View.GONE);
                updateDialog.ok.setText("更新至V" + newversion + "版本");
                updateAdapter = new UpdateAdapter(contexts, updateList);
                updateDialog.listView.setAdapter(updateAdapter);
                updateDialog.mDialog.setCancelable(false);
                updateDialog.show();
                break;
        }
        updateDialog.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code != 1) {
                    //用户点击更新，跳转到下载更新页面
                    Intent intent = new Intent(contexts, UpdateService.class);
                    intent.putExtra(UpdateService.DOWNLOAD_URL, downurl);
                    intent.putExtra(UpdateService.VERSIONNAME, newversion);
                    contexts.startService(intent);
                    ToastUtil.toastShow(contexts, "彩之云已开始下载更新,详细信息可在通知栏查看哟!");
                }
                updateDialog.dismiss();
            }
        });
        updateDialog.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
            }
        });
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        if (!TextUtils.isEmpty(result)) {
            try {
                UpdateVersionEntity updateVersionEntity = GsonUtils.gsonToBean(result, UpdateVersionEntity.class);
                UpdateVersionEntity.ContentBean contentBean = updateVersionEntity.getContent();
                code = contentBean.getResult();
                UpdateVersionEntity.ContentBean.InfoBean updateInfoBean = contentBean.getInfo();
                newversion = updateInfoBean.getVersion();
                updateList = Arrays.asList(updateInfoBean.getFunc().split("\n|\r"));
                downurl = updateInfoBean.getUrl();
                int type = contentBean.getType(); //当result为2时有效，1：大版本更新，2：小版本更新
                int showUpdate = 0;
                if (isShowDialog) {  //大版本
                    if (type == 1) {
                        showUpdate = 1;
                    }
                } else {
                    showUpdate = 1;
                }
                if (!TextUtils.isEmpty(downurl) && showUpdate == 1) {
                    showNewUpdateDialog();
                }
            } catch (Exception e) {

            }
        } else {
            if (!isShowDialog) {
                ToastUtil.toastShow(contexts, "彩之云已经是最新版本！");
            }
        }
    }
}
