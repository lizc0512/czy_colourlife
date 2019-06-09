
package cn.net.cyberway.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AppHomePropertyListGetRequest {
    public String version;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.version = jsonObject.optString("version");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("version", version);
        return localItemObject;
    }
}