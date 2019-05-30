package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/2 10:37
 * @change
 * @chang time
 * @class describe
 */
public class CouponEntity extends BaseContentEntity {

    private double couponAmount;
    private String couponDesc;
    private long couponDate;
    private String couponId;

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public long getCouponDate() {
        return couponDate;
    }

    public void setCouponDate(long couponDate) {
        this.couponDate = couponDate;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }
}
