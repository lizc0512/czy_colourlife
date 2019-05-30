package com.message.model;

import android.content.Context;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.message.protocol.HomeconfigGetuserinfourlGetApi;
import com.message.protocol.PushmessageDeleteDeleteApi;
import com.message.protocol.PushmessageGetApi;
import com.message.protocol.PushmessageSetreadGetApi;
import com.message.protocol.PushmessageViewGetApi;
import com.message.protocol.WetownMessagecentergetlistPostApi;
import com.message.protocol.WetownMessagecentersetallreadGetApi;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.UserAppConst;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统通知/商家消息model
 * Created by chenql on 16/1/13.
 */
public class MessageModel extends BaseModel {

    public MessageModel(Context context) {
        super(context);
    }

    /**
     * 获取系统通知列表
     *
     * @param httpApiResponse httpApiResponse
     * @param page            页数
     * @param limit           页
     * @param showProgress    是否显示loading
     */
    public void getSystemMsg(HttpApiResponse httpApiResponse, int page, int limit, boolean showProgress) {
        final PushmessageGetApi api = new PushmessageGetApi();
        api.request.page = page;
        api.request.pagesize = limit;
        api.httpApiResponse = httpApiResponse;
        Map<String, Object> params = null;
        try {
            params = Utils.transformJsonToMap(api.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = PushmessageGetApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 0, basePath, params), RequestMethod.GET);
        request(0, request, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            saveCache(UserAppConst.SYSTEMMESSAGE, jsonObject.toString());
                            api.response.fromJson(jsonObject);
                            api.httpApiResponse.OnHttpResponse(api);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
        }, true, showProgress);
    }

    /**
     * 新添加系统通知跳转
     *
     * @param httpApiResponse
     * @param resource_id
     * @param url
     * @param community_type
     * @param customer_id
     */
    public void getSystemMsgNew(HttpApiResponse httpApiResponse, String resource_id, String url, String community_type, String customer_id, final boolean showProgress) {
        final HomeconfigGetuserinfourlGetApi api2 = new HomeconfigGetuserinfourlGetApi();
        Map<String, Object> params = new HashMap<>();
        try {
            api2.request.resource_id = resource_id;
            api2.request.url = URLEncoder.encode(url, "utf-8");
            api2.request.community_type = community_type;
            api2.request.customer_id = customer_id;
            api2.httpApiResponse = httpApiResponse;
            params.putAll(Utils.transformJsonToMap(api2.request.toJson()));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String basePath = HomeconfigGetuserinfourlGetApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 0, basePath, params), RequestMethod.GET);
        request(0, request, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            api2.response.fromJson(jsonObject);
                            api2.httpApiResponse.OnHttpResponse(api2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {
                    if (showProgress) {
                        showErrorCodeMessage(responseCode, response);
                    }

                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (showProgress) {
                    showExceptionMessage(what, response);
                }
            }
        }, true, showProgress);
    }


    /**
     * 获取商家消息列表
     *
     * @param httpApiResponse httpApiResponse
     * @param mainType        mainType
     * @param lastId          lastId
     * @param limit           limit
     * @param showProgress    是否显示loading
     */
    public void getShopMsg(HttpApiResponse httpApiResponse, String mainType, String lastId, String limit, boolean showProgress) {
        final WetownMessagecentergetlistPostApi api = new WetownMessagecentergetlistPostApi();
        api.request.Limit = limit;
        api.request.MainType = mainType;
        api.request.LastID = lastId;
        api.httpApiResponse = httpApiResponse;
        Map<String, Object> params = null;
        try {
            params = Utils.transformJsonToMap(api.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = WetownMessagecentergetlistPostApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 0, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            saveCache(UserAppConst.SHOPMESSAGE, jsonObject.toString());
                            api.response.fromJson(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {

                }
                api.httpApiResponse.OnHttpResponse(api);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                api.httpApiResponse.OnHttpResponse(api);
            }
        }, true, showProgress);
    }

    /**
     * 查看一条系统通知
     *
     * @param httpApiResponse httpApiResponse
     * @param id              系统通知的id
     */
    public void readSystemMsg(HttpApiResponse httpApiResponse, String id) {
        final PushmessageViewGetApi api = new PushmessageViewGetApi();
        api.request.m_id = id;
        api.httpApiResponse = httpApiResponse;
        Map<String, Object> params = null;
        try {
            params = Utils.transformJsonToMap(api.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = PushmessageViewGetApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 0, basePath, params), RequestMethod.GET);
        request(0, request, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            api.response.fromJson(jsonObject);
                            api.httpApiResponse.OnHttpResponse(api);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
     * 删除一条系统通知
     *
     * @param httpApiResponse httpApiResponse
     * @param msg_id          系统通知的id
     */
    public void deleteSystemMsg(HttpApiResponse httpApiResponse, String msg_id) {
        final PushmessageDeleteDeleteApi api = new PushmessageDeleteDeleteApi();
        api.request.msg_id = msg_id;
        api.httpApiResponse = httpApiResponse;
        String basePath = api.apiURI.replace("/:ver", "/1.0");
        Map<String, Object> params = null;
        try {
            params = Utils.transformJsonToMap(api.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 0, basePath), RequestMethod.DELETE);
        request(0, request, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        int ok = jsonObject.optInt("ok");
                        if (ok == 1) {
                            api.httpApiResponse.OnHttpResponse(api);
                        } else {
                            ToastUtil.toastShow(mContext, "删除消息失败");
                        }
                    } else {
                        ToastUtil.toastShow(mContext, "删除消息失败");
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
     * 设置系统消息全部已读
     *
     * @param httpApiResponse httpApiResponse
     */
    public void readAllSystemMsg(HttpApiResponse httpApiResponse) {
        final PushmessageSetreadGetApi api = new PushmessageSetreadGetApi();
        api.request.flag = "";
        api.httpApiResponse = httpApiResponse;
        String basePath = PushmessageSetreadGetApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 0, basePath, null), RequestMethod.GET);
        request(0, request, null, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            api.response.fromJson(jsonObject);
                            api.httpApiResponse.OnHttpResponse(api);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
     * 设置商家消息全部已读
     *
     * @param httpApiResponse httpApiResponse
     */
    public void readAllShopMsg(HttpApiResponse httpApiResponse) {
        final WetownMessagecentersetallreadGetApi api = new WetownMessagecentersetallreadGetApi();
        api.httpApiResponse = httpApiResponse;
        String basePath = WetownMessagecentersetallreadGetApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 0, basePath, null), RequestMethod.GET);
        request(0, request, null, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            api.response.fromJson(jsonObject);
                            api.httpApiResponse.OnHttpResponse(api);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
}
