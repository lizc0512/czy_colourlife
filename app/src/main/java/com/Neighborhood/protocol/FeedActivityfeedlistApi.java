
package com.Neighborhood.protocol;

import com.BeeFramework.model.HttpApi;

public class FeedActivityfeedlistApi extends HttpApi
{
  public FeedActivityfeedlistRequest request;
  public FeedActivityfeedlistResponse response;
  public static String apiURI="app/linli/listActivityFeed";
  public FeedActivityfeedlistApi()
  {
     request=new FeedActivityfeedlistRequest();
     response=new FeedActivityfeedlistResponse();
  }
}
