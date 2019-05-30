package com.door.protocol;

import com.BeeFramework.model.HttpApi;

/**
 * Created by chengyun on 2016/1/8.
 */
public class CommunityListApi extends HttpApi {
    public CommunityListResponse response;
    public static String apiURI="/:ver/wetown/userCommunitylist";
    public CommunityListApi()
    {

        response=new CommunityListResponse();
    }
}
