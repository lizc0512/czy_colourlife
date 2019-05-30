
package com.message.protocol;

import com.message.model.SYSTEMMSG_INFO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PushmessageViewGetResponse {
    public SYSTEMMSG_INFO info; // 系统通知详情

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        SYSTEMMSG_INFO info = new SYSTEMMSG_INFO();
        info.fromJson(jsonObject.optJSONObject("info"));
        this.info = info;

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        if (null != info) {
            localItemObject.put("info", info.toJson());
        }
        return localItemObject;
    }
}
