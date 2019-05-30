
package com.mycarinfo.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class COLOURTICKETPROVINCEINFOLIST {
    public int id;

    public String short_name;


    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.id = jsonObject.optInt("id");
        this.short_name = jsonObject.optString("short_name");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("id", id);
        localItemObject.put("short_name", short_name);
        return localItemObject;
    }
}
