package com.scanCode.model;

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
import java.util.Map;

/**
 * Created by junier_li on 2016/1/5.
 */
public class ScanCodeModel extends BaseModel {


    public ScanCodeModel(Context context) {
        super(context);
    }


    /**
     * 后台处理URL返回最终进入的h5的url
     */
    private String analysisUrl = "app/formatUrl";

    public void analysisUrl(int what, String url, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("url", url);
        params.put("app_type", "czy");
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 12, analysisUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }else{
                        newHttpResponse.OnHttpResponse(what, "");
                    }
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
        }, true, true);
    }
}
