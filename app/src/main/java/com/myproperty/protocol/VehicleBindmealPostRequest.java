
package com.myproperty.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class VehicleBindmealPostRequest {
    public String pano; //饭票标识

    public String vehicle_id; //车辆id

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.pano = jsonObject.optString("pano");
        this.vehicle_id = jsonObject.optString("vehicle_id");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("pano", pano);
        localItemObject.put("vehicle_id", vehicle_id);
        return localItemObject;
    }
}
