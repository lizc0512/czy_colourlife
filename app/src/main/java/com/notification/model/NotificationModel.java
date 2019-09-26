package com.notification.model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.update.activity.UpdateVerSion;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HX_CHEN on 2015/12/19.
 */
public class NotificationModel extends BaseModel {
    private final String msglist = "app/home/msg/getMsgList";
    private final String msgdetail = "app/home/msg/getBannerTxtMsgDetail";
    private final String msgmore = "app/home/msg/getMsgDetailList";
    private final String msgeye = "app/home/msg/setShowType";

    public NotificationModel(Context context) {
        super(context);
    }


    /**
     * @param what
     * @param isloding
     * @param newHttpResponse
     *
     * 获取消息列表
     */
    public void getMsgList(int what, Boolean isloding, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 1, msglist, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String reulst = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(reulst);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what,reulst);
                    } else {
                        newHttpResponse.OnHttpResponse(what,"");
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
        }, true, isloding);
    }

    /**
     * @param what
     * @param isloding
     * @param newHttpResponse
     * 获取Banner文本消息详情
     */
    public void getMsgDetail(int what, String msg_id, Boolean isloding, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("msg_id", msg_id);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 1, msgdetail, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String reulst = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(reulst);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what,reulst);
                    } else {

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
        }, true, isloding);
    }
    /**
     * @param what
     * @param app_id
     * @param page
     * @param isloding
     * @param newHttpResponse
     *
     * 获取应用更多列表
     */
    public void getMsgMoreList(int what,String app_id,int page, Boolean isloding, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("app_id", app_id);
        params.put("page", page);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 1, msgmore, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String reulst = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(reulst);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what,reulst);
                    } else {
                        newHttpResponse.OnHttpResponse(what,"");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    showErrorCodeMessage(responseCode, response);
                    newHttpResponse.OnHttpResponse(what,"");
                } else {
                    showErrorCodeMessage(responseCode, response);
                    newHttpResponse.OnHttpResponse(what,"");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
                newHttpResponse.OnHttpResponse(what,"");
            }
        }, true, isloding);
    }
    /**
     * @param what
     * @param msg_id
     * @param show_type
     * @param isloding
     * @param newHttpResponse
     *
     * 设置文本消息眼睛睁开关闭接口
     */
    public void getShowEye(int what,String msg_id,int show_type, Boolean isloding, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("msg_id", msg_id);
        params.put("show_type", show_type);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 3, msgeye), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String reulst = response.get(); //{"code":1000,"message":"\u8bf7\u6c42\u53c2\u6570\u7f3a\u5931\u6216\u4e0d\u5408\u6cd5"}
                if (responseCode == RequestEncryptionUtils.responseSuccess) {

                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {

                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, isloding);
    }
}
