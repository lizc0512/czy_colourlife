package com.pay.protocol;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class wxbeforepayResponse  {

    public String appid;


    public String timestamp;


    public String noncestr;


    public String wx_package;

    public String prepayid;

    public String sign;

    public int succeed;

    public int error_code;


    public String error_desc;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.appid = jsonObject.optString("appid");

        this.noncestr = jsonObject.optString("noncestr");

        this.succeed = jsonObject.optInt("succeed");

        this.wx_package = jsonObject.optString("package");

        this.prepayid = jsonObject.optString("prepayid");

        this.timestamp = jsonObject.optString("timestamp");

        this.sign = jsonObject.optString("sign");

        this.error_code = jsonObject.optInt("error_code");

        this.error_desc = jsonObject.optString("error_desc");
        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("appid", appid);
        localItemObject.put("timestamp", timestamp);
        localItemObject.put("noncestr", noncestr);
        localItemObject.put("succeed", succeed);
        localItemObject.put("package", wx_package);
        localItemObject.put("prepayid", prepayid);
        localItemObject.put("sign", sign);
        localItemObject.put("error_code", error_code);
        localItemObject.put("error_desc", error_desc);
        return localItemObject;
    }

}
