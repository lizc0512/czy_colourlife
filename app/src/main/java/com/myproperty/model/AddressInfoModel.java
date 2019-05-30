package com.myproperty.model;

import android.content.Context;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.myproperty.protocol.HouseaddressAddhousePostApi;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.UserAppConst;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


/**
 * 房产地址信息model
 * Created by HX_CHEN on 2017/3/28.
 * TIME: ${YESR}${MONTY} 28 1836
 */

public class AddressInfoModel extends BaseModel {
    public AddressInfoModel(Context context) {
        super(context);
    }

    private HouseaddressAddhousePostApi houseaddressAddhousePostApi;



    /**
     * 新增房产列表
     */
    public void postPropertyList(int what, String mUuid, String mBuild_uuid,
                                 String mUnit_uuid, String mRoom_uuid, String mBuild, String mUnit, String mRoom, final NewHttpResponse newHttpResponse) {
        houseaddressAddhousePostApi = new HouseaddressAddhousePostApi();
        int mCustomer_id = shared.getInt(UserAppConst.Colour_User_id, 0);
        houseaddressAddhousePostApi.request.customer_id = mCustomer_id + "";
        houseaddressAddhousePostApi.request.community_uuid = mUuid;
        houseaddressAddhousePostApi.request.build_uuid = mBuild_uuid;
        houseaddressAddhousePostApi.request.unit_uuid = mUnit_uuid;
        houseaddressAddhousePostApi.request.room_uuid = mRoom_uuid;
        houseaddressAddhousePostApi.request.build_name = mBuild;
        houseaddressAddhousePostApi.request.unit_name = mUnit;
        houseaddressAddhousePostApi.request.room_name = mRoom;
        String basePath = houseaddressAddhousePostApi.apiURI;
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params = Utils.transformJsonToMap(houseaddressAddhousePostApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Request<String> request_oauthRegister = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 9, basePath), RequestMethod.POST);
        request(what, request_oauthRegister, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int code = showSuccesResultMessage(result);
                    if (code == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    showErrorCodeMessage(responseCode, response);
                } else {
                    showErrorCodeMessage(responseCode, response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showErrorCodeMessage(what, response);
            }
        }, true, true);
    }

}
