package com.feed.model;

import android.content.Context;
import android.text.TextUtils;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.feed.protocol.VerFeedPublishNormalApi;
import com.feed.protocol.VerFeedPublishNormalResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.UserAppConst;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class CreateFeedModel extends BaseModel
{

    private VerFeedPublishNormalApi api;

    public CreateFeedModel(Context context) {
        super(context);
    }

    /**
     * 发布动态
     *
     * @param businessResponse
     * @param content          内容
     * @param photos           图片，json数组
     * @param cummunity_id     小区id
     */
    public void releaseFeed(HttpApiResponse businessResponse, String content, ArrayList<String> photos, String cummunity_id) {
        api = new VerFeedPublishNormalApi();
        api.request.content = content;
        api.request.photo = photos;
        api.request.community_uuid= cummunity_id;
        int uid = shared.getInt(UserAppConst.Colour_User_id, 0);
        api.request.from_uid = String.valueOf(uid);
        api.httpApiResponse = businessResponse;
        Map<String, Object> params = null;
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
                            VerFeedPublishNormalResponse verFeedPublishNormalResponse=  api.response;
                            verFeedPublishNormalResponse.fromJson(jsonObject);
                            if (verFeedPublishNormalResponse.ok==1){
                                api.httpApiResponse.OnHttpResponse(api);
                            }else{
                                String errorMsg=verFeedPublishNormalResponse.message;
                                String code=verFeedPublishNormalResponse.code;
                                if (!TextUtils.isEmpty(errorMsg)){
                                    callback(code+errorMsg);
                                }
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
}
