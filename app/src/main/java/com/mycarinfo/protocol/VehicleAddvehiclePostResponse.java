
package com.mycarinfo.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class VehicleAddvehiclePostResponse {
    public int code;

    public String message;

    public COLOURTICKETADDCARINFODATA content;

    public String contentEncrypt;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.code = jsonObject.optInt("code");
        this.message = jsonObject.optString("message");
        COLOURTICKETADDCARINFODATA content = new COLOURTICKETADDCARINFODATA();
        content.fromJson(jsonObject.optJSONObject("content"));
        this.content = content;
        this.contentEncrypt = jsonObject.optString("contentEncrypt");

        return;
    }
}
