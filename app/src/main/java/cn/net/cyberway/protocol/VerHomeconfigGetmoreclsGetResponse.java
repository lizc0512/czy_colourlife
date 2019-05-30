
package cn.net.cyberway.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class VerHomeconfigGetmoreclsGetResponse {
    public int ok; //成功

    public String code; //详细错误号，类似 xyz.a 结构；xyz 和 HTTP 状态返回值一致，a 是扩展错误值

    public String message; //详细的错误提示信息

    public ArrayList<MORE_DATA> data = new ArrayList<MORE_DATA>(); //data是key,json数组没有key的情况下

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.ok = jsonObject.optInt("ok");
        this.code = jsonObject.optString("code");
        this.message = jsonObject.optString("message");
        subItemArray = jsonObject.optJSONArray("data");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                MORE_DATA subItem = new MORE_DATA();
                subItem.fromJson(subItemObject);
                this.data.add(subItem);
            }
        }

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("ok", ok);
        localItemObject.put("code", code);
        localItemObject.put("message", message);
        itemJSONArray = new JSONArray();
        for (int i = 0; i < data.size(); i++) {
            MORE_DATA itemData = data.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("data", itemJSONArray);

        return localItemObject;
    }
}
