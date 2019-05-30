
package com.invite.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class INVITERECORD {
    public String create_time; // 邀请时间

    public String mobile; // 被邀请人手机号

    public String status; // 被邀请人手机号注册状态

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.create_time = jsonObject.optString("create_time");
        this.mobile = jsonObject.optString("mobile");
        this.status = jsonObject.optString("status");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("create_time", create_time);
        localItemObject.put("mobile", mobile);
        localItemObject.put("status", status);
        return localItemObject;
    }
}
