package com.gem.model;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.BeeFramework.BeeFrameworkApp;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.AdsWebViewActivity;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.Constants;
import com.gem.IsTimeData;
import com.gem.protocol.HomeconfigGetactivitytipGetApi;
import com.gem.protocol.HomeconfigGetactivitytipGetResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: zhudewei
 * 时间： 2016/11/28 12:07
 * God bless the program never bug
 */

public class GemModel extends BaseModel {


    public GemModel(Context context) {
        super(context);
    }

    public void getGemData(String section_code, String url, int customerId, String version, final ImageView imageView) {
        HomeconfigGetactivitytipGetApi homeconfigGetactivitytipGetApi = new HomeconfigGetactivitytipGetApi();
        final HomeconfigGetactivitytipGetResponse homeconfigGetactivitytipGetResponse = homeconfigGetactivitytipGetApi.response;
        homeconfigGetactivitytipGetApi.request.customer_id = customerId;
        homeconfigGetactivitytipGetApi.request.version = version;
        homeconfigGetactivitytipGetApi.request.section_code = section_code;
        homeconfigGetactivitytipGetApi.request.url = url;
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params = Utils.transformJsonToMap(homeconfigGetactivitytipGetApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
