
package com.about.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class INFODATA {
    public ArrayList<String> func = new ArrayList<String>(); //主要功能

    public ArrayList<String> bug = new ArrayList<String>(); //修复bug

    public String size; //app大小

    public String create_time; //创建时间

    public String version; //版本号

    public String url; //url地址

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        subItemArray = jsonObject.optJSONArray("func");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                this.func.add(subItemArray.optString(i));
            }
        }
        subItemArray = jsonObject.optJSONArray("bug");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                this.bug.add(subItemArray.optString(i));
            }
        }
        this.size = jsonObject.optString("size");
        this.create_time = jsonObject.optString("create_time");
        this.version = jsonObject.optString("version");
        this.url = jsonObject.optString("url");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        itemJSONArray = new JSONArray();
        for (int i = 0; i < func.size(); i++) {
            itemJSONArray.put(func.get(i));
        }
        localItemObject.put("func", itemJSONArray);

        itemJSONArray = new JSONArray();
        for (int i = 0; i < bug.size(); i++) {
            itemJSONArray.put(bug.get(i));
        }
        localItemObject.put("bug", itemJSONArray);

        localItemObject.put("size", size);
        localItemObject.put("create_time", create_time);
        localItemObject.put("version", version);
        localItemObject.put("url", url);
        return localItemObject;
    }
}
