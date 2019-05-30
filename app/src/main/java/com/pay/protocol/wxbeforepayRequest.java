package com.pay.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class wxbeforepayRequest {

    public int uid;


    public String sid;


    public int ver;


    public String order_price;


    public String product_name;


    public String order_no;

    public String device_info;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.uid = jsonObject.optInt("uid");

        this.sid = jsonObject.optString("sid");

        this.ver = jsonObject.optInt("ver");

        this.order_price = jsonObject.optString("order_price");

        this.product_name = jsonObject.optString("product_name");

        this.order_no = jsonObject.optString("order_no");

        this.device_info = jsonObject.optString("device_info");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("uid", uid);
        localItemObject.put("sid", sid);
        localItemObject.put("ver", ver);
        localItemObject.put("order_price", order_price);
        localItemObject.put("product_name", product_name);
        localItemObject.put("order_no", order_no);
        localItemObject.put("device_info", device_info);
        return localItemObject;
    }

}
