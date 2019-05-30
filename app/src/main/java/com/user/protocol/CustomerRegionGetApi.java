
package com.user.protocol;

import com.BeeFramework.model.HttpApi;

public class CustomerRegionGetApi extends HttpApi
{
  public CustomerRegionGetRequest request;
  public CustomerRegionGetResponse response;
  public static String apiURI="/:ver/customer/region";
  public CustomerRegionGetApi()
  {
     request=new CustomerRegionGetRequest();
     response=new CustomerRegionGetResponse();
  }
}
