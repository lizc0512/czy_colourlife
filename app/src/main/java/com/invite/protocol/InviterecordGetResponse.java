
package com.invite.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class InviterecordGetResponse {
    public ArrayList<INVITERECORD> inviteRecord = new ArrayList<INVITERECORD>(); // 邀请详情列表

    public int inviteCount; // 邀请人数

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        subItemArray = jsonObject.optJSONArray("inviteRecord");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                INVITERECORD subItem = new INVITERECORD();
                subItem.fromJson(subItemObject);
                this.inviteRecord.add(subItem);
            }
        }
        this.inviteCount = jsonObject.optInt("inviteCount");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        itemJSONArray = new JSONArray();
        for (int i = 0; i < inviteRecord.size(); i++) {
            INVITERECORD itemData = inviteRecord.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("inviteRecord", itemJSONArray);

        localItemObject.put("inviteCount", inviteCount);
        return localItemObject;
    }
}
