
package com.user.protocol;

import com.BeeFramework.model.HttpApi;

public class CustomerCompanyloginPostApi extends HttpApi
{
  public CustomerCompanyloginPostRequest request;
  public CustomerCompanyloginPostResponse response;
  public static String apiURI="/:ver/customer/companyLogin";
  public CustomerCompanyloginPostApi()
  {
     request=new CustomerCompanyloginPostRequest();
     response=new CustomerCompanyloginPostResponse();
  }
}
