
package com.message.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class INFOURL {
    public String completeUrl; //带用户数据的URL

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.completeUrl = jsonObject.optString("completeUrl");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("completeUrl", completeUrl);
        return localItemObject;
    }
}
