
package com.repair.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class PhotoPostResponse {

    public String url; //图片URL

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.url = jsonObject.optString("url");

        return;
    }
}
