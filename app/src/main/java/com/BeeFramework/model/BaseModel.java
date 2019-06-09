package com.BeeFramework.model;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.BeeFramework.Utils.NetworkUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.entity.BaseErrorEntity;
import com.nohttp.entity.BaseRetCodeEntity;
import com.nohttp.utils.CallServer;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.HttpResponseListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.nohttp.utils.SSLContextUtil;
import com.user.UserAppConst;
import com.user.model.RefreshTokenModel;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import javax.net.ssl.SSLContext;

import cn.net.cyberway.R;

import static com.BeeFramework.model.Constants.NEWAPP_ADDRESS;


public class BaseModel {

    protected Context mContext;
    private Object object;
    public SharedPreferences shared;
    public SharedPreferences.Editor editor;


    public BaseModel() {

    }

    public BaseModel(Context context) {
        mContext = context;
        shared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        editor = shared.edit();
    }


    private boolean againGetToken() {
        long lastSaveTime = shared.getLong(UserAppConst.Colour_get_time, System.currentTimeMillis());
        long nowTime = System.currentTimeMillis();
        long distance = (nowTime - lastSaveTime) / 1000;
        long expires_in = shared.getLong(UserAppConst.Colour_expires_in, 7200);
        if (distance >= expires_in - 60 * 10) {
            return true; //需要刷新
        } else {
            return false;
        }
    }

    /***数据请求的**/
    protected <T> void request(final int what, final Request<T> request, Map<String, Object> paramsMap, final HttpListener<T> callback, final boolean canCancel, final boolean
            isLoading) {
        object = new Object();
        request.setCancelSign(object);
        String requestUrl = request.url();
        paramsMap = RequestEncryptionUtils.getTrimMap(paramsMap);
        if (requestUrl.startsWith(Constants.SERVER_ADDRESS) || requestUrl.startsWith(NEWAPP_ADDRESS)) {
            request.add(paramsMap);
        } else {
            if (!paramsMap.containsKey("signature")) {  //正常请求
                request.add(RequestEncryptionUtils.getNewSaftyMap(mContext, paramsMap));
            } else { //access_token过期后 重新请求
                request.add(paramsMap);
            }
        }
        SSLContext sslContext = SSLContextUtil.getDefaultSLLContext();
        if (sslContext != null) {  //放开https的限制
            request.setSSLSocketFactory(sslContext.getSocketFactory());
        }
        Boolean islogin = shared.getBoolean(UserAppConst.IS_LOGIN, false);
        if (islogin) { //用户已登录
            if (!requestUrl.endsWith("token")) {   //请求的不是获取或更新access_token
                if (!againGetToken()) {//用户不需要刷新access_token
                    String colorToken = shared.getString(UserAppConst.Colour_access_token, "");
                    if (!TextUtils.isEmpty(colorToken)) {
                        request.addHeader("color-token", colorToken);
                    }
                    CallServer.getInstance().request(what, request, new HttpResponseListener<T>(mContext, request, callback, canCancel, isLoading));
                } else {
                    if (requestUrl.startsWith(Constants.NEWAPP_ADDRESS) || requestUrl.startsWith(Constants.SERVER_ADDRESS)) { //cmobile与单设备不需要重新刷新token
                        String colorToken = shared.getString(UserAppConst.Colour_access_token, "");
                        if (!TextUtils.isEmpty(colorToken)) {
                            request.addHeader("color-token", colorToken);
                        }
                        CallServer.getInstance().request(what, request, new HttpResponseListener<T>(mContext, request, callback, canCancel, isLoading));
                    } else {
//                        NewUserModel newUserModel = new NewUserModel(mContext);
//                        newUserModel.refreshAuthToken(what, request, paramsMap, callback, canCancel, isLoading);
                        CallServer.getInstance().addQuestParams(what, request, paramsMap, callback, canCancel, isLoading);
                        RefreshTokenModel refreshTokenModel = new RefreshTokenModel(mContext);
                        refreshTokenModel.refreshAuthToken();
                    }
                }
            } else {  //请求access_token
                CallServer.getInstance().request(what, request, new HttpResponseListener<T>(mContext, request, callback, canCancel, isLoading));
            }
        } else {
            CallServer.getInstance().request(what, request, new HttpResponseListener<T>(mContext, request, callback, canCancel, isLoading));
        }

    }

