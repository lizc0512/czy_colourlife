package com.myproperty.model;

import android.content.Context;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.myproperty.protocol.ColorhouseLocallistGetApi;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * 尊享饭票model
 * Created by HX_CHEN on 2017/3/28.
 * TIME: ${YESR}${MONTY} 28 1836
 */

public class VipFpInfoModel extends BaseModel {
    public VipFpInfoModel(Context context) {
        super(context);
    }

    private ColorhouseLocallistGetApi colorhouseLocallistGetApi;

    /**
     * 我的饭票列表
     */
    public void getlocalList(HttpApiResponse businessResponse, Boolean isloading) {
        colorhouseLocallistGetApi = new ColorhouseLocallistGetApi();
        colorhouseLocallistGetApi.httpApiResponse = businessResponse;
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params = Utils.transformJsonToMap(colorhouseLocallistGetApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = colorhouseLocallistGetApi.apiURI;
        final Request<String> request_oauthRegister = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 6, basePath, null), RequestMethod.GET);
        request(0, request_oauthRegister, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            colorhouseLocallistGetApi.response.fromJson(jsonObject);
                            if (colorhouseLocallistGetApi.response.code == 0) {
                                colorhouseLocallistGetApi.httpApiResponse.OnHttpResponse(colorhouseLocallistGetApi);
                            } else {
                                callback(colorhouseLocallistGetApi.response.message);
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
        }, true, isloading);
    }


}
