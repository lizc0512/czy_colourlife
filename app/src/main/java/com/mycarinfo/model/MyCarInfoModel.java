package com.mycarinfo.model;

import android.content.Context;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.mycarinfo.protocol.VehicleAddvehiclePostApi;
import com.mycarinfo.protocol.VehicleDeletevehiclePostApi;
import com.mycarinfo.protocol.VehicleGetcarbrandGetApi;
import com.mycarinfo.protocol.VehicleGetcarmodelGetApi;
import com.mycarinfo.protocol.VehicleGetcolourGetApi;
import com.mycarinfo.protocol.VehicleGethotcarGetApi;
import com.mycarinfo.protocol.VehicleGetmyvehicleGetApi;
import com.mycarinfo.protocol.VehicleGetprovinceGetApi;
import com.myproperty.protocol.VehicleBindmealPostApi;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.UserAppConst;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的车辆model
 * Created by HX_CHEN on 2017/3/28.
 * TIME: ${YESR}${MONTY} 28 1836
 */

public class MyCarInfoModel extends BaseModel {
    public MyCarInfoModel(Context context) {
        super(context);
    }

    private VehicleGetmyvehicleGetApi vehicleGetmyvehicleGetApi;
    private VehicleDeletevehiclePostApi vehicleDeletevehiclePostApi;
    private VehicleGethotcarGetApi vehicleGethotcarGetApi;
    private VehicleGetcarbrandGetApi vehicleGetcarbrandGetApi;
    private VehicleAddvehiclePostApi vehicleAddvehiclePostApi;
    private VehicleGetprovinceGetApi vehicleGetprovinceGetApi;
    private VehicleGetcarmodelGetApi vehicleGetcarmodelGetApi;
    private VehicleGetcolourGetApi vehicleGetcolourGetApi;
    private VehicleBindmealPostApi vehicleBindmealPostApi;

