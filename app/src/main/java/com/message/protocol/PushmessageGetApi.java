
package com.message.protocol;
import com.BeeFramework.model.HttpApi;

public class PushmessageGetApi extends HttpApi
{
  public PushmessageGetRequest request;
  public PushmessageGetResponse response;
  public static String apiURI="/1.0/pushMessage";
  public PushmessageGetApi()
  {
     request=new PushmessageGetRequest();
     response=new PushmessageGetResponse();
  }
}
