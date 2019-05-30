
package com.feed.protocol;

import com.BeeFramework.model.HttpApi;

public class VerFeedCommentApi extends HttpApi
{
  public VerFeedCommentRequest request;
  public VerFeedCommentResponse response;
  public static String apiURI="app/linli/commentFeed";
  public VerFeedCommentApi()
  {
     request=new VerFeedCommentRequest();
     response=new VerFeedCommentResponse();
  }
}
