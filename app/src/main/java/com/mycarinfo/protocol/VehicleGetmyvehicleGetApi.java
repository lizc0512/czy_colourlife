
package com.mycarinfo.protocol;

import com.BeeFramework.model.HttpApi;

public class VehicleGetmyvehicleGetApi extends HttpApi
{
  public VehicleGetmyvehicleGetRequest request;
  public VehicleGetmyvehicleGetResponse response;
  public static String apiURI="/app/vehicle/getMyVehicle";
  public VehicleGetmyvehicleGetApi()
  {
     request=new VehicleGetmyvehicleGetRequest();
     response=new VehicleGetmyvehicleGetResponse();
  }
}
