
package com.cashier.protocolnew;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PAYINFODATA {
    public String orderInfo; //支付宝支付字符串

    public String tno;

    public String paytype;//1.成功  0，失败


    public int retcode; //微信支付返回的字串

    public String retmsg;

    public String appid; //微信id

    public String noncestr; //微信附加字串

    public String packageStr; //微信签名方式

    public String prepayid; //微信预处理订单号

    public int timestamp; //微信时间戳

    public String appkey; //微信id

    public String partnerid; //商家向财付通申请的商家id

    public String sign; //签名


    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray = new JSONArray();

        this.orderInfo = jsonObject.optString("orderInfo");
        this.tno = jsonObject.optString("tno");
        this.paytype = jsonObject.optString("paytype");

        this.retmsg = jsonObject.optString("retmsg");
        this.retcode = jsonObject.optInt("retcode");
        this.appid = jsonObject.optString("appid");
        this.noncestr = jsonObject.optString("noncestr");
        this.packageStr = jsonObject.optString("packageStr");
        this.prepayid = jsonObject.optString("prepayid");
        this.timestamp = jsonObject.optInt("timestamp");
        this.appkey = jsonObject.optString("appkey");
        this.partnerid = jsonObject.optString("partnerid");
        this.sign = jsonObject.optString("sign");


        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("orderInfo", orderInfo);
        localItemObject.put("tno", tno);
        localItemObject.put("paytype", paytype);

        localItemObject.put("retmsg", retmsg);
        localItemObject.put("retcode", retcode);
        localItemObject.put("appid", appid);
        localItemObject.put("noncestr", noncestr);
        localItemObject.put("packageStr", packageStr);
        localItemObject.put("prepayid", prepayid);
        localItemObject.put("timestamp", timestamp);
        localItemObject.put("appkey", appkey);
        localItemObject.put("partnerid", partnerid);
        localItemObject.put("sign", sign);

        return localItemObject;
    }
}
