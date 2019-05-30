
package com.myproperty.protocol;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CTMYHOUSEGETHOUSECONTENTDATA
{
  public int id;

  public int customer_id;

  public String community_uuid;

  public String build_uuid;

  public String unit_uuid;

  public String room_uuid;

  public String build_name;

  public String room_name;

  public String unit_name;

  public int identity_state;

  public int identity_id;

  public int bind_mealticket;

  public int state;

  public int create_time;

  public String city;

  public String district;

  public String community_name;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }
    this.id = jsonObject.optInt("id");
    this.customer_id = jsonObject.optInt("customer_id");
    this.community_uuid = jsonObject.optString("community_uuid");
    this.build_uuid = jsonObject.optString("build_uuid");
    this.unit_uuid = jsonObject.optString("unit_uuid");
    this.room_uuid = jsonObject.optString("room_uuid");
    this.build_name = jsonObject.optString("build_name");
    this.room_name = jsonObject.optString("room_name");
    this.unit_name = jsonObject.optString("unit_name");
    this.identity_state = jsonObject.optInt("identity_state");
    this.identity_id = jsonObject.optInt("identity_id");
    this.bind_mealticket = jsonObject.optInt("bind_mealticket");
    this.state = jsonObject.optInt("state");
    this.create_time = jsonObject.optInt("create_time");
    this.city = jsonObject.optString("city");
    this.district = jsonObject.optString("district");
    this.community_name = jsonObject.optString("community_name");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    localItemObject.put("id", id);
    localItemObject.put("customer_id", customer_id);
    localItemObject.put("community_uuid", community_uuid);
    localItemObject.put("build_uuid", build_uuid);
    localItemObject.put("unit_uuid", unit_uuid);
    localItemObject.put("room_uuid", room_uuid);
    localItemObject.put("build_name", build_name);
    localItemObject.put("room_name", room_name);
    localItemObject.put("unit_name", unit_name);
    localItemObject.put("identity_state", identity_state);
    localItemObject.put("identity_id", identity_id);
    localItemObject.put("bind_mealticket", bind_mealticket);
    localItemObject.put("state", state);
    localItemObject.put("create_time", create_time);
    localItemObject.put("city", city);
    localItemObject.put("district", district);
    localItemObject.put("community_name", community_name);
    return localItemObject;
  }
}
