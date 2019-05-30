
package com.message.protocol;
import com.BeeFramework.model.HttpApi;

public class WetownMessagecentersetallreadGetApi extends HttpApi
{
  public WetownMessagecentersetallreadGetRequest request;
  public WetownMessagecentersetallreadGetResponse response;
  public static String apiURI="/1.0/wetown/messagecenterSetallread";
  public WetownMessagecentersetallreadGetApi()
  {
     request=new WetownMessagecentersetallreadGetRequest();
     response=new WetownMessagecentersetallreadGetResponse();
  }
}
