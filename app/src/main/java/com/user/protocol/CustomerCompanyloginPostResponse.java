package com.user.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CustomerCompanyloginPostResponse {
    public String model; //customer 业主用户模型名

    public String id; //登录成功的业主用户 ID

    public String username; //登录成功的业主用户账号

    public String mobile; //登录成功的业主用户手机号码

    public String build_name; //楼栋

    public String community_name; //小区

    public String community_id; //小区

    public int is_show_in_neighbor; //是否在邻友里显示

    public String nickname; //昵称

    public String name;//真实姓名

    public String portraitUrl;//头像

    public String room; //门牌号

    public String key;

    public String secret;

    public ArrayList<String> tags = new ArrayList<String>();

    public String is_gesture;

    public String password;
    public String build_id;
    public String gender;
    public int ok = 0;
    public String message;
    public String community_uuid = "";
    public String uuid = "";


    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();
        this.gender = jsonObject.optString("gender");
        this.build_id = jsonObject.optString("build_id");
        this.model = jsonObject.optString("model");
        this.id = jsonObject.optString("id");
        this.username = jsonObject.optString("username");
        this.mobile = jsonObject.optString("mobile");
        this.build_name = jsonObject.optString("build_name");
        this.community_name = jsonObject.optString("community_name");
        this.community_id = jsonObject.optString("community_id");
        this.is_show_in_neighbor = jsonObject.optInt("is_show_in_neighbor");
        this.nickname = jsonObject.optString("nickname");
        this.name = jsonObject.optString("name");
        this.portraitUrl = jsonObject.optString("portraitUrl");
        this.room = jsonObject.optString("room");
        this.key = jsonObject.optString("key");
        this.secret = jsonObject.optString("secret");
        this.is_gesture = jsonObject.optString("is_gesture");
        this.password = jsonObject.optString("password");
        this.ok = jsonObject.optInt("ok");
        this.message = jsonObject.optString("message");
        if (!jsonObject.isNull("community_uuid")) {
            this.community_uuid = jsonObject.getString("community_uuid");
        }
        if (!jsonObject.isNull("uuid")) {
            this.uuid = jsonObject.getString("uuid");
        }
        subItemArray = jsonObject.optJSONArray("tags");
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
        localItemObject.put("build_id", build_id);
        localItemObject.put("model", model);
        localItemObject.put("id", id);
        localItemObject.put("username", username);
        localItemObject.put("mobile", mobile);
        localItemObject.put("build_name", build_name);
        localItemObject.put("community_name", community_name);
        localItemObject.put("community_id", community_id);
        localItemObject.put("is_show_in_neighbor", is_show_in_neighbor);
        localItemObject.put("nickname", nickname);
        localItemObject.put("name", name);
        localItemObject.put("portraitUrl", portraitUrl);
        localItemObject.put("room", room);
        localItemObject.put("key", key);
        localItemObject.put("secret", secret);
        localItemObject.put("is_gesture", is_gesture);
        localItemObject.put("password", password);
        localItemObject.put("ok", ok);
        localItemObject.put("message", message);
        for (int i = 0; i < tags.size(); i++) {
            itemJSONArray.put(tags.get(i));
        }
        localItemObject.put("tags", itemJSONArray);

        return localItemObject;
    }
}
