
package com.message.protocol;
import com.BeeFramework.model.HttpApi;

public class WetownMessagecentergetlistPostApi extends HttpApi
{
  public WetownMessagecentergetlistPostRequest request;
  public WetownMessagecentergetlistPostResponse response;
  public static String apiURI="/1.0/wetown/messagecenterGetlist";
  public WetownMessagecentergetlistPostApi()
  {
     request=new WetownMessagecentergetlistPostRequest();
     response=new WetownMessagecentergetlistPostResponse();
  }
}
