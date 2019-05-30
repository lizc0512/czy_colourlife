package com.popupScreen.model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.update.activity.UpdateVerSion;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: zhudewei
 * 时间： 2016/12/28 12:13
 * God bless the program never bug
 */

public class PopupModel extends BaseModel {
    public PopupModel(Context context) {
        super(context);
    }

    private String homePopupMsgUrl = "/1.0/homeConfig/getPopup";
    private String newHomePopupMsgUrl = "app/home/msg/getPopupMsg";

    /**
     * 获取弹出窗口消息接口
     */
    public void getHomePopupMsg(int what, int customer_id, int category_code, final NewHttpResponse newHttpResponse) {
        String ver = UpdateVerSion.handleVersionName(UpdateVerSion.getVersionName(mContext));
        Map<String, Object> map = new HashMap<>();
        map.put("customer_id", customer_id);
        map.put("category_code", category_code);//不能用id 应该用用户的手机号
        map.put("v", ver);//不能用id 应该用用户的手机号
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 0, homePopupMsgUrl, map), RequestMethod.GET);
        request(what, request, map, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showCombileSuccesResult(result, false);
                    if (code == 1) {
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
     * 获取弹出窗口消息接口
     */
    public void getNewHomePopupMsg(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.getCombileMD5(mContext, 1, newHomePopupMsgUrl,null), RequestMethod.GET);
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
