
package com.user.protocol;

import com.BeeFramework.model.HttpApi;

public class AuthPostApi extends HttpApi
{
  public AuthPostRequest request;
  public AuthPostResponse response;
  public static String apiURI="/:ver/auth";
  public AuthPostApi()
  {
     request=new AuthPostRequest();
     response=new AuthPostResponse();
  }
}
