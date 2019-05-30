
package com.feed.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


public class NORMAL_FEED_CONTENT  implements Serializable {
    public String content; // 发表内容

    public ArrayList<String> photos = new ArrayList<String>(); // 发表图片列表

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.content = jsonObject.optString("content");
        subItemArray = jsonObject.optJSONArray("photos");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                this.photos.add(subItemArray.optString(i));
            }
        }

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("content", content);
        itemJSONArray = new JSONArray();
        for (int i = 0; i < photos.size(); i++) {
            itemJSONArray.put(photos.get(i));
        }
        localItemObject.put("photos", itemJSONArray);

        return localItemObject;
    }
}
