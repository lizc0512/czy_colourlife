
package com.feed.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class VerFeedPublishActivityRequest {
    public String activity_type; //活动分类

    public String start_time; //开始时间

    public String end_time; //结束时间

    public String content; //内容

    public String location; //地址

    public String community_uuid;
    public String from_uid;//用户Id 未登录传0


    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("activity_type", activity_type);
        localItemObject.put("start_time", start_time);
        localItemObject.put("end_time", end_time);
        localItemObject.put("content", content);
        localItemObject.put("location", location);
        localItemObject.put("community_uuid", community_uuid);
        localItemObject.put("from_uid", from_uid);
        return localItemObject;
    }
}
