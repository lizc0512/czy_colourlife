package com.notification.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class NotifycategoryGetRequest
{
    public void fromJson(JSONObject jsonObject) throws JSONException
    {
        if( null == jsonObject ) {
            return ;
        }

        JSONArray subItemArray = new JSONArray();


        return;
    }

    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        return localItemObject;
    }
}
