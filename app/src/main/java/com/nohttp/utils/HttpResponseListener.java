
package com.nohttp.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.BeeFramework.Utils.JsonFormatTool;
import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.model.Constants;
import com.BeeFramework.view.MyProgressDialog;
import com.nohttp.entity.BaseContentEntity;
import com.user.Utils.TokenUtils;
import com.user.model.RequestFailModel;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.ProtocolRequest;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.tools.MultiValueMap;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cn.net.cyberway.utils.FileUtils;

/*
 * @date 创建时间 2017/4/20
 * @author  yuansk
 * @Description  数据请求的监听
 * @version  1.1
 */
public class HttpResponseListener<T> implements OnResponseListener<T> {

    private Context mActivity;
    /**
     * Dialog.
     */
    private MyProgressDialog mWaitDialog;
    /**
     * Request.
     */
    private Request<?> mRequest;
    /**
     * 结果回调.
     */
    private HttpListener<T> callback;

    /**
     * @param activity     context用来实例化dialog.
     * @param request      请求对象.
     * @param httpCallback 回调对象.
     * @param canCancel    是否允许用户取消请求.
     * @param isLoading    是否显示dialog.
     */
    public HttpResponseListener(Context activity, Request<?> request, HttpListener<T> httpCallback, boolean
            canCancel, boolean isLoading) {
        this.mActivity = activity;
        this.mRequest = request;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity != null && isLoading && isValidContext(mActivity) && null == mWaitDialog) {
                mWaitDialog = new MyProgressDialog(activity, "");
                mWaitDialog.setCanceledOnTouchOutside(canCancel);
                mWaitDialog.show();
            }
        } else {
            try {
                if (activity != null && isLoading && null == mWaitDialog) {
                    mWaitDialog = new MyProgressDialog(activity, "");
                    mWaitDialog.setCanceledOnTouchOutside(canCancel);
                    mWaitDialog.show();
                }
            } catch (Exception e) {

            }
        }
        this.callback = httpCallback;
    }


    /**
     * 开始请求, 这里显示一个dialog.
     */
    @Override
    public void onStart(int what) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (mActivity != null && mWaitDialog != null && isValidContext(mActivity))
                mWaitDialog.show();
        } else {
            try {
                if (mActivity != null && mWaitDialog != null)
                    mWaitDialog.show();
            } catch (Exception e) {

            }

        }
    }

    /**
     * 结束请求, 这里关闭dialog.
     */
    @Override
    public void onFinish(int what) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (mActivity != null && mWaitDialog != null && isValidContext(mActivity))
                mWaitDialog.dismiss();
        } else {
            try {
                if (mActivity != null && mWaitDialog != null)
                    mWaitDialog.dismiss();
            } catch (Exception e) {

            }
        }
    }

    /**
     * 成功回调.
     */
    @Override
    public void onSucceed(int what, Response<T> response) {
        if (callback != null) {
            // 这里判断一下http响应码，这个响应码问下你们的服务端你们的状态有几种，一般是200成功。
            // w3c标准http响应码：http://www.w3school.com.cn/tags/html_ref_httpmessages.asp
            try {
                String requestUrl = response.request().url();
                MultiValueMap<String, Object> params = response.request().getParamKeyValues();
                Set<String> keySet = params.keySet();
                Map<String, Object> requestParams = new HashMap<>();
                Iterator<String> iterator = keySet.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    requestParams.put(key, params.getValue(key));
                }
                if (Constants.SAVENOHTTPRECORD == 1) {

                    StringBuffer stringBuffer = new StringBuffer();
                    String paramString = "";
                    if (response.request().getRequestMethod().toString().equals(RequestMethod.GET)) {
                        paramString = RequestEncryptionUtils.getMapToString(requestParams);
                        stringBuffer.append("请求的url与参数:");
                        stringBuffer.append(requestUrl + paramString);
                    } else {
                        if (requestUrl.endsWith("oauth/token")) {
                            requestParams.put("resultTime", TimeUtil.getTime() + System.currentTimeMillis());
                        }
                        paramString = JsonFormatTool.formatJson(GsonUtils.gsonString(requestParams), "  ");
                        stringBuffer.append("请求的url:");
                        stringBuffer.append(requestUrl);
                        stringBuffer.append("请求的参数:");
                        stringBuffer.append(paramString);
                    }
                    stringBuffer.append("请求返回结果:");
                    stringBuffer.append(response.get());
                    FileUtils.saveQuestAndResultRecord(stringBuffer.toString());
                }
                String result = String.valueOf(response.get());
                if (!TextUtils.isEmpty(result)) {
                    JSONObject resultJson = new JSONObject(result);
                    if (!resultJson.isNull("code")) { //上传通用的接口
                        BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                        if (baseContentEntity.getCode() != 0) {
                            saveFailRequest(response, baseContentEntity.getMessage());
                        }
                    } else if (!resultJson.isNull("error")) { //上传accesstoken请求失败的接口
                        saveFailRequest(response, resultJson.optString("message"));
                    }
                } else {
                    saveFailRequest(response, "");
                }
            } catch (Exception e) {

            }
            callback.onSucceed(what, response);
        }
    }

    /**
     * 失败回调.
     */
    @Override
    public void onFailed(int what, Response<T> response) {
        if (callback != null)
            callback.onFailed(what, response);
        try {
            saveFailRequest(response, response.getException().toString());
        } catch (Exception e) {

        }
    }

    private void saveFailRequest(Response<T> response, String errorMsg) {
        ProtocolRequest<?, T> request = response.request();
        String requestUrl = request.url();
        if (!requestUrl.endsWith("logSubmit")) {
            String result = String.valueOf(response.get());
            MultiValueMap<String, Object> params = request.getParamKeyValues();
            RequestMethod requestMethod = request.getRequestMethod();
            Set<String> keySet = params.keySet();
            Map<String, Object> requestParams = new HashMap<>();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                requestParams.put(key, params.getValue(key));
            }
            String paramString = RequestEncryptionUtils.getMapToString(requestParams);
            RequestFailModel failModel = new RequestFailModel(mActivity);
            Map<String, Object> failMap = new HashMap<>();
            failMap.put("URL", requestUrl);
            if (paramString.length() > 1) {
                failMap.put("params", paramString.substring(1, paramString.length()));
            }
            failMap.put("error", errorMsg);
            failMap.put("method", requestMethod.toString());
            failMap.put("responseTime", response.getNetworkMillis());
            int responseCode = response.getHeaders().getResponseCode();
            if (TextUtils.isEmpty(result)) {
                failMap.put("response", responseCode);
            } else {
                failMap.put("response", responseCode + ":" + result);
            }
            failMap.put("network", TokenUtils.getNetworkType(mActivity));
            failModel.uploadRequestFailLogs(0, RequestEncryptionUtils.getMapToString(failMap));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean isValidContext(Context c) {
        Activity a = (Activity) c;
        if (a.isDestroyed() || a.isFinishing()) {
            return false;
        } else {
            return true;
        }
    }
}
