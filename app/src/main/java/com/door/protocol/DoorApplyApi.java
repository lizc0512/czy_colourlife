package com.door.protocol;

import com.BeeFramework.model.HttpApi;

/**
 * Created by chengyun on 2016/1/6.
 */
public class DoorApplyApi extends HttpApi {
    public DoorApplyRequest request;
    public DoorApplyRespone respone;
    public static String apiURI="/:ver/wetown/applyApply4mobile";
    public DoorApplyApi() {
        request = new DoorApplyRequest();
        respone = new DoorApplyRespone();
    }


}
