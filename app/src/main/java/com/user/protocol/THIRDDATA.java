
package com.user.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class THIRDDATA
{
  public int is_register;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.is_register = jsonObject.optInt("is_register");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("is_register", is_register);
    return localItemObject;
  }
}
