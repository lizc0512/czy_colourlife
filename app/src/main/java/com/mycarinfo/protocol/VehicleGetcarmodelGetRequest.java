
package com.mycarinfo.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class VehicleGetcarmodelGetRequest {
    public String brand_id; //（车型id）

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        this.brand_id = jsonObject.optString("brand_id");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("brand_id", brand_id);
        return localItemObject;
    }
}
