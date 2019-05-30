package com.user.protocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 创建时间 : 2017/4/10.
 * 编写人 :  ${yuansk}
 * 功能描述:
 * 版本:
 */

public class AuthTokenRequest  {
    public String username;
    public String client_id = "2";
    public String client_secret = "oy4x7fSh5RI4BNc78UoV4fN08eO5C4pj0daM0B8M";
    public String grant_type = "password";
    public String password;
    public String type = "";


    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("password", password);
        localItemObject.put("username", username);
        localItemObject.put("client_id", client_id);
        localItemObject.put("client_secret", client_secret);
        localItemObject.put("grant_type", grant_type);
        localItemObject.put("type", type);
        return localItemObject;
    }
}
