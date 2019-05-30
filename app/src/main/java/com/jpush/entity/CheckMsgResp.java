package com.jpush.entity;

import java.io.Serializable;

/**
 * 推送信息数目统计
 *
 * @author liqingjun
 */
public class CheckMsgResp implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -261315080143433359L;

    private String cardid;
    private int c1;// 软卡信息
    private int c2;// 硬卡信息
    private int c3;// 商户充值
    private int c4;// 商户扣款
    private int c5;// 预约确认
    private int c6;// 重复登录
    private int c7;// 反馈回复
    private int c8;// 删除会员卡
    private int c9;// 积分充值
    private int c10;// 积分消费
    private int c11;// 交易取消
    private int c12;// 积分交易取消
    private int c13;// 接收优惠券
    private int c14;// 接收活动
    private int c15;// 接收投票
    private int c16;// 订单列表
    private int c17;//商家未支付订单

    public void setC17(int c17) {
        this.c17 = c17;
    }

    public int getC17() {
        return c17;
    }

    public int getC16() {
        return c16;
    }

    public void setC16(int c16) {
        this.c16 = c16;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    /**
     * 推送总数
     *
     * @return
     */
    public int getTotal() {

        return c1 + c3 + c4 + c5 + c7 + c9 + c10 + c11 + c12 + c13 + c14 + c15
                + c16;
    }

    /**
     * 短信 投票 活动 推送总数
     *
     * @return
     */
    public int getMsgCount() {
        return c1 + c14 + c15;
    }

    /**
     * 积分推送总数
     *
     * @return
     */
    public int getPointCount() {
        return c9 + c10 + c12;
    }

    /**
     * 余额推送总数
     *
     * @return
     */
    public int getBalanceCount() {
        return c3 + c4 + c11;
    }

    public int getC1() {
        return c1;
    }

    public void setC1(int c1) {
        this.c1 = c1;
    }

    public int getC2() {
        return c2;
    }

    public void setC2(int c2) {
        this.c2 = c2;
    }

    public int getC3() {
        return c3;
    }

    public void setC3(int c3) {
        this.c3 = c3;
    }

    public int getC4() {
        return c4;
    }

    public void setC4(int c4) {
        this.c4 = c4;
    }

    public int getC5() {
        return c5;
    }

    public void setC5(int c5) {
        this.c5 = c5;
    }

    public int getC6() {
        return c6;
    }

    public void setC6(int c6) {
        this.c6 = c6;
    }

    public int getC7() {
        return c7;
    }

    public void setC7(int c7) {
        this.c7 = c7;
    }

    public int getC8() {
        return c8;
    }

    public void setC8(int c8) {
        this.c8 = c8;
    }

    public int getC9() {
        return c9;
    }

    public void setC9(int c9) {
        this.c9 = c9;
    }

    public int getC10() {
        return c10;
    }

    public void setC10(int c10) {
        this.c10 = c10;
    }

    public int getC11() {
        return c11;
    }

    public void setC11(int c11) {
        this.c11 = c11;
    }

    public int getC12() {
        return c12;
    }

    public void setC12(int c12) {
        this.c12 = c12;
    }

    public int getC13() {
        return c13;
    }

    public void setC13(int c13) {
        this.c13 = c13;
    }

    public int getC14() {
        return c14;
    }

    public void setC14(int c14) {
        this.c14 = c14;
    }

    public int getC15() {
        return c15;
    }

    public void setC15(int c15) {
        this.c15 = c15;
    }

    @Override
    public String toString() {
        return "\"" + cardid + "\": {\"c1\":" + c1 + ", \"c2\":" + c2
                + ", \"c3\":" + c3 + ", \"c4\":" + c4 + ", \"c5\":" + c5
                + ", \"c6\":" + c6 + ", \"c7\":" + c7 + ", \"c8\":" + c8
                + ", \"c9\":" + c9 + ", \"c10\":" + c10 + ", \"c11\":" + c11
                + ", \"c12\":" + c12 + ", \"c13\":" + c13 + ", \"c14\":" + c14
                + ", \"c15\":" + c15 + ", \"c16\":" + c16 + "}";
    }

}
