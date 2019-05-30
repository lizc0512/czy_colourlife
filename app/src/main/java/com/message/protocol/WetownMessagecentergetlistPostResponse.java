
package com.message.protocol;

import com.message.model.SHOPMSG;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class WetownMessagecentergetlistPostResponse {
    public String result;

    public String reason;

    public ArrayList<SHOPMSG> MList = new ArrayList<SHOPMSG>(); // 列表

    public String LastID;

    public String LastDateTime;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        subItemArray = jsonObject.optJSONArray("MList");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                SHOPMSG subItem = new SHOPMSG();
                subItem.fromJson(subItemObject);
                this.MList.add(subItem);
            }
        }
        this.result = jsonObject.optString("result");
        this.reason = jsonObject.optString("reason");
        this.LastID = jsonObject.optString("LastID");
        this.LastDateTime = jsonObject.optString("LastDateTime");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        itemJSONArray = new JSONArray();
        for (int i = 0; i < MList.size(); i++) {
            SHOPMSG itemData = MList.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("MList", itemJSONArray);

        localItemObject.put("result", result);
        localItemObject.put("reason", reason);
        localItemObject.put("LastID", LastID);
        localItemObject.put("LastDateTime", LastDateTime);
        return localItemObject;
    }
}
