package com.door.protocol;

import com.BeeFramework.model.HttpApi;

/**
 * Created by chengyun on 2016/1/4.
 */
public class DoorApi extends HttpApi{

    public DoorResponse response;
    public static String apiURI="/:ver/wetown/authorizationIsgranted";
    public DoorApi()
    {

        response=new DoorResponse();
    }
}
