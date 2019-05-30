
package com.user.protocol;

import com.BeeFramework.model.HttpApi;

public class PersonalgettotalsGetApi extends HttpApi
{
  public PersonalgettotalsGetRequest request;
  public PersonalgettotalsGetResponse response;
  public static String apiURI="/:ver/personalGetTotals";
  public PersonalgettotalsGetApi()
  {
     request=new PersonalgettotalsGetRequest();
     response=new PersonalgettotalsGetResponse();
  }
}
