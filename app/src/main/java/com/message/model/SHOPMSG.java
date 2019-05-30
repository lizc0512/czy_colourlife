
package com.message.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SHOPMSG {
    public String creationtime;

    public String id;

    public String maintype;

    public String title;

    public String isread;

    public String outlink;

    public String isdeleted;

    public String subtype;

    public String bid;

    public String cid;

    public String relatedid;

    public String cardid;

    public String imageurl;

    public String content;

    public String name;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.creationtime = jsonObject.optString("creationtime");
        this.id = jsonObject.optString("id");
        this.maintype = jsonObject.optString("maintype");
        this.title = jsonObject.optString("title");
        this.isread = jsonObject.optString("isread");
        this.outlink = jsonObject.optString("outlink");
        this.isdeleted = jsonObject.optString("isdeleted");
        this.subtype = jsonObject.optString("subtype");
        this.bid = jsonObject.optString("bid");
        this.cid = jsonObject.optString("cid");
        this.relatedid = jsonObject.optString("relatedid");
        this.cardid = jsonObject.optString("cardid");
        this.imageurl = jsonObject.optString("imageurl");
        this.content = jsonObject.optString("content");
        this.name = jsonObject.optString("name");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("creationtime", creationtime);
        localItemObject.put("id", id);
        localItemObject.put("maintype", maintype);
        localItemObject.put("title", title);
        localItemObject.put("isread", isread);
        localItemObject.put("outlink", outlink);
        localItemObject.put("isdeleted", isdeleted);
        localItemObject.put("subtype", subtype);
        localItemObject.put("bid", bid);
        localItemObject.put("cid", cid);
        localItemObject.put("relatedid", relatedid);
        localItemObject.put("cardid", cardid);
        localItemObject.put("imageurl", imageurl);
        localItemObject.put("content", content);
        localItemObject.put("name", name);
        return localItemObject;
    }
}
