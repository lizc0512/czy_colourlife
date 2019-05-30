
package com.feed.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class VerFeedPublishNormalRequest {
    public String content; //内容

    public ArrayList<String> photo = new ArrayList<String>(); //图片

    public String community_uuid;
    public String from_uid;//用户Id 未登录传0


    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray;
        localItemObject.put("content", content);
        localItemObject.put("community_uuid", community_uuid);
        localItemObject.put("from_uid", from_uid);
        itemJSONArray = new JSONArray();
        for (int i = 0; i < photo.size(); i++) {
            itemJSONArray.put(photo.get(i));
        }
        if (itemJSONArray.length() > 0) {
            localItemObject.put("photo", itemJSONArray);
        }

        return localItemObject;
    }
}
