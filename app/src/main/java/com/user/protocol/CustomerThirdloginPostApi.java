
package com.user.protocol;

import com.BeeFramework.model.HttpApi;

public class CustomerThirdloginPostApi extends HttpApi
{
  public CustomerThirdloginPostRequest request;
  public CustomerThirdloginPostResponse response;
  public static String apiURI="/:ver/customer/thirdLogin";
  public CustomerThirdloginPostApi()
  {
     request=new CustomerThirdloginPostRequest();
     response=new CustomerThirdloginPostResponse();
  }
}
