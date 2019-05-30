
package cn.net.cyberway.home.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeconfigGethousekeeperGetRequest {
    public int customer_id; //用户id

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.customer_id = jsonObject.optInt("customer_id");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("customer_id", customer_id);
        return localItemObject;
    }
}
