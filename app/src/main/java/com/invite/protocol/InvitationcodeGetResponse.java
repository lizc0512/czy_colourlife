
package com.invite.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class InvitationcodeGetResponse {
    public String msg; // 邀请说明文字

    public String title;

    public String content;
    public String url;
    public String icon;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.msg = jsonObject.optString("msg");
        this.title = jsonObject.optString("title");
        this.content = jsonObject.optString("content");
        this.url = jsonObject.optString("url");
        this.icon = jsonObject.optString("icon");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("msg", msg);
        localItemObject.put("title", title);
        localItemObject.put("content", content);
        localItemObject.put("url", url);
        localItemObject.put("icon", icon);
        return localItemObject;
    }
}
