
package com.mycarinfo.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class VehicleDeletevehiclePostRequest {
    public String vehicle_id;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        this.vehicle_id = jsonObject.optString("vehicle_id");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("vehicle_id", vehicle_id);
        return localItemObject;
    }
}
