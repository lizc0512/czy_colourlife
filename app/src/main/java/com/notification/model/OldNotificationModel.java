package com.notification.model;

import android.content.Context;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.notification.protocol.NOTICE;
import com.notification.protocol.NotifyGetApi;
import com.notification.protocol.NotifycategoryGetApi;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by HX_CHEN on 2015/12/19.
 */
public class OldNotificationModel extends BaseModel {
    public NOTICE notice;
    private NotifycategoryGetApi mNotifycategoryapi;
    private NotifyGetApi mNotityapi;

    public OldNotificationModel(Context context) {
        super(context);
    }

    /**
     * 获取通知类型
     */
    public void getCategory(HttpApiResponse businessResponse) {

        mNotifycategoryapi = new NotifycategoryGetApi();
        mNotifycategoryapi.httpApiResponse = businessResponse;
        String basePath = mNotifycategoryapi.apiURI;

        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 0, basePath, null), RequestMethod.GET);
        request(0, request, null, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String resultValue = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jo = showSuccesCodeMessage(resultValue);
                    try {
                        if (jo != null) {
                            mNotifycategoryapi.response.fromJson(jo);
                            mNotifycategoryapi.httpApiResponse.OnHttpResponse(mNotifycategoryapi);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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

    /**
     * 获取通知内容
     *
     * @param businessResponse
     * @param community_id     小区ID
     * @param cocategory_id    通知类型ID
     * @param page
     */
    public void getNotification(HttpApiResponse businessResponse, String community_id, String cocategory_id, int page, boolean isfresh) {
        mNotityapi = new NotifyGetApi();
        mNotityapi.request.community_id = community_id;
        mNotityapi.request.category_id = cocategory_id;
        mNotityapi.request.contact = "";
        mNotityapi.request.page = page;
        mNotityapi.request.pagesize = 10;
        mNotityapi.httpApiResponse = businessResponse;
        Map<String, Object> parms = null;
        try {
            parms = Utils.transformJsonToMap(mNotityapi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = mNotityapi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 0, basePath, parms), RequestMethod.GET);
        request(0, request, parms, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String resultValue = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jo = showSuccesCodeMessage(resultValue);
                    if (jo != null) {
                        try {
                            mNotityapi.response.fromJson(jo);
                            mNotityapi.httpApiResponse.OnHttpResponse(mNotityapi);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        }, true, isfresh);
    }
}
