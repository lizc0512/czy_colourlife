package com.eparking.model;

import android.content.Context;
import android.text.TextUtils;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.helper.ConstantKey;
import com.eparking.protocol.CityInforEntity;
import com.nohttp.utils.GsonUtils;
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
 * @class name：com.eparking.model
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/6 15:00
 * @change
 * @chang time
 * @class describe
 */
public class CityModel extends BaseModel {


    private String cityUrl = "/app/epark/home/city"; //获取城市信息

    public CityModel(Context context) {
        super(context);
    }

    public void getCityInfor(int what, String city_name, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("city_name", city_name);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 8, cityUrl,params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
                        try {
                            if (!TextUtils.isEmpty(result)) {
                                CityInforEntity cityInforEntity = GsonUtils.gsonToBean(result, CityInforEntity.class);
                                CityInforEntity.ContentBean contentBean = cityInforEntity.getContent();
                                editor.putString(ConstantKey.EPARKINGLON, contentBean.getLongitude());
                                editor.putString(ConstantKey.EPARKINGLAT, contentBean.getLatitude());
                                editor.putString(ConstantKey.EPARKINCITYID, String.valueOf(contentBean.getId()));
                                editor.putString(ConstantKey.EPARKINGCITYINFOR, result);
                                editor.commit();
                            }
                        } catch (Exception e) {

                        }
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, false);
    }
}
