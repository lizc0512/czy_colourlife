
package com.message.protocol;

import com.message.model.SYSTEMMSG;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PushmessageGetResponse {
    public ArrayList<SYSTEMMSG> push = new ArrayList<SYSTEMMSG>();

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        subItemArray = jsonObject.optJSONArray("push");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                SYSTEMMSG subItem = new SYSTEMMSG();
                subItem.fromJson(subItemObject);
                this.push.add(subItem);
            }
        }

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        itemJSONArray = new JSONArray();
        for (int i = 0; i < push.size(); i++) {
            SYSTEMMSG itemData = push.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("push", itemJSONArray);

        return localItemObject;
    }
}
