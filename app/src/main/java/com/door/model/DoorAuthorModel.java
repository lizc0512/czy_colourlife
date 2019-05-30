package com.door.model;

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
 * @class name：${PACKAGE_NAME}
 * @class describe
 * @anthor ${USER} QQ:827194927
 * @time ${DATE} ${TIME}
 * @change
 * @chang time
 * @class describe
 */
public class DoorAuthorModel extends BaseModel {
    public DoorAuthorModel(Context context) {
        super(context);
    }


    //获取授权列表
    public void getAuthorList(int what, boolean isShowLoding, final NewHttpResponse newHttpResponse) {
        String basePath = "user/authorize/list";
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, basePath, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String resultValue = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(resultValue);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, resultValue);
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
        }, true, isShowLoding);
    }

    /**
     * 授权（主动授权）
     *
     * @param mobile    目标用户手机号
     * @param bid       小区编号
     * @param usertype  授权类型，1家人，2朋友，3访客，4送货
     * @param granttype 二次授权，0没有，1有
     * @param autype    授权类型，0临时，1限时，2永久
     * @param starttime 授权生效起始时间
     * @param stoptime  授权生效结束时间
     * @param memo      备注，拒绝时为申请备注，批准时为授权备注
     */
    public void setAutor(int what, String mobile, String bid, String usertype,
                         String granttype, String autype, long starttime, long stoptime,
                         String memo, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        params.put("bid", bid);
        params.put("usertype", usertype);
        params.put("granttype", granttype);
        params.put("autype", autype);
        params.put("starttime", starttime);
        params.put("stoptime", stoptime);
        params.put("memo", memo);
        String basePath = "user/authorize/mobile";
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, basePath), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String resultValue = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(resultValue);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, resultValue);
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
     * 取消授权
     *
     * @param aid 授权编号
     */
    public void cancelAutor(int what, String aid, final NewHttpResponse newHttpResponse) {
        String basePath = "user/auth/cancel";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("aid", aid);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, basePath), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String resultValue = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(resultValue);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, resultValue);
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
     * 批复申请（被动授权）
     *
     * @param applyid   申请编号
     * @param bid       小区编号
     * @param approve   批准结果，1批准，2拒绝
     * @param usertype  授权类型，1家人，2朋友，3访客，4送货
     * @param autype    授权类型，0临时，1限时，2永久
     * @param granttype 二次授权，0没有，1有
     * @param starttime 授权生效起始时间
     * @param stoptime  授权生效结束时间
     * @param memo      备注，拒绝时为申请备注，批准时为授权备注
     */
    public void approveNew(int what, String applyid, String bid, String approve,
                           String usertype, String autype, String granttype, long starttime,
                           long stoptime, String memo, final NewHttpResponse newHttpResponse) {
        String basePath = "user/apply/approve";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("applyid", applyid);
        params.put("approve", approve);
        params.put("bid", bid);
        params.put("usertype", usertype);
        params.put("granttype", granttype);
        params.put("autype", autype);
        params.put("starttime", starttime);
        params.put("stoptime", stoptime);
        params.put("memo", memo);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, basePath), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String resultValue = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(resultValue);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, resultValue);
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
     * 再次授权（主动授权）
     *
     * @param toid      目标用户编号
     * @param bid       小区编号
     * @param autype    授权类型，0临时，1限时，2永久
     * @param usertype  授权类型，1家人，2朋友，3访客，4送货
     * @param granttype 二次授权，0没有，1有
     * @param starttime 授权生效起始时间
     * @param stoptime  授权生效结束时间
     * @param memo      备注，拒绝时为申请备注，批准时为授权备注
     */
    public void reAuthorize(int what, String toid, String bid, String usertype,
                            String granttype, String autype, long starttime, long stoptime,
                            String memo, final NewHttpResponse newHttpResponse) {
        String basePath = "user/auth/authorize";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("toid", toid);
        params.put("bid", bid);
        params.put("autype", autype);
        params.put("granttype", granttype);
        params.put("starttime", starttime);
        params.put("stoptime", stoptime);
        params.put("usertype", usertype);
        params.put("memo", memo);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, basePath), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String resultValue = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(resultValue);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, resultValue);
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
