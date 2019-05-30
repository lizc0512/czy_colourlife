package com.door.protocol;

import com.BeeFramework.model.HttpApi;

/**
 * Created by chengyun on 2016/1/7.
 */
public class DoorAuthorListApi extends HttpApi {

    public DoorApplyListResponse response;
    public static String apiURI="/:ver/wetown/authorizationGetList4top";
    public DoorAuthorListApi(){

        response = new DoorApplyListResponse();
    }
}
