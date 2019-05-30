package com.user.model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.UserAppConst;
import com.user.Utils.TokenUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * @name ${yuansk}
 * @class name：com.user.model
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/5/3 16:28
 * @change
 * @chang time
 * @class describe
 */

public class RequestFailModel extends BaseModel {
    public RequestFailModel(Context context) {
        super(context);
    }

    private String uploadFialRequestUrl = "user/logSubmit";

    public void uploadRequestFailLogs(int what, String logFile) {
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", shared.getString(UserAppConst.Colour_login_mobile, ""));
        params.put("logFile", logFile.substring(1, logFile.length()));
        params.put("device_uuid", TokenUtils.getUUID(mContext));
        String basePath = uploadFialRequestUrl;
        final Request<String> request_oauthRegister = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 6, basePath), RequestMethod.POST);
        request(what, request_oauthRegister, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {

            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, false);
    }
}
