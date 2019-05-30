package com.door.protocol;

import com.BeeFramework.model.HttpApi;

/**
 * Created by chengyun on 2016/1/11.
 */
public class DoorAutorApproveNewApi extends HttpApi {
    public DoorAutorApproveNewRequest request;
    public DoorApplyRespone respone;
    public static String apiURI="/:ver/wetown/applyApprove";
    public DoorAutorApproveNewApi() {
        request = new DoorAutorApproveNewRequest();
        respone = new DoorApplyRespone();
    }


}
