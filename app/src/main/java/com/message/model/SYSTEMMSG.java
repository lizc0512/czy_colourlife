
package com.message.model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class SYSTEMMSG implements Serializable {
    public String id;

    public String push_information_id;

    public String title;

    public String content;

    public String is_read;

    public String create_time;

    public String url;

    public String resource_id;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.id = jsonObject.optString("id");
        this.push_information_id = jsonObject.optString("push_information_id");
        this.title = jsonObject.optString("title");
        this.content = jsonObject.optString("content");
        this.is_read = jsonObject.optString("is_read");
        this.create_time = jsonObject.optString("create_time");
        this.url = jsonObject.optString("url");
        this.resource_id = jsonObject.optString("resource_id");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("id", id);
        localItemObject.put("push_information_id", push_information_id);
        localItemObject.put("title", title);
        localItemObject.put("content", content);
        localItemObject.put("is_read", is_read);
        localItemObject.put("create_time", create_time);
        localItemObject.put("url", url);
        localItemObject.put("resource_id", resource_id);
        return localItemObject;
    }
}
