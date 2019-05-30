
package com.myproperty.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CTGLORYTICKETMYTICKETDATA
{
  public String help_url;
  public String name;
  public String help_content;
  public ArrayList<CTGLORYTICKETMYTICKETLISTDATA> list = new ArrayList<CTGLORYTICKETMYTICKETLISTDATA>();

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }
    JSONArray subItemArray = new JSONArray();
    this.help_url = jsonObject.optString("help_url");
    this.name = jsonObject.optString("name");
    this.help_content = jsonObject.optString("help_content");
    subItemArray = jsonObject.optJSONArray("list");
    if(null != subItemArray)
    {
      for(int i = 0;i < subItemArray.length();i++)
      {
        JSONObject subItemObject = subItemArray.getJSONObject(i);
        CTGLORYTICKETMYTICKETLISTDATA subItem = new CTGLORYTICKETMYTICKETLISTDATA();
        subItem.fromJson(subItemObject);
        this.list.add(subItem);
      }
    }
    return;
  }
}
