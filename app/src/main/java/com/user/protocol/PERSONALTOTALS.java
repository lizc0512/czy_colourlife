
package com.user.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PERSONALTOTALS
{
  public String url;//跳转url

  public String title;//title

  public String img;


  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.url = jsonObject.optString("url");
    this.title = jsonObject.optString("title");
    this.img = jsonObject.optString("img");
    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("url", url);
    localItemObject.put("title", title);
    localItemObject.put("img", img);
    return localItemObject;
  }
}
