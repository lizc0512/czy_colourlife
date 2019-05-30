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

public class IMCommunityModel extends BaseModel {
    private Context mContext;
    private final String sameCommunityURL = "/api/im/getUserByCommunity";
    private final String nearPeopleURL = "/api/im/getAroundPeople";
    private final String permitPositionURL = "/api/im/setPermitPosition";
    private final String addCommunityURL = "/api/im/addImCommunity";
    private final String cancelApplyCommunityURL = "/api/im/cancelApplyCommunity";
    private final String singleImCommunityURL = "/api/im/getImCommunity";
    private final String userCommunityURL = "/api/im/getUserCommunity";
    private final String repeatCommitApplyURL = "/api/im/repeatCommitApply";

    public IMCommunityModel(Context context) {
        super(context);
        this.mContext = context;
    }

    /***同小区的人**/
    public void getSameCommunityPeople(int what, int page, boolean isLoading, final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 4, sameCommunityURL, map), RequestMethod.GET);
        request(what, request, map, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, isLoading);
    }

    /***获取附近的人列表**/
    public void getNearPeople(int what, int page, String longitude, String latitude, boolean isLoading, final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 4, nearPeopleURL, map), RequestMethod.GET);
        request.setConnectTimeout(25000);
        request.setReadTimeout(25000);
        request(what, request, map, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, isLoading);
    }

    /***附近的人是否可见**/
    public void setPermitPosition(int what,  String permit, final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        map.put("permit", permit);
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 7, permitPositionURL), RequestMethod.POST);
        request(what, request, map, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, false);
    }

    /***创建社群或修改社群**/
    public void createOrModifyCommunity(int what, String group_name, String total_num, String area, String owner, String mobile, final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        map.put("group_name", group_name);
        map.put("total_num", total_num);
        map.put("area", area);
        map.put("owner", owner);
        map.put("mobile", mobile);
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 7, addCommunityURL), RequestMethod.POST);
        request(what, request, map, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, true);
    }

    /***取消申请社群**/
    public void cancelApplyCommunity(int what, String id, final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 7, cancelApplyCommunityURL), RequestMethod.POST);
        request(what, request, map, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, true);
    }

    /***获取单个社群的信息**/
    public void getSingleCommunityInfor(int what, String id, final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 4, singleImCommunityURL, map), RequestMethod.GET);
        request(what, request, map, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, true);
    }

    /***获取用户的社群消息**/
    public void getUserCommunityInfor(int what,boolean isLoading, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 4, userCommunityURL, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, isLoading);
    }

    /***再次提交社群资料和关联IM的id**/
    public void repeatCommitApply(int what, String id, String type, String imId, String group_name, String total_num, String area,
                                  String owner, String mobile, final boolean isLoading, final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("group_name", group_name);
        map.put("total_num", total_num);
        map.put("area", area);
        map.put("owner", owner);
        map.put("mobile", mobile);
        map.put("type", type);
        map.put("im_id", imId);
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 7, repeatCommitApplyURL), RequestMethod.POST);
        request(what, request, map, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = 0;
                    if (isLoading) {
                        code = showSuccesResultMessage(result);
                    } else {
                        code = showSuccesResultMessageTheme(result);
                    }
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        if (isLoading) {
                            showErrorCodeMessage(responseCode, response);
                        }

                    }
                } else {
                    if (isLoading) {
                        showErrorCodeMessage(responseCode, response);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (isLoading) {
                    showExceptionMessage(what, response);
                }
            }
        }, true, isLoading);
    }
}
