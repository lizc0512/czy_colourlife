package com.feed.model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.feed.protocol.PhotoPostApi;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.yanzhenjie.nohttp.BasicBinary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;


public class UploadImageModel extends BaseModel {
    private PhotoPostApi api;

    public UploadImageModel(Context context) {
        super(context);
    }

    /**
     * 图片上传
     *
     * @param businessResponse
     * @param image
     */
    public void upload(HttpApiResponse businessResponse, String image) {
        api = new PhotoPostApi();
        api.httpApiResponse = businessResponse;
        BasicBinary binary = new FileBinary(new File(image));
        String basePath = "app/linli/uploadImage";
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 4, basePath), RequestMethod.POST);
        request.add("file", binary);
        request.setConnectTimeout(25000);
        request.setReadTimeout(25000);
        request(0, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jo = showSuccesCodeMessage(result);
                    try {
                        if (null != jo) {
                            api.response.fromJson(jo);
                            api.httpApiResponse.OnHttpResponse(api);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
