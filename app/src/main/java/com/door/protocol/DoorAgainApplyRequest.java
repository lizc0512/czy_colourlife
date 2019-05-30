package com.door.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chengyun on 2016/1/6.
 */
public class DoorAgainApplyRequest {
    public String cid;
    public String memo;

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("cid", cid);
        localItemObject.put("memo", memo);

        return localItemObject;
    }

}
