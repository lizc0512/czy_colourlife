
package com.invite.protocol;
import com.BeeFramework.model.HttpApi;

public class InvitesuccessrecordGetApi extends HttpApi
{
  public InvitesuccessrecordGetRequest request;
  public InvitesuccessrecordGetResponse response;
  public static String apiURI="/1.0/inviteSuccessRecord";
  public InvitesuccessrecordGetApi()
  {
     request=new InvitesuccessrecordGetRequest();
     response=new InvitesuccessrecordGetResponse();
  }
}
