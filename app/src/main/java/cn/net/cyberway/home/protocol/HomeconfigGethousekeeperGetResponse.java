
package cn.net.cyberway.home.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeconfigGethousekeeperGetResponse {
    public int retCode;

    public String retMsg;

    public GETHOUSEKEEPERCONTENTDATA data;

    public String contentEncrypt;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.retCode = jsonObject.optInt("retCode");
        this.retMsg = jsonObject.optString("retMsg");
        GETHOUSEKEEPERCONTENTDATA data = new GETHOUSEKEEPERCONTENTDATA();
        data.fromJson(jsonObject.optJSONObject("data"));
        this.data = data;
        this.contentEncrypt = jsonObject.optString("contentEncrypt");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("retCode", retCode);
        localItemObject.put("retMsg", retMsg);
        if (null != data) {
            localItemObject.put("data", data.toJson());
        }
        localItemObject.put("contentEncrypt", contentEncrypt);
        return localItemObject;
    }
}
