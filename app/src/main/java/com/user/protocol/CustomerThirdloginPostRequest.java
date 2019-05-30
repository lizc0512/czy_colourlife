
package com.user.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class CustomerThirdloginPostRequest {
    public String reg_type; //客户端类型（1表示android/2表示IOS）

    public String device_info; //填写设备信息，可以检测注册号码是否有风险

    public String nickname; //昵称

    public String gender; //性别（0未知，1男，2女）

    public String source; //来源 qq/wechat

    public String open_code; //唯一标识

    public String portrait; //图像

    public String token;

    public String version;
    public String device_uuid;


    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.reg_type = jsonObject.optString("reg_type");
        this.device_info = jsonObject.optString("device_info");
        this.nickname = jsonObject.optString("nickname");
        this.gender = jsonObject.optString("gender");
        this.source = jsonObject.optString("source");
        this.open_code = jsonObject.optString("open_code");
        this.portrait = jsonObject.optString("portrait");
        this.token = jsonObject.optString("token");
        this.version = jsonObject.optString("version");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("reg_type", reg_type);
        localItemObject.put("device_info", device_info);
        localItemObject.put("nickname", nickname);
        localItemObject.put("gender", gender);
        localItemObject.put("source", source);
        localItemObject.put("open_code", open_code);
        localItemObject.put("portrait", portrait);
        localItemObject.put("token", token);
        localItemObject.put("version", version);
        localItemObject.put("device_uuid", device_uuid);
        return localItemObject;
    }
}
