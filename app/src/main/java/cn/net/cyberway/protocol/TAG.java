
package cn.net.cyberway.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TAG {
    public String id;

    public String name;

    public String img;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.id = jsonObject.optString("id");
        this.name = jsonObject.optString("name");
        this.img = jsonObject.optString("img");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("id", id);
        localItemObject.put("name", name);
        localItemObject.put("img", img);
        return localItemObject;
    }
}
