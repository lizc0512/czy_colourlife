
package com.user.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CustomerThirdloginPostResponse {
    public String retCode;

    public String retMsg;

    public THIRDDATA data;

    public int ok; //状态

    public String model; //业主用户模型名

    public String id; //登录成功的业主用户 ID

    public String username; //登录成功的业主用户账号

    public String mobile; //手机号码

    public String build_id; //楼栋id

    public String build_name; //楼栋名

    public String community_id; //小区id
    public String community_uuid = ""; //小区id

    public String community_name; //小区名

    public String name; //真实姓名

    public String nickname; //昵称

    public String room; //房间号

    public String is_show_in_neighbor; //是否在邻友里显示

    public String portraitUrl; //用户头像

    public String gender; //性别

    public String password; //密码

    public String chatName;

    public String type;

    public ArrayList<String> tags = new ArrayList<String>(); //选择的标签

    public String is_gesture; //是否设置了手势密码

    public String uuid; //UUUID
    public String key;
    public String secret;
    public String message;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        JSONArray subItemArray = null;
        this.retCode = jsonObject.optString("retCode");
        this.retMsg = jsonObject.optString("retMsg");
        THIRDDATA data = new THIRDDATA();
        data.fromJson(jsonObject.optJSONObject("data"));
        this.data = data;
        this.ok = jsonObject.optInt("ok");
        this.model = jsonObject.optString("model");
        this.id = jsonObject.optString("id");
        this.username = jsonObject.optString("username");
        this.mobile = jsonObject.optString("mobile");
        this.build_id = jsonObject.optString("build_id");
        this.build_name = jsonObject.optString("build_name");
        this.community_id = jsonObject.optString("community_id");
        this.community_name = jsonObject.optString("community_name");
        this.name = jsonObject.optString("name");
        this.nickname = jsonObject.optString("nickname");
        this.room = jsonObject.optString("room");
        this.is_show_in_neighbor = jsonObject.optString("is_show_in_neighbor");
        this.portraitUrl = jsonObject.optString("portraitUrl");
        this.gender = jsonObject.optString("gender");
        this.password = jsonObject.optString("password");
        this.chatName = jsonObject.optString("chatName");
        this.type = jsonObject.optString("type");
        this.key = jsonObject.optString("key");
        this.secret = jsonObject.optString("secret");
        this.message = jsonObject.optString("message");
        if (!jsonObject.isNull("community_uuid")) {
            this.community_uuid = jsonObject.optString("community_uuid");
        }
        subItemArray = jsonObject.optJSONArray("tags");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                this.tags.add(subItemArray.optString(i));
            }
        }
        this.is_gesture = jsonObject.optString("is_gesture");
        this.uuid = jsonObject.optString("uuid");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("retCode", retCode);
        localItemObject.put("retMsg", retMsg);
        if (null != data) {
            localItemObject.put("data", data.toJson());
        }
        localItemObject.put("ok", ok);
        localItemObject.put("model", model);
        localItemObject.put("id", id);
        localItemObject.put("username", username);
        localItemObject.put("mobile", mobile);
        localItemObject.put("build_id", build_id);
        localItemObject.put("build_name", build_name);
        localItemObject.put("community_id", community_id);
        localItemObject.put("community_name", community_name);
        localItemObject.put("name", name);
        localItemObject.put("nickname", nickname);
        localItemObject.put("room", room);
        localItemObject.put("is_show_in_neighbor", is_show_in_neighbor);
        localItemObject.put("portraitUrl", portraitUrl);
        localItemObject.put("gender", gender);
        localItemObject.put("password", password);
        localItemObject.put("chatName", chatName);
        localItemObject.put("type", type);
        localItemObject.put("key", key);
        localItemObject.put("secret", secret);
        localItemObject.put("message", message);
        itemJSONArray = new JSONArray();
        for (int i = 0; i < tags.size(); i++) {
            itemJSONArray.put(tags.get(i));
        }
        localItemObject.put("tags", itemJSONArray);

        localItemObject.put("is_gesture", is_gesture);
        localItemObject.put("uuid", uuid);
        return localItemObject;
    }
}
