package com.user.model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.update.activity.UpdateVerSion;
import com.user.UserAppConst;
import com.user.Utils.TokenUtils;
import com.user.protocol.CheckDeviceLoginApi;
import com.user.protocol.SingleDeviceLoginOutApi;
import com.user.protocol.WetownUsergettokenGetApi;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TokenModel extends BaseModel {

    public TokenModel(Context context) {
        super(context);
    }

    private WetownUsergettokenGetApi tokenApi;
    private SingleDeviceLoginOutApi singleDeviceLoginOutApi;
    private CheckDeviceLoginApi checkDeviceLoginApi;

    /**
     * 静默登录获取token
     *
     * @param businessResponse
     */
    public void getToken(int loginType, boolean isLoading, HttpApiResponse businessResponse) {
        tokenApi = new WetownUsergettokenGetApi();
        Map<String, Object> params = new HashMap<>();
        tokenApi.httpApiResponse = businessResponse;
        params.put("login_type", loginType);
        params.put("device_type", 1);
        params.put("version", UpdateVerSion.handleVersionName(UpdateVerSion.getVersionName(mContext)));
        String deviceCode = TokenUtils.getUUID(mContext);
        params.put("device_code", deviceCode);
        params.put("device_info", TokenUtils.getDeviceInfor(mContext));
        params.put("device_name", TokenUtils.getDeviceBrand() + " " + TokenUtils.getDeviceType());
        String basePath = tokenApi.apiURI;
        final Request<String> request_oauthRegister = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 2, basePath), RequestMethod.POST);
        request(0, request_oauthRegister, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        tokenApi.response.fromJson(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (tokenApi.response.code == 0) {
                        editor.putString(UserAppConst.Colour_token, tokenApi.response.device_token);
                        editor.apply();
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                }
                tokenApi.httpApiResponse.OnHttpResponse(tokenApi);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                tokenApi.httpApiResponse.OnHttpResponse(tokenApi);
            }
        }, true, isLoading);
    }

    /**
     * 获取token
     *
     * @param newHttpResponse
     */
    public void getToken(int what, int loginType, boolean isLoading, final NewHttpResponse newHttpResponse) {
        tokenApi = new WetownUsergettokenGetApi();
        Map<String, Object> params = new HashMap<>();
        params.put("login_type", loginType);
        params.put("device_type", 1);
        params.put("version", UpdateVerSion.handleVersionName(UpdateVerSion.getVersionName(mContext)));
        String deviceCode = TokenUtils.getUUID(mContext);
        params.put("device_code", deviceCode);
        params.put("device_info", TokenUtils.getDeviceInfor(mContext));
        params.put("device_name", TokenUtils.getDeviceBrand() + " " + TokenUtils.getDeviceType());
        String basePath = tokenApi.apiURI;
        final Request<String> request_oauthRegister = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 2, basePath), RequestMethod.POST);
        request(what, request_oauthRegister, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        tokenApi.response.fromJson(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (tokenApi.response.code == 0) {
                        editor.putString(UserAppConst.Colour_token, tokenApi.response.device_token);
                        editor.apply();
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                }
                newHttpResponse.OnHttpResponse(what, result);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, isLoading);
    }

    /**
     * 退出设备登录的接口
     */
    public void deviceLogout(HttpApiResponse businessResponse) {
        singleDeviceLoginOutApi = new SingleDeviceLoginOutApi();
        singleDeviceLoginOutApi.httpApiResponse = businessResponse;
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        String deviceToken = shared.getString(UserAppConst.Colour_token, TokenUtils.getUUID(mContext));
        paramsMap.put("device_token", deviceToken);
        String basePath = singleDeviceLoginOutApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 2, basePath), RequestMethod.POST);
        request(0, request, paramsMap, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        singleDeviceLoginOutApi.response.fromJson(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                }
                singleDeviceLoginOutApi.httpApiResponse.OnHttpResponse(singleDeviceLoginOutApi);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                singleDeviceLoginOutApi.httpApiResponse.OnHttpResponse(singleDeviceLoginOutApi);
            }
        }, true, true);
    }

    /**
     * 检查设备是否在别的设备登录
     *
     * @param businessResponse
     */
    public void checkDeviceLogin(HttpApiResponse businessResponse) {  //这个接口的加密方式和4.0的接口保持一致
        checkDeviceLoginApi = new CheckDeviceLoginApi();
        checkDeviceLoginApi.httpApiResponse = businessResponse;
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        String deviceToken = shared.getString(UserAppConst.Colour_token, TokenUtils.getUUID(mContext));
        paramsMap.put("device_token", deviceToken);
        paramsMap.put("account", shared.getInt(UserAppConst.Colour_User_id, 0));
        String basePath = checkDeviceLoginApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 2, basePath), RequestMethod.POST);
        request(0, request, RequestEncryptionUtils.getNewSaftyMap(mContext, paramsMap), new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        checkDeviceLoginApi.response.fromJson(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                }
                checkDeviceLoginApi.httpApiResponse.OnHttpResponse(checkDeviceLoginApi);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                checkDeviceLoginApi.httpApiResponse.OnHttpResponse(checkDeviceLoginApi);
            }
        }, true, false);
    }

    /**
     * 极验的登录验证
     *
     * @param businessResponse
     */
    public void checkDeepKnow(int what, String phone, String challenge, final NewHttpResponse businessResponse) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("phone", phone);
        paramsMap.put("challenge", challenge);
        paramsMap.put("id_type", 1);
        paramsMap.put("device_uuid", TokenUtils.getUUID(mContext));
        String basePath = "geek/checkDeepKnow";
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, basePath), RequestMethod.POST);
        request(what, request, paramsMap, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                String result = response.get();
                int responseCode = response.getHeaders().getResponseCode();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        businessResponse.OnHttpResponse(what, result);
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    showErrorCodeMessage(responseCode, response);
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
