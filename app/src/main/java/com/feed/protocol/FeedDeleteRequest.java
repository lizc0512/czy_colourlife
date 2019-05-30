
package com.feed.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FeedDeleteRequest {
    public String feed_id; //动态id
    public String from_uid;//用户Id 未登录传0

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();
        this.from_uid = jsonObject.getString("from_uid");
        this.feed_id = jsonObject.optString("feed_id");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("feed_id", feed_id);
        localItemObject.put("from_uid", from_uid);
        return localItemObject;
    }
}
