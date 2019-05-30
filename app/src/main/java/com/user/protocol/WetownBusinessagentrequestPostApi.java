
package com.user.protocol;

import com.BeeFramework.model.HttpApi;

public class WetownBusinessagentrequestPostApi extends HttpApi
{
  public WetownBusinessagentrequestPostRequest request;
  public WetownBusinessagentrequestPostResponse response;
  public static String apiURI="/:ver/wetown/businessagentRequest";
  public WetownBusinessagentrequestPostApi()
  {
     request=new WetownBusinessagentrequestPostRequest();
     response=new WetownBusinessagentrequestPostResponse();
  }
}
