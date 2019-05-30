
package cn.net.cyberway.home.protocol;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class LIFERECOMMENDLISTALLCONTENT
{
  public String app_type;
  public String desc;
  public ArrayList<MODULELISTCONTENT> list = new ArrayList<MODULELISTCONTENT>();

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();
    this.desc = jsonObject.optString("desc");
    this.app_type = jsonObject.optString("app_type");
    subItemArray = jsonObject.optJSONArray("list");
    if(null != subItemArray)
    {
      for(int i = 0;i < subItemArray.length();i++)
      {
        JSONObject subItemObject = subItemArray.getJSONObject(i);
        MODULELISTCONTENT subItem = new MODULELISTCONTENT();
        subItem.fromJson(subItemObject);
        this.list.add(subItem);
      }
    }

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    itemJSONArray = new JSONArray();
    localItemObject.put("desc", desc);
    localItemObject.put("app_type", app_type);
    for(int i =0; i< list.size(); i++)
    {
      MODULELISTCONTENT itemData =list.get(i);
      JSONObject itemJSONObject = itemData.toJson();
      itemJSONArray.put(itemJSONObject);
    }
    localItemObject.put("list", itemJSONArray);

    return localItemObject;
  }
}
