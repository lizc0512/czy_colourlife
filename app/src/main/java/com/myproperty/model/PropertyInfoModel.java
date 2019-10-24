package com.myproperty.model;

import android.content.Context;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.myproperty.protocol.HouseaddressDeletehousePostApi;
import com.myproperty.protocol.HouseaddressGetmyhouseGetApi;
import com.myproperty.protocol.HouseaddressPropretyifyPostApi;
import com.myproperty.protocol.ImageuploadPostApi;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.UserAppConst;
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
import java.util.Map;


/**
 * 我的房产model
 * Created by HX_CHEN on 2017/3/28.
 * TIME: ${YESR}${MONTY} 28 1836
 */

public class PropertyInfoModel extends BaseModel {
    public PropertyInfoModel(Context context) {
        super(context);
    }

    private HouseaddressGetmyhouseGetApi houseaddressGetmyhouseGetApi;
    private HouseaddressDeletehousePostApi houseaddressDeletehousePostApi;
    private ImageuploadPostApi imageuploadPostApi;
    private HouseaddressPropretyifyPostApi houseaddressPropretyifyPostApi;

    /**
     * 获取用户房产列表
     */
    public void getMyHouse(HttpApiResponse businessResponse, boolean isLoading) {
        houseaddressGetmyhouseGetApi = new HouseaddressGetmyhouseGetApi();
        houseaddressGetmyhouseGetApi.httpApiResponse = businessResponse;
        int customer_id = shared.getInt(UserAppConst.Colour_User_id, 0);
        houseaddressGetmyhouseGetApi.request.customer_id = customer_id + "";
        String basePath = houseaddressGetmyhouseGetApi.apiURI;
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params = Utils.transformJsonToMap(houseaddressGetmyhouseGetApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Request<String> request_oauthRegister = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 6, basePath, params), RequestMethod.GET);
        request(0, request_oauthRegister, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            houseaddressGetmyhouseGetApi.response.fromJson(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    showErrorCodeMessage(responseCode, response);
                } else {
                    showErrorCodeMessage(responseCode, response);
                }
                houseaddressGetmyhouseGetApi.httpApiResponse.OnHttpResponse(houseaddressGetmyhouseGetApi);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                houseaddressGetmyhouseGetApi.httpApiResponse.OnHttpResponse(houseaddressGetmyhouseGetApi);
            }
        }, true, isLoading);

    }

    /**
     * 删除房产
     *
     * @param businessResponse
     * @param position
     */
    public void postdeleteHouse(HttpApiResponse businessResponse, int position, boolean isLoading) {
        houseaddressDeletehousePostApi = new HouseaddressDeletehousePostApi();
        houseaddressDeletehousePostApi.httpApiResponse = businessResponse;
        int customer_id = shared.getInt(UserAppConst.Colour_User_id, 0);
        houseaddressDeletehousePostApi.request.house_id = String.valueOf(position);
        houseaddressDeletehousePostApi.request.customer_id = String.valueOf(customer_id);
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params = Utils.transformJsonToMap(houseaddressDeletehousePostApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = houseaddressDeletehousePostApi.apiURI;
        final Request<String> request_oauthRegister = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 9, basePath), RequestMethod.POST);
        request(0, request_oauthRegister, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            houseaddressDeletehousePostApi.response.fromJson(jsonObject);
                            if (houseaddressDeletehousePostApi.response.code == 0) {
                                houseaddressDeletehousePostApi.httpApiResponse.OnHttpResponse(houseaddressDeletehousePostApi);
                            } else {
                                callback(houseaddressDeletehousePostApi.response.message);
                            }
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
        }, true, isLoading);
    }

    /**
     * 上传图片
     *
     * @param file 文件路径
     */
    public void postimageUpload(HttpApiResponse businessResponse, String file, final String types) {
        imageuploadPostApi = new ImageuploadPostApi();
        imageuploadPostApi.httpApiResponse = businessResponse;
        File uploadFile = new File(file);
        BasicBinary binary = new FileBinary(uploadFile);
        Map<String, Object> params = new HashMap<>();
        params.put("type",types);
        String basePath = imageuploadPostApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 10, basePath), RequestMethod.POST);
        request.add("file", binary);
        request(0, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            imageuploadPostApi.response.fromJson(jsonObject);
                            if (imageuploadPostApi.response.code == 0) {
                                imageuploadPostApi.httpApiResponse.OnHttpResponse(imageuploadPostApi);
                            } else {
                                callback(imageuploadPostApi.response.message);
                            }
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
        }, true, true);
    }

    /**
     * 提交房产认证数据
     */
    public void postpropretyVerify(HttpApiResponse businessResponse, String property_id, String house_uuid,
                                   String family_name, String last_name, String mobile,
                                   String idcard_number, String idcardFront_id, String idcardBack_id,
                                   String propertyFront_id, String propertyBack_id) {
        houseaddressPropretyifyPostApi = new HouseaddressPropretyifyPostApi();
        houseaddressPropretyifyPostApi.httpApiResponse = businessResponse;
        houseaddressPropretyifyPostApi.request.property_id = property_id;
        houseaddressPropretyifyPostApi.request.house_uuid = house_uuid;
        houseaddressPropretyifyPostApi.request.family_name = family_name;
        houseaddressPropretyifyPostApi.request.last_name = last_name;
        houseaddressPropretyifyPostApi.request.mobile = mobile;
        houseaddressPropretyifyPostApi.request.idcard_number = idcard_number;
        houseaddressPropretyifyPostApi.request.idcardFront_id = idcardFront_id;
        houseaddressPropretyifyPostApi.request.idcardBack_id = idcardBack_id;
        houseaddressPropretyifyPostApi.request.propertyFront_id = propertyFront_id;
        houseaddressPropretyifyPostApi.request.propertyBack_id = propertyBack_id;
        int customer_id = shared.getInt(UserAppConst.Colour_User_id, 0);
        houseaddressPropretyifyPostApi.request.customer_id = String.valueOf(customer_id);

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params = Utils.transformJsonToMap(houseaddressPropretyifyPostApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = houseaddressPropretyifyPostApi.apiURI;
        final Request<String> request_oauthRegister = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 10, basePath), RequestMethod.POST);
        request(0, request_oauthRegister, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            houseaddressPropretyifyPostApi.response.fromJson(jsonObject);
                            if (houseaddressPropretyifyPostApi.response.code == 0) {
                                houseaddressPropretyifyPostApi.httpApiResponse.OnHttpResponse(houseaddressPropretyifyPostApi);
                            } else {
                                callback(houseaddressPropretyifyPostApi.response.message);
                            }
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
        }, true, true);
    }
}
