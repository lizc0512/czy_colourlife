
package com.gesturepwd.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CustomerIssetgestureGetRequest {
    public String mobile; // 手机号码
    public String v; //

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.mobile = jsonObject.optString("mobile");
        this.v = jsonObject.optString("v");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("mobile", mobile);
        localItemObject.put("v", v);
        return localItemObject;
    }
}
