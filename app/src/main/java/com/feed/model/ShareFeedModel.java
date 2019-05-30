package com.feed.model;

import android.content.Context;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.Neighborhood.protocol.FeedPublishShareApi;
import com.Neighborhood.protocol.FeedPublishShareRequest;
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
 * Created by HX_CHEN on 2016/5/10.
 */
public class ShareFeedModel extends BaseModel {
    private FeedPublishShareApi feedPublishShareApi;
    public ShareFeedModel(Context context) {
        super(context);
    }
    //邻里圈分享发布
    public void shareFeedPublish(HttpApiResponse httpApiResponse, FeedPublishShareRequest feedPublishShareRequest){
        feedPublishShareApi = new FeedPublishShareApi();
        feedPublishShareApi.request = feedPublishShareRequest;
        int uid = shared.getInt(UserAppConst.Colour_User_id,0);
        feedPublishShareApi.request.from_uid = String.valueOf(uid);
        feedPublishShareApi.httpApiResponse = httpApiResponse;
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params = Utils.transformJsonToMap(feedPublishShareApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = feedPublishShareApi.apiURI;
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
                            feedPublishShareApi.response.fromJson(jsonObject);
                            feedPublishShareApi.httpApiResponse.OnHttpResponse(feedPublishShareApi);
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
