
package com.user.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AuthPostResponse {
    public int ok;

    public int _new; // 是否是新标识 1 新的 0 通过唯一设备号取回该设备已有的标识和密钥，暂未实现

    public String key; // 客户端标识

    public String secret; // 客户端密钥

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.ok = jsonObject.optInt("ok");
        this._new = jsonObject.optInt("new");
        this.key = jsonObject.optString("key");
        this.secret = jsonObject.optString("secret");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("ok", ok);
        localItemObject.put("new", _new);
        localItemObject.put("key", key);
        localItemObject.put("secret", secret);
        return localItemObject;
    }
}
