
package com.myproperty.protocol;

import com.BeeFramework.model.HttpApi;

public class HouseaddressPropretyifyPostApi extends HttpApi
{
  public HouseaddressPropretyifyPostRequest request;
  public HouseaddressPropretyifyPostResponse response;
  public static String apiURI="/app/verify/create";
  public HouseaddressPropretyifyPostApi()
  {
     request=new HouseaddressPropretyifyPostRequest();
     response=new HouseaddressPropretyifyPostResponse();
  }
}
