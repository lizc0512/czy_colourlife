
package com.feed.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PHOTO {
    public int id; // 图片宽

    public String url; // 大图URL

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.id = jsonObject.optInt("id");
        this.url = jsonObject.optString("url");


        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("id", id);
        localItemObject.put("url", url);

        return localItemObject;
    }
}
