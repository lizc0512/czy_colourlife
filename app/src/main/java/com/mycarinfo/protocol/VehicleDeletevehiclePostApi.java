
package com.mycarinfo.protocol;

import com.BeeFramework.model.HttpApi;

public class VehicleDeletevehiclePostApi extends HttpApi
{
  public VehicleDeletevehiclePostRequest request;
  public VehicleDeletevehiclePostResponse response;
  public static String apiURI="/app/vehicle/deleteVehicle";
  public VehicleDeletevehiclePostApi()
  {
     request=new VehicleDeletevehiclePostRequest();
     response=new VehicleDeletevehiclePostResponse();
  }
}
