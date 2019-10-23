package com.door.model;

import android.content.Context;
import android.text.TextUtils;

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

import static com.user.UserAppConst.COLOUR_COMMUNITYLIST;

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
public class NewDoorAuthorModel extends BaseModel {

    private String identifyListUrl = "app/door/remoteDoor/identityList"; //获取用户身份列表
    private String authorizeMobileUrl = "app/door/remoteDoor/authorizeMobile"; //通过手机号授权门禁权限
    private String authorizeListUrl = "app/door/remoteDoor/authorizeList"; //获取登录用户的授权记录
    private String applyApproveUrl = "app/door/remoteDoor/applyApprove"; //门禁-申请批复
    private String unAuthorizeUrl = "app/door/remoteDoor/unauthorize"; //门禁取消授权
    private String authGrantedUrl = "app/door/remoteDoor/authGranted"; //门禁-是否有授权权限
    private String authCommunityUrl = "app/door/remoteDoor/authCommunity"; //门禁-是否有授权权限
    private String supportDoorUrl = "app/door/remoteDoor/supportDoor"; //门禁-获取小区支持门禁类别
    private String remoteDoorApplyUrl = "app/door/commonApply"; //门禁-获取小区支持门禁类别
    private String bluetoothCommunitySetUrl = "app/door/bluetooth/getCommunitySet"; //门禁-获取小区支持门禁类别
    private String applyListUrl = "app/door/remoteDoor/applyList"; //门禁-获取用户门禁申请记录
    private String doorAuthorizeToidUrl = "app/door/remoteDoor/authorizeToid"; //门禁-远程门禁重新授权
    private String doorExtensionMsgUrl = "app/door/remoteDoor/extensionMsg"; //门禁-门禁权限申请延期用户信息
    private String remoteDoorExtensionValidUrl = "app/door/remoteDoor/extensionValid"; //门禁-门禁权限申请延期操作接口
    private String bluetoothDoorExtensionValidUrl = "app/door/bluetooth/extensionValid"; //门禁-门禁权限申请延期操作接口


    public NewDoorAuthorModel(Context context) {
        super(context);
    }

