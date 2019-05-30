
package com.scanCode.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by junier_li on 2016/1/5.
 */
public class ConfirmUrlRequest {
    public String url; //模块
    public String v;//版本号
    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.url = jsonObject.optString("url");
        this.v = jsonObject.optString("v");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();

        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("url", url);
        localItemObject.put("v", v);

        return localItemObject;
    }
}
