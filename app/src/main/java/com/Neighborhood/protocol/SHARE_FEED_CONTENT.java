
package com.Neighborhood.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class SHARE_FEED_CONTENT  implements Serializable {
    public int id;

    public String title; //邻里分享

    public String content; //分享内容

    public String url;

    public String image;

    public int creationtime;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.id = jsonObject.optInt("id");
        this.title = jsonObject.optString("title");
        this.content = jsonObject.optString("content");
        this.url = jsonObject.optString("url");
        this.image = jsonObject.optString("image");
        this.creationtime = jsonObject.optInt("creationtime");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("id", id);
        localItemObject.put("title", title);
        localItemObject.put("content", content);
        localItemObject.put("url", url);
        localItemObject.put("image", image);
        localItemObject.put("creationtime", creationtime);
        return localItemObject;
    }
}
