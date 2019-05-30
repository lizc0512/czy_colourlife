
package com.gesturepwd.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CustomerLogingesturePostResponse {
    public int retCode; //状态值 1为成功 0为失败
    public String retMsg; //message
    public String ok = ""; //成功

    public String model; //customer 业主用户模型名

    public String id; //登录成功的业主用户 ID

    public String username; //登录成功的业主用户账号

    public String mobile; //登录成功的业主用户手机号码

    public String build_id; //楼栋
    public String community_uuid = ""; //楼栋

    public String build_name; //楼栋

    public String community_name; //小区

    public String name;

    public String nickname; //昵称

    public String room; //门牌号

    public int community_id;

    public int is_show_in_neighbor; //是否在邻友里显示

    public String portraitUrl;
    public String key;
    public String secret;
    public String is_gesture;

    public String password;
    public String gender;
    public String uuid;

    public ArrayList<String> tags = new ArrayList<String>();

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();
        this.gender = jsonObject.optString("gender");
        this.ok = jsonObject.optString("ok");
        this.model = jsonObject.optString("model");
        this.id = jsonObject.optString("id");
        this.username = jsonObject.optString("username");
        this.mobile = jsonObject.optString("mobile");
        this.build_id = jsonObject.optString("build_id");
        this.build_name = jsonObject.optString("build_name");
        this.community_name = jsonObject.optString("community_name");
        this.name = jsonObject.optString("name");
        this.nickname = jsonObject.optString("nickname");
        this.room = jsonObject.optString("room");
        this.community_id = jsonObject.optInt("community_id");
        this.is_show_in_neighbor = jsonObject.optInt("is_show_in_neighbor");
        this.portraitUrl = jsonObject.optString("portraitUrl");
        this.retCode = jsonObject.optInt("retCode");
        this.retMsg = jsonObject.optString("retMsg");
        this.key = jsonObject.optString("key");
        this.secret = jsonObject.optString("secret");
        subItemArray = jsonObject.optJSONArray("tags");
        this.is_gesture = jsonObject.optString("is_gesture");
        this.password = jsonObject.optString("password");
        if (!jsonObject.isNull("community_uuid")) {
            this.community_uuid = jsonObject.getString("community_uuid");
        }
        if (!jsonObject.isNull("uuid")) {
            this.uuid = jsonObject.getString("uuid");
        }

        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                this.tags.add(subItemArray.optString(i));
            }
        }

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("gender", gender);
        localItemObject.put("ok", ok);
        localItemObject.put("model", model);
        localItemObject.put("id", id);
        localItemObject.put("username", username);
        localItemObject.put("mobile", mobile);
        localItemObject.put("build_id", build_id);
        localItemObject.put("build_name", build_name);
        localItemObject.put("community_name", community_name);
        localItemObject.put("name", name);
        localItemObject.put("nickname", nickname);
        localItemObject.put("room", room);
        localItemObject.put("community_id", community_id);
        localItemObject.put("is_show_in_neighbor", is_show_in_neighbor);
        localItemObject.put("portraitUrl", portraitUrl);
        localItemObject.put("retCode", retCode);
        localItemObject.put("retMsg", retMsg);
        localItemObject.put("is_gesture", is_gesture);
        localItemObject.put("password", password);
        for (int i = 0; i < tags.size(); i++) {
            itemJSONArray.put(tags.get(i));
        }
        localItemObject.put("tags", itemJSONArray);
        return localItemObject;
    }
}
