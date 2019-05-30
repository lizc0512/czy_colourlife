package com.cardcoupons.model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.cardcoupons.entity.CouponsEntity;
import com.cardcoupons.protocol.coupons.CouponGettApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by junier_li on 2016/1/5.
 */
public class CouponsModel extends BaseModel {

    private static String TAG = "CouponsModel";

    private Gson gson = new Gson();

    public CouponsModel(Context context) {
        super(context);
    }

    CouponGettApi couponGettApi = new CouponGettApi();


    /**
     * @param bussinessResponse
     */
    public void getCountAvailable(HttpApiResponse bussinessResponse) {

        couponGettApi.httpApiResponse = bussinessResponse;
        Map<String, Object> params = new HashMap<String, Object>();
        String basePath = CouponGettApi.apiURI.replace("/:ver", "/1.0");
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 0, basePath, null), RequestMethod.GET);
        request(0, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            String balance = jsonObject.get("balance").toString();
                            String coupon = jsonObject.get("coupon").toString();
                            String complain = jsonObject.get("complain").toString();
//                            JSONObject object = new JSONObject(response.getString("caidou"));
//                            JSONObject object2 = new JSONObject(response.getString("about"));
                            //返回的JSON数组已经没有优惠券，注释掉
//                            JSONObject object3 = new JSONObject(response.getString("youHuiQuan"));// 这个坑越来越大啦
//                            JSONObject object4 = new JSONObject(response.getString("tiHuoQuan"));// +1
                            JSONObject object3 = jsonObject.getJSONObject("youHuiQuan");// 这个坑越来越大啦
                            JSONObject object4 = jsonObject.getJSONObject("tiHuoQuan");// +1
                            Type type = new TypeToken<CouponsEntity>() {
                            }.getType();
//                            CouponsEntity caiDou = gson.fromJson(object.toString(), type);
//                            CouponsEntity about = gson.fromJson(object2.toString(), type);
//                            CouponsEntity youHuiQuan = gson.fromJson(object3.toString(), type);
//                            CouponsEntity tiHuoQuan = gson.fromJson(object4.toString(), type);
//                            couponGettApi.response.result = code;
//                            couponGettApi.response.reason = message;
                            couponGettApi.response.youHuiQuan_WebUrl = object3.getString("url");
                            couponGettApi.response.tiHuoQuan_WebUrl = object4.getString("url");
//                            couponGettApi.response.balance = balance;
//                            couponGettApi.response.coupon = coupon;
//                            couponGettApi.response.complain = complain;
//                            couponGettApi.response.caidou = caiDou;
//                            couponGettApi.response.about = about;
//                            couponGettApi.response.youHuiQuan = youHuiQuan;
//                            couponGettApi.response.tiHuoQuan = tiHuoQuan;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        couponGettApi.httpApiResponse.OnHttpResponse(couponGettApi);
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {
                    showErrorCodeMessage(responseCode, response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showErrorCodeMessage(what, response);
            }
        }, true, true);
    }
}
