
package com.message.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PushmessageGetRequest {
    public int page; // 页数

    public int pagesize; // 每页条数

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.page = jsonObject.optInt("page");
        this.pagesize = jsonObject.optInt("pagesize");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("page", page);
        localItemObject.put("pagesize", pagesize);
        return localItemObject;
    }
}
