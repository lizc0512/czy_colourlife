
package cn.net.cyberway.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class VerHomeconfigGetmoreclsGetRequest {
    public int mobileType; //可选，手机类型（1为果机，2为安卓)空为果机

    public String community_id; //社区ID
    public String v; //社区ID

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.mobileType = jsonObject.optInt("mobileType");
        this.community_id = jsonObject.optString("community_id");
        this.v = jsonObject.optString("v");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("mobileType", mobileType);
        localItemObject.put("community_id", community_id);
        localItemObject.put("v", v);
        return localItemObject;
    }
}
