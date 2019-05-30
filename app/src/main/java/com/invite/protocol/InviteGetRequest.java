
package com.invite.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class InviteGetRequest {
    public String mobile; // 被邀请人手机号

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.mobile = jsonObject.optString("mobile");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("mobile", mobile);
        return localItemObject;
    }
}
