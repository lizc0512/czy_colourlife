
package com.user.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CustomerCompanyloginPostRequest {
    public String username; //姓名

    public int reg_type; //客户端类型（1表示android/2表示IOS）

    public String device_uuid;
    public String device_info;

    public String password; //昵称
    public String token; //
    public String version;
    public String source;//来源：Android


    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.username = jsonObject.optString("username");
        this.reg_type = jsonObject.optInt("reg_type");
        this.device_info = jsonObject.optString("device_info");
        this.password = jsonObject.optString("password");
        this.token = jsonObject.optString("token");
        this.version = jsonObject.optString("version");
        this.source = jsonObject.optString("source");
        this.device_uuid = jsonObject.optString("device_uuid");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("username", username);
        localItemObject.put("reg_type", reg_type);
        localItemObject.put("device_info", device_info);
        localItemObject.put("password", password);
        localItemObject.put("token", token);
        localItemObject.put("version", version);
        localItemObject.put("source", source);
        localItemObject.put("device_uuid", device_uuid);
        return localItemObject;
    }
}
