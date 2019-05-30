
package cn.net.cyberway.home.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GETHOUSEKEEPERCONTENTDATA {
    public String ok;

    public String bind_status;

    public String username;

    public String avatar;

    public String link; //跳转链接

    public String mobile;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.ok = jsonObject.optString("ok");
        this.bind_status = jsonObject.optString("bind_status");
        this.username = jsonObject.optString("username");
        this.avatar = jsonObject.optString("avatar");
        this.link = jsonObject.optString("link");
        this.mobile = jsonObject.optString("mobile");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("ok", ok);
        localItemObject.put("bind_status", bind_status);
        localItemObject.put("username", username);
        localItemObject.put("avatar", avatar);
        localItemObject.put("link", link);
        localItemObject.put("mobile", mobile);
        return localItemObject;
    }
}
