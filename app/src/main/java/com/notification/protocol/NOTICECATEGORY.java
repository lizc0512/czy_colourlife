package com.notification.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class NOTICECATEGORY
{
    public String id; //通知ID

    public String name; //名称

    public void fromJson(JSONObject jsonObject) throws JSONException
    {
        if( null == jsonObject ) {
            return ;
        }

        JSONArray subItemArray = new JSONArray();

        this.id = jsonObject.optString("id");
        this.name = jsonObject.optString("name");

        return;
    }

    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("id", id);
        localItemObject.put("name", name);
        return localItemObject;
    }
}

