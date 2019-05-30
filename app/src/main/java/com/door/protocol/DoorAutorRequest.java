package com.door.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chengyun on 2016/1/6.
 */
public class DoorAutorRequest {
    public String mobile;
    public String bid;
    public String usertype;
    public String granttype;
    public String autype;
    public String starttime;
    public String stoptime;

    public String memo;

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("mobile", mobile);
        localItemObject.put("bid", bid);
        localItemObject.put("usertype", usertype);
        localItemObject.put("granttype", granttype);
        localItemObject.put("autype", autype);
        localItemObject.put("starttime", starttime);
        localItemObject.put("stoptime", stoptime);
        localItemObject.put("memo", memo);

        return localItemObject;
    }

}
