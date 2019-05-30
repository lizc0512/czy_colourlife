package com.door.protocol;

import com.BeeFramework.model.HttpApi;

/**
 * Created by chengyun on 2016/1/11.
 */
public class DoorAutorCancelApi  extends HttpApi {
    public DoorAutorCancelRequest request;
    public DoorApplyRespone respone;
    public static String apiURI="/:ver/wetown/authorizationUnauthorize";
    public DoorAutorCancelApi() {
        request = new DoorAutorCancelRequest();
        respone = new DoorApplyRespone();
    }

}
