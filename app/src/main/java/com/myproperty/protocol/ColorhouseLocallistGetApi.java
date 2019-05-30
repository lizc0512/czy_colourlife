
package com.myproperty.protocol;

import com.BeeFramework.model.HttpApi;

public class ColorhouseLocallistGetApi extends HttpApi
{
  public ColorhouseLocallistGetRequest request;
  public ColorhouseLocallistGetResponse response;
  public static String apiURI="/app/localFp/list";
  public ColorhouseLocallistGetApi()
  {
     request=new ColorhouseLocallistGetRequest();
     response=new ColorhouseLocallistGetResponse();
  }
}
