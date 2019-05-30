package com.notification.protocol;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NotifyGetRequest
{
    public String community_id; //小区ID

    public String category_id; //类型

    public String contact; //通知内容

    public int page; //页码

    public int pagesize; //每页数

    public void fromJson(JSONObject jsonObject) throws JSONException
    {
        if( null == jsonObject ) {
            return ;
        }

        JSONArray subItemArray = new JSONArray();

        this.community_id = jsonObject.optString("community_id");
        this.category_id = jsonObject.optString("category_id");
        this.contact = jsonObject.optString("contact");
        this.page = jsonObject.optInt("page");
        this.pagesize = jsonObject.optInt("pagesize");

        return;
    }

    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("community_id", community_id);
        localItemObject.put("category_id", category_id);
        localItemObject.put("contact", contact);
        localItemObject.put("page", page);
        localItemObject.put("pagesize", pagesize);
        return localItemObject;
    }
}

