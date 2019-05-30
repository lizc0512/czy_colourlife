
package com.mycarinfo.protocol;

import com.BeeFramework.model.HttpApi;

public class VehicleGetcolourGetApi extends HttpApi
{
  public VehicleGetcolourGetRequest request;
  public VehicleGetcolourGetResponse response;
  public static String apiURI="/app/vehicle/getColour";
  public VehicleGetcolourGetApi()
  {
     request=new VehicleGetcolourGetRequest();
     response=new VehicleGetcolourGetResponse();
  }
}
