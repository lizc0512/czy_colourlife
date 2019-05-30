
package com.myproperty.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class ColorhouseLocallistGetResponse
{
  public int code;

  public String message;

  public CTGLORYTICKETMYTICKETDATA data;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }
    this.code = jsonObject.optInt("code");
    this.message = jsonObject.optString("message");
    CTGLORYTICKETMYTICKETDATA data = new CTGLORYTICKETMYTICKETDATA();
    data.fromJson(jsonObject.optJSONObject("content"));
    this.data = data;

    return;
  }

}
