
package com.user.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PHOTO {
    public int width; // 图片宽

    public int height; // 图片高

    public String thumb; // 缩略图URL

    public String large; // 大图URL

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.width = jsonObject.optInt("width");
        this.height = jsonObject.optInt("height");
        this.thumb = jsonObject.optString("thumb");
        this.large = jsonObject.optString("large");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("width", width);
        localItemObject.put("height", height);
        localItemObject.put("thumb", thumb);
        localItemObject.put("large", large);
        return localItemObject;
    }
}
