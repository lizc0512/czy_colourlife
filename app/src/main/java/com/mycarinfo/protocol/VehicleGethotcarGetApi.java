
package com.mycarinfo.protocol;

import com.BeeFramework.model.HttpApi;

public class VehicleGethotcarGetApi extends HttpApi
{
  public VehicleGethotcarGetRequest request;
  public VehicleGethotcarGetResponse response;
  public static String apiURI="/app/vehicle/getHotCar";
  public VehicleGethotcarGetApi()
  {
     request=new VehicleGethotcarGetRequest();
     response=new VehicleGethotcarGetResponse();
  }
}
