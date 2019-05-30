
package com.Neighborhood.protocol;

import com.BeeFramework.model.HttpApi;

public class FeedAdlistApi extends HttpApi
{
  public FeedAdlistRequest request;
  public FeedAdlistResponse response;
  public static String apiURI="app/linli/listAd";
  public FeedAdlistApi()
  {
     request=new FeedAdlistRequest();
     response=new FeedAdlistResponse();
  }
}
