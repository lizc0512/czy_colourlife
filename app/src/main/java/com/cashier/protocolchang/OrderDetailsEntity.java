package com.cashier.protocolchang;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.cashier.protocolchang
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/26 14:16
 * @change
 * @chang time
 * @class describe  订单详情的
 */

public class OrderDetailsEntity extends BaseContentEntity {

    /**
     * content : {"trade_state_name":"交易成功","trade_state":2,"total_fee":"0.30","time_create":1540297049,"time_pay":1540297057,"colour_sn":"201810_019b674e8dd2c84ad488ba48cba0a6a9eedc","colour_trade_no":"1681000101810230810287544406","payment_name":"彩集饭票","body":"用户商品下单","shop_url":"0","discount_amount":"10.00","discount_content":"彩钱包支付98折立减","is_fanpiao":1,"bussiness_name":"饭票商城"}
     * contentEncrypt :
     */

    private ContentBean content;
    private String contentEncrypt;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public String getContentEncrypt() {
        return contentEncrypt;
    }

    public void setContentEncrypt(String contentEncrypt) {
        this.contentEncrypt = contentEncrypt;
    }

    public static class ContentBean {
        /**
         * trade_state_name : 交易成功
         * trade_state : 2
         * total_fee : 0.30
         * time_create : 1540297049
         * time_pay : 1540297057
         * colour_sn : 201810_019b674e8dd2c84ad488ba48cba0a6a9eedc
         * colour_trade_no : 1681000101810230810287544406
         * payment_name : 彩集饭票
         * body : 用户商品下单
         * shop_url : 0
         * discount_amount : 10.00
         * discount_content : 彩钱包支付98折立减
         * is_fanpiao : 1
         * bussiness_name : 饭票商城
         */

        private String trade_state_name;
        private int trade_state;
        private int stages_support;
        private String total_fee;

        public int getStages_support() {
            return stages_support;
        }

        public void setStages_support(int stages_support) {
            this.stages_support = stages_support;
        }

        public String getStages_url() {
            return stages_url;
        }

        public void setStages_url(String stages_url) {
            this.stages_url = stages_url;
        }

        private String stages_url ;


        public String getActual_fee() {
            return actual_fee;
        }

        public void setActual_fee(String actual_fee) {
            this.actual_fee = actual_fee;
        }

        private String actual_fee;
        private int time_create;
        private int time_pay;
        private String colour_sn;
        private String colour_trade_no;
        private String payment_name;
        private String body;
        private String shop_url;
        private String discount_amount;
        private String discount_content;
        private int is_fanpiao;
        private String bussiness_name;
        private String pop_img;

        public String getPop_img() {
            return pop_img;
        }

        public void setPop_img(String pop_img) {
            this.pop_img = pop_img;
        }

        public String getPop_redirect() {
            return pop_redirect;
        }

        public void setPop_redirect(String pop_redirect) {
            this.pop_redirect = pop_redirect;
        }

        private String pop_redirect;

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

        public int getTime_pay() {
            return time_pay;
        }

        public void setTime_pay(int time_pay) {
            this.time_pay = time_pay;
        }

        public String getColour_sn() {
            return colour_sn;
        }

        public void setColour_sn(String colour_sn) {
            this.colour_sn = colour_sn;
        }

        public String getColour_trade_no() {
            return colour_trade_no;
        }

        public void setColour_trade_no(String colour_trade_no) {
            this.colour_trade_no = colour_trade_no;
        }

        public String getPayment_name() {
            return payment_name;
        }

        public void setPayment_name(String payment_name) {
            this.payment_name = payment_name;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getShop_url() {
            return shop_url;
        }

        public void setShop_url(String shop_url) {
            this.shop_url = shop_url;
        }

        public String getDiscount_amount() {
            return discount_amount;
        }

        public void setDiscount_amount(String discount_amount) {
            this.discount_amount = discount_amount;
        }

        public String getDiscount_content() {
            return discount_content;
        }

        public void setDiscount_content(String discount_content) {
            this.discount_content = discount_content;
        }

        public int getIs_fanpiao() {
            return is_fanpiao;
        }

        public void setIs_fanpiao(int is_fanpiao) {
            this.is_fanpiao = is_fanpiao;
        }

        public String getBussiness_name() {
            return bussiness_name;
        }

        public void setBussiness_name(String bussiness_name) {
            this.bussiness_name = bussiness_name;
        }
    }
}
