
package com.gem.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ACTIVITYTIPDATA {
    public String is_time;//0结束 1 进行
    public ACTIVITYSDATA data;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.is_time = jsonObject.optString("is_time");
        ACTIVITYSDATA data = new ACTIVITYSDATA();
        data.fromJson(jsonObject.optJSONObject("data"));
        this.data = data;
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("is_time", is_time);
        if (null != data) {
            localItemObject.put("data", data.toJson());
        }
        return localItemObject;
    }
}
