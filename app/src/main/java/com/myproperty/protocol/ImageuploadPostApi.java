
package com.myproperty.protocol;

import com.BeeFramework.model.HttpApi;

public class ImageuploadPostApi extends HttpApi
{
  public ImageuploadPostRequest request;
  public ImageuploadPostResponse response;
  public static String apiURI="/app/image/upload";
  public ImageuploadPostApi()
  {
     request=new ImageuploadPostRequest();
     response=new ImageuploadPostResponse();
  }
}