    /***设置请求的唯一标识**/
    protected <T> void request(final int what, final Request<T> request, Map<String, Object> paramsMap, String sign, final HttpListener<T> callback, final boolean canCancel, final boolean
            isLoading) {
        request.setCancelSign(sign);
        String requestUrl = request.url();
        paramsMap = RequestEncryptionUtils.getTrimMap(paramsMap);
        if (requestUrl.startsWith(Constants.SERVER_ADDRESS) || requestUrl.startsWith(NEWAPP_ADDRESS)) {
            request.add(paramsMap);
        } else {
            if (!paramsMap.containsKey("signature")) {  //正常请求
                request.add(RequestEncryptionUtils.getNewSaftyMap(mContext, paramsMap));
            } else { //access_token过期后 重新请求
                request.add(paramsMap);
            }
        }
        SSLContext sslContext = SSLContextUtil.getDefaultSLLContext();
        if (sslContext != null) {  //放开https的限制
            request.setSSLSocketFactory(sslContext.getSocketFactory());
        }
        Boolean islogin = shared.getBoolean(UserAppConst.IS_LOGIN, false);
        if (islogin) { //用户已登录
            if (!requestUrl.endsWith("token")) {   //请求的不是获取或更新access_token
                if (!againGetToken()) {//用户不需要刷新access_token
                    String colorToken = shared.getString(UserAppConst.Colour_access_token, "");
                    if (!TextUtils.isEmpty(colorToken)) {
                        request.addHeader("color-token", colorToken);
                    }
                    CallServer.getInstance().request(what, request, new HttpResponseListener<T>(mContext, request, callback, canCancel, isLoading));
                } else {
                    if (requestUrl.startsWith(Constants.NEWAPP_ADDRESS) || requestUrl.startsWith(Constants.SERVER_ADDRESS)) { //cmobile与单设备不需要重新刷新token
                        String colorToken = shared.getString(UserAppConst.Colour_access_token, "");
                        if (!TextUtils.isEmpty(colorToken)) {
                            request.addHeader("color-token", colorToken);
                        }
                        CallServer.getInstance().request(what, request, new HttpResponseListener<T>(mContext, request, callback, canCancel, isLoading));
                    } else {
//                        NewUserModel newUserModel = new NewUserModel(mContext);
//                        newUserModel.refreshAuthToken(what, request, paramsMap, callback, canCancel, isLoading);
                        CallServer.getInstance().addQuestParams(what, request, paramsMap, callback, canCancel, isLoading);
                        RefreshTokenModel refreshTokenModel = new RefreshTokenModel(mContext);
                        refreshTokenModel.refreshAuthToken();

                    }
                }
            } else {  //请求access_token
                CallServer.getInstance().request(what, request, new HttpResponseListener<T>(mContext, request, callback, canCancel, isLoading));
            }
        } else {
            CallServer.getInstance().request(what, request, new HttpResponseListener<T>(mContext, request, callback, canCancel, isLoading));
        }
    }


    /*****取消所有的请求****/
    public void cancelAll() {
        CallServer.getInstance().CancelAll();
    }

    /***取消单个请求****/
    public void cancelBySign() {
        CallServer.getInstance().CancelSingle(object);
    }

    public void cancelBySign(String sign) {
        CallServer.getInstance().CancelSingle(sign);
    }

