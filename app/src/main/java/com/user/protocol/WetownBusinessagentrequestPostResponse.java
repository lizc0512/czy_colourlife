
package com.user.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WetownBusinessagentrequestPostResponse
{
  public int r;

  public String reason;

  public String result;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.r = jsonObject.optInt("r");
    this.reason = jsonObject.optString("reason");
    this.result = jsonObject.optString("result");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("r", r);
    localItemObject.put("reason", reason);
    localItemObject.put("result", result);
    return localItemObject;
  }
}
