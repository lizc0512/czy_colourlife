package com.user.model;

import android.content.Context;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.protocol.CustomerPasswordPostApi;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PasswordModel extends BaseModel {


    public PasswordModel(Context context) {
        super(context);
    }

    private CustomerPasswordPostApi resetApi;



    /**
     * 重置用户密码
     *
     * @param mobile   手机号
     * @param code     验证码
     * @param password 密码
     */
    public void resetPassword(HttpApiResponse businessResponse, final String mobile, String code, String password) {
        resetApi = new CustomerPasswordPostApi();
        resetApi.httpApiResponse = businessResponse;
        resetApi.request.mobile = mobile;
        resetApi.request.token = code;
        resetApi.request.password = password;
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params = Utils.transformJsonToMap(resetApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = resetApi.apiURI.replace("/:ver", "/1.0");
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 0, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>()
        {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            resetApi.response.fromJson(jsonObject);
                            resetApi.httpApiResponse.OnHttpResponse(resetApi);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        showErrorCodeMessage(responseCode,response);
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {
                    showErrorCodeMessage(responseCode, response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
            }
        }, true, true);
    }
}
