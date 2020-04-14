package cn.net.cyberway.model;

import android.content.Context;
import android.text.TextUtils;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.UserAppConst;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

import cn.net.cyberway.protocol.ThemeEntity;

/**
 * Created by Administrator on 2018/1/25.
 * 主题Model
 * lizc
 *
 * @Description
 */

public class ThemeModel extends BaseModel {
    public ThemeModel(Context context) {
        super(context);
    }

    private final String themeUrl = "app/home/theme";//请求主题接口Get

    /**
     * 获取主题
     *
     * @param what
     * @param newHttpResponse
     */
    public void getTheme(int what, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        final String mCommunityId = shared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da");
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
                            saveCache(UserAppConst.THEME, result);
                            try {
                                ThemeEntity themeEntity = GsonUtils.gsonToBean(result, ThemeEntity.class);
                                long time = themeEntity.getContent().getDefault_theme().getTabbar().getUpdated_at();
                                if (!TextUtils.isEmpty(time + "")) {
                                    saveCache(UserAppConst.THEMEUPDATETIME, time + "");
                                }
                                newHttpResponse.OnHttpResponse(what, result);
                            } catch (Exception e) {

                            }
                        }
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, false);
    }
}
