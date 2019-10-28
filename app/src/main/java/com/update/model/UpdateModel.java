package com.update.model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.update.activity.UpdateVerSion;
import com.update.entity.UpdateContentEntity;
import com.update.entity.UpdateVersionEntity;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.update.activity.UpdateVerSion.showVersionName;

public class UpdateModel extends BaseModel {
    public UpdateModel(Context context) {
        super(context);
    }


    /**
     * 检查软件版本更新
     *
     * @param version 当前版本
     */
    public void chekVersion(String version, boolean showLoading, NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("version", showVersionName(version));
        params.put("app", "czy");
        params.put("type", "1");
        String basePath = "get/version";
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 14, basePath, params), RequestMethod.GET);
        request(0, request, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessageTheme(result);
                    if (code == 0) {
                        try {
                            UpdateVersionEntity updateVersionEntity = GsonUtils.gsonToBean(result, UpdateVersionEntity.class);
                            UpdateVersionEntity.ContentBean contentBean = updateVersionEntity.getContent();
                            int update_code = contentBean.getResult();
                            if (update_code == 2 || update_code == 3) {
                                editor.putBoolean(UpdateVerSion.HASNEWCODE, true);
                                editor.putString(UpdateVerSion.SAVEVERSION, contentBean.getInfo().getVersion());
                            } else {
                                editor.putBoolean(UpdateVerSion.HASNEWCODE, false);
                            }
                            editor.commit();
                        } catch (Exception e) {

                        }
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, showLoading);
    }


    /**
     * cmobile检查软件版本更新  下个版本替换
     *
     * @param version 当前版本
     * @param minType 传1检查大版本 2检查小版本
     */
    public void chekOldVersion(String version, final String minType, boolean showLoading, NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("version", version);
        params.put("minType", minType);
        params.put("type", "android");
        String basePath = "/1.0/version";
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 0, basePath, params), RequestMethod.GET);
        request(0, request, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            UpdateContentEntity updateVersionEntity = GsonUtils.gsonToBean(result, UpdateContentEntity.class);
                            int code = updateVersionEntity.getResult();
                            if (code == -1 || code == 0) {//3.6.0和3.6.1版本，此处被错误修改为code=1，不会执行回调数据，后台对对此特殊处理
                                //保存软件最新版本（发现只有检测小版本的时候后台返回的才是最新的版本，首页返回的不是最新版本）
                                editor.putBoolean(UpdateVerSion.HASNEWCODE, true);
                                editor.putString(UpdateVerSion.SAVEVERSION, updateVersionEntity.getInfo().get(0).getVersion());
                            } else {
                                editor.putBoolean(UpdateVerSion.HASNEWCODE, false);
                            }
                            editor.commit();
                            newHttpResponse.OnHttpResponse(what, result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, showLoading);
    }
}