    /***code返回为非200***/
    protected void showErrorCodeMessage(int responseCode, Response<String> response) {
        String result = response.get();
        String url = response.request().url();
        StringBuffer stringBuffer = new StringBuffer();
        if (Constants.SAVENOHTTPRECORD == 1) {
            stringBuffer.append(url);
        } else {
            stringBuffer.append("");
        }
        if (TextUtils.isEmpty(result)) {
            callback(stringBuffer.toString());
        } else {
            try {
                BaseErrorEntity baseErrorEntity = GsonUtils.gsonToBean(result, BaseErrorEntity.class);
                String message = baseErrorEntity.getMessage();
                callback(message);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (!jsonObject.isNull("message")) {
                        stringBuffer.append(jsonObject.optString("message"));
                        callback(stringBuffer.toString());
                    } else {
                        callback(stringBuffer.toString());
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /***code返回为200，返回内容为空，提示相应的错误信息***/
    protected JSONObject showSuccesCodeMessage(String result) {
        JSONObject jsonObject = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                jsonObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject == null) {
            callback("");
        } else {
            if (!jsonObject.isNull("code")) {
                try {
                    BaseErrorEntity baseErrorEntity = GsonUtils.gsonToBean(result, BaseErrorEntity.class);
                    if (baseErrorEntity.getCode() != 0) {
                        final String message = baseErrorEntity.getMessage();
                        callback(message);
                    }
                } catch (Exception e) {

                }

            }
        }
        return jsonObject;
    }

    /***
     * cmobile接口不返回任何提示内容
     * code返回为200，返回内容为空 (不提示错误信息)
     * **/
    protected JSONObject showSuccesCodeNullMessage(String result) {
        JSONObject jsonObject = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                jsonObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            jsonObject = new JSONObject();
        }
        return jsonObject;
    }

    /***请求返回的是200且 code来表示成功用来4.0的接口使用 有提示语 **/
    protected int showSuccesResultMessage(String result) {
        int code = -1;
        JSONObject jsonObject = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                jsonObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject == null) {
            callback("");
        } else {
            try {
                BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                code = baseContentEntity.getCode();
                if (code != 0) {
                    boolean isLogin = shared.getBoolean(UserAppConst.IS_LOGIN, false);
                    String message = baseContentEntity.getMessage();
                    if (isLogin) {
                        callback(message);
                    } else {
                        if (!message.contains("token")) {
                            callback(message);
                        }
                    }
                }
            } catch (NumberFormatException e) {  //返回的code不是数字
                callback("");
            } catch (Exception e) {

            }
        }
        return code;
    }

    /***请求返回的是200且 code来表示成功
     * 主题Model不提示任何提示语
     * **/
    protected int showSuccesResultMessageTheme(String result) {
        int code = -1;
        JSONObject jsonObject = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                jsonObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject != null) {
            try {
                BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                code = baseContentEntity.getCode();
            } catch (Exception e) {

            }
        }
        return code;
    }

    /***
     * 显示cmobile请求成功的结果
     * **/
    protected int showCombileSuccesResult(String result, boolean isShow) {
        int retCode = -1;
        String retMessage;
        JSONObject jsonObject = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                jsonObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jsonObject != null) {
            BaseRetCodeEntity baseRetCodeEntity = GsonUtils.gsonToBean(result, BaseRetCodeEntity.class);
            retCode = baseRetCodeEntity.getRetCode();
            retMessage = baseRetCodeEntity.getRetMsg();
            if (retCode != 1 && isShow) {
                ToastUtil.toastShow(mContext, retMessage);
            }
        }
        return retCode;
    }

    /***请求失败,出现异常的处理***/
    protected void showExceptionMessage(int what, Response<String> response) {
        Exception exception = response.getException();
        if (exception instanceof NetworkError) {// 网络不好:请检查网络。
            ToastUtil.toastInterShow(mContext, mContext.getString(R.string.error_please_check_network));
        } else if (exception instanceof TimeoutError) {// 请求超时
            ToastUtil.toastInterShow(mContext, mContext.getString(R.string.error_timeout));
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            ToastUtil.toastInterShow(mContext, mContext.getString(R.string.error_not_found_server) + response.request().url());
        } else if (exception instanceof URLError) {// URL是错的
            ToastUtil.toastInterShow(mContext, mContext.getString(R.string.error_url_error));
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            ToastUtil.toastInterShow(mContext, mContext.getString(R.string.error_not_found_cache));
        } else {
            String errorNotice = mContext.getResources().getString(R.string.app_requestdate_fail);
            if (Constants.SAVENOHTTPRECORD == 1) {
                ToastUtil.toastInterShow(mContext, response.request().url() + errorNotice);
            } else {
                ToastUtil.toastInterShow(mContext, errorNotice);
            }

        }
    }

    protected void saveCache(String keyword, String content) {
        editor.putString(keyword, content);
        editor.commit();
        return;
    }

    protected String getCache(String keyword) {
        return shared.getString(keyword, "");
    }


    //公共的错误处理
    public boolean callback(String errorDesc) {
        if (!TextUtils.isEmpty(errorDesc)) {
            ToastUtil.toastInterShow(mContext, errorDesc);
        } else {
            if (NetworkUtil.isConnect(mContext)) {
                ToastUtil.toastInterShow(mContext, mContext.getResources().getString(R.string.app_getdate_fail));
            } else {
                ToastUtil.toastInterShow(mContext, mContext.getResources().getString(R.string.app_network_error));
            }
        }
        return true;
    }
}
