
package com.feed.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class REGION implements Serializable {
    public PROVICE province;

    public CITY city;

    public DISTRICT district;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        PROVICE province = new PROVICE();
        province.fromJson(jsonObject.optJSONObject("province"));
        this.province = province;
        CITY city = new CITY();
        city.fromJson(jsonObject.optJSONObject("city"));
        this.city = city;
        DISTRICT district = new DISTRICT();
        district.fromJson(jsonObject.optJSONObject("district"));
        this.district = district;

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        if (null != province) {
            localItemObject.put("province", province.toJson());
        }
        if (null != city) {
            localItemObject.put("city", city.toJson());
        }
        if (null != district) {
            localItemObject.put("district", district.toJson());
        }
        return localItemObject;
    }
}
