
package cn.net.cyberway.protocol;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AppHomePropertyListGetResponse
{
  public int code;

  public String message;

  public PROPERTYCONTENT content;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.code = jsonObject.optInt("code");
    this.message = jsonObject.optString("message");
    PROPERTYCONTENT content = new PROPERTYCONTENT();
    content.fromJson(jsonObject.optJSONObject("content"));
    this.content = content;

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("code", code);
    localItemObject.put("message", message);
    if(null != content)
    {
      localItemObject.put("content", content.toJson());
    }
    return localItemObject;
  }
}
