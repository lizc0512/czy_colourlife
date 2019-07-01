package cn.net.cyberway.model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;


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
                    int code = 0;
                    if (isShow) {
                        code = showSuccesResultMessageTheme(result);
                    } else {
                        code = showSuccesResultMessage(result);
                    }
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else {
                    if (isShow) {
                        showErrorCodeMessage(responseCode, response);
                    }
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (isShow) {
                    showExceptionMessage(what, response);
                }
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, false);
    }

    public void getMySubMenuList(int what, boolean isLoading, NewHttpResponse newHttpResponse) {
        String basePath = "app/home/v5/appProfile";
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 1, basePath, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = 0;
                    if (isLoading) {
                        code = showSuccesResultMessageTheme(result);
                    } else {
                        code = showSuccesResultMessage(result);
                    }
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else {
                    if (isLoading) {
                        showErrorCodeMessage(responseCode, response);
                    }
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
                if (isLoading) {
                    showExceptionMessage(what, response);
                }
            }
        }, true, false);

    }
}
