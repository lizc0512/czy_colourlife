
package com.Neighborhood.protocol;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BANNER
{
  public String id;

  public String img;

  public String name;

  public String url;

  public String start_time;

  public String stop_time;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.id = jsonObject.optString("id");
    this.img = jsonObject.optString("img");
    this.name = jsonObject.optString("name");
    this.url = jsonObject.optString("url");
    this.start_time = jsonObject.optString("start_time");
    this.stop_time = jsonObject.optString("stop_time");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("id", id);
    localItemObject.put("img", img);
    localItemObject.put("name", name);
    localItemObject.put("url", url);
    localItemObject.put("start_time", start_time);
    localItemObject.put("stop_time", stop_time);
    return localItemObject;
  }
}
