
package com.mycarinfo.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class COLOURTICKETCARCOLOURINFOLIST
{
  public int id;

  public String colour;

  public String colour_rgb;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.id = jsonObject.optInt("id");
    this.colour = jsonObject.optString("colour");
    this.colour_rgb = jsonObject.optString("colour_rgb");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("id", id);
    localItemObject.put("colour", colour);
    localItemObject.put("colour_rgb", colour_rgb);
    return localItemObject;
  }
}
