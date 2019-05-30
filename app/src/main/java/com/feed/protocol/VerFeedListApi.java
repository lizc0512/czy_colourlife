
package com.feed.protocol;

import com.BeeFramework.model.HttpApi;

public class VerFeedListApi extends HttpApi
{
  public VerFeedListRequest request;
  public VerFeedListResponse response;
  public static String apiURI="app/linli/listFeed";
  public VerFeedListApi()
  {
     request=new VerFeedListRequest();
     response=new VerFeedListResponse();
  }
}


