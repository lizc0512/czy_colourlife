
package com.message.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeconfigGetuserinfourlGetResponse {
    public int retCode; //状态值 1为成功 0为失败

    public String retMsg; //message

    public INFOURL data;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.retCode = jsonObject.optInt("retCode");
        this.retMsg = jsonObject.optString("retMsg");
        INFOURL data = new INFOURL();
        data.fromJson(jsonObject.optJSONObject("data"));
        this.data = data;

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("retCode", retCode);
        localItemObject.put("retMsg", retMsg);
        if (null != data) {
            localItemObject.put("data", data.toJson());
        }
        return localItemObject;
    }
}
