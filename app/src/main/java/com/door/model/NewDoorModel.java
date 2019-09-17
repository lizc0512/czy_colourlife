package com.door.model;

import android.content.Context;

import com.BeeFramework.Utils.ToastUtil;
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

import static com.user.UserAppConst.COLOUR_BLUETOOTH_ADVISE;

/**
 * Created by chengyun on 2016/6/23.
 */
public class NewDoorModel extends BaseModel {
    private final String doorCommunityAllUrl = "user/door/communityAll";
    private final String doorAuthorityUrl = "user/door/authority";
    private final String singleCommunitydoorUrl = "user/door/community";
    private final String haveDoorRightUrl = "/user/authorization/granted";
    private final String changedoorPositionUrl = "user/doorFixed/position";
    public final String openDoorUrl = "user/door/open";
    public final String getDevicePwdUrl = "app/door/devicePassword";
    public final String getCommunityKeyUrl = "app/door/getCommunityKey";
    public final String devicePasswordLogUrl = "app/door/devicePasswordLog";
    public final String deviceOpenAdvertUrl = "app/door/openAdvert";

    public NewDoorModel(Context context) {
        super(context);
    }

    /**
     * 开门
     *
     * @param what
     * @param qrcode
     * @param isloading
     * @param newHttpResponse
     */
    public void openDoor(int what, String qrcode, boolean isloading, final NewHttpResponse newHttpResponse) {
        String communityUUid = shared.getString(UserAppConst.Colour_login_community_uuid, "");
        Map<String, Object> map = new HashMap<>();
        map.put("community_uuid", communityUUid);
        map.put("qrcode", qrcode);
        map.put("native_type", "1");
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 6, openDoorUrl), RequestMethod.POST);
        request.setConnectTimeout(25000);
        request.setReadTimeout(25000);
        request(what, request, RequestEncryptionUtils.getNewSaftyMap(mContext, map), new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    newHttpResponse.OnHttpResponse(what, result);
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {
                    showErrorCodeMessage(responseCode, response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
            }
        }, true, isloading);
    }


    /**
     * 获取是否有授权权限
     *
     * @param newHttpResponse
     * @param isloading
     * @param what
     */
    public void getHaveDoorRight(int what, boolean isloading, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 3, haveDoorRightUrl, null), RequestMethod.GET);
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

    /**
     * 常用门禁顺序调整
     *
     * @param what
     * @param params
     * @param isloading
     * @param newHttpResponse
     */
    public void changeDoorPositonFixlist(int what, String params, boolean isloading, final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        map.put("params", params);
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 6, changedoorPositionUrl), RequestMethod.POST);
        request(what, request, RequestEncryptionUtils.getNewSaftyMap(mContext, map), new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {
                    showErrorCodeMessage(responseCode, response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
            }
        }, true, isloading);
    }

    /**
     * 门禁列表（按小区返回）
     *
     * @param what
     * @param isloading
     * @param newHttpResponse
     */
    public void getDoorCommunityList(int what, final boolean isloading, final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 3, doorCommunityAllUrl, map), RequestMethod.GET);
        request(what, request, RequestEncryptionUtils.getNewSaftyMap(mContext, map), new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (isloading == true) {
                    ToastUtil.toastShow(mContext, "获取常用门禁失败，请重试");
                }
            }
        }, true, isloading);
    }

    /**
     * 单个小区常用非常用门禁
     *
     * @param what
     * @param isloading
     * @param newHttpResponse
     */
    public void getSingleCommunityList(int what, String community_uuid, final boolean isloading, final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        map.put("community_uuid", community_uuid);
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 3, singleCommunitydoorUrl, map), RequestMethod.GET);
        request(what, request, RequestEncryptionUtils.getNewSaftyMap(mContext, map), new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = 0;
                    if (isloading) {
                        code = showSuccesResultMessage(result);
                    } else {
                        code = showSuccesResultMessageTheme(result);
                    }
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                        editor.putString(UserAppConst.COLOR_HOME_USEDOOR, result).apply();
                    } else {
                        if (!isloading) {
                            newHttpResponse.OnHttpResponse(what, "");
                        }
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (isloading) {
                    ToastUtil.toastShow(mContext, "获取常用门禁失败，请重试");
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                }

            }
        }, true, isloading);
    }

    /**
     * 用户是否有开门权限
     *
     * @param what
     * @param isloading
     * @param newHttpResponse
     */
    public void getDoorRight(int what, final boolean isloading, final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 3, doorAuthorityUrl, map), RequestMethod.GET);
        request(what, request, RequestEncryptionUtils.getNewSaftyMap(mContext, map), new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "1");
            }
        }, true, isloading);
    }

    public void openQuickDoor(int what, String qrcode, String communityuuid, final NewHttpResponse newHttpResponse) {
        Map<String, Object> map = new HashMap<>();
        map.put("community_uuid", communityuuid);
        map.put("qrcode", qrcode);
        map.put("native_type", "1");
        map.put("open_type", "8");
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 6, openDoorUrl), RequestMethod.POST);
        request.setConnectTimeout(25000);
        request.setReadTimeout(25000);
        request(what, request, RequestEncryptionUtils.getNewSaftyMap(mContext, map), new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

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
     * 获取蓝牙开门的广告图
     */
    public void getDeviceAdvertData() {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 11, deviceOpenAdvertUrl, null), RequestMethod.GET);
        request(1111, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
                        editor.putString(COLOUR_BLUETOOTH_ADVISE, result).apply();
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, false);
    }

    public void getDevicePwd(int what, String device_id, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("device_id", device_id);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 11, getDevicePwdUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        showErrorCodeMessage(responseCode, response);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, true);
    }


    /**
     * 获取用户门禁信息
     */
    public void getCommunityKey(int what, boolean isLoading, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 11, getCommunityKeyUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
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
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, isLoading);
    }

    /**
     * 获取设备获取密码记录
     */
    public void devicePasswordLog(int what, String device_id, int page, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("device_id", device_id);
        params.put("page", page);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 11, devicePasswordLogUrl, params), RequestMethod.GET);
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
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, true);
    }

}
