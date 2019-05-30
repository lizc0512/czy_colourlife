
package com.user.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CustomerRegionGetResponse
{
  public String city;

  public String id;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.city = jsonObject.optString("city");
    this.id = jsonObject.optString("id");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("city", city);
    localItemObject.put("id", id);
    return localItemObject;
  }
}
