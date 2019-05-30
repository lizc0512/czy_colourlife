
package com.invite.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class InvitesuccessrecordGetResponse
{
  public ArrayList<INVITESUCCESSRECORD> inviteSuccessRecord = new ArrayList<INVITESUCCESSRECORD>(); // 邀请战绩详情列表

  public int inviteSuccessCount; // 邀请成功注册人数

  public String message; // 邀请成功注册描述

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    subItemArray = jsonObject.optJSONArray("inviteSuccessRecord");
    if(null != subItemArray)
    {
      for(int i = 0;i < subItemArray.length();i++)
      {
        JSONObject subItemObject = subItemArray.getJSONObject(i);
        INVITESUCCESSRECORD subItem = new INVITESUCCESSRECORD();
        subItem.fromJson(subItemObject);
        this.inviteSuccessRecord.add(subItem);
      }
    }
    this.inviteSuccessCount = jsonObject.optInt("inviteSuccessCount");
    this.message = jsonObject.optString("message");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    itemJSONArray = new JSONArray();
    for(int i =0; i< inviteSuccessRecord.size(); i++)
    {
      INVITESUCCESSRECORD itemData =inviteSuccessRecord.get(i);
      JSONObject itemJSONObject = itemData.toJson();
      itemJSONArray.put(itemJSONObject);
    }
    localItemObject.put("inviteSuccessRecord", itemJSONArray);

    localItemObject.put("inviteSuccessCount", inviteSuccessCount);
    localItemObject.put("message", message);
    return localItemObject;
  }
}
