package com.user.model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.BeeFramework.Utils.EncryptUtil;
import com.BeeFramework.Utils.PasswordRSAUtils;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.door.model.NewDoorModel;
import com.external.eventbus.EventBus;
import com.nohttp.utils.CallServer;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.UserAppConst;
import com.user.Utils.TokenUtils;
import com.user.entity.UserInformationEntity;
import com.user.protocol.AuthTokenApi;
import com.user.protocol.AuthTokenRequest;
import com.user.protocol.AuthTokenResponse;
import com.yanzhenjie.nohttp.BasicBinary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.net.cyberway.utils.CityCustomConst;

import static com.user.UserMessageConstant.SQUEEZE_OUT;

/**
 * @name ${yuansk}
 * @class name：com.user.model
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/2/26 11:29
 * @change
 * @chang time
 * @class describe  4.1.0 与用户改造相关的model
 */

public class NewUserModel extends BaseModel {
    private String checkRegisterUrl = "user/checkRegister";
    private String checkWhiteUrl = "user/checkWhite";
    private String sendCodeUrl = "sms/sendCode";
    private String checkCodeUrl = "sms/checkCode";
    private String registerUrl = "user/register";
    private String informationUrl = "user/information";
    private String resetPasswordUrl = "user/resetPassword";
    private String changePasswordUrl = "user/changePassword";
    private String portraitUrl = "user/portrait";
    private String changeInfomationUrl = "user/changeInformation";
    private String qrCodeUrl = "user/qrCode";
    private String isSetGestureUrl = "user/isSetGesture";
    private String setGestureUrl = "user/setGesture";
    private String verifyGestureUrl = "user/verifyGesture";
    private String switchGestureUrl = "user/switchGesture";
    private String thirdRegisterUrl = "user/thirdRegister";
    private String verifyPasswordUrl = "user/verifyPassword";
    private String getResetPasswordUrl = "user/setPassword";
    private String authRegisterUrl = "user/checkAuthRegister";
    private String thridIsNewUrl = "user//thirdAuth";
    private String unbindThirdAuthUrl = "user/unbindThirdAuth";
    private String bindThirdAuthUrl = "user/bindThirdAuth";
    private String inviteUrl = "user/invite";
    private String inviteRecordUrl = "user/inviteRecord";
    private String inviteCodeUrl = "user/inviteCode";
    private String changMobileUrl = "app/home/changmobile";
    private String getIntegralUrl = "integral/user/getIntegral";
    private String getTaskListUrl = "integral/user/getTaskList";
    private String getBeanDetailUrl = "integral/user/flowDetail";
    private String setIntegralUrl = "integral/user/setIntegral";
    private String changeBeanUrl = "integral/user/exchangeFp";
    private String signInUrl = "integral/user/setSignIn";
    private String getRealTokenUrl = "user/bizToken";
    private String getIsRealUrl = "user/checkIdentity";
    private String submitRealUrl = "user/identity";
    private String isNewUrl = "user/isNew";
    private String getDoorUrl = "app/door/getToken";
    private String regetDoorUrl = "app/door/refreshToken";
    private String getAuthList = "app/auth/application/list";
    private String getAuthDetail = "app/auth/application/detail";
    private String unbindAuth = "app/auth/application/removal";
    private String oneKeyLoginUrl = "user/onekey/login";
    private String openRecordUrl = "app/door/openRocord";
    private String finishTask = "user/finishTask";


    public NewUserModel(Context context) {
        super(context);
    }

    /**
     * 检查当前的手机号是否注册
     *
     * @param
     */
    public void getCheckRegister(int what, String mobile, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, checkRegisterUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    newHttpResponse.OnHttpResponse(what, "");
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

    /****检查用户是不是白名单**/
    public void getCheckWhite(int what, String mobile, int is_register, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        params.put("is_register", is_register);
        params.put("device_uuid", TokenUtils.getUUID(mContext));
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, checkWhiteUrl, params), RequestMethod.GET);
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
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    newHttpResponse.OnHttpResponse(what, "");
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

