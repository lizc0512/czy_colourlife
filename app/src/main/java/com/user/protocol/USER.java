
package com.user.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class USER  implements Serializable

{
    public int id;
    public String name; //姓名

    public String nickname; //昵称

    public String username; //注册成功的业主用户账号

    public String mobile;

    public int gender; //添加性别

    public String community_id; //小区编号

    public String build_id; //楼栋编号

    public String build_name; //楼栋

    public String community_name; //小区

    public String portrait;

    public String room; //门牌号

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.name = jsonObject.optString("name");
        this.nickname = jsonObject.optString("nickname");
        this.username = jsonObject.optString("username");
        this.mobile = jsonObject.optString("mobile");
        this.gender = jsonObject.optInt("gender");
        this.community_id = jsonObject.optString("community_id");
        this.build_id = jsonObject.optString("build_id");
        this.build_name = jsonObject.optString("build_name");
        this.community_name = jsonObject.optString("community_name");
        this.id = jsonObject.optInt("id");
        this.portrait = jsonObject.optString("portrait");
        this.room = jsonObject.optString("room");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("name", name);
        localItemObject.put("nickname", nickname);
        localItemObject.put("username", username);
        localItemObject.put("mobile", mobile);
        localItemObject.put("gender", gender);
        localItemObject.put("community_id", community_id);
        localItemObject.put("build_id", build_id);
        localItemObject.put("build_name", build_name);
        localItemObject.put("community_name", community_name);
        localItemObject.put("id", id);
        localItemObject.put("portrait", portrait);
        localItemObject.put("room", room);
        return localItemObject;
    }
}
