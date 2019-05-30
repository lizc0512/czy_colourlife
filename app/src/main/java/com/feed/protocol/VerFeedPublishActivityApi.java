
package com.feed.protocol;
import com.BeeFramework.model.HttpApi;

public class VerFeedPublishActivityApi extends HttpApi
{
  public VerFeedPublishActivityRequest request;
  public VerFeedPublishActivityResponse response;
  public static String apiURI="app/linli/publishActivity";
  public VerFeedPublishActivityApi()
  {
     request=new VerFeedPublishActivityRequest();
     response=new VerFeedPublishActivityResponse();
  }
}
