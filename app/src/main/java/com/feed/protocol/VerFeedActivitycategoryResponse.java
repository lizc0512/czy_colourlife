
package com.feed.protocol;

import com.user.protocol.PAGED;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class VerFeedActivitycategoryResponse {
    public int ok; //成功

    public ArrayList<ACTIVITY_CATEGORY> activity_categoties = new ArrayList<ACTIVITY_CATEGORY>();

    public PAGED paged;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.ok = jsonObject.optInt("ok");
        subItemArray = jsonObject.optJSONArray("activity_categoties");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                ACTIVITY_CATEGORY subItem = new ACTIVITY_CATEGORY();
                subItem.fromJson(subItemObject);
                this.activity_categoties.add(subItem);
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
        itemJSONArray = new JSONArray();
        for (int i = 0; i < activity_categoties.size(); i++) {
            ACTIVITY_CATEGORY itemData = activity_categoties.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("activity_categoties", itemJSONArray);

        if (null != paged) {
            localItemObject.put("paged", paged.toJson());
        }
        return localItemObject;
    }
}
