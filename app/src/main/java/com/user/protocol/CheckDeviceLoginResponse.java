package com.user.protocol;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 创建时间 : 2017/8/26.
 * 编写人 :  ${yuansk}
 * 功能描述:
 * 版本:
 */

public class CheckDeviceLoginResponse {
    public int code = -1;
    public String message;
    public int login_state = -1;
    public String contentEncrypt;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.code = jsonObject.optInt("code");
        this.message = jsonObject.optString("message");
        this.contentEncrypt = jsonObject.optString("contentEncrypt");
        if (!jsonObject.isNull("content") && !TextUtils.isEmpty(jsonObject.optString("content"))) {
            JSONObject contentJsonObject = jsonObject.optJSONObject("content");
            if (null != contentJsonObject && !contentJsonObject.isNull("login_state")) {
                this.login_state = contentJsonObject.optInt("login_state");
            }
        }
        return;
    }
}
