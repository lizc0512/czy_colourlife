
package cn.net.cyberway.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MORE_DATA
{
  public String name;

  public int id;

  public ArrayList<ATTR> attr = new ArrayList<ATTR>();

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.name = jsonObject.optString("name");
    this.id = jsonObject.optInt("id");
    subItemArray = jsonObject.optJSONArray("attr");
    if(null != subItemArray)
    {
      for(int i = 0;i < subItemArray.length();i++)
      {
        JSONObject subItemObject = subItemArray.getJSONObject(i);
        ATTR subItem = new ATTR();
        subItem.fromJson(subItemObject);
        this.attr.add(subItem);
      }
    }

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("name", name);
    localItemObject.put("id", id);
    itemJSONArray = new JSONArray();
    for(int i =0; i< attr.size(); i++)
    {
      ATTR itemData =attr.get(i);
      JSONObject itemJSONObject = itemData.toJson();
      itemJSONArray.put(itemJSONObject);
    }
    localItemObject.put("attr", itemJSONArray);

    return localItemObject;
  }

}
