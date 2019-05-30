
package com.Neighborhood.protocol;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FeedAdlistRequest
{
  public String community_uuid; //小区ID
  public String from_uid;//用户Id 未登录传0


  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("community_uuid", community_uuid);
    localItemObject.put("from_uid",from_uid);
    return localItemObject;
  }
}
