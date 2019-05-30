package com.door.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chengyun on 2016/1/6.
 */
public class DoorAutorCancelRequest {
    public String aid;

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("aid", aid);


        return localItemObject;
    }

}
