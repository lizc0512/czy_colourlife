
package cn.net.cyberway.protocol;


import org.json.JSONException;
import org.json.JSONObject;


public class ATTR {
    public int id;

    public String name; //首页更多里面使用

    public String img; //首页更多里面使用

    public String topImage; //首页更多里面使用，右上角图片

    public int topUsed; //首页更多里面使用，是否显示右上角图片

    public int tipUsed; //首页更多里面使用，是否显示中间图片

    public String des; //首页更多里面使用，描述

    public String tipsImage; //首页更多里面使用，中间图片

    public String bgname;

    public String bg; //图片地址

    public String title;

    public String sname;

    public String link; //链接跳转，详细见协议

    public String act; //proto 本地  url 地址

    public String url; //如果是url 跳转的url

    public String proto; // 对应的activty，viewcontroler

    public String label_icon;//角标

    public int category_id;//（类别id）

    public int isnew;//是否最新，1为最新，0为不是

    public boolean isBlank = false;

    public boolean showRight = false;


    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.id = jsonObject.optInt("id");
        this.name = jsonObject.optString("name");
        this.img = jsonObject.optString("img");
        this.topImage = jsonObject.optString("topImage");
        this.topUsed = jsonObject.optInt("topUsed");
        this.tipUsed = jsonObject.optInt("tipUsed");
        this.des = jsonObject.optString("des");
        this.tipsImage = jsonObject.optString("tipsImage");
        this.bgname = jsonObject.optString("bgname");
        this.bg = jsonObject.optString("bg");
        this.title = jsonObject.optString("title");
        this.sname = jsonObject.optString("sname");
        this.link = jsonObject.optString("link");
        this.act = jsonObject.optString("act");
        this.url = jsonObject.optString("url");
        this.proto = jsonObject.optString("proto");
        this.category_id = jsonObject.optInt("category_id");
        this.isnew = jsonObject.optInt("isnew");
        this.label_icon = jsonObject.optString("label_icon");
        this.isBlank = jsonObject.optBoolean("isBlank");
        this.showRight = jsonObject.optBoolean("showRight");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("id", id);
        localItemObject.put("name", name);
        localItemObject.put("img", img);
        localItemObject.put("topImage", topImage);
        localItemObject.put("topUsed", topUsed);
        localItemObject.put("tipUsed", tipUsed);
        localItemObject.put("des", des);
        localItemObject.put("tipsImage", tipsImage);
        localItemObject.put("bgname", bgname);
        localItemObject.put("bg", bg);
        localItemObject.put("title", title);
        localItemObject.put("sname", sname);
        localItemObject.put("link", link);
        localItemObject.put("act", act);
        localItemObject.put("url", url);
        localItemObject.put("proto", proto);
        localItemObject.put("isnew", isnew);
        localItemObject.put("label_icon", label_icon);
        localItemObject.put("category_id", category_id);
        localItemObject.put("isBlank", isBlank);
        localItemObject.put("showRight", showRight);
        return localItemObject;
    }

}
