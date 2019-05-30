
package com.feed.protocol;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PROVICE
{
  public String id;

  public String name;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.id = jsonObject.optString("id");
    this.name = jsonObject.optString("name");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("id", id);
    localItemObject.put("name", name);
    return localItemObject;
  }
}
