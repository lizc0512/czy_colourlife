
package com.mycarinfo.protocol;

import com.BeeFramework.model.HttpApi;

public class VehicleGetcarbrandGetApi extends HttpApi
{
  public VehicleGetcarbrandGetRequest request;
  public VehicleGetcarbrandGetResponse response;
  public static String apiURI="/app/vehicle/getCarBrand";
  public VehicleGetcarbrandGetApi()
  {
     request=new VehicleGetcarbrandGetRequest();
     response=new VehicleGetcarbrandGetResponse();
  }
}
