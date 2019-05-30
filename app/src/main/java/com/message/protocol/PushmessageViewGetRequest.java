
package com.message.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PushmessageViewGetRequest {
    public String m_id; // 系统通知id

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.m_id = jsonObject.optString("m_id");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("m_id", m_id);
        return localItemObject;
    }
}
