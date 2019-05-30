
package com.gem.protocol;

import com.BeeFramework.model.HttpApi;

public class HomeconfigGetactivitytipGetApi extends HttpApi
{
  public HomeconfigGetactivitytipGetRequest request;
  public HomeconfigGetactivitytipGetResponse response;
  public static String apiURI="/1.0/homeConfig/getActivityTip";
  public HomeconfigGetactivitytipGetApi()
  {
     request=new HomeconfigGetactivitytipGetRequest();
     response=new HomeconfigGetactivitytipGetResponse();
  }
}
