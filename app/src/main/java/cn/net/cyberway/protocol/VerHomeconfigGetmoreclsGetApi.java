
package cn.net.cyberway.protocol;

import com.BeeFramework.model.HttpApi;

public class VerHomeconfigGetmoreclsGetApi extends HttpApi
{
  public VerHomeconfigGetmoreclsGetRequest request;
  public VerHomeconfigGetmoreclsGetResponse response;
  public static String apiURI="/:ver/homeConfig/getMoreCls";
  public VerHomeconfigGetmoreclsGetApi()
  {
     request=new VerHomeconfigGetmoreclsGetRequest();
     response=new VerHomeconfigGetmoreclsGetResponse();
  }
}
