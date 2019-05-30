
package com.user.protocol;

import com.BeeFramework.model.HttpApi;

public class WetownUsergettokenGetApi extends HttpApi
{
  public WetownUsergettokenGetResponse response;
  public static String apiURI="single/device/login";
  public WetownUsergettokenGetApi()
  {
     response=new WetownUsergettokenGetResponse();
  }
}
