
package com.user.protocol;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TsPostResponse
{
  public int ok;

  public int ts; // 服务端收到的客户端时间戳

  public int now; // 服务端收到该请求时服务端当前时间戳

  public int diff; // 上述两个值的差值

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.ok = jsonObject.optInt("ok");
    this.ts = jsonObject.optInt("ts");
    this.now = jsonObject.optInt("now");
    this.diff = jsonObject.optInt("diff");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("ok", ok);
    localItemObject.put("ts", ts);
    localItemObject.put("now", now);
    localItemObject.put("diff", diff);
    return localItemObject;
  }
}
