package com.feed.model;


import android.content.Context;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.feed.protocol.VerFeedPublishActivityApi;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.UserAppConst;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ActivityPublishModel extends BaseModel {
    private  VerFeedPublishActivityApi api;
    public ActivityPublishModel(Context context) {
        super(context);
    }

    /**
     * 发布活动
     * @param businessResponse
     * @param community_uuid  小区id
     * @param activity_type  活动类型
     * @param start_time 开始时间
     * @param content 内容
     * @param location 地址
     */
    public void publish(HttpApiResponse businessResponse, String community_uuid,String activity_type,String start_time,String content,String location) {
        api = new VerFeedPublishActivityApi();
        api.httpApiResponse = businessResponse;
        api.request.activity_type = activity_type;
        api.request.community_uuid=community_uuid;
        api.request.start_time=start_time;
        api.request.end_time="0";
        api.request.content=content;
        api.request.location=location;
        int uid = shared.getInt(UserAppConst.Colour_User_id,0);
        api.request.from_uid = String.valueOf(uid);
        Map<String, Object> params =null;
        try {
            params = Utils.transformJsonToMap(api.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = api.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 4, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>()
        {

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