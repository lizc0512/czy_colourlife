
package com.Neighborhood.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class FeedPublishShareResponse {
    public int ok;

    public String code;

    public String message;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.ok = jsonObject.optInt("ok");
        this.code = jsonObject.optString("code");
        this.message = jsonObject.optString("message");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("ok", ok);
        localItemObject.put("code", code);
        localItemObject.put("message", message);
        return localItemObject;
    }
}
