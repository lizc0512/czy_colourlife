
package com.myproperty.protocol;

import com.BeeFramework.model.HttpApi;

public class VehicleBindmealPostApi extends HttpApi
{
  public VehicleBindmealPostRequest request;
  public VehicleBindmealPostResponse response;
  public static String apiURI="/app/vehicle/bindMeal";
  public VehicleBindmealPostApi()
  {
     request=new VehicleBindmealPostRequest();
     response=new VehicleBindmealPostResponse();
  }
}
