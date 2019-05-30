package com.door.protocol;

import com.door.entity.AuthorizationListResp;
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
public class DoorApplyListResponse {
    public List<AuthorizationListResp> applyList;
    Gson gson = new Gson();

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        if (!jsonObject.isNull("list")) {
            JSONArray applyJson = jsonObject
                    .getJSONArray("list");
            Type applyType = new TypeToken<List<AuthorizationListResp>>() {
            }.getType();

            applyList = gson
                    .fromJson(applyJson.toString(),
                            applyType);
        }
    }
}
