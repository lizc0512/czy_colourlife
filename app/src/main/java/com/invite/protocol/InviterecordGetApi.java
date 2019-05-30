
package com.invite.protocol;
import com.BeeFramework.model.HttpApi;

public class InviterecordGetApi extends HttpApi
{
  public InviterecordGetRequest request;
  public InviterecordGetResponse response;
  public static String apiURI="/1.0/inviteRecord";
  public InviterecordGetApi()
  {
     request=new InviterecordGetRequest();
     response=new InviterecordGetResponse();
  }
}
