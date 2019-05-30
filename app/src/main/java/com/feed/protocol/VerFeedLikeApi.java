
package com.feed.protocol;

import com.BeeFramework.model.HttpApi;

public class VerFeedLikeApi extends HttpApi
{
  public VerFeedLikeRequest request;
  public VerFeedLikeResponse response;
  public static String apiURI="app/linli/likeFeed";
  public VerFeedLikeApi()
  {
     request=new VerFeedLikeRequest();
     response=new VerFeedLikeResponse();
  }
}
