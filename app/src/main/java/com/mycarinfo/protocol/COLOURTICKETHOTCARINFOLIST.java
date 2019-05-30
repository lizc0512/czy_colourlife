
package com.mycarinfo.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class COLOURTICKETHOTCARINFOLIST {
    public String name;

    public String img_path;

    public int total;

    public int id;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.name = jsonObject.optString("name");
        this.img_path = jsonObject.optString("img_path");
        this.total = jsonObject.optInt("total");
        this.id = jsonObject.optInt("id");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("name", name);
        localItemObject.put("img_path", img_path);
        localItemObject.put("total", total);
        localItemObject.put("id", id);
        return localItemObject;
    }
}
