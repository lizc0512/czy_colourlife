
package com.feed.protocol;

import com.BeeFramework.model.HttpApi;

public class VerFeedUnlikeApi extends HttpApi
{
  public VerFeedUnlikeRequest request;
  public VerFeedUnlikeResponse response;
  public static String apiURI="app/linli/unlikeFeed";
  public VerFeedUnlikeApi()
  {
     request=new VerFeedUnlikeRequest();
     response=new VerFeedUnlikeResponse();
  }
}
