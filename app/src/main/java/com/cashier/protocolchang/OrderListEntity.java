package com.cashier.protocolchang;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.cashier.protocolchang
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/26 14:14
 * @change
 * @chang time
 * @class describe  订单列表的
 */

public class OrderListEntity extends BaseContentEntity {


    /**
     * content : [{"colour_sn":"201711_cdd021a04df2744aef4a81d4e7c8f30a9205","business_name":"e费通-彩网测试小区","total_fee":"0.01","time_create":1509951832,"trade_state_name":"交易成功","trade_state":2,"payment":1,"business_logo":""},{"colour_sn":"201711_147109862ea1d240fa2856e219a1ffa4cc6c","business_name":"e费通-彩网测试小区","total_fee":"0.01","time_create":1509952803,"trade_state_name":"交易成功","payment":2,"trade_state":2,"business_logo":""}]
     * contentEncrypt :
     */

    private String contentEncrypt;
    private List<ContentBean> content;

    public String getContentEncrypt() {
        return contentEncrypt;
    }

    public void setContentEncrypt(String contentEncrypt) {
        this.contentEncrypt = contentEncrypt;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * colour_sn : 201711_cdd021a04df2744aef4a81d4e7c8f30a9205
         * business_name : e费通-彩网测试小区
         * total_fee : 0.01
         * time_create : 1509951832
         * trade_state_name : 交易成功
         * trade_state : 2
         * payment : 1
         * business_logo :
         */

        private String colour_sn;
        private String business_name;
        private String total_fee;
        private int time_create;
        private String trade_state_name;
        private int trade_state;

        public String getOrder_detail() {
            return order_detail;
        }

        public void setOrder_detail(String order_detail) {
            this.order_detail = order_detail;
        }

        private String order_detail;

        public int getPayment_type() {
            return payment_type;
        }

        public void setPayment_type(int payment_type) {
            this.payment_type = payment_type;
        }

        private int  payment_type;
        private String business_logo;

        public String getColour_sn() {
            return colour_sn;
        }

        public void setColour_sn(String colour_sn) {
            this.colour_sn = colour_sn;
        }

        public String getBusiness_name() {
            return business_name;
        }

        public void setBusiness_name(String business_name) {
            this.business_name = business_name;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }

        public int getTime_create() {
            return time_create;
        }

        public void setTime_create(int time_create) {
            this.time_create = time_create;
        }

        public String getTrade_state_name() {
            return trade_state_name;
        }

        public void setTrade_state_name(String trade_state_name) {
            this.trade_state_name = trade_state_name;
        }

        public int getTrade_state() {
            return trade_state;
        }

        public void setTrade_state(int trade_state) {
            this.trade_state = trade_state;
        }


        public String getBusiness_logo() {
            return business_logo;
        }

        public void setBusiness_logo(String business_logo) {
            this.business_logo = business_logo;
        }
    }
}
