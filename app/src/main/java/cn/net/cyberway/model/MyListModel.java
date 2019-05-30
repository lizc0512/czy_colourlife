package cn.net.cyberway.model;

import android.content.Context;
import android.text.TextUtils;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
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

import cn.net.cyberway.home.protocol.MyOptionsGetApi;


/**
 * 我的页面点击listmodel
 * Created by HX_CHEN on 2017/9/28.
 */

public class MyListModel extends BaseModel {
    public MyListModel(Context context) {
        super(context);
    }

    private MyOptionsGetApi myOptionsGetApi;

    /**
     * 我的页面配置列表
     */
    public void getmypageList(HttpApiResponse businessResponse, final boolean isShow) {
        myOptionsGetApi = new MyOptionsGetApi();
        myOptionsGetApi.httpApiResponse = businessResponse;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("device_type", 1);
        String basePath = myOptionsGetApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 1, basePath, params), RequestMethod.GET);
        request(0, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    String MYPAGELISTCache = shared.getString(UserAppConst.MYPAGELIST, "");
                    JSONObject jsonObject = null;
                    if (TextUtils.isEmpty(MYPAGELISTCache) && isShow) {
                        jsonObject = showSuccesCodeMessage(result);
                    } else {
                        jsonObject = showSuccesCodeNullMessage(result);
                    }
                    if (null != jsonObject) {
                        try {
                            myOptionsGetApi.response.fromJson(jsonObject);
                            if (myOptionsGetApi.response.code == 0) {
                                saveCache(UserAppConst.MYPAGELIST, jsonObject.toString());
                                myOptionsGetApi.httpApiResponse.OnHttpResponse(myOptionsGetApi);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    String MYPAGELISTCache = shared.getString(UserAppConst.MYPAGELIST, "");
                    if (TextUtils.isEmpty(MYPAGELISTCache) && isShow) {
                        showErrorCodeMessage(responseCode, response);
                    }
                } else {
                    String MYPAGELISTCache = shared.getString(UserAppConst.MYPAGELIST, "");
                    if (TextUtils.isEmpty(MYPAGELISTCache) && isShow) {
                        showErrorCodeMessage(responseCode, response);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
//                showExceptionMessage(what, response);
            }
        }, true, false);
    }
}
