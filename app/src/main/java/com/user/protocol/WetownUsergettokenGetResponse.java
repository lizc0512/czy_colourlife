
package com.user.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class WetownUsergettokenGetResponse {
    public int code = -1;

    public String message;

    public String device_token;

    public String contentEncrypt;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.code = jsonObject.optInt("code");
        this.message = jsonObject.optString("message");
        this.contentEncrypt = jsonObject.optString("contentEncrypt");
        if (jsonObject.isNull("content")) {
            JSONObject contentJsonObject = jsonObject.optJSONObject("content");
            this.device_token = contentJsonObject.optString("device_token");
        }
        return;
    }

}
