package com.user.protocol;

import com.BeeFramework.model.HttpApi;

/**
 * 创建时间 : 2017/4/10.
 * 编写人 :  ${yuansk}
 * 功能描述:
 * 版本:
 */

public class AuthTokenApi extends HttpApi


{
    public AuthTokenRequest request;
    public AuthTokenResponse response;
    public static String apiURI="/oauth/token";
    public AuthTokenApi()
    {
        request=new AuthTokenRequest();
        response=new AuthTokenResponse();
    }


}
