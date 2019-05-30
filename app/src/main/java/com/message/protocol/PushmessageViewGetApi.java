
package com.message.protocol;
import com.BeeFramework.model.HttpApi;

public class PushmessageViewGetApi extends HttpApi
{
  public PushmessageViewGetRequest request;
  public PushmessageViewGetResponse response;
  public static String apiURI="/1.0/pushMessage/detail";
  public PushmessageViewGetApi()
  {
     request=new PushmessageViewGetRequest();
     response=new PushmessageViewGetResponse();
  }
}
