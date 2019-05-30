
package com.message.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WetownMessagecentersetallreadGetResponse {
    public String result; // 0成功

    public String reason;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.result = jsonObject.optString("result");
        this.reason = jsonObject.optString("reason");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("result", result);
        localItemObject.put("reason", reason);
        return localItemObject;
    }
}
