
package com.myproperty.protocol;

import com.BeeFramework.model.HttpApi;

public class HouseaddressGetmyhouseGetApi extends HttpApi
{
  public HouseaddressGetmyhouseGetRequest request;
  public HouseaddressGetmyhouseGetResponse response;
  public static String apiURI="/app/houseAddress/getMyHouse";
  public HouseaddressGetmyhouseGetApi()
  {
     request=new HouseaddressGetmyhouseGetRequest();
     response=new HouseaddressGetmyhouseGetResponse();
  }
}
