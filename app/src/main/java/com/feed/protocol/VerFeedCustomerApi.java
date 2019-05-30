
package com.feed.protocol;

import com.BeeFramework.model.HttpApi;

public class VerFeedCustomerApi extends HttpApi
{
  public VerFeedCustomerRequest request;
  public VerFeedCustomerResponse response;
  public static String apiURI="app/linli/customerFeed";
  public VerFeedCustomerApi()
  {
     request=new VerFeedCustomerRequest();
     response=new VerFeedCustomerResponse();
  }
}
