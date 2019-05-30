
package com.mycarinfo.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class COLOURTICKETCARMODELINFOLIST {
    public int id;

    public int brand_id;

    public String name;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        this.id = jsonObject.optInt("id");
        this.brand_id = jsonObject.optInt("brand_id");
        this.name = jsonObject.optString("name");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("id", id);
        localItemObject.put("brand_id", brand_id);
        localItemObject.put("name", name);
        return localItemObject;
    }
}
