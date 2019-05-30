
package com.mycarinfo.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class COLOURTICKETCARBRANDINFOLIST
{
  public String prefix;

  public ArrayList<COLOURTICKETCARBRANDINFO> list = new ArrayList<COLOURTICKETCARBRANDINFO>();

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.prefix = jsonObject.optString("prefix");
    subItemArray = jsonObject.optJSONArray("list");
    if(null != subItemArray)
    {
      for(int i = 0;i < subItemArray.length();i++)
      {
        JSONObject subItemObject = subItemArray.getJSONObject(i);
        COLOURTICKETCARBRANDINFO subItem = new COLOURTICKETCARBRANDINFO();
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
    localItemObject.put("prefix", prefix);
    itemJSONArray = new JSONArray();
    for(int i =0; i< list.size(); i++)
    {
      COLOURTICKETCARBRANDINFO itemData =list.get(i);
      JSONObject itemJSONObject = itemData.toJson();
      itemJSONArray.put(itemJSONObject);
    }
    localItemObject.put("list", itemJSONArray);

    return localItemObject;
  }
}
