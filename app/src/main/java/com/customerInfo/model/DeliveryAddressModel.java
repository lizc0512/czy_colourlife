package com.customerInfo.model;

import android.content.Context;
import android.text.TextUtils;

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
 * @class name：com.customerInfo.model
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/7 17:41
 * @change
 * @chang time
 * @class describe
 */

public class DeliveryAddressModel extends BaseModel {

    private String deliveryRegionUrl = "/region";
    private String deliveryIncreaseUrl = "/addUserAddress";
    private String deliveryUpdateUrl = "/updateUserAddress";
    private String deliveryDelUrl = "/delUserAddress";
    private String deliveryListUrl = "/getUserAddress/list";
    private String deliverySingleUrl = "/getUserAddress";

    public DeliveryAddressModel(Context context) {
        super(context);
    }

    /**
     * 获取收货的省市区
     */
    public void getDeliveryRegionData(int what, String region_id, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (!TextUtils.isEmpty(region_id)) {
            params.put("region_id", region_id);
        }
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 5, deliveryRegionUrl, params), RequestMethod.GET);
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
     * 获取收货地址列表
     */
    public void getDeliveryAddressList(int what, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 5, deliveryListUrl, params), RequestMethod.GET);
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
     * 获取单条地址的详情
     */
    public void getDeliverySingleAddress(int what, String addressId, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uuid", addressId);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 5, deliverySingleUrl, params), RequestMethod.GET);
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
     * 新增收货地址
     */
    public void postDeliveryAddress(int what, String province_uuid, String city_uuid, String county_uuid, String town_uuid,
                                    String community_uuid, String address, String name, String mobile, String postcode, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("province_uuid", province_uuid);
        params.put("city_uuid", city_uuid);
        params.put("county_uuid", county_uuid);
        params.put("town_uuid", town_uuid);
        params.put("community_uuid", community_uuid);
        params.put("address", address);
        params.put("name", name);
        params.put("mobile", mobile);
        params.put("postcode", postcode);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 8, deliveryIncreaseUrl), RequestMethod.POST);
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


    /**
     * 修改收货地址
     */
    public void updateDeliveryAddress(int what, String address_uuid, String province_uuid, String city_uuid, String county_uuid, String town_uuid,
                                      String community_uuid, String address, String name, String mobile, String postcode, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("address_uuid", address_uuid);
        params.put("province_uuid", province_uuid);
        params.put("city_uuid", city_uuid);
        params.put("county_uuid", county_uuid);
        params.put("town_uuid", town_uuid);
        params.put("community_uuid", community_uuid);
        params.put("address", address);
        params.put("name", name);
        params.put("mobile", mobile);
        params.put("postcode", postcode);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 8, deliveryUpdateUrl), RequestMethod.POST);
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

    /**
     * 删除用户地址
     */
    public void deleteDeliveryAddress(int what, String address_uuid, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("address_uuid", address_uuid);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 8, deliveryDelUrl), RequestMethod.POST);
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
}
