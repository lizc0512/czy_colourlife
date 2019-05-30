package com.door.protocol;

import com.door.entity.CommunityResp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by chengyun on 2016/1/8.
 */
public class CommunityListResponse {
    public List<CommunityResp> communityResps;
    Gson gson = new Gson();

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        if (!jsonObject.isNull("communitylist")) {
            JSONArray applyJson = jsonObject
                    .getJSONArray("communitylist");
            Type applyType = new TypeToken<List<CommunityResp>>() {
            }.getType();

            communityResps = gson
                    .fromJson(applyJson.toString(),
                            applyType);
        }
    }
}
