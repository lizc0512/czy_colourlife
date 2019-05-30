
package com.user.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CustomerPasswordPostRequest
{
  public String mobile; //账号名，为空不修改

  public String token; //姓名，为空不修改

  public String password;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.mobile = jsonObject.optString("mobile");
    this.token = jsonObject.optString("token");
    this.password = jsonObject.optString("password");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("mobile", mobile);
    localItemObject.put("token", token);
    localItemObject.put("password", password);
    return localItemObject;
  }
}
