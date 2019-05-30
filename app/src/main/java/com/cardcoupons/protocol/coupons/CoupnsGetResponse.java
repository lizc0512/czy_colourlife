package com.cardcoupons.protocol.coupons;

/**
 * Created by junier_li on 2016/1/5.
 */
public class CoupnsGetResponse {

    public String result; //状态值 1为成功 0为失败

    public String reason; //message

//    public String balance; //
//
//    public String coupon; //
//
//    public String complain;

//    public CouponsEntity caidou;
//
//    public CouponsEntity about;

//    public CouponsEntity youHuiQuan;
//
//    public CouponsEntity tiHuoQuan;

    public String youHuiQuan_WebUrl;
    public String tiHuoQuan_WebUrl;


//    @Override
//    public void fromJson(JSONObject jsonObject) throws JSONException {
//        if (null == jsonObject) {
//            return;
//        }
//
//        JSONArray subItemArray = new JSONArray();
//
//        this.balance = jsonObject.optString("balance");
//        this.coupon = jsonObject.optString("coupon");
//        this.complain = jsonObject.optString("complain");
//
//        JSONObject caidouObject = jsonObject.getJSONObject("caidou");
//        CouponsEntity caidouItem = new CouponsEntity();
//        caidouItem.fromJson(caidouObject);
//        this.caidou = caidouItem;
//
//        JSONObject aboutObject = jsonObject.getJSONObject("about");
//        CouponsEntity aboutItem = new CouponsEntity();
//        aboutItem.fromJson(aboutObject);
//        this.about = aboutItem;
//
//        JSONObject youHuiQuanObject = jsonObject.getJSONObject("youHuiQuan");
//        CouponsEntity youHuiQuanItem = new CouponsEntity();
//        youHuiQuanItem.fromJson(youHuiQuanObject);
//        this.youHuiQuan = youHuiQuanItem;
//
//        JSONObject tiHuoQuanObject = jsonObject.getJSONObject("tiHuoQuan");
//        CouponsEntity tiHuoQuanItem = new CouponsEntity();
//        tiHuoQuanItem.fromJson(tiHuoQuanObject);
//        this.tiHuoQuan = tiHuoQuanItem;
//
//        return;
//    }
//
//    @Override
//    public JSONObject toJson(JSONObject jsonObject) throws JSONException {
//        JSONObject localItemObject = new JSONObject();
//        JSONArray itemJSONArray = new JSONArray();
//
//        localItemObject.put("balance", balance);
//        localItemObject.put("coupon", coupon);
//        localItemObject.put("complain", complain);
//        localItemObject.put("caidou", caidou);
//        localItemObject.put("about", about);
//        localItemObject.put("youHuiQuan", youHuiQuan);
//        localItemObject.put("tiHuoQuan", tiHuoQuan);
//
//        return localItemObject;
//    }
}
