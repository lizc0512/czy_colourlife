package com.update.model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.update.activity.UpdateVerSion;
import com.update.entity.UpdateVersionEntity;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

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
    public void checkVersion(String version, boolean showLoading,boolean slient, NewHttpResponse newHttpResponse) {
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
                        if (!slient){ //不是静默的有回调处理
                            newHttpResponse.OnHttpResponse(what, result);
                        }
                    }else {
                        if (!slient){ //不是静默的有回调处理
                            newHttpResponse.OnHttpResponse(what, "");
                        }
                    }
                }else{
                    if (!slient){ //不是静默的有回调处理
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (!slient){ //不是静默的有回调处理
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }
        }, true, showLoading);
    }
}
