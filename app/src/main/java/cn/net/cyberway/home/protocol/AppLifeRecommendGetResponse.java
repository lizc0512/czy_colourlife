
package cn.net.cyberway.home.protocol;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AppLifeRecommendGetResponse
{
  public int code;

  public String message;

  public ArrayList<LIFERECOMMENDLISTALLCONTENT> content = new ArrayList<LIFERECOMMENDLISTALLCONTENT>();
  public String contentEncrypt;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.code = jsonObject.optInt("code");
    this.message = jsonObject.optString("message");
    this.contentEncrypt = jsonObject.optString("contentEncrypt");
    subItemArray = jsonObject.optJSONArray("content");
    if(null != subItemArray)
    {
      for(int i = 0;i < subItemArray.length();i++)
      {
        JSONObject subItemObject = subItemArray.getJSONObject(i);
        LIFERECOMMENDLISTALLCONTENT subItem = new LIFERECOMMENDLISTALLCONTENT();
        subItem.fromJson(subItemObject);
        this.content.add(subItem);
      }
    }
    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("code", code);
    localItemObject.put("message", message);
    for(int i =0; i< content.size(); i++)
    {
      LIFERECOMMENDLISTALLCONTENT itemData =content.get(i);
      JSONObject itemJSONObject = itemData.toJson();
      itemJSONArray.put(itemJSONObject);
    }
    localItemObject.put("content", itemJSONArray);
    localItemObject.put("contentEncrypt", contentEncrypt);
    return localItemObject;
  }
}
