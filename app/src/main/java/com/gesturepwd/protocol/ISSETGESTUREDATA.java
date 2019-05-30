
package com.gesturepwd.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ISSETGESTUREDATA {
    public String state; // 状态（0位设置手势密码、1设置手势密码并开启、2设置手势密码但未开启）

    public String portraitUrl; //用户头像

    public String mobile; //手机号码

    public int customerId; //用户id

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.state = jsonObject.optString("state");
        this.portraitUrl = jsonObject.optString("portraitUrl");
        this.mobile = jsonObject.optString("mobile");
        this.customerId = jsonObject.optInt("customerId");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("state", state);
        localItemObject.put("portraitUrl", portraitUrl);
        localItemObject.put("mobile", mobile);
        localItemObject.put("customerId", customerId);
        return localItemObject;
    }
}
