
package com.myproperty.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HouseaddressAddhousePostRequest
{
  public String customer_id; //用户

  public String community_uuid; //小区uuid

  public String build_uuid; //楼栋uuid

  public String build_name; //楼栋名称

  public String unit_uuid; //单元uuid

  public String unit_name; //单元名称

  public String room_name; //房间名称

  public String room_uuid; //房间uuid

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.customer_id = jsonObject.optString("customer_id");
    this.community_uuid = jsonObject.optString("community_uuid");
    this.build_uuid = jsonObject.optString("build_uuid");
    this.build_name = jsonObject.optString("build_name");
    this.unit_uuid = jsonObject.optString("unit_uuid");
    this.unit_name = jsonObject.optString("unit_name");
    this.room_name = jsonObject.optString("room_name");
    this.room_uuid = jsonObject.optString("room_uuid");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("customer_id", customer_id);
    localItemObject.put("community_uuid", community_uuid);
    localItemObject.put("build_uuid", build_uuid);
    localItemObject.put("build_name", build_name);
    localItemObject.put("unit_uuid", unit_uuid);
    localItemObject.put("unit_name", unit_name);
    localItemObject.put("room_name", room_name);
    localItemObject.put("room_uuid", room_uuid);
    return localItemObject;
  }
}
