
package com.message.protocol;

import com.BeeFramework.model.HttpApi;

public class HomeconfigGetuserinfourlGetApi extends HttpApi
{
  public HomeconfigGetuserinfourlGetRequest request;
  public HomeconfigGetuserinfourlGetResponse response;
  public static String apiURI="/1.0/homeConfig/getUserInfoUrl";
  public HomeconfigGetuserinfourlGetApi()
  {
     request=new HomeconfigGetuserinfourlGetRequest();
     response=new HomeconfigGetuserinfourlGetResponse();
  }
}