    public void getHaveDoorRight(int what, boolean isloading, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 11
                        , authGrantedUrl, null), RequestMethod.GET);
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

            }
        }, true, isloading);
    }

    public void getAuthorCommunityList(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 11, authCommunityUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                        editor.putString(COLOUR_COMMUNITYLIST, result).apply();
                    }
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

    //获取用户身份列表
    public void getIdentifyList(int what, boolean isShowLoding, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 11, identifyListUrl, null), RequestMethod.GET);
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

    public void setAuthorizeByMobile(int what, String community_uuid, String bid, String usertype,
                                     String autype, String granttype, long starttime, long stoptime, String mobile, String name,
                                     String memo, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("community_uuid", community_uuid);
        params.put("bid", bid);
        params.put("usertype", usertype);
        params.put("autype", autype);
        params.put("granttype", granttype);
        params.put("starttime", starttime);
        params.put("stoptime", stoptime);
        params.put("mobile", mobile);
        params.put("name", name);
        params.put("memo", memo);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 15, authorizeMobileUrl), RequestMethod.POST);
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

    public void getAuthorizeAndApplyList(int what, boolean isShowLoding, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 11, authorizeListUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String resultValue = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(resultValue);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, resultValue);
                        editor.putString(UserAppConst.COLOUR_DOOR_AUTHOUR_APPLY, resultValue).apply();
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

    public void approveApplyAuthority(int what, String applyid, String bid, String approve,
                                      String memo, String autype, String granttype, long starttime,
                                      long stoptime, String usertype, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("applyid", applyid);
        params.put("approve", approve);//1批准，2拒绝
        params.put("memo", memo);
        params.put("autype", autype);//autype 授权类型，0临时，1限定时间，2永久
        params.put("granttype", granttype);//是否允许二次授权
        params.put("starttime", starttime);
        params.put("stoptime", stoptime);
        params.put("bid", bid);
        params.put("usertype", usertype);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 15, applyApproveUrl), RequestMethod.POST);
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

    public void cancelUserAutor(int what, String aid, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("aid", aid);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 15, unAuthorizeUrl), RequestMethod.POST);
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

    public void getSupportDoorType(int what, String community_uuid, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("community_uuid", community_uuid);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 11, supportDoorUrl, params), RequestMethod.GET);
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


    public void applyRemoteDoor(int what, String community_uuid, String community_name, String build_uuid, String buid_name,
                                String unit_uuid, String unit_name, String room_uuid, String room_name,
                                String identity_id, String auth_name, String auth_mobile, String resident_mobile,
                                String apply_type, String bid, String tg_status, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("community_uuid", community_uuid);
        params.put("community_name", community_name);
        params.put("build_uuid", build_uuid);
        params.put("build_name", buid_name);
        params.put("unit_uuid", unit_uuid);
        params.put("unit_name", unit_name);
        params.put("room_uuid", room_uuid);
        params.put("room_name", room_name);
        params.put("identity_id", identity_id);
        params.put("auth_name", auth_name);
        params.put("auth_mobile", auth_mobile);
        params.put("resident_mobile", resident_mobile);
        params.put("apply_type", apply_type);
        params.put("bid", bid);
        if (TextUtils.isEmpty(tg_status)) {
            params.put("tg_status", 1);
        } else {
            params.put("tg_status", tg_status);
        }
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 15, remoteDoorApplyUrl), RequestMethod.POST);
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

    public void bluetoothDoorVerify(int what, String community_uuid, String unit_uuid, String build_uuid, String room_uuid, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("community_uuid", community_uuid);
        params.put("unit_uuid", unit_uuid);
        params.put("build_uuid", build_uuid);
        params.put("room_uuid", room_uuid);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 11, bluetoothCommunitySetUrl, params), RequestMethod.GET);
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

    public void getDoorExtensionMsg(int what, String community_uuid, String identity_id, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("community_uuid", community_uuid);
        params.put("identity_id", identity_id);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 11, doorExtensionMsgUrl, params), RequestMethod.GET);
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

    public void setRemoteDoorExtensionValid(int what, String community_uuid, String community_name, String identity_id, String bid, String auth_mobile, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("community_uuid", community_uuid);
        params.put("community_name", community_name);
        params.put("identity_id", identity_id);
        params.put("bid", bid);
        params.put("auth_mobile", auth_mobile);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 15, remoteDoorExtensionValidUrl), RequestMethod.POST);
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


    public void setBluetoothDoorExtensionValid(int what, String community_uuid, String community_name, String unit_name, String unit_uuid,
                                               String identity_id, String auth_mobile, String auth_name, String resident_mobile, String tg_status,
                                               final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("community_uuid", community_uuid);
        params.put("community_name", community_name);
        params.put("unit_name", unit_name);
        params.put("unit_uuid", unit_uuid);
        params.put("identity_id", identity_id);
        params.put("auth_mobile", auth_mobile);
        params.put("auth_name", auth_name);
        params.put("resident_mobile", resident_mobile);
        if (!TextUtils.isEmpty(tg_status)) {
            params.put("tg_status", tg_status);
        } else {
            params.put("tg_status", 1);
        }
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 15, bluetoothDoorExtensionValidUrl), RequestMethod.POST);
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

    public void setDoorAgainAuthorize(int what, String autype, String granttype, long starttime, long stoptime,
                                      String bid, String usertype, String toid, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("autype", autype);
        params.put("granttype", granttype);
        params.put("starttime", starttime);
        params.put("stoptime", stoptime);
        params.put("bid", bid);
        params.put("usertype", usertype);
        params.put("toid", toid);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 15, doorAuthorizeToidUrl), RequestMethod.POST);
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

    public void getUserApplyList(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 11, applyListUrl, null), RequestMethod.GET);
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
        }, true, true);
    }
}
