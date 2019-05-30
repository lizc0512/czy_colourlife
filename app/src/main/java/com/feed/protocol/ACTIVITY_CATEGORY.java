
package com.feed.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class ACTIVITY_CATEGORY implements Serializable {
    public String id;

    public String name; //活动的名称

    public String photo; //活动封面

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.id = jsonObject.optString("id");
        this.name = jsonObject.optString("name");
        this.photo = jsonObject.optString("photo");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("id", id);
        localItemObject.put("name", name);
        localItemObject.put("photo", photo);
        return localItemObject;
    }
}
