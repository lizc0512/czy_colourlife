package com.notification.protocol;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class NOTICE
{
    public String id; //通知ID

    public String title; //名称

    public String contact; //通知内容

    public String categoryName; //通知类型

    public String create_time; //时间

    public String total;

    public void fromJson(JSONObject jsonObject) throws JSONException
    {
        if( null == jsonObject ) {
            return ;
        }

        JSONArray subItemArray = new JSONArray();

        this.id = jsonObject.optString("id");
        this.title = jsonObject.optString("title");
        this.contact = jsonObject.optString("contact");
        this.categoryName = jsonObject.optString("categoryName");
        this.create_time = jsonObject.optString("create_time");
        this.total = jsonObject.optString("total");

        return;
    }

    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("id", id);
        localItemObject.put("title", title);
        localItemObject.put("contact", contact);
        localItemObject.put("categoryName", categoryName);
        localItemObject.put("create_time", create_time);
        localItemObject.put("total", total);
        return localItemObject;
    }
}
