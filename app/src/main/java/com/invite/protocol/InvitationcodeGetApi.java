
package com.invite.protocol;
import com.BeeFramework.model.HttpApi;

public class InvitationcodeGetApi extends HttpApi
{
  public InvitationcodeGetRequest request;
  public InvitationcodeGetResponse response;
  public static String apiURI="/1.0/invitationCode";
  public InvitationcodeGetApi()
  {
     request=new InvitationcodeGetRequest();
     response=new InvitationcodeGetResponse();
  }
}