    /**
     * 获取我的车辆列表
     */
    public void getMyVehicleInfo(HttpApiResponse businessResponse, boolean isLoading) {
        vehicleGetmyvehicleGetApi = new VehicleGetmyvehicleGetApi();
        vehicleGetmyvehicleGetApi.httpApiResponse = businessResponse;
        int customer_id = shared.getInt(UserAppConst.Colour_User_id, 0);
        vehicleGetmyvehicleGetApi.request.customer_id = customer_id + "";
        Map<String, Object> params = new HashMap<String, Object>();
        String basePath = vehicleGetmyvehicleGetApi.apiURI;
        try {
            params = Utils.transformJsonToMap(vehicleGetmyvehicleGetApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 6, basePath, params), RequestMethod.GET);
        request(0, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            vehicleGetmyvehicleGetApi.response.fromJson(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {
                    showErrorCodeMessage(responseCode, response);
                }
                vehicleGetmyvehicleGetApi.httpApiResponse.OnHttpResponse(vehicleGetmyvehicleGetApi);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                vehicleGetmyvehicleGetApi.httpApiResponse.OnHttpResponse(vehicleGetmyvehicleGetApi);
            }
        }, true, isLoading);
    }

    /**
     * 删除我的车辆
     */
    public void postdeleteVehicle(HttpApiResponse businessResponse, String vehicle_id, boolean isLoading) {
        vehicleDeletevehiclePostApi = new VehicleDeletevehiclePostApi();
        vehicleDeletevehiclePostApi.httpApiResponse = businessResponse;
        vehicleDeletevehiclePostApi.request.vehicle_id = vehicle_id;
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params = Utils.transformJsonToMap(vehicleDeletevehiclePostApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = vehicleDeletevehiclePostApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 9, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            vehicleDeletevehiclePostApi.response.fromJson(jsonObject);
                            vehicleDeletevehiclePostApi.httpApiResponse.OnHttpResponse(vehicleDeletevehiclePostApi);
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
        }, true, isLoading);
    }

    /**
     * 获取省份简称
     */
    public void getProvince(HttpApiResponse businessResponse) {
        vehicleGetprovinceGetApi = new VehicleGetprovinceGetApi();
        vehicleGetprovinceGetApi.httpApiResponse = businessResponse;
        Map<String, Object> params = new HashMap<String, Object>();
        String basePath = vehicleGetprovinceGetApi.apiURI;
        try {
            params = Utils.transformJsonToMap(vehicleGetprovinceGetApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 6, basePath, null), RequestMethod.GET);
        request(0, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            vehicleGetprovinceGetApi.response.fromJson(jsonObject);
                            if (vehicleGetprovinceGetApi.response.code == 0) {
                                vehicleGetprovinceGetApi.httpApiResponse.OnHttpResponse(vehicleGetprovinceGetApi);
                            } else {
                                ToastUtil.toastShow(mContext,vehicleGetprovinceGetApi.response.message);
                            }
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
     * 获取热门车型
     */
    public void getHotCar(HttpApiResponse businessResponse) {
        vehicleGethotcarGetApi = new VehicleGethotcarGetApi();
        vehicleGethotcarGetApi.httpApiResponse = businessResponse;
        Map<String, Object> params = new HashMap<String, Object>();
        String basePath = vehicleGethotcarGetApi.apiURI;
        try {
            params = Utils.transformJsonToMap(vehicleGethotcarGetApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 6, basePath, null), RequestMethod.GET);
        request(0, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            vehicleGethotcarGetApi.response.fromJson(jsonObject);
                            if (vehicleGethotcarGetApi.response.code == 0) {
                                vehicleGethotcarGetApi.httpApiResponse.OnHttpResponse(vehicleGethotcarGetApi);
                            } else {
                                callback(vehicleGetcarmodelGetApi.response.message);
                            }
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
     * 获取所有车牌品牌
     */
    public void getCarBrand(HttpApiResponse businessResponse) {
        vehicleGetcarbrandGetApi = new VehicleGetcarbrandGetApi();
        vehicleGetcarbrandGetApi.httpApiResponse = businessResponse;
        Map<String, Object> params = new HashMap<String, Object>();
        String basePath = vehicleGetcarbrandGetApi.apiURI;
        try {
            params = Utils.transformJsonToMap(vehicleGetcarbrandGetApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 6, basePath, null), RequestMethod.GET);
        request(0, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            vehicleGetcarbrandGetApi.response.fromJson(jsonObject);
                            if (vehicleGetcarbrandGetApi.response.code == 0) {
                                vehicleGetcarbrandGetApi.httpApiResponse.OnHttpResponse(vehicleGetcarbrandGetApi);
                            } else {
                                callback(vehicleGetcarmodelGetApi.response.message);
                            }
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
     * 获取所有车牌品牌下的车型信息
     */
    public void getCarModel(HttpApiResponse businessResponse, String brand_id) {
        vehicleGetcarmodelGetApi = new VehicleGetcarmodelGetApi();
        vehicleGetcarmodelGetApi.httpApiResponse = businessResponse;
        vehicleGetcarmodelGetApi.request.brand_id = brand_id;
        String basePath = vehicleGetcarmodelGetApi.apiURI;
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params = Utils.transformJsonToMap(vehicleGetcarmodelGetApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 6, basePath, params), RequestMethod.GET);
        request(0, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            vehicleGetcarmodelGetApi.response.fromJson(jsonObject);
                            if (vehicleGetcarmodelGetApi.response.code == 0) {
                                vehicleGetcarmodelGetApi.httpApiResponse.OnHttpResponse(vehicleGetcarmodelGetApi);
                            } else {
                                callback(vehicleGetcarmodelGetApi.response.message);
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
     * 获取颜色
     */
    public void getColour(HttpApiResponse businessResponse) {
        vehicleGetcolourGetApi = new VehicleGetcolourGetApi();
        vehicleGetcolourGetApi.httpApiResponse = businessResponse;
        String basePath = vehicleGetcolourGetApi.apiURI;
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params = Utils.transformJsonToMap(vehicleGetcolourGetApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 6, basePath, null), RequestMethod.GET);
        request(0, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            vehicleGetcolourGetApi.response.fromJson(jsonObject);
                            if (vehicleGetcolourGetApi.response.code == 0) {
                                vehicleGetcolourGetApi.httpApiResponse.OnHttpResponse(vehicleGetcolourGetApi);
                            } else {
                                callback(vehicleGetcolourGetApi.response.message);
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
     * 新增车辆
     */
    public void postaddVehicle(HttpApiResponse businessResponse, String name, String mobile, String plate_number,
                               String vehicle_code, String engine_code, String brand_id, String model_id
            , String colour_id, String card_font_id, String card_back_id, String driver_card_font_id
            , String driver_card_back_id) {
        vehicleAddvehiclePostApi = new VehicleAddvehiclePostApi();
        vehicleAddvehiclePostApi.httpApiResponse = businessResponse;
        vehicleAddvehiclePostApi.request.name = name;
        vehicleAddvehiclePostApi.request.mobile = mobile;
        vehicleAddvehiclePostApi.request.plate_number = plate_number;
        vehicleAddvehiclePostApi.request.vehicle_code = vehicle_code;
        vehicleAddvehiclePostApi.request.engine_code = engine_code;
        vehicleAddvehiclePostApi.request.brand_id = brand_id;
        vehicleAddvehiclePostApi.request.model_id = model_id;
        vehicleAddvehiclePostApi.request.colour_id = colour_id;
        vehicleAddvehiclePostApi.request.card_font_id = card_font_id;
        vehicleAddvehiclePostApi.request.card_back_id = card_back_id;
        vehicleAddvehiclePostApi.request.driver_card_font_id = driver_card_font_id;
        vehicleAddvehiclePostApi.request.driver_card_back_id = driver_card_back_id;
        Map<String, Object> params = new HashMap<String, Object>();
        int customer_id = shared.getInt(UserAppConst.Colour_User_id, 0);
        vehicleAddvehiclePostApi.request.customer_id = String.valueOf(customer_id);

        try {
            params = Utils.transformJsonToMap(vehicleAddvehiclePostApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = vehicleAddvehiclePostApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 9, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            vehicleAddvehiclePostApi.response.fromJson(jsonObject);
                            if (vehicleAddvehiclePostApi.response.code == 0) {
                                vehicleAddvehiclePostApi.httpApiResponse.OnHttpResponse(vehicleAddvehiclePostApi);
                            } else {
                                ToastUtil.toastShow(mContext, vehicleAddvehiclePostApi.response.message);
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
     * 车辆绑定饭票
     */
    public void postbindMeal(HttpApiResponse businessResponse, String vehicle_id, String pano) {
        vehicleBindmealPostApi = new VehicleBindmealPostApi();
        vehicleBindmealPostApi.httpApiResponse = businessResponse;
        vehicleBindmealPostApi.request.vehicle_id = vehicle_id;
        vehicleBindmealPostApi.request.pano = pano;
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params = Utils.transformJsonToMap(vehicleBindmealPostApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = vehicleBindmealPostApi.apiURI;
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
                            vehicleBindmealPostApi.response.fromJson(jsonObject);
                            vehicleBindmealPostApi.httpApiResponse.OnHttpResponse(vehicleBindmealPostApi);
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
