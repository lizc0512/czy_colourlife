
package com.message.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SYSTEMMSG_INFO {
    public String title;

    public String content;

    public String is_read;

    public String create_time;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.title = jsonObject.optString("title");
        this.content = jsonObject.optString("content");
        this.is_read = jsonObject.optString("is_read");
        this.create_time = jsonObject.optString("create_time");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("title", title);
        localItemObject.put("content", content);
        localItemObject.put("is_read", is_read);
        localItemObject.put("create_time", create_time);
        return localItemObject;
    }
}
