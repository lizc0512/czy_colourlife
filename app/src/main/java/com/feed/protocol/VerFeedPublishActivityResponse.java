
package com.feed.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class VerFeedPublishActivityResponse {
    public int ok; //成功

    public String retCode; //详细错误号，类似 xyz.a 结构；xyz 和 HTTP 状态返回值一致，a 是扩展错误值

    public String retMsg; //详细的错误提示信息

    public FEED feeds;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.ok = jsonObject.optInt("ok");
        this.retCode = jsonObject.optString("retCode");
        this.retMsg = jsonObject.optString("retMsg");
        FEED feeds = new FEED();
        feeds.fromJson(jsonObject.optJSONObject("feeds"));
        this.feeds = feeds;

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("ok", ok);
        localItemObject.put("retCode", retCode);
        localItemObject.put("retMsg", retMsg);
        if (null != feeds) {
            localItemObject.put("feeds", feeds.toJson());
        }
        return localItemObject;
    }
}
