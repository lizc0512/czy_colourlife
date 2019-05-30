
package com.Neighborhood.protocol;

import com.BeeFramework.model.HttpApi;

public class FeedPublishShareApi extends HttpApi
{
  public FeedPublishShareRequest request;
  public FeedPublishShareResponse response;
  public static String apiURI="app/linli/publishShare";
  public FeedPublishShareApi()
  {
     request=new FeedPublishShareRequest();
     response=new FeedPublishShareResponse();
  }
}
