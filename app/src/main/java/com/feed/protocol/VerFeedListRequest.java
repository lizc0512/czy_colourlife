
package com.feed.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class VerFeedListRequest {
    public String community_id; //小区ID

    public int page; // 页号

    public int per_page; // 每页数量

    public String from_uid;//用户Id 未登录传0

    public String v;//传1返回分享列表，0不返回

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.community_id = jsonObject.optString("community_id");
        this.page = jsonObject.optInt("page");
        this.per_page = jsonObject.optInt("per_page");
        this.from_uid = jsonObject.getString("from_uid");
        this.v = jsonObject.getString("v");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("community_uuid", community_id);
        localItemObject.put("page", page);
        localItemObject.put("per_page", per_page);
        localItemObject.put("from_uid", from_uid);
        localItemObject.put("v", v);
        return localItemObject;
    }
}
