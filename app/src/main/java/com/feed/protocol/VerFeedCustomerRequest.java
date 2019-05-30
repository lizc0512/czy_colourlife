
package com.feed.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class VerFeedCustomerRequest {
    public String customer_id; //需获取动态的用户id

    public int page; // 页号

    public int per_page; // 每页数量
    public String from_uid;//用户Id 未登录传0

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.customer_id = jsonObject.optString("customer_id");
        this.page = jsonObject.optInt("page");
        this.per_page = jsonObject.optInt("per_page");
        this.from_uid = jsonObject.getString("from_uid");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("customer_id", customer_id);
        localItemObject.put("page", page);
        localItemObject.put("per_page", per_page);
        localItemObject.put("from_uid", from_uid);
        return localItemObject;
    }
}
