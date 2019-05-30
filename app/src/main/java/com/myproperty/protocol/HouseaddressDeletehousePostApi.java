
package com.myproperty.protocol;

import com.BeeFramework.model.HttpApi;

public class HouseaddressDeletehousePostApi extends HttpApi
{
  public HouseaddressDeletehousePostRequest request;
  public HouseaddressDeletehousePostResponse response;
  public static String apiURI="/app/houseAddress/deleteHouse";
  public HouseaddressDeletehousePostApi()
  {
     request=new HouseaddressDeletehousePostRequest();
     response=new HouseaddressDeletehousePostResponse();
  }
}
