
package com.feed.protocol;

import com.BeeFramework.model.HttpApi;

public class VerFeedPublishNormalApi extends HttpApi
{
  public VerFeedPublishNormalRequest request;
  public VerFeedPublishNormalResponse response;
  public static String apiURI="app/linli/publishNormal";
  public  VerFeedPublishNormalApi()
  {
     request=new VerFeedPublishNormalRequest();
     response=new VerFeedPublishNormalResponse();
  }
}
