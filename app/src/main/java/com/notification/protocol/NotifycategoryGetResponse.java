package com.notification.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotifycategoryGetResponse
{
    public ArrayList<NOTICECATEGORY> categorys = new ArrayList<NOTICECATEGORY>(); //返回数组

    public void fromJson(JSONObject jsonObject) throws JSONException
    {
        if( null == jsonObject ) {
            return ;
        }

        JSONArray subItemArray = new JSONArray();

        subItemArray = jsonObject.optJSONArray("categorys");
        if(null != subItemArray)
        {
            for(int i = 0;i < subItemArray.length();i++)
            {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                NOTICECATEGORY subItem = new NOTICECATEGORY();
                subItem.fromJson(subItemObject);
                this.categorys.add(subItem);
            }
        }

        return;
    }

    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        itemJSONArray = new JSONArray();
        for(int i =0; i< categorys.size(); i++)
        {
            NOTICECATEGORY itemData =categorys.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("categorys", itemJSONArray);

        return localItemObject;
    }
}

