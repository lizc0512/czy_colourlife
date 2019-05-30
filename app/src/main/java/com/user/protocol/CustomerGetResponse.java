
package com.user.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CustomerGetResponse
{
  public String id;

  public String name; //姓名                     

  public String nickname; //昵称

  public String username; //注册成功的业主用户账号

  public String mobile;

  public String community_id; //小区编号

  public String build_id; //楼栋编号

  public String build_name; //楼栋

  public String community_name; //小区

  public String room; //门牌号

  public String regions;//省市区

  public String province;//省

  public String city;//市

  public String district;//区
  public String gender;//性别

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }
    this.gender = jsonObject.optString("gender");
    if (!jsonObject.isNull("id")){
      this.id = jsonObject.optString("id");
    }
    this.name = jsonObject.optString("name");
    this.nickname = jsonObject.optString("nickname");
    this.username = jsonObject.optString("username");
    this.mobile = jsonObject.optString("mobile");
    this.community_id = jsonObject.optString("community_id");
    this.build_id = jsonObject.optString("build_id");
    this.build_name = jsonObject.optString("build_name");
    this.community_name = jsonObject.optString("community_name");
    this.room = jsonObject.optString("room");
    if (jsonObject.optJSONArray("regions").length()!=0){
      this.province = jsonObject.optJSONArray("regions").optJSONObject(0).optString("name");
      this.city = jsonObject.optJSONArray("regions").optJSONObject(1).optString("name");
      this.district = jsonObject.optJSONArray("regions").optJSONObject(2).optString("name");
      this.regions = this.province + this.city + this.district + this.community_name + this.build_name
              + "-" + this.room;
    }

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("id", id);
    localItemObject.put("name", name);
    localItemObject.put("nickname", nickname);
    localItemObject.put("username", username);
    localItemObject.put("mobile", mobile);
    localItemObject.put("community_id", community_id);
    localItemObject.put("build_id", build_id);
    localItemObject.put("build_name", build_name);
    localItemObject.put("community_name", community_name);
    localItemObject.put("room", room);
    localItemObject.put("gender", gender);
    return localItemObject;
  }
}
