package com.user.protocol;

import com.BeeFramework.model.HttpApi;

/**
 * 创建时间 : 2017/8/26.
 * 编写人 :  ${yuansk}
 * 功能描述:
 * 版本:
 */

public class SingleDeviceLoginOutApi extends HttpApi
{
    public SingleDeviceLoginOutResponse response;
    public static String apiURI = "single/device/logout";
    public SingleDeviceLoginOutApi() {
        response = new SingleDeviceLoginOutResponse();
    }
}
