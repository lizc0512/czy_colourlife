
package com.feed.protocol;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class ACTIVITY_FEED_CONTENT  implements Serializable
{
  public String start_time; //开始时间

  public String location; //地点

  public ACTIVITY_CATEGORY activity_categoty; //活动类型

  public String content; //  附言(选填)

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.start_time = jsonObject.optString("start_time");
    this.location = jsonObject.optString("location");
    ACTIVITY_CATEGORY activity_categoty = new ACTIVITY_CATEGORY();
    activity_categoty.fromJson(jsonObject.optJSONObject("activity_categoty"));
    this.activity_categoty = activity_categoty;
    this.content = jsonObject.optString("content");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("start_time", start_time);
    localItemObject.put("location", location);
    if(null != activity_categoty)
    {
      localItemObject.put("activity_categoty", activity_categoty.toJson());
    }
    localItemObject.put("content", content);
    return localItemObject;
  }
}
