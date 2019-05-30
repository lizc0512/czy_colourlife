
package com.user.protocol;

import com.BeeFramework.model.HttpApi;

public class CustomerLogoutPostApi extends HttpApi
{
  public CustomerLogoutPostRequest request;
  public CustomerLogoutPostResponse response;
  public static String apiURI="/:ver/customer/logout";
  public CustomerLogoutPostApi()
  {
     request=new CustomerLogoutPostRequest();
     response=new CustomerLogoutPostResponse();
  }
}
