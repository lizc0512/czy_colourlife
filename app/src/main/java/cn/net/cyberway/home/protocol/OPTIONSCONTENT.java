
package cn.net.cyberway.home.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class OPTIONSCONTENT {
    public int id;

    public ArrayList<OPTIONSDATA> data = new ArrayList<OPTIONSDATA>();

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.id = jsonObject.optInt("id");
        subItemArray = jsonObject.optJSONArray("data");
        if (null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                OPTIONSDATA subItem = new OPTIONSDATA();
                subItem.fromJson(subItemObject);
                this.data.add(subItem);
            }
        }

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("id", id);
        itemJSONArray = new JSONArray();
        for (int i = 0; i < data.size(); i++) {
            OPTIONSDATA itemData = data.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("data", itemJSONArray);

        return localItemObject;
    }
}
