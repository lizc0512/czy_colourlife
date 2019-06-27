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

import cn.net.cyberway.R;

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

    private Context context;

    public RefreshTokenModel(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * refresh_token去获取access_token
     * 然后重新请求原来的接口数据
     *
     * @param
     */
    public <T> void refreshAuthToken(boolean isLoading) {
        synchronized (RefreshTokenModel.class) {
            String refresh_token = shared.getString(UserAppConst.Colour_refresh_token, "");
            if (TextUtils.isEmpty(refresh_token)) {
                Message msg = android.os.Message.obtain();
                msg.what = SQUEEZE_OUT;
                msg.obj = context.getResources().getString(R.string.account_extrude_login);
                EventBus.getDefault().post(msg);
            } else {
                final AuthTokenApi authTokenApi = new AuthTokenApi();
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("grant_type", "refresh_token");
                params.put("refresh_token", refresh_token);
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
                                        callServer.clearAllQuest();
                                    } else {
                                        againRequsetAgain();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    againRequsetAgain();
                                }
                            } else {
                                againRequsetAgain();
                            }
                        } else {
                            againRequsetAgain();
                        }
                    }

                    @Override
                    public void onFailed(int what, Response<String> response) {
                        againRequsetAgain();
                    }
                }, true, isLoading);
            }
        }
    }


    private <T> void againRequsetAgain() {
        CallServer callServer = CallServer.getInstance();
        List<Integer> requestWhatList = callServer.getRequestWhat();
        for (int j = 0; j < requestWhatList.size(); j++) {
            int what = requestWhatList.get(j);
            Request<T> request = callServer.getRequestList().get(j);
            Map<String, Object> paramsMap = callServer.getRequestMap().get(j);
            boolean canCancel = callServer.getRequestCancel().get(j);
            boolean isLoading = callServer.getRequestLoading().get(j);
            HttpListener httpListener = callServer.getRequestLister().get(j);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean refreshStatus = shared.getBoolean(UserAppConst.Colour_refresh_status, false);
                    int waitSize = requestWhatList.size();
                    if (waitSize != 0) {  //单个刷新refrshtoken失败
                        if (refreshStatus) {  //根据队列去隔5秒进行轮询请求
                            request(what, request, paramsMap, httpListener, canCancel, isLoading);
                            callServer.deleteSendRequsetDelete(what, request, paramsMap, httpListener, canCancel, isLoading);
                        } else {
                            NewUserModel newUserModel = new NewUserModel(mContext);
                            newUserModel.refreshAuthToken(what, request, paramsMap, httpListener, canCancel, isLoading);
                        }
                    }
                }
            }, 5000 * j);
        }
    }
}

