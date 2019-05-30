
package com.message.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WetownMessagecentergetlistPostRequest {
    public String MainType; // 消息主类型，取值范围：0～3

    public String LastID; // 列表最后记录的id

    public String Limit; // 最大记录数量，默认20

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.MainType = jsonObject.optString("MainType");
        this.LastID = jsonObject.optString("LastID");
        this.Limit = jsonObject.optString("Limit");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("MainType", MainType);
        localItemObject.put("LastID", LastID);
        localItemObject.put("Limit", Limit);
        return localItemObject;
    }
}
