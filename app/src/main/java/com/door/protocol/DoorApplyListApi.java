package com.door.protocol;

import com.BeeFramework.model.HttpApi;

/**
 * Created by chengyun on 2016/1/5.
 */
public class DoorApplyListApi extends HttpApi {

    public DoorApplyListResponse response;
    public static String apiURI="/:ver/wetown/authorizationGetList4topByToID";
    public DoorApplyListApi(){

        response = new DoorApplyListResponse();
    }
}
