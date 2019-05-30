
package com.feed.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class VerFeedActivitycategoryRequest {
    public String community_uuid; //小区ID

    public int page; // 页号

    public int per_page; // 每页数量


    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("community_uuid", community_uuid);
        localItemObject.put("page", page);
        localItemObject.put("per_page", per_page);
        return localItemObject;
    }
}
