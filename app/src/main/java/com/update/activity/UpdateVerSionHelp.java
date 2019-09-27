package com.update.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.GsonUtils;
import com.update.adapter.UpdateAdapter;
import com.update.entity.UpdateContentEntity;
import com.update.model.UpdateModel;
import com.update.service.UpdateService;

import java.util.List;

/**
 * Created by HX_CHEN on 2016/4/13.
 * 小版本检测更新
 */
public class UpdateVerSionHelp implements NewHttpResponse {
    private int code;
    private String newversion;
    private String downurl;
    private UpdateVerSionDialog updateDialog;
    private UpdateAdapter updateAdapter;
    private List<String> updateList;
    private Context contexts;
    private boolean isShowDialog;//true弹出更新 false不弹出

    /**
     * 获取软件最新版本
     *
     * @param minType 首页传1，检查大版本，关于页面传2 检查小版本更新
     */
    public void getNewVerSion(Context context, String minType, boolean isshow) {
        contexts = context;
        isShowDialog = isshow;
        UpdateModel updateModel = new UpdateModel(context);
        updateModel.chekOldVersion(UpdateVerSion.getVersionName(context), minType, isshow, this);
    }

    /**
     * 检查更新版本更新弹窗
     */
    public void showUpdateDialog() {
        updateDialog = new UpdateVerSionDialog(contexts);
        switch (code) {
            case 1://最新版本
                ToastUtil.toastShow(contexts, "彩之云已经是最新版本！");
                break;
            case 0://可选更新
                updateDialog.cancel.setVisibility(View.VISIBLE);
                updateDialog.ok.setText("更新至V" + UpdateVerSion.handleVersionName(newversion) + "版本");
                updateAdapter = new UpdateAdapter(contexts, updateList);
                updateDialog.listView.setAdapter(updateAdapter);
                updateDialog.show();
                break;
            case -1://强制更新
                updateDialog.cancel.setVisibility(View.GONE);
                updateDialog.ok.setText("更新至V" + UpdateVerSion.handleVersionName(newversion) + "版本");
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
        if (isShowDialog) {//isShowDialog 为false的时候不做处理，大版本检测更新
            if (!TextUtils.isEmpty(result)) {
                try {
                  /*  UpdateVersionEntity updateVersionEntity = GsonUtils.gsonToBean(result, UpdateVersionEntity.class);
                    UpdateVersionEntity.ContentBean contentBean = updateVersionEntity.getContent();
                    code = contentBean.getUpdate_code();
                    UpdateVersionEntity.ContentBean.UpdateInfoBean updateInfoBean = contentBean.getUpdate_info();
                    newversion = updateInfoBean.getNew_version();
                    updateList = updateInfoBean.getFunc_list();
                    downurl = updateInfoBean.getUrl();
                    if (!TextUtils.isEmpty(downurl)) {
                        showUpdateDialog();
                    }*/
                    UpdateContentEntity updateContentEntity = GsonUtils.gsonToBean(result, UpdateContentEntity.class);
                    code = updateContentEntity.getResult();
                    List<UpdateContentEntity.InfoBean> infoBeanList = updateContentEntity.getInfo();
                    UpdateContentEntity.InfoBean infoBean = infoBeanList.get(0);
                    newversion = infoBean.getVersion();
                    updateList = infoBean.getFunc();
                    downurl = infoBean.getUrl();
                    if (!TextUtils.isEmpty(downurl)) {
                        showUpdateDialog();
                    }
                } catch (Exception e) {
                    ToastUtil.toastShow(contexts, "彩之云已经是最新版本");
                }
            } else {
                ToastUtil.toastShow(contexts, "彩之云已经是最新版本");
            }
        }
        /*
        //        if (!TextUtils.isEmpty(result)) {
//            try {
//                UpdateVersionEntity updateVersionEntity = GsonUtils.gsonToBean(result, UpdateVersionEntity.class);
//                UpdateVersionEntity.ContentBean contentBean = updateVersionEntity.getContent();
//                code = contentBean.getResult();
//                UpdateVersionEntity.ContentBean.InfoBean updateInfoBean = contentBean.getInfo();
//                newversion = updateInfoBean.getVersion();
//                updateList = Arrays.asList(updateInfoBean.getFunc().split("\n|\r"));
//                downurl = updateInfoBean.getUrl();
//                int type = contentBean.getType(); //当result为2时有效，1：大版本更新，2：小版本更新
//                int showUpdate=0;
//                if (isShowDialog) {  //大版本
//                    if (type == 1) {
//                        showUpdate = 1;
//                    }
//                } else {
//                    showUpdate = 1;
//                }
//                if (!TextUtils.isEmpty(downurl) && showUpdate == 1) {
//                    showUpdateDialog();
//                }
//            } catch (Exception e) {
//
//            }
//        }
         */
    }
}
