
package com.message.protocol;
import com.BeeFramework.model.HttpApi;

public class PushmessageSetreadGetApi extends HttpApi
{
  public PushmessageSetreadGetRequest request;
  public PushmessageSetreadGetResponse response;
  public static String apiURI="/1.0/pushMessage/setRead";
  public PushmessageSetreadGetApi()
  {
     request=new PushmessageSetreadGetRequest();
     response=new PushmessageSetreadGetResponse();
  }
}
