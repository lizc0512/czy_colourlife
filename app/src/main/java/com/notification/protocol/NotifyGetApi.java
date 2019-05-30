package com.notification.protocol;

import com.BeeFramework.model.HttpApi;

public class NotifyGetApi extends HttpApi
{
    public NotifyGetRequest request;
    public NotifyGetResponse response;
    public static String apiURI="/1.0/notify";
    public NotifyGetApi()
    {
        request=new NotifyGetRequest();
        response=new NotifyGetResponse();
    }
}


