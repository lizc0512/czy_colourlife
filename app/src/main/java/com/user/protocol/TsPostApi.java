
package com.user.protocol;

import com.BeeFramework.model.HttpApi;

public class TsPostApi extends HttpApi
{
  public TsPostRequest request;
  public TsPostResponse response;
  public static String apiURI="/:ver/ts";
  public TsPostApi()
  {
     request=new TsPostRequest();
     response=new TsPostResponse();
  }
}
