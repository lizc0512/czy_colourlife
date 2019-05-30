package com.notification.protocol;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotifyGetResponse
{
    public ArrayList<NOTICE> notifies = new ArrayList<NOTICE>(); //返回数组

    public void fromJson(JSONObject jsonObject) throws JSONException
    {
        if( null == jsonObject ) {
            return ;
        }

        JSONArray subItemArray = new JSONArray();

        subItemArray = jsonObject.optJSONArray("notifies");
        if(null != subItemArray)
        {
            for(int i = 0;i < subItemArray.length();i++)
            {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                NOTICE subItem = new NOTICE();
                subItem.fromJson(subItemObject);
                this.notifies.add(subItem);
            }
        }

        return;
    }

    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        itemJSONArray = new JSONArray();
        for(int i =0; i< notifies.size(); i++)
        {
            NOTICE itemData =notifies.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("notifies", itemJSONArray);

        return localItemObject;
    }
}

