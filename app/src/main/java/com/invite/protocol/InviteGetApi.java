
package com.invite.protocol;
import com.BeeFramework.model.HttpApi;

public class InviteGetApi extends HttpApi
{
  public InviteGetRequest request;
  public InviteGetResponse response;
  public static String apiURI="/1.0/invite";
  public InviteGetApi()
  {
     request=new InviteGetRequest();
     response=new InviteGetResponse();
  }
}
