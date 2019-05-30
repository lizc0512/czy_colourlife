
package com.mycarinfo.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class COLOURTICKETADDCARINFODATA {


    public int vehicle_id;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.vehicle_id = jsonObject.optInt("vehicle_id");
        return;
    }
}
