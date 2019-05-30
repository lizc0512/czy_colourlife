
package com.gem.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeconfigGetactivitytipGetResponse {
    public int code; //0成功； 1失败

    public String message; //请求失败描述

    public ACTIVITYTIPDATA content;

    public String contentEncrypt;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.code = jsonObject.optInt("code");
        this.message = jsonObject.optString("message");
        ACTIVITYTIPDATA content = new ACTIVITYTIPDATA();
        content.fromJson(jsonObject.optJSONObject("content"));
        this.content = content;
        this.contentEncrypt = jsonObject.optString("contentEncrypt");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("code", code);
        localItemObject.put("message", message);
        if (null != content) {
            localItemObject.put("content", content.toJson());
        }
        localItemObject.put("contentEncrypt", contentEncrypt);
        return localItemObject;
    }
}
