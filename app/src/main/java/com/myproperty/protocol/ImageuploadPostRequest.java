
package com.myproperty.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ImageuploadPostRequest {
    public String type; //type: idcard_front | idcard_front | property_front | property_back | license_front | license_back

    public String file; //图片

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.type = jsonObject.optString("type");
        this.file = jsonObject.optString("file");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("type", type);
        localItemObject.put("file", file);
        return localItemObject;
    }
}
