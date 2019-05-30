package com.door.protocol;

import com.BeeFramework.model.HttpApi;

/**
 * Created by chengyun on 2016/1/11.
 */
public class DoorAutorReAuthorizeApi extends HttpApi {
    public DoorAutorReAuthorizeRequest request;
    public DoorApplyRespone respone;
    public static String apiURI="/:ver/wetown/authorizationAuthorize";
    public DoorAutorReAuthorizeApi() {
        request = new DoorAutorReAuthorizeRequest();
        respone = new DoorApplyRespone();
    }


}
