package com.scanCode.protocol;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chengyun on 2016/1/14.
 */
public class ConfirmUrlRespon  {

    public String retCode;
    public String retMsg;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        retCode = jsonObject.get("retCode").toString();
        retMsg = jsonObject.get("retMsg").toString();
    }
}
