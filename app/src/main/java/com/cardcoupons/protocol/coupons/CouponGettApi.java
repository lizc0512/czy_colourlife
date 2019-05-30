package com.cardcoupons.protocol.coupons;

import com.BeeFramework.model.HttpApi;

/**
 * Created by junier_li on 2016/1/5.
 */
public class CouponGettApi extends HttpApi {

    public CoupnsGetResponse response;
    public static String apiURI="/:ver/personalGetTotals";
    public CouponGettApi()
    {
        response=new CoupnsGetResponse();
    }
}
