package cn.net.cyberway.model;


import android.content.Context;
import android.util.Log;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.notification.protocol.NotifyGetApi;
import com.update.activity.UpdateVerSion;
import com.user.UserAppConst;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.net.cyberway.protocol.AppHomePropertyListGetApi;

/**
 * lizc
 * 2017/11/07
 * 找物业model
 */
public class FindPropertyModel extends BaseModel {
    private AppHomePropertyListGetApi appHomePropertyListGetApi;
    private NotifyGetApi mNotityapi;

    public FindPropertyModel(Context context) {
        super(context);
    }


    /**
     * 找物业页面接口
     *
     * @param businessResponse
     * @param
     */
    public void getproperty(HttpApiResponse businessResponse,Boolean isloading) {
        appHomePropertyListGetApi = new AppHomePropertyListGetApi();
        appHomePropertyListGetApi.httpApiResponse = businessResponse;
        String baseUrl = appHomePropertyListGetApi.apiURI;
        Map<String, Object> map = new HashMap<>();
        map.put("version", UpdateVerSion.handleVersionName(UpdateVerSion.getVersionName(mContext)));
        Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(
                mContext, 1, baseUrl, map), RequestMethod.GET);
        request(0, request, map, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                Log.d("TAG0FIND", result);
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject object=showSuccesCodeMessage(result);
                    if(null!=object){
                        try {
                            appHomePropertyListGetApi.response.fromJson(object);
                            if(appHomePropertyListGetApi.response.code==0){
                                int userid=shared.getInt(UserAppConst.Colour_User_id,0);
                                saveCache(UserAppConst.FINDPROPERTY+userid,object.toString());
                                appHomePropertyListGetApi.httpApiResponse.OnHttpResponse(appHomePropertyListGetApi);
                            } else {
                                showErrorCodeMessage(responseCode, response);
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

            }
        }, true, isloading);
    }
}
