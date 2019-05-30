package com.door.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chengyun on 2016/1/6.
 */
public class DoorAutorApproveNewRequest {
    public String applyid;
    public String bid;
    public String usertype;
    public String granttype;
    public String autype;
    public String approve;
    public String starttime;
    public String stoptime;

    public String memo;

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("applyid", applyid);
        localItemObject.put("bid", bid);
        localItemObject.put("usertype", usertype);
        localItemObject.put("approve", approve);
        localItemObject.put("granttype", granttype);
        localItemObject.put("autype", autype);
        localItemObject.put("starttime", starttime);
        localItemObject.put("stoptime", stoptime);
        localItemObject.put("memo", memo);

        return localItemObject;
    }

}
