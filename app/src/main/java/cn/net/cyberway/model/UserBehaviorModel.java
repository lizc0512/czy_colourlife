package cn.net.cyberway.model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.update.activity.UpdateVerSion;
import com.user.Utils.TokenUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

import static cn.net.cyberway.utils.BuryingPointUtils.channel;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.model
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/1/15 19:15
 * @change
 * @chang time
 * @class describe
 */
public class UserBehaviorModel extends BaseModel {
    private String batchUploadUrl = "app/behavior/batchUpload";
    private Context mContext;

    public UserBehaviorModel(Context context) {
        super(context);
        mContext = context;
    }

    public void uploadUserBehavior(int what, String data, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("data", data);
        params.put("version", UpdateVerSion.showVersionName(UpdateVerSion.getVersionName(mContext)));
        params.put("device_name", TokenUtils.getDeviceBrand() + TokenUtils.getDeviceType());
        params.put("device_ip", TokenUtils.getIPAddress(mContext));
        params.put("source", channel);
        params.put("native", 1);
        String uploadRecord = GsonUtils.gsonString(params);
        params.clear();
        params.put("record", uploadRecord);
        final Request<String> request = NoHttp.createStringRequest(
                RequestEncryptionUtils.postCombileMD5(mContext, 13, batchUploadUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
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
