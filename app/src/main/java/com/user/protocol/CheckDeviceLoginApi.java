package com.user.protocol;

import com.BeeFramework.model.HttpApi;

/**
 * 创建时间 : 2017/8/26.
 * 编写人 :  ${yuansk}
 * 功能描述: 检查设备是否在别的设备登录
 * 版本:
 */

public class CheckDeviceLoginApi extends HttpApi {
    public CheckDeviceLoginResponse response;
    public static String apiURI = "single/device/checkLogin";

    public CheckDeviceLoginApi() {
        response = new CheckDeviceLoginResponse();
    }
}
