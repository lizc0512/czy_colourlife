
package com.myproperty.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class HouseaddressAddhousePostResponse
{
  public int code;

  public String message;

  public CTMYHOUSEADDHOUSECONTENT content;

  public String contentEncrypt;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }
    this.code = jsonObject.optInt("code");
    this.message = jsonObject.optString("message");
    CTMYHOUSEADDHOUSECONTENT content = new CTMYHOUSEADDHOUSECONTENT();
    content.fromJson(jsonObject.optJSONObject("content"));
    this.content = content;
    this.contentEncrypt = jsonObject.optString("contentEncrypt");
    return;
  }

}
