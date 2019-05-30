package com.notification.protocol;

import com.BeeFramework.model.HttpApi;

public class NotifycategoryGetApi extends HttpApi
{
    public NotifycategoryGetRequest request;
    public NotifycategoryGetResponse response;
    public static String apiURI="/1.0/notifyCategory";
    public NotifycategoryGetApi()
    {
        request=new NotifycategoryGetRequest();
        response=new NotifycategoryGetResponse();
    }
}


