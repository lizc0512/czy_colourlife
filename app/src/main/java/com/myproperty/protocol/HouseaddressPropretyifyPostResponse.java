
package com.myproperty.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HouseaddressPropretyifyPostResponse {
    public int code;

    public String message;

    public ArrayList<CTMYHOUSEPROPERTYVERIFYDATA> content = new ArrayList<CTMYHOUSEPROPERTYVERIFYDATA>();

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.code = jsonObject.optInt("code");
        this.message = jsonObject.optString("message");
        subItemArray = jsonObject.optJSONArray("content");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                CTMYHOUSEPROPERTYVERIFYDATA subItem = new CTMYHOUSEPROPERTYVERIFYDATA();
                subItem.fromJson(subItemObject);
                this.content.add(subItem);
            }
        }

        return;
    }

}
