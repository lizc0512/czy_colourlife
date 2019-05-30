
package cn.net.cyberway.home.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class OPTIONSDATA {
    public int id;

    public String img; //图片

    public String name; //名称

    public String url; //跳转链接
    public int group_id; //

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.id = jsonObject.optInt("id");
        this.group_id = jsonObject.optInt("group_id");
        this.img = jsonObject.optString("img");
        this.name = jsonObject.optString("name");
        this.url = jsonObject.optString("url");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("group_id", group_id);
        localItemObject.put("img", img);
        localItemObject.put("id", id);
        localItemObject.put("name", name);
        localItemObject.put("url", url);
        return localItemObject;
    }
}
