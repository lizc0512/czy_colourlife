
package com.gesturepwd.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CustomerSetgesturePostRequest {
    public String gesture_code; // 手势密码

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.gesture_code = jsonObject.optString("gesture_code");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("gesture_code", gesture_code);
        return localItemObject;
    }
}
