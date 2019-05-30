
package com.user.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PAGED
{
  public int total; // 总数

  public int page; // 页号

  public int size; // 当前返回数量

  public int more; // 是否还有更多

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.total = jsonObject.optInt("total");
    this.page = jsonObject.optInt("page");
    this.size = jsonObject.optInt("size");
    this.more = jsonObject.optInt("more");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("total", total);
    localItemObject.put("page", page);
    localItemObject.put("size", size);
    localItemObject.put("more", more);
    return localItemObject;
  }
}
