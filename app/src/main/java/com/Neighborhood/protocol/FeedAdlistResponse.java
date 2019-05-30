
package com.Neighborhood.protocol;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FeedAdlistResponse
{
  public int ok;

  public String retCode;

  public String retMsg;

  public ArrayList<BANNER> adlist = new ArrayList<BANNER>();

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.ok = jsonObject.optInt("ok");
    this.retCode = jsonObject.optString("retCode");
    this.retMsg = jsonObject.optString("retMsg");
    subItemArray = jsonObject.optJSONArray("adlist");
    if(null != subItemArray)
    {
      for(int i = 0;i < subItemArray.length();i++)
      {
        JSONObject subItemObject = subItemArray.getJSONObject(i);
        BANNER subItem = new BANNER();
        subItem.fromJson(subItemObject);
        this.adlist.add(subItem);
      }
    }

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("ok", ok);
    localItemObject.put("code", retCode);
    localItemObject.put("message", retMsg);
    itemJSONArray = new JSONArray();
    for(int i =0; i< adlist.size(); i++)
    {
      BANNER itemData =adlist.get(i);
      JSONObject itemJSONObject = itemData.toJson();
      itemJSONArray.put(itemJSONObject);
    }
    localItemObject.put("adlist", itemJSONArray);

    return localItemObject;
  }
}
