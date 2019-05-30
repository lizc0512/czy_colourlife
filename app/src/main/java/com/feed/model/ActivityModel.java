package com.feed.model;

import android.content.Context;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.feed.protocol.ACTIVITY_CATEGORY;
import com.feed.protocol.VerFeedActivitycategoryApi;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class ActivityModel extends BaseModel
{
    public boolean isMore = false;
    public ArrayList<ACTIVITY_CATEGORY> activity_categories = new ArrayList<ACTIVITY_CATEGORY>();
    private static final int PER_PAGE = 10;
    private VerFeedActivitycategoryApi api;

    public ActivityModel(Context context) {
        super(context);
    }


    /**
     * 获取活动类型列表
     *
     * @param businessResponse
     * @param communityId      小区id
     */
    public void getCategory(HttpApiResponse businessResponse, String communityId) {

        api = new VerFeedActivitycategoryApi();
        api.request.community_uuid = communityId;
        api.request.page = 1;
        api.request.per_page = PER_PAGE;
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
                            api.response.fromJson(jsonObject);
                            if (api.response.paged.more == 1) {
                                isMore = true;
                            } else {
                                isMore = false;
                            }
                            activity_categories.clear();
                            activity_categories.addAll(api.response.activity_categoties);
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
        }, true, false);
    }

}
