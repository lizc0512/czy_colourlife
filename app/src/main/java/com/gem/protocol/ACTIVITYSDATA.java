package com.gem.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liusw on 2016/12/15.
 */
public class ACTIVITYSDATA
{
    public String name;

    public String url;

    public String image;

    public void fromJson(JSONObject jsonObject) throws JSONException
    {
        if( null == jsonObject ) {
            return ;
        }

        JSONArray subItemArray = new JSONArray();

        this.name = jsonObject.optString("name");
        this.url = jsonObject.optString("url");
        this.image = jsonObject.optString("image");

        return;
    }

    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("name", name);
        localItemObject.put("url", url);
        localItemObject.put("image", image);
        return localItemObject;
    }
}
