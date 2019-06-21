package cn.net.cyberway.model;

import android.content.Context;
import android.text.TextUtils;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.BeeFramework.model.NewHttpResponse;
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

    /**
     * 我的页面配置列表
     */
    public void getmypageList(int what, final boolean isShow, NewHttpResponse newHttpResponse) {
        String basePath = "app/home/my/options";
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 1, basePath, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessageTheme(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, false);
    }

    public void getMySubMenuList(int what, NewHttpResponse newHttpResponse) {
        String basePath = "app/home/v5/appProfile";
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 1, basePath, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessageTheme(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, false);

    }
}
