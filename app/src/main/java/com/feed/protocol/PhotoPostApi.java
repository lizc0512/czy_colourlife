
package com.feed.protocol;

import com.BeeFramework.model.HttpApi;

public class PhotoPostApi extends HttpApi
{
  public PhotoPostRequest request;
  public PhotoPostResponse response;
  public PhotoPostApi()
  {
     request=new PhotoPostRequest();
     response=new PhotoPostResponse();
  }
}
