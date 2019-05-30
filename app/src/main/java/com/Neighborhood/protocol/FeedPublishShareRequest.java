
package com.Neighborhood.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FeedPublishShareRequest {
    public String url; //分享链接

    public String content; //分享内容

    public String title; //分享标题

    public String image; //分享显示照片

    public String from_uid;//用户Id 未登录传0

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.url = jsonObject.optString("url");
        this.content = jsonObject.optString("content");
        this.title = jsonObject.optString("title");
        this.image = jsonObject.optString("image");
        this.from_uid = jsonObject.getString("from_uid");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("url", url);
        localItemObject.put("content", content);
        localItemObject.put("title", title);
        localItemObject.put("image", image);
        localItemObject.put("from_uid", from_uid);
        return localItemObject;
    }
}