    /****获取短信或语音验证码**/
    public void getSmsCode(int what, String mobile, int work_type, int sms_type, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        params.put("work_type", work_type);
        params.put("sms_type", sms_type);
        params.put("device_uuid", TokenUtils.getUUID(mContext));
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, sendCodeUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    newHttpResponse.OnHttpResponse(what, "");
                    showErrorCodeMessage(responseCode, response);
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                    showErrorCodeMessage(responseCode, response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
                showExceptionMessage(what, response);
            }
        }, true, true);
    }

    /****短信验证码校验**/
    public void checkSMSCode(int what, String mobile, String code, String work_type, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        params.put("sms_token", code);
        params.put("work_type", work_type);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, checkCodeUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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


    /****用户注册的****/
    public void userRegister(int what, String mobile, String token, String password, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        params.put("sms_token", token);
        params.put("password", PasswordRSAUtils.encryptByPublicKey(password));
        params.put("device_uuid", TokenUtils.getUUID(mContext));
        final Request<String> request_oauthRegister = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, registerUrl), RequestMethod.POST);
        request(what, request_oauthRegister, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    newHttpResponse.OnHttpResponse(what, result);
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    ToastUtil.toastShow(mContext, "注册失败");
                    newHttpResponse.OnHttpResponse(what, "");
                } else {
                    ToastUtil.toastShow(mContext, "注册失败");
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, true);
    }


    /**
     * 获取的Oauth2.0的Access_token
     * 保存用户登录的手机号,密码
     *
     * @param
     */

    public void getAuthToken(int what, String username, String password, final String type, boolean isLoading, final NewHttpResponse newHttpResponse) {
        final AuthTokenApi authTokenApi = new AuthTokenApi();
        AuthTokenRequest authTokenRequest = authTokenApi.request;
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            authTokenRequest.grant_type = "password";
            if ("1".equals(type) || "2".equals(type)) {
                authTokenRequest.password = RequestEncryptionUtils.setMD5(password);
            } else {
                authTokenRequest.password = password;
            }
            authTokenRequest.client_id = "2";
            authTokenRequest.client_secret = "oy4x7fSh5RI4BNc78UoV4fN08eO5C4pj0daM0B8M";
            authTokenRequest.type = type;
            authTokenRequest.username = username;
            params = Utils.transformJsonToMap(authTokenRequest.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = "/oauth/token";
        final Request<String> request_oauthRegister = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 1, basePath), RequestMethod.POST);
        request(what, request_oauthRegister, params, new HttpListener<String>() {
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
                                editor.putString(UserAppConst.Colour_token_type, authTokenResponse.token_type);
                                editor.putLong(UserAppConst.Colour_get_time, System.currentTimeMillis());
                                editor.commit();
                                newHttpResponse.OnHttpResponse(what, result);
                            } else {
                                editor.putBoolean(UserAppConst.IS_LOGIN, false);
                                editor.commit();
                                newHttpResponse.OnHttpResponse(what, "");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            editor.putBoolean(UserAppConst.IS_LOGIN, false);
                            editor.commit();
                            newHttpResponse.OnHttpResponse(what, "");
                        }
                    } else {
                        editor.putBoolean(UserAppConst.IS_LOGIN, false);
                        editor.commit();
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    editor.putBoolean(UserAppConst.IS_LOGIN, false);
                    editor.commit();
                    showErrorCodeMessage(responseCode, response);
                    newHttpResponse.OnHttpResponse(what, "");
                } else {
                    editor.putBoolean(UserAppConst.IS_LOGIN, false);
                    editor.commit();
                    showErrorCodeMessage(responseCode, response);
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                editor.putBoolean(UserAppConst.IS_LOGIN, false);
                editor.commit();
                showExceptionMessage(what, response);
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, isLoading);
    }


    /********通过refresh_token 刷新获取access_token****/
    public void refreshAuthToken(int what, NewHttpResponse newHttpResponse) {
        final AuthTokenApi authTokenApi = new AuthTokenApi();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", shared.getString(UserAppConst.Colour_refresh_token, ""));
        params.put("client_id", "2");
        params.put("client_secret", "oy4x7fSh5RI4BNc78UoV4fN08eO5C4pj0daM0B8M");
        String basePath = "/oauth/token";
        final Request<String> request_oauthRegister = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 1, basePath), RequestMethod.POST);
        request(what, request_oauthRegister, params, new HttpListener<String>() {
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
                                editor.putString(UserAppConst.Colour_token_type, authTokenResponse.token_type);
                                editor.putLong(UserAppConst.Colour_get_time, System.currentTimeMillis());
                                editor.commit();
                                newHttpResponse.OnHttpResponse(what, result);
                            } else {
                                newHttpResponse.OnHttpResponse(what, "");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            newHttpResponse.OnHttpResponse(what, "");
                        }
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


    private void tokenInvaildLoginOut(String result) {
        editor.putBoolean(UserAppConst.IS_LOGIN, false);
        editor.apply();
        Message msg = android.os.Message.obtain();
        msg.what = SQUEEZE_OUT;
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (!jsonObject.isNull("message")) {
                msg.obj = jsonObject.optString("message");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EventBus.getDefault().post(msg);

    }


    /**
     * refresh_token去获取access_token
     * 然后重新请求原来的接口数据
     *
     * @param
     */
    public <T> void refreshAuthToken(final boolean isLoading) {
        final AuthTokenApi authTokenApi = new AuthTokenApi();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", shared.getString(UserAppConst.Colour_refresh_token, ""));
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
                                editor.putString(UserAppConst.Colour_token_type, authTokenResponse.token_type);
                                editor.putLong(UserAppConst.Colour_get_time, System.currentTimeMillis());
                                editor.commit();
                                CallServer callServer = CallServer.getInstance();
                                List<Integer> requestWhatList = callServer.getRequestWhat();
                                int size = requestWhatList.size();
                                for (int j = 0; j < size; j++) {
                                    int k = j;
                                    int requestWhat = requestWhatList.get(k);
                                    Request<T> requestAgain = callServer.getRequestList().get(k);
                                    Map<String, Object> requestParamsMap = callServer.getRequestMap().get(k);
                                    HttpListener<T> requestCallback = callServer.getRequestLister().get(k);
                                    boolean requestCanCancel = callServer.getRequestCancel().get(k);
                                    boolean requestLoading = callServer.getRequestLoading().get(k);
                                    if (k >= 4) {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                request(requestWhat, requestAgain, requestParamsMap,
                                                        requestCallback, requestCanCancel,
                                                        requestLoading);
                                                if (k == size - 1) {
                                                    callServer.clearAllQuest();
                                                }
                                            }
                                        }, 2000 * (k - 3));
                                    } else {
                                        request(requestWhat, requestAgain, requestParamsMap,
                                                requestCallback, requestCanCancel,
                                                requestLoading);
                                        if (k == size - 1) {
                                            callServer.clearAllQuest();
                                        }
                                    }
                                }
                            } else {
                                tokenInvaildLoginOut(result);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            tokenInvaildLoginOut(result);
                        } catch (Exception e) {

                        }
                    } else {
                        tokenInvaildLoginOut(result);
                    }
                } else {
                    tokenInvaildLoginOut(result);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                tokenInvaildLoginOut("");
            }
        }, true, isLoading);
    }


    /****获取用户的信息**/
    public void getUserInformation(int what, boolean isLoading, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("device_uuid", TokenUtils.getUUID(mContext));
        params.put("longitude", shared.getString(CityCustomConst.LOCATION_LONGITUDE, ""));
        params.put("latitude", shared.getString(CityCustomConst.LOCATION_LATITUDE, ""));
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, informationUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        try {
                            UserInformationEntity userInformationEntity = GsonUtils.gsonToBean(result, UserInformationEntity.class);
                            UserInformationEntity.ContentBean contentBean = userInformationEntity.getContent();
                            editor.putString(UserAppConst.Colour_login_community_uuid, contentBean.getCommunity_uuid());
                            editor.putString(UserAppConst.Colour_login_community_name, contentBean.getCommunity_name());
                            editor.putString(UserAppConst.Colour_login_mobile, String.valueOf(contentBean.getMobile()));
                            editor.putString(UserAppConst.Colour_GENDER, String.valueOf(contentBean.getGender()));
                            editor.putString(UserAppConst.Colour_NAME, contentBean.getName());
                            editor.putString(UserAppConst.COLOUR_EMAIL, contentBean.getEmail());
                            editor.putString(UserAppConst.Colour_NIACKNAME, contentBean.getNick_name());
                            editor.putString(UserAppConst.Colour_head_img, contentBean.getPortrait_url());
                            editor.putString(UserAppConst.Colour_User_uuid, contentBean.getUuid());
                            editor.putInt(UserAppConst.Colour_User_id, contentBean.getId());
                            editor.putString(UserAppConst.Colour_Build_name, contentBean.getBuild_name());
                            editor.putString(UserAppConst.Colour_Unit_name, contentBean.getUnit_name());
                            editor.putString(UserAppConst.Colour_Room_name, contentBean.getRoom_name());
                            editor.putString(UserAppConst.Colour_Real_name, contentBean.getReal_name());
                            editor.putString(UserAppConst.Colour_Permit_position, contentBean.getPermit_position());
                            editor.putString(UserAppConst.Colour_set_password, contentBean.getPassword());
                            editor.putString(UserAppConst.Colour_authentication, contentBean.getAuthentication());//是否认证房产 1：是，2：否
                            editor.apply();
                            newHttpResponse.OnHttpResponse(what, result);
                        } catch (Exception e) {
                            newHttpResponse.OnHttpResponse(what, "");
                        }
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    if (isLoading) {
                        showErrorCodeMessage(responseCode, response);
                    }
                    newHttpResponse.OnHttpResponse(what, "");
                } else {
                    if (isLoading) {
                        showErrorCodeMessage(responseCode, response);
                    }
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (isLoading) {
                    showExceptionMessage(what, response);
                }
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, isLoading);
        NewDoorModel newDoorModel = new NewDoorModel(mContext);
        newDoorModel.getDeviceAdvertData();
    }

    /****重置用户的密码**/
    public void resetUserPassword(int what, String mobile, String token, String password, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        params.put("sms_token", token);
        String decrptPassword = PasswordRSAUtils.encryptByPublicKey(password);
        params.put("password", decrptPassword);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, resetPasswordUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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

    /****修改用户的登录密码**/
    public void changeUserPassword(int what, String password_new, String password, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("password", PasswordRSAUtils.encryptByPublicKey(password));
        params.put("password_new", PasswordRSAUtils.encryptByPublicKey(password_new));
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, changePasswordUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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

    /****上传用户的头像**/
    public void uploadPortrait(int what, String filePath, final boolean isShow, final NewHttpResponse newHttpResponse) {
        BasicBinary binary = new FileBinary(new File(filePath));
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, portraitUrl), RequestMethod.POST);
        request.add("img", binary);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    if (isShow) {
                        showSuccesResultMessage(result);
                    }
                    newHttpResponse.OnHttpResponse(what, result);
                } else {
                    if (isShow) {
                        showErrorCodeMessage(responseCode, response);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (isShow) {
                    showExceptionMessage(what, response);
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }
        }, true, true);
    }

    /****修改用户的个人信息**/
    public void changeUserInfomation(int what, String name, String nick_name, String email, int gender, String come_from, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        params.put("email", email);
        params.put("nick_name", nick_name);
        params.put("gender", gender);
        params.put("come_from", come_from);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, changeInfomationUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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


    /****获取用户的二维码**/
    public void getQrCode(int what, boolean isLoading, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, qrCodeUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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
        }, true, isLoading);
    }

    /****用户是否设置手势密码**/
    public void isSetGesture(int what, String mobile, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, isSetGestureUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    newHttpResponse.OnHttpResponse(what, "");
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

    /****设置手势密码**/
    public void setGesturePassword(int what, String gesture_password, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("gesture_password", PasswordRSAUtils.encryptByPublicKey(gesture_password));
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, setGestureUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    showErrorCodeMessage(responseCode, response);
                    newHttpResponse.OnHttpResponse(what, "");
                } else {
                    showErrorCodeMessage(responseCode, response);
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, true);
    }

    /****手势密码的验证接口**/
    public void verifyGesture(int what, String gesture_password, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("gesture_password", PasswordRSAUtils.encryptByPublicKey(gesture_password));
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, verifyGestureUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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

    /****开启/关闭手势密码接口**/
    public void switchGesture(int what, int switchOffOn, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("switch_on", switchOffOn);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, switchGestureUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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

    /****第三方注册接口**/
    public void thirdRegister(int what, String mobile, String token, String source, String open_code, String portrait, String gender, String nickName, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        params.put("sms_token", token);
        params.put("source", source);
        params.put("open_code", open_code);
        params.put("portrait", portrait);
        params.put("device_uuid", TokenUtils.getUUID(mContext));
        params.put("name", nickName);
        params.put("nick_name", nickName);
        params.put("gender", gender);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, thirdRegisterUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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


    /****忘记手势密码校验密码接口**/
    public void verifyPassword(int what, String password, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("password", PasswordRSAUtils.encryptByPublicKey(password));
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, verifyPasswordUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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

    /***设置登录密码***/
    public void setLoginPassword(int what, String password, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("password", PasswordRSAUtils.encryptByPublicKey(password));
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, getResetPasswordUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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

    /****后期用来判断第三方是否注册彩之云**/
    public void authRegister(int what, String source, String open_code, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source", source);
        params.put("open_code", open_code);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, authRegisterUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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


    /****是否绑定微信/QQ **/
    public void getThridBindStatus(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, thridIsNewUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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

    /****绑定第三方登录**/
    public void bindThridSource(int what, String source, String open_code, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source", source);
        params.put("open_code", open_code);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, bindThirdAuthUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    showErrorCodeMessage(responseCode, response);
                    newHttpResponse.OnHttpResponse(what, "");
                } else {
                    showErrorCodeMessage(responseCode, response);
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, true);
    }


    /****解绑第三方登录**/
    public void unbindThridSource(int what, String source, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source", source);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, unbindThirdAuthUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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
        }, true, true);
    }


    /****邀请注册接口**/
    public void inviteRegister(int what, String mobile, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 6, inviteUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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

    /****邀请记录**/
    public void inviteRecord(int what, int invite_state, int page, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("invite_state", invite_state);
        params.put("page_size", 10);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, inviteRecordUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    newHttpResponse.OnHttpResponse(what, "");
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

    /****自己的邀请码**/
    public void inviteCode(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, inviteCodeUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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
        }, true, false);
    }

    /**
     * 获取入口
     *
     * @param
     */
    public void getChangeMobileEnter(int what, String mobile, int referer, final boolean isShow, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        params.put("referer", referer);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 1, changMobileUrl, params), RequestMethod.GET);
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
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    newHttpResponse.OnHttpResponse(what, "");
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, isShow);
    }

    public void getDingTalk(int what, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ts", System.currentTimeMillis() / 1000);
        params.put("token", "");
        params.put("version", "1.0");
        Map<String, Object> deviceParams = new HashMap<String, Object>();
        deviceParams.put("deviceId", TokenUtils.getUUID(mContext));
        params.put("content", EncryptUtil.aesEncrypt(GsonUtils.gsonString(deviceParams), "123434"));
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 1, changMobileUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    newHttpResponse.OnHttpResponse(what, result);
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
     * 获取用户彩豆积分信息
     */
    public void getBeanMsg(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 10, getIntegralUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
                        editor.putString(UserAppConst.BEAN_NUMBER, result);
                        editor.commit();
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
                showExceptionMessage(what, response);
            }
        }, true, false);
    }

    /**
     * 获取用户彩豆任务列表信息
     */
    public void getDutyList(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 10, getTaskListUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
                        editor.putString(UserAppConst.BEAN_DUTY, result);
                        editor.commit();
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
                showExceptionMessage(what, response);
            }
        }, true, false);
    }

    /**
     * 获取用户彩豆详情列表信息
     */
    public void getBeanDetailList(int what, int page, String month, boolean isLoading, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("month", month);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 10, getBeanDetailUrl, params), RequestMethod.GET);
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
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
            }
        }, true, isLoading);
    }

    /**
     * 发送完成消息类型任务获得彩豆
     */
    public void sendMsgColourBean(int what, String flag, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("flag", flag);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 10, setIntegralUrl, params), RequestMethod.POST);
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
        }, true, false);
    }

    /**
     * 兑换饭票
     */
    public void changeBean(int what, String money, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("money", money);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 10, changeBeanUrl, params), RequestMethod.POST);
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
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
            }
        }, true, true);
    }

    /**
     * 签到
     */
    public void beanSignIn(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 10, signInUrl, null), RequestMethod.POST);
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
                showExceptionMessage(what, response);
            }
        }, true, true);
    }

    /**
     * 获取实名认证BizToken
     */
    public void getRealNameToken(int what, final NewHttpResponse newHttpResponse, boolean loading) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, getRealTokenUrl, null), RequestMethod.GET);
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
        }, true, loading);
    }

    /**
     * 获取是否实名认证
     */
    public void getIsRealName(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, getIsRealUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, false);
    }

    /**
     * 提交实名认证
     */
    public void submitRealName(int what, String identity_val, String identity_name, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("identity_val", identity_val);
        params.put("identity_name", identity_name);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, submitRealUrl, params), RequestMethod.POST);
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
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
            }
        }, true, true);
    }

    /**
     * 是否新用户
     */
    public void isNew(int what, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, isNewUrl, params), RequestMethod.GET);
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
        }, true, false);
    }

    /**
     * 注册、获取乐开钥匙
     */
    public void getLekaiDoor(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 11, getDoorUrl, null), RequestMethod.GET);
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
        }, true, false);
    }

    /**
     * 刷新乐开token
     */
    public void regetLekaiDoor(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 11, regetDoorUrl, null), RequestMethod.GET);
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
        }, true, false);
    }

    public void oneKeyLoginByBlue(int what, String flash_data, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", 2);
        params.put("client_secret", "oy4x7fSh5RI4BNc78UoV4fN08eO5C4pj0daM0B8M");
        params.put("grant_type", "password");
        params.put("flash_data", flash_data);
        params.put("password", "1");
        params.put("username", "1");
        params.put("type", 7);
        String basePath = "/oauth/token";
        final Request<String> request_oauthRegister = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 1, basePath), RequestMethod.POST);
        request(what, request_oauthRegister, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        AuthTokenResponse authTokenResponse = new AuthTokenResponse();
                        try {
                            authTokenResponse.fromJson(jsonObject);
                            if (!TextUtils.isEmpty(authTokenResponse.access_token)) {
                                editor.putBoolean(UserAppConst.IS_LOGIN, true);
                                editor.putString(UserAppConst.Colour_access_token, authTokenResponse.access_token);
                                editor.putString(UserAppConst.Colour_refresh_token, authTokenResponse.refresh_token);
                                editor.putLong(UserAppConst.Colour_expires_in, Long.valueOf(authTokenResponse.expires_in));
                                editor.putString(UserAppConst.Colour_token_type, authTokenResponse.token_type);
                                editor.putLong(UserAppConst.Colour_get_time, System.currentTimeMillis());
                                editor.commit();
                                newHttpResponse.OnHttpResponse(what, result);
                            } else {
                                editor.putBoolean(UserAppConst.IS_LOGIN, false);
                                editor.commit();
                                newHttpResponse.OnHttpResponse(what, "");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            editor.putBoolean(UserAppConst.IS_LOGIN, false);
                            editor.commit();
                            newHttpResponse.OnHttpResponse(what, "");
                        }
                    } else {
                        editor.putBoolean(UserAppConst.IS_LOGIN, false);
                        editor.commit();
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    editor.putBoolean(UserAppConst.IS_LOGIN, false);
                    editor.commit();
                    showErrorCodeMessage(responseCode, response);
                    newHttpResponse.OnHttpResponse(what, "");
                } else {
                    editor.putBoolean(UserAppConst.IS_LOGIN, false);
                    editor.commit();
                    showErrorCodeMessage(responseCode, response);
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                editor.putBoolean(UserAppConst.IS_LOGIN, false);
                editor.commit();
                showExceptionMessage(what, response);
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, false);
    }

    /**
     * 获取授权管理列表
     */
    public void getAuthList(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 12, getAuthList, null), RequestMethod.GET);
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
                showExceptionMessage(what, response);
            }
        }, true, false);
    }

    /**
     * 获取授权管理详情
     */
    public void getAuthDetail(int what, String id, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("app_id", id);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 12, getAuthDetail, params), RequestMethod.GET);
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
        }, true, true);
    }

    /**
     * 解除授权
     */
    public void unbindAuth(int what, String id, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("app_id", id);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 12, unbindAuth, params), RequestMethod.POST);
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
        }, true, false);
    }

    /**
     * 开门记录
     */
    public void uploadOpenDoor(int what, String cipher_id, String open_type, int status, String error_code) {
        Map<String, Object> params = new HashMap<>();
        params.put("cipher_id", cipher_id);
        params.put("open_type", open_type);
        params.put("community_uuid", ""); //小区id 暂无用处
        params.put("status", status);
        params.put("error_code", error_code);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 11, openRecordUrl, params), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
            }

            @Override
            public void onFailed(int what, Response<String> response) {
            }
        }, true, false);
    }

    /**
     * 获取授权管理详情
     */
    public void finishTask(int what, String type, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);//2实名认证、3意见反馈
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 3, finishTask, params), RequestMethod.GET);
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
        }, true, true);
    }


}
