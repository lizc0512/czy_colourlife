
package com.message.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeconfigGetuserinfourlGetRequest {
    public String resource_id; //资源ID

    public String url; //URL

    public String community_type; //小区类型。默认为1

    public String customer_id; //用户ID

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.resource_id = jsonObject.optString("resource_id");
        this.url = jsonObject.optString("url");
        this.community_type = jsonObject.optString("community_type");
        this.customer_id = jsonObject.optString("customer_id");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("resource_id", resource_id);
        localItemObject.put("url", url);
        localItemObject.put("community_type", community_type);
        localItemObject.put("customer_id", customer_id);
        return localItemObject;
    }

}
