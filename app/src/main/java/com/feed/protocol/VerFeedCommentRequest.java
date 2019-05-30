
package com.feed.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class VerFeedCommentRequest {
    public String feed_id; // 回复对象：动态id

    public String to_uid; // 回复对象：用户id

    public String content; // 回复内容
    public String from_uid;//用户Id 未登录传0

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();
        this.from_uid = jsonObject.getString("from_uid");
        this.feed_id = jsonObject.optString("feed_id");
        this.to_uid = jsonObject.optString("to_uid");
        this.content = jsonObject.optString("content");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("feed_id", feed_id);
        localItemObject.put("to_uid", to_uid);
        localItemObject.put("content", content);
        localItemObject.put("from_uid", from_uid);
        return localItemObject;
    }
}
