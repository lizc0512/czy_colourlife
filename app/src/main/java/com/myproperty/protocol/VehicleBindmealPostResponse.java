
package com.myproperty.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class VehicleBindmealPostResponse {
    public int code;

    public String message;


    public String contentEncrypt;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.code = jsonObject.optInt("code");
        this.message = jsonObject.optString("message");
        this.contentEncrypt = jsonObject.optString("contentEncrypt");
        return;
    }

}
