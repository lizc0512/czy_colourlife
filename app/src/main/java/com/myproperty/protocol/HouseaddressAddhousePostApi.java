
package com.myproperty.protocol;

import com.BeeFramework.model.HttpApi;

public class HouseaddressAddhousePostApi extends HttpApi
{
  public HouseaddressAddhousePostRequest request;
  public HouseaddressAddhousePostResponse response;
  public static String apiURI="/app/houseAddress/addHouse";
  public HouseaddressAddhousePostApi()
  {
     request=new HouseaddressAddhousePostRequest();
     response=new HouseaddressAddhousePostResponse();
  }
}
