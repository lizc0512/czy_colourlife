
package com.feed.protocol;


import com.user.protocol.PAGED;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class VerFeedListResponse {
    public int ok; //成功

    public String code; //详细错误号，类似 xyz.a 结构；xyz 和 HTTP 状态返回值一致，a 是扩展错误值

    public String message; //详细的错误提示信息

    public ArrayList<FEED> feeds = new ArrayList<FEED>();

    public PAGED paged;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.ok = jsonObject.optInt("ok");
        this.code = jsonObject.optString("code");
        this.message = jsonObject.optString("message");
        subItemArray = jsonObject.optJSONArray("feeds");
        if (null != subItemArray) {
            feeds.clear();
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                FEED subItem = new FEED();
                subItem.fromJson(subItemObject);
                this.feeds.add(subItem);
            }
        }
        PAGED paged = new PAGED();
        paged.fromJson(jsonObject.optJSONObject("paged"));
        this.paged = paged;

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("ok", ok);
        localItemObject.put("code", code);
        localItemObject.put("message", message);
        itemJSONArray = new JSONArray();
        for (int i = 0; i < feeds.size(); i++) {
            FEED itemData = feeds.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("feeds", itemJSONArray);

        if (null != paged) {
            localItemObject.put("paged", paged.toJson());
        }
        return localItemObject;
    }
}
