package com.audio.model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;

/**
 * @name ${yuansk}
 * @class name：com.audio.model
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/3/8 14:55
 * @change
 * @chang time
 * @class describe
 */
public class AudioHangupModel extends BaseModel {
    private String roomTokenUrl = "app/home/menjin/offlineMsg";

    public AudioHangupModel(Context context) {
        super(context);
    }

    public void uploadHangUpMethod(int what, String deviceId, NewHttpResponse newHttpResponse) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("deviceId", deviceId);
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 3, roomTokenUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, false);
    }
}
