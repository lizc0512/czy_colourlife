package com.scanCode.protocol;

import com.BeeFramework.model.HttpApi;

/**
 * Created by chengyun on 2016/1/18.
 */
public class ConfirmUrlApi extends HttpApi{
    public ConfirmUrlRequest request;
    public ConfirmUrlRespon response;
    public static String apiURI="/1.0/formatUrl/index";
    public ConfirmUrlApi()
    {
        request = new ConfirmUrlRequest();
        response=new ConfirmUrlRespon();
    }
}
