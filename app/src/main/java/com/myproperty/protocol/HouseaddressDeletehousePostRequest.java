
package com.myproperty.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HouseaddressDeletehousePostRequest {
    public String house_id; //房间id
    public String customer_id; //id

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.house_id = jsonObject.optString("house_id");
        this.customer_id = jsonObject.optString("customer_id");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("house_id", house_id);
        localItemObject.put("customer_id", customer_id);
        return localItemObject;
    }
}
