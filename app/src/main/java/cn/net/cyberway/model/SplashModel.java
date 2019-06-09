package cn.net.cyberway.model;


import android.content.Context;
import android.text.TextUtils;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.UserAppConst;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 闪屏页model
 */

public class SplashModel extends BaseModel {


    public SplashModel(Context context) {
        super(context);
    }

    private final static String themeUrl = "app/home/start/page";


    /**
     * 可配置化-启动图片
     */
    public void getStartImage(final int what, final NewHttpResponse newHttpResponse) {
        final String mCommunityId = shared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("community_uuid", mCommunityId);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 1, themeUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    if (!TextUtils.isEmpty(result)) {
                        int resultCode = showSuccesResultMessageTheme(result);
                        if (resultCode == 0) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                if (!jsonObject.isNull("content")) {
                                    String imageContent = jsonObject.optString("content");
                                    if (!TextUtils.isEmpty(imageContent)) {
                                        saveCache(UserAppConst.Colour_SPLASH_CACHE, imageContent);
                                    } else {
                                        saveCache(UserAppConst.Colour_SPLASH_CACHE, "");
                                    }
                                } else {
                                    saveCache(UserAppConst.Colour_SPLASH_CACHE, "");
                                }
                            } catch (Exception e) {
                                saveCache(UserAppConst.Colour_SPLASH_CACHE, "");
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, false);
    }

}
