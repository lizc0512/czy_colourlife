package com.door.protocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chengyun on 2016/1/4.
 */
public class DoorResponse {
    public String isgranted = "";

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        isgranted = jsonObject.get("isgranted").toString();
    }
}
