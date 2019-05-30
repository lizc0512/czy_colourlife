package cn.net.cyberway.model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.UserAppConst;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.model
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/10/18 17:14
 * @change
 * @chang time
 * @class describe   首页加入切换小区的相关操作
 */

public class HomeCommunityModel extends BaseModel {
    public HomeCommunityModel(Context context) {
        super(context);
    }

    private final String homeNearBy = "app/home/nearby";//获取附近小区接口
    private final String homeLocalSearch = "app/home/local/search";//根据名称搜索小区
    private final String homeCommunityJoin = "app/home/community/join";//加入小区接口

    /****通过经纬度获取附近的小区**/
    public void getHomeNearCommunity(int what, String longitude, String latitude, final NewHttpResponse newHttpResponse) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("longitude", longitude);
        paramsMap.put("latitude", latitude);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 1, homeNearBy, paramsMap), RequestMethod.GET);
        request(what, request, paramsMap, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        saveCache(UserAppConst.JOINCOMMUNITY, result);
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {
                    showErrorCodeMessage(responseCode, response);
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, false);
    }


    /****通过小区的名称搜索小区**/
    public void getHomeLocalSearchCommunity(int what, String communityName, final NewHttpResponse newHttpResponse) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("name", communityName);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 1, homeLocalSearch, paramsMap), RequestMethod.GET);
        request(what, request, paramsMap, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {
                    showErrorCodeMessage(responseCode, response);
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
            }
        }, true, true);
    }


    /****加入小区**/
    public void joinHomeCommunity(int what, String customerId, String communityId, String communityName, String address, String type, final NewHttpResponse newHttpResponse) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("customer_id", customerId);
        paramsMap.put("community_id", communityId);
        paramsMap.put("name", communityName);
        paramsMap.put("address", address);
        paramsMap.put("type", type);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 3, homeCommunityJoin), RequestMethod.POST);
        request(what, request, paramsMap, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
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
