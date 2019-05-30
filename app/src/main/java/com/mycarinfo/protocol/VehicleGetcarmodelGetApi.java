
package com.mycarinfo.protocol;

import com.BeeFramework.model.HttpApi;

public class VehicleGetcarmodelGetApi extends HttpApi
{
  public VehicleGetcarmodelGetRequest request;
  public VehicleGetcarmodelGetResponse response;
  public static String apiURI="/app/vehicle/getCarModel";
  public VehicleGetcarmodelGetApi()
  {
     request=new VehicleGetcarmodelGetRequest();
     response=new VehicleGetcarmodelGetResponse();
  }
}
