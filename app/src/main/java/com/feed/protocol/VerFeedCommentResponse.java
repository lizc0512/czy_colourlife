
package com.feed.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class VerFeedCommentResponse {
    public int ok; //成功

    public String code; //详细错误号，类似 xyz.a 结构；xyz 和 HTTP 状态返回值一致，a 是扩展错误值

    public String message; //详细的错误提示信息
    public String comment_id;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.ok = jsonObject.optInt("ok");
        this.code = jsonObject.optString("code");
        this.message = jsonObject.optString("message");
        this.comment_id = jsonObject.optString("comment_id");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("ok", ok);
        localItemObject.put("code", code);
        localItemObject.put("message", message);
        localItemObject.put("comment_id", comment_id);
        return localItemObject;
    }
}
