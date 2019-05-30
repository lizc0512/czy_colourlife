package com.door.protocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chengyun on 2016/1/6.
 */
public class DoorApplyRespone {
    public String reason;
    public String result;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        result = jsonObject.get("result").toString();
        reason = jsonObject.get("reason").toString();
    }
}
