package com.im.model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * @name ${yuansk}
 * @class name：com.im.model
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/26 18:27
 * @change
 * @chang time
 * @class describe
 */

public class IMUploadPhoneModel extends BaseModel {
    private Context mContext;
    private final String userByMobileUrl = "/api/im/getUserByMobile";
    private final String userByUuidUrl = "/api/im/getUserByUuid"; //用户uuid获取用户信息
    private final String userByMobiledUrl = "/api/im/searchUserByMobile"; //用户手机号获取用户信息
    private final String userInfoByIdUrl = "user/userInfoById"; //根据用户的id获取用户信息

    public IMUploadPhoneModel(Context context) {
        super(context);
        this.mContext = context;
    }

    public void uploadMobilePhone(int what, String mobile_data, final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        map.put("mobile_data", mobile_data);
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 7, userByMobileUrl), RequestMethod.POST);
        request(what, request, map, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessageTheme(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
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


    public void getUserInforByUuid(int what, String user_uuid, boolean isLoading ,final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_uuid", user_uuid);
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 7, userByUuidUrl), RequestMethod.POST);
        request(what, request, map, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessageTheme(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                } else {
                    showErrorCodeMessage(responseCode, response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
            }
        }, true, isLoading);
    }

    public void getUserInforByMobile(int what, String mobile, final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        map.put("search_mobile", mobile);
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 7, userByMobiledUrl), RequestMethod.POST);
        request(what, request, map, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
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

    public void getUserInforByUserId(int what, String userId, final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", userId);
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 3, userInfoByIdUrl,map), RequestMethod.GET);
        request(what, request, map, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessageTheme(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
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
