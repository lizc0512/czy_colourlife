
package com.gesturepwd.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class CustomerLogingesturePostRequest {
    public String mobile; //手机号码
    public String gesture_code; // 手势密码
    public String token;//进行下一步操作的标记
    public String version;
    public String device_uuid;
    public String reg_type;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.mobile = jsonObject.optString("mobile");
        this.gesture_code = jsonObject.optString("gesture_code");
        this.token = jsonObject.optString("token");
        this.version = jsonObject.optString("version");
        this.reg_type = jsonObject.optString("reg_type");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("mobile", mobile);
        localItemObject.put("gesture_code", gesture_code);
        localItemObject.put("token", token);
        localItemObject.put("version", version);
        localItemObject.put("device_uuid", device_uuid);
        localItemObject.put("reg_type", reg_type);
        return localItemObject;
    }
}
