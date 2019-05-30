
package com.gem.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeconfigGetactivitytipGetRequest {
    public String section_code; //类别

    public String url;

    public int customer_id;

    public String version;//接口版本号

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.section_code = jsonObject.optString("section_code");
        this.url = jsonObject.optString("url");
        this.url = jsonObject.optString("version");
        this.customer_id = jsonObject.optInt("customer_id");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("section_code", section_code);
        localItemObject.put("url", url);
        localItemObject.put("customer_id", customer_id);
        localItemObject.put("version", version);
        return localItemObject;
    }
}
