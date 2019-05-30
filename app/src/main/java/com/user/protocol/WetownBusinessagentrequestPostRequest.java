
package com.user.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WetownBusinessagentrequestPostRequest
{
  public String module;

  public String func;

  public String params;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.module = jsonObject.optString("module");
    this.func = jsonObject.optString("func");
    this.params = jsonObject.optString("params");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("module", module);
    localItemObject.put("func", func);
    localItemObject.put("params", params);
    return localItemObject;
  }
}
