
package com.gesturepwd.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CustomerClosegestureGetResponse {
    public int retCode; //结果

    public SETGESTUREMSG data;

    public String retMsg; //返回消息

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.retCode = jsonObject.optInt("retCode");
        SETGESTUREMSG data = new SETGESTUREMSG();
        data.fromJson(jsonObject.optJSONObject("data"));
        this.data = data;
        this.retMsg = jsonObject.optString("retMsg");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("retCode", retCode);
        if (null != data) {
            localItemObject.put("data", data.toJson());
        }
        localItemObject.put("retMsg", retMsg);
        return localItemObject;
    }
}
