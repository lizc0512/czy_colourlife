package com.invite.model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.UserAppConst;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * 邀请好友
 * hxg 2019/05/27
 */
public class NewInviteModel extends BaseModel {
    private final String distributionUrl = "distribution/user/info";
    private final String inviteQuantityUrl = "distribution/invite/quantity";
    private final String myInviteRecordUrl = "distribution/invite/record";
    private final String inviteDetailUrl = "distribution/income/record";
    private final String inviteProfit = "distribution/invite/incomeRecord";

    public NewInviteModel(Context context) {
        super(context);
    }

    /**
     * 邀请页面数据
     */
    public void shareFriend(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, distributionUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                        editor.putString(UserAppConst.INVITE_FRIEND, result).commit();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
            }
        }, true, false);
    }

    /**
     * 邀请页面数据
     */
    public void getInviteNum(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, inviteQuantityUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                        editor.putString(UserAppConst.INVITE_INVITE, result).commit();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
            }
        }, true, false);
    }

    /**
     * 我的邀请
     */
    public void myInvite(int what, int level, int page, int page_size, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("level", level);
        params.put("page", page);
        params.put("page_size", page_size);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, myInviteRecordUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
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
        }, true, false);
    }

    /**
     * 累计邀请
     */
    public void allProfit(int what, int state, int page, int page_size, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("state", state);
        params.put("page", page);
        params.put("page_size", page_size);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, inviteProfit, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
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
        }, true, false);
    }

    /**
     * 收益详情
     */
    public void inviteDetail(int what, int page, int page_size, String month, final NewHttpResponse newHttpResponse, boolean isLoading) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("page_size", page_size);
        params.put("month", month);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, inviteDetailUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
            }
        }, true, isLoading);
    }
}
