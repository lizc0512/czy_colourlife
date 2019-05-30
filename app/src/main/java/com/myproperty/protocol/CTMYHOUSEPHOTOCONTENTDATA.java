
package com.myproperty.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class CTMYHOUSEPHOTOCONTENTDATA {
    public String image_id;

    public String path;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        this.image_id = jsonObject.optString("image_id");
        this.path = jsonObject.optString("path");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("image_id", image_id);
        localItemObject.put("path", path);
        return localItemObject;
    }
}
