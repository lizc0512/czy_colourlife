
package cn.net.cyberway.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FANPIAOCONTENTDATA {
    public String img;

    public String name; //

    public String url; //

    public String desc; //

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.img = jsonObject.optString("img");
        this.name = jsonObject.optString("name");
        this.url = jsonObject.optString("url");
        this.desc = jsonObject.optString("desc");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("img", img);
        localItemObject.put("name", name);
        localItemObject.put("url", url);
        localItemObject.put("desc", desc);
        return localItemObject;
    }
}
