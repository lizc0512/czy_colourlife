
package com.Neighborhood.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FeedActivityfeedlistRequest
{
  public String community_id;

  public int activity_type_id;

  public int page;

  public int per_page;

  public String from_uid;//用户Id 未登录传0

  public String province_id;//省份ID

  public String city_id;//城市Id

  public String district_id;//行政区ID 比如：龙华新区的Id

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.community_id = jsonObject.optString("community_id");
    this.activity_type_id = jsonObject.optInt("activity_type_id");
    this.page = jsonObject.optInt("page");
    this.per_page = jsonObject.optInt("per_page");
    this.from_uid = jsonObject.getString("from_uid");
    this.province_id = jsonObject.getString("province_id");
    this.city_id = jsonObject.getString("city_id");
    this.district_id = jsonObject.getString("district_id");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("community_id", community_id);
    localItemObject.put("activity_type_id", activity_type_id);
    localItemObject.put("page", page);
    localItemObject.put("per_page", per_page);
    localItemObject.put("from_uid",from_uid);
    localItemObject.put("province_id",province_id);
    localItemObject.put("city_id",city_id);
    localItemObject.put("district_id",district_id);
    return localItemObject;
  }
}
