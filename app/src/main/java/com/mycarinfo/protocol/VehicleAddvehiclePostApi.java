
package com.mycarinfo.protocol;

import com.BeeFramework.model.HttpApi;

public class VehicleAddvehiclePostApi extends HttpApi
{
  public VehicleAddvehiclePostRequest request;
  public VehicleAddvehiclePostResponse response;
  public static String apiURI="/app/vehicle/addVehicle";
  public VehicleAddvehiclePostApi()
  {
     request=new VehicleAddvehiclePostRequest();
     response=new VehicleAddvehiclePostResponse();
  }
}
