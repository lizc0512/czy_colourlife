
package com.message.protocol;
import com.BeeFramework.model.HttpApi;

public class PushmessageDeleteDeleteApi extends HttpApi
{
  public PushmessageDeleteDeleteRequest request;
  public PushmessageDeleteDeleteResponse response;
  public static String apiURI="/1.0/pushMessage/DeleteSingle";
  public PushmessageDeleteDeleteApi()
  {
     request=new PushmessageDeleteDeleteRequest();
     response=new PushmessageDeleteDeleteResponse();
  }
}
