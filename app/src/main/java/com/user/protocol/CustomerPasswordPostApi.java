
package com.user.protocol;

import com.BeeFramework.model.HttpApi;

public class CustomerPasswordPostApi extends HttpApi
{
  public CustomerPasswordPostRequest request;
  public CustomerPasswordPostResponse response;
  public static String apiURI="/:ver/customer/password";
  public CustomerPasswordPostApi()
  {
     request=new CustomerPasswordPostRequest();
     response=new CustomerPasswordPostResponse();
  }
}
