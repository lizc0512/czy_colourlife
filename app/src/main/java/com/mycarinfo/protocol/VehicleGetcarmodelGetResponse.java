
package com.mycarinfo.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class VehicleGetcarmodelGetResponse {
    public int code;

    public String message;

    public ArrayList<COLOURTICKETCARMODELINFOLIST> data = new ArrayList<COLOURTICKETCARMODELINFOLIST>();

    public String contentEncrypt;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.code = jsonObject.optInt("code");
        this.message = jsonObject.optString("message");
        JSONArray subItemArray = jsonObject.optJSONArray("content");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                COLOURTICKETCARMODELINFOLIST subItem = new COLOURTICKETCARMODELINFOLIST();
                subItem.fromJson(subItemObject);
                this.data.add(subItem);
            }
            return;
        }
        this.contentEncrypt = jsonObject.optString("contentEncrypt");
    }
}
