package com.pay;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.pay.protocol.ORDER_INFO;


/**
 * 微信支付
 */
public class WxPayModel extends BaseModel {
    private Context mContext;

    public static String appid;
    public static String timestamp;
    public static String noncestr;
    public static String wx_package;
    public static String prepayid;
    public static String sign;

    public static ORDER_INFO publicOrder;

    public WxPayModel(Context context) {
        super(context);
        mContext = context;
    }
}
