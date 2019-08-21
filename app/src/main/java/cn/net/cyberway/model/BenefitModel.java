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

import java.util.HashMap;
import java.util.Map;

/**
 * 彩惠人生原生接口
 * hxg 2019.07.31
 */
public class BenefitModel extends BaseModel {
    private Context mContext;
    private String profileUrl = "app/chrs/user/profile";
    private String bannerUrl = "app/chrs/column/banner";
    private String getHotUrl = "app/chrs/column/hot";
    private String getChannelUrl = "app/chrs/column/channel";
    private String getArticleUrl = "app/chrs/column/article";
    private String getActivityUrl = "/app/chrs/popup/notice";

    public BenefitModel(Context context) {
        super(context);
        this.mContext = context;
    }

    public void getProfile(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 13, profileUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessageTheme(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, false);
    }

    public void getBanner(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 13, bannerUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessageTheme(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, false);
    }

    public void getHot(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 13, getHotUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessageTheme(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, false);
    }

    public void getChannel(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 13, getChannelUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessageTheme(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, false);
    }

    public void getArticle(int what, int page, final NewHttpResponse newHttpResponse) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("page", page);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 13, getArticleUrl, paramsMap), RequestMethod.GET);
        request(what, request, paramsMap, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessageTheme(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, false);
    }

    /**
     * 活动
     */
    public void getActivity(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 13, getActivityUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessageTheme(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, false);
    }
}
