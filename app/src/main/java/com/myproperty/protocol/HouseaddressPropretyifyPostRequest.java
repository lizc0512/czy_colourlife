
package com.myproperty.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HouseaddressPropretyifyPostRequest {
    public String property_id; //房产id

    public String house_uuid; //rms系统返回的house_uuid

    public String family_name; //用户姓氏

    public String last_name; //用户名字

    public String mobile; //手机号

    public String idcard_number; //身份证号

    public String idcardFront_id; //身份证正面图片ID

    public String idcardBack_id; //身份证背面图片ID

    public String propertyFront_id; //房产证正面图片ID

    public String propertyBack_id; //房产证背面图片ID

    public String customer_id;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.property_id = jsonObject.optString("property_id");
        this.house_uuid = jsonObject.optString("house_uuid");
        this.family_name = jsonObject.optString("family_name");
        this.last_name = jsonObject.optString("last_name");
        this.mobile = jsonObject.optString("mobile");
        this.idcard_number = jsonObject.optString("idcard_number");
        this.idcardFront_id = jsonObject.optString("idcardFront_id");
        this.idcardBack_id = jsonObject.optString("idcardBack_id");
        this.propertyFront_id = jsonObject.optString("propertyFront_id");
        this.propertyBack_id = jsonObject.optString("propertyBack_id");
        this.customer_id = jsonObject.optString("customer_id");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("property_id", property_id);
        localItemObject.put("house_uuid", house_uuid);
        localItemObject.put("family_name", family_name);
        localItemObject.put("last_name", last_name);
        localItemObject.put("mobile", mobile);
        localItemObject.put("idcard_number", idcard_number);
        localItemObject.put("idcardFront_id", idcardFront_id);
        localItemObject.put("idcardBack_id", idcardBack_id);
        localItemObject.put("propertyFront_id", propertyFront_id);
        localItemObject.put("propertyBack_id", propertyBack_id);
        localItemObject.put("customer_id", customer_id);
        return localItemObject;
    }
}
