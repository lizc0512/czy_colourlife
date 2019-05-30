
package com.user.protocol;

import com.BeeFramework.model.HttpApi;

public class CustomerGetApi extends HttpApi
{
  public CustomerGetRequest request;
  public CustomerGetResponse response;
  public static String apiURI="/1.0/customer";
  public CustomerGetApi()
  {
     request=new CustomerGetRequest();
     response=new CustomerGetResponse();
  }
}
