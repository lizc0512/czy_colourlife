
package cn.net.cyberway.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PROPERTYCONTENT
{
  public PROPERTYDATA top;

  public PROPERTYDATA middle; // 

  public PROPERTYDATA bottom;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    PROPERTYDATA top = new PROPERTYDATA();
    top.fromJson(jsonObject.optJSONObject("top"));
    this.top = top;
    PROPERTYDATA middle = new PROPERTYDATA();
    middle.fromJson(jsonObject.optJSONObject("middle"));
    this.middle = middle;
    PROPERTYDATA bottom = new PROPERTYDATA();
    bottom.fromJson(jsonObject.optJSONObject("bottom"));
    this.bottom = bottom;

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    if(null != top)
    {
      localItemObject.put("top", top.toJson());
    }
    if(null != middle)
    {
      localItemObject.put("middle", middle.toJson());
    }
    if(null != bottom)
    {
      localItemObject.put("bottom", bottom.toJson());
    }
    return localItemObject;
  }
}
