
package com.feed.protocol;


import com.user.protocol.USER;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class REPLY implements Serializable {
    public USER to_user; //回复谁

    public USER from_user; //来自于谁的回复

    public String create_time; //创建时间

    public String id;

    public String content; //  回复内容

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        USER to_user = new USER();
        to_user.fromJson(jsonObject.optJSONObject("to_user"));
        this.to_user = to_user;
        USER from_user = new USER();
        from_user.fromJson(jsonObject.optJSONObject("from_user"));
        this.from_user = from_user;
        this.create_time = jsonObject.optString("create_time");
        this.id = jsonObject.optString("id");
        this.content = jsonObject.optString("content");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        if (null != to_user) {
            localItemObject.put("to_user", to_user.toJson());
        }
        if (null != from_user) {
            localItemObject.put("from_user", from_user.toJson());
        }
        localItemObject.put("create_time", create_time);
        localItemObject.put("id", id);
        localItemObject.put("content", content);
        return localItemObject;
    }
}
