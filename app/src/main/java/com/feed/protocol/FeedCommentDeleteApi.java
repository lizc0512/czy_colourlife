
package com.feed.protocol;

import com.BeeFramework.model.HttpApi;

public class FeedCommentDeleteApi extends HttpApi
{
  public FeedCommentDeleteRequest request;
  public FeedCommentDeleteResponse response;
  public static String apiURI="app/linli/deleteComment";
  public FeedCommentDeleteApi()
  {
     request=new FeedCommentDeleteRequest();
     response=new FeedCommentDeleteResponse();
  }
}
