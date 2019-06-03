package com.user.model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.BeeFramework.model.BaseModel;
import com.external.eventbus.EventBus;
import com.nohttp.utils.CallServer;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.UserAppConst;
import com.user.protocol.AuthTokenApi;
import com.user.protocol.AuthTokenResponse;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.user.UserMessageConstant.SQUEEZE_OUT;

/**
 * @name ${yuansk}
 * @class name：com.user.model
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/6/3 10:24
 * @change
 * @chang time
 * @class describe
 */
public class RefreshTokenModel extends BaseModel {

    public RefreshTokenModel(Context context) {
        super(context);
    }

    /**
     * refresh_token去获取access_token
     * 然后重新请求原来的接口数据
     *
     * @param
     */
    public <T> void refreshAuthToken() {
        synchronized (RefreshTokenModel.class) {
            final AuthTokenApi authTokenApi = new AuthTokenApi();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("grant_type", "refresh_token");
            params.put("refresh_token", shared.getString(UserAppConst.Colour_refresh_token, ""));
            params.put("client_id", "2");
            params.put("client_secret", "oy4x7fSh5RI4BNc78UoV4fN08eO5C4pj0daM0B8M");
            String basePath = "/oauth/token";
            final Request<String> request_oauthRegister = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 1, basePath), RequestMethod.POST);
            request(0, request_oauthRegister, params, new HttpListener<String>() {
                @Override
                public void onSucceed(int what, Response<String> response) {
                    int responseCode = response.getHeaders().getResponseCode();
                    String result = response.get();
                    if (responseCode == RequestEncryptionUtils.responseSuccess) {
                        JSONObject jsonObject = showSuccesCodeMessage(result);
                        if (null != jsonObject) {
                            AuthTokenResponse authTokenResponse = authTokenApi.response;
                            try {
                                authTokenResponse.fromJson(jsonObject);
                                if (!TextUtils.isEmpty(authTokenResponse.access_token)) {
                                    editor.putBoolean(UserAppConst.IS_LOGIN, true);
                                    editor.putString(UserAppConst.Colour_access_token, authTokenResponse.access_token);
                                    editor.putString(UserAppConst.Colour_refresh_token, authTokenResponse.refresh_token);
                                    editor.putLong(UserAppConst.Colour_expires_in, Long.valueOf(authTokenResponse.expires_in));
                                    editor.putBoolean(UserAppConst.Colour_refresh_status, true);
                                    editor.putString(UserAppConst.Colour_token_type, authTokenResponse.token_type);
                                    editor.putLong(UserAppConst.Colour_get_time, System.currentTimeMillis());
                                    editor.commit();
                                    CallServer callServer = CallServer.getInstance();
                                    List<Integer> requestWhatList = callServer.getRequestWhat();
                                    for (int j = 0; j < requestWhatList.size(); j++) {
                                        request(requestWhatList.get(j), callServer.getRequestList().get(j),
                                                callServer.getRequestMap().get(j), callServer.getRequestLister().get(j),
                                                callServer.getRequestCancel().get(j), callServer.getRequestLoading().get(j));
                                    }
                                } else {
                                    tokenInvaildLoginOut(result);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                tokenInvaildLoginOut(result);
                            }
                        } else {
                            tokenInvaildLoginOut(result);
                        }
                    } else {
                        tokenInvaildLoginOut(result);
                    }
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    tokenInvaildLoginOut("");
                }
            }, true, false);
        }
    }


    private void tokenInvaildLoginOut(String result) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean refreshStatus = shared.getBoolean(UserAppConst.Colour_refresh_status, false);
                if (!refreshStatus) {
                    editor.putBoolean(UserAppConst.IS_LOGIN, false);
                    editor.putBoolean(UserAppConst.Colour_refresh_status, false);
                    editor.apply();
                    Message msg = android.os.Message.obtain();
                    msg.what = SQUEEZE_OUT;
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (!jsonObject.isNull("message")) {
                            msg.obj = jsonObject.optString("message");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(msg);
                }
            }
        }, 300000);
    }
}
