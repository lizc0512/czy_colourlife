
package com.myproperty.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class CTMYHOUSEADDHOUSECONTENT
{
  public int house_id;
  public String message;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    this.house_id = jsonObject.optInt("house_id");
    this.message = jsonObject.optString("message");

    return;
  }
}
