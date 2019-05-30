
package com.feed.protocol;

import com.Neighborhood.protocol.SHARE_FEED_CONTENT;
import com.user.protocol.USER;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class FEED implements Serializable {
    public String id;

    public int type; // FEED类型

    public NORMAL_FEED_CONTENT normal_feed_content; //普通FEED

    public ACTIVITY_FEED_CONTENT activity_feed_content; //活动FEED

    public SHARE_FEED_CONTENT share_feed_content; //分享

    public USER user; // 发表人

    public ArrayList<REPLY> replys = new ArrayList<REPLY>(); // 回复信息

    public ArrayList<USER> like = new ArrayList<USER>(); // 喜欢人信息

    public int like_total; // 喜欢总数

    public int reply_total; // 回复总数

    public int creationtime; // 发布时间

    public int like_status; // 是否喜欢过 1喜欢

    public String is_top; //是否置顶

    public String is_top_label; //置顶标示

    public COMMUNITY community;

    public REGION region;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.id = jsonObject.optString("id");
        this.type = jsonObject.optInt("type");
        NORMAL_FEED_CONTENT normal_feed_content = new NORMAL_FEED_CONTENT();
        normal_feed_content.fromJson(jsonObject.optJSONObject("normal_feed_content"));
        this.normal_feed_content = normal_feed_content;
        ACTIVITY_FEED_CONTENT activity_feed_content = new ACTIVITY_FEED_CONTENT();
        activity_feed_content.fromJson(jsonObject.optJSONObject("activity_feed_content"));
        this.activity_feed_content = activity_feed_content;
        SHARE_FEED_CONTENT share_feed_content = new SHARE_FEED_CONTENT();
        share_feed_content.fromJson(jsonObject.optJSONObject("share_feed_content"));
        this.share_feed_content = share_feed_content;
        USER user = new USER();
        user.fromJson(jsonObject.optJSONObject("user"));
        this.user = user;
        subItemArray = jsonObject.optJSONArray("replys");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                REPLY subItem = new REPLY();
                subItem.fromJson(subItemObject);
                this.replys.add(subItem);
            }
        }
        subItemArray = jsonObject.optJSONArray("like");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                USER subItem = new USER();
                subItem.fromJson(subItemObject);
                this.like.add(subItem);
            }
        }
        this.like_total = jsonObject.optInt("like_total");
        this.reply_total = jsonObject.optInt("reply_total");
        this.creationtime = jsonObject.optInt("creationtime");
        this.like_status = jsonObject.optInt("like_status");
        this.is_top = jsonObject.optString("is_top");
        this.is_top_label = jsonObject.optString("is_top_label");
        COMMUNITY community = new COMMUNITY();
        community.fromJson(jsonObject.optJSONObject("community"));
        this.community = community;
        REGION region = new REGION();
        region.fromJson(jsonObject.optJSONObject("regions"));
        this.region = region;

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("id", id);
        localItemObject.put("type", type);
        if (null != normal_feed_content) {
            localItemObject.put("normal_feed_content", normal_feed_content.toJson());
        }
        if (null != activity_feed_content) {
            localItemObject.put("activity_feed_content", activity_feed_content.toJson());
        }
        if (null != share_feed_content) {
            localItemObject.put("share_feed_content", share_feed_content.toJson());
        }
        if (null != user) {
            localItemObject.put("user", user.toJson());
        }
        itemJSONArray = new JSONArray();
        for (int i = 0; i < replys.size(); i++) {
            REPLY itemData = replys.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("replys", itemJSONArray);

        itemJSONArray = new JSONArray();
        for (int i = 0; i < like.size(); i++) {
            USER itemData = like.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("like", itemJSONArray);

        localItemObject.put("like_total", like_total);
        localItemObject.put("reply_total", reply_total);
        localItemObject.put("create_time", creationtime);
        localItemObject.put("like_status", like_status);
        localItemObject.put("is_top", is_top);
        localItemObject.put("is_top_label", is_top_label);
        if (null != community) {
            localItemObject.put("community", community.toJson());
        }
        if (null != region) {
            localItemObject.put("regions", region.toJson());
        }
        return localItemObject;
    }
}
