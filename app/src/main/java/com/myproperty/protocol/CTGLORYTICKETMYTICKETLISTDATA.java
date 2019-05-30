
package com.myproperty.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class CTGLORYTICKETMYTICKETLISTDATA
{
  public String name;

  public String balance; //余额

  public String pano; //饭票标识

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }
    this.name = jsonObject.optString("name");
    this.balance = jsonObject.optString("balance");
    this.pano = jsonObject.optString("pano");

    return;
  }

}
