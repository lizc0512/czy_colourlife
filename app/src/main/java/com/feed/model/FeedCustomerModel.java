package com.feed.model;

import android.content.Context;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.feed.protocol.FEED;
import com.feed.protocol.VerFeedCustomerApi;
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


public class FeedCustomerModel extends BaseModel {

    private static final int PER_PAGE = 10;
    private VerFeedCustomerApi customerApi;
    public ArrayList<FEED>  feedList = new ArrayList<FEED>();
    public boolean isMore = false;
    public FeedCustomerModel(Context context){
        super(context);
    }

    /**
     * 获取用户动态列表
     * @param businessResponse
     * @param customer_id 用户id
     */
    public void getCustomerFeedPro(HttpApiResponse businessResponse ,String customer_id){

        customerApi = new VerFeedCustomerApi();

        customerApi.request.customer_id = customer_id;
        customerApi.request.page = 1;
        customerApi.request.per_page = PER_PAGE;
        int uid = shared.getInt(UserAppConst.Colour_User_id,0);
        customerApi.request.from_uid = String.valueOf(uid);
        customerApi.httpApiResponse = businessResponse;
        Map<String,Object> params = null;
        try {
            params = Utils.transformJsonToMap(customerApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = customerApi.apiURI;
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
                            customerApi.response.fromJson(jsonObject);
                            if(customerApi.response.paged.more == 1){
                                isMore = true;
                            }else{
                                isMore = false;
                            }
                            feedList.clear();
                            feedList.addAll(customerApi.response.feeds);
                            customerApi.httpApiResponse.OnHttpResponse(customerApi);
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

    /**
     * 获取用户动态列表
     * @param businessResponse
     * @param customer_id  用户id
     */
    public void getCustomerFeedNext(HttpApiResponse businessResponse ,String customer_id){

        customerApi = new VerFeedCustomerApi();
        customerApi.request.customer_id = customer_id;
        customerApi.request.page = feedList.size()/PER_PAGE +1;
        customerApi.request.per_page = PER_PAGE;
        int uid = shared.getInt(UserAppConst.Colour_User_id,0);
        customerApi.request.from_uid = String.valueOf(uid);
        customerApi.httpApiResponse = businessResponse;
        Map<String,Object> params = null;
        try {
            params = Utils.transformJsonToMap(customerApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = customerApi.apiURI;
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
                            customerApi.response.fromJson(jsonObject);
                            if(customerApi.response.paged.more == 1){
                                isMore = true;
                            }else{
                                isMore = false;
                            }
                            feedList.addAll(customerApi.response.feeds);
                            customerApi.httpApiResponse.OnHttpResponse(customerApi);
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
