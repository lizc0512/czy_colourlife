package com.door.protocol;

import com.BeeFramework.model.HttpApi;

/**
 * Created by chengyun on 2016/1/11.
 */
public class DoorAutorApi extends HttpApi {
    public DoorAutorRequest request;
    public DoorApplyRespone respone;
    public static String apiURI="/:ver/wetown/authorizationAuthorize4mobile";
    public DoorAutorApi() {
        request = new DoorAutorRequest();
        respone = new DoorApplyRespone();
    }


}
