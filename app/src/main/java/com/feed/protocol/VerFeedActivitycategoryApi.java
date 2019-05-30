
package com.feed.protocol;
import com.BeeFramework.model.HttpApi;

public class VerFeedActivitycategoryApi extends HttpApi
{
  public VerFeedActivitycategoryRequest request;
  public VerFeedActivitycategoryResponse response;
  public static String apiURI="app/linli/activityCategory";
  public VerFeedActivitycategoryApi()
  {
     request=new VerFeedActivitycategoryRequest();
     response=new VerFeedActivitycategoryResponse();
  }
}
