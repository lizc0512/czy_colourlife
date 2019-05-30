package com.door.protocol;

import com.BeeFramework.model.HttpApi;

/**
 * Created by chengyun on 2016/1/6.
 */
public class DoorAgainApplyApi extends HttpApi {
    public DoorAgainApplyRequest request;
    public DoorApplyRespone respone;
    public static String apiURI="/:ver/wetown/applyApply1";
    public DoorAgainApplyApi() {
        request = new DoorAgainApplyRequest();
        respone = new DoorApplyRespone();
    }
}
