
package com.mycarinfo.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class COLOURTICKETCARBRANDINFO
{
  public int id;

  public String name;

  public String prefix;

  public String img_path;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    this.id = jsonObject.optInt("id");
    this.name = jsonObject.optString("name");
    this.prefix = jsonObject.optString("prefix");
    this.img_path = jsonObject.optString("img_path");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    localItemObject.put("id", id);
    localItemObject.put("name", name);
    localItemObject.put("prefix", prefix);
    localItemObject.put("img_path", img_path);
    return localItemObject;
  }
}
