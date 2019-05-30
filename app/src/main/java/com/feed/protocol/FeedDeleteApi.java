
package com.feed.protocol;

import com.BeeFramework.model.HttpApi;

public class FeedDeleteApi extends HttpApi
{
  public FeedDeleteRequest request;
  public FeedDeleteResponse response;
  public static String apiURI="app/linli/deleteFeed";
  public FeedDeleteApi()
  {
     request=new FeedDeleteRequest();
     response=new FeedDeleteResponse();
  }
}
