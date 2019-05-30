
package com.invite.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class InviteGetResponse {
    public int ok; // 1:邀请成功

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.ok = jsonObject.optInt("ok");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("ok", ok);
        return localItemObject;
    }
}
