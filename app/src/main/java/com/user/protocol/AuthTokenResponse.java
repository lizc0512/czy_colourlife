package com.user.protocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 创建时间 : 2017/4/10.
 * 编写人 :  ${yuansk}
 * 功能描述:
 * 版本:
 */

public class AuthTokenResponse
{

    public String token_type;

    public String expires_in; // access_token的有效时间，单位是秒

    public String access_token; // 就是认证后在header中使用的身份认证token

    public String refresh_token; // 用于access_token过期后用新refresh_token获取新的access_token,不用反复提交username和password获取新的token

    public void fromJson(JSONObject jsonObject) throws JSONException
    {
        if( null == jsonObject ) {
            return ;
        }
        this.token_type = jsonObject.optString("token_type");
        this.expires_in = jsonObject.optString("expires_in");
        this.access_token = jsonObject.optString("access_token");
        this.refresh_token = jsonObject.optString("refresh_token");
        return;
    }
}
