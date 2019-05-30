package com.cashier.protocolchang;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.cashier.protocolchang
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/7 15:17
 * @change
 * @chang time
 * @class describe
 */

public class PayEntity {


    /**
     * code : 0
     * message : success
     * content : {"order":{"colour_sn":"201711_123456789123456","total_fee":22,"meal_total":23,"business_name":"e费通-彩科大厦"},"payment":{"meal_pay":{"pay_name":"饭票支付","pay_list":[{"payment_name":"普通饭票","payment_uuid":"undkn1d44f4gs455e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2,"meal_type":1},{"payment_name":"尊享饭票","amount":5000,"payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","meal_type":2,"list":[{"payment_name":"采集饭票","payment_uuid":"5511d1fd12f1ds2g12dsg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2},{"payment_name":"惠州别样城饭票","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2}]}]},"wallet_pay":{"pay_name":"彩钱包支付","pay_list":[{"payment_name":"账户余额支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2},{"payment_name":"银行卡支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2}]},"other_pay":{"pay_name":"其他支付方式","pay_list":[{"payment_name":"支付宝支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2},{"payment_name":"微信支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2}]}}}
     * contentEncrypt  :
     */

    private int code;
    private String message;
    private ContentBean content;
    private String contentEncrypt;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
         * order : {"colour_sn":"201711_123456789123456","total_fee":22,"meal_total":23,"business_name":"e费通-彩科大厦"}
         * payment : {"meal_pay":{"pay_name":"饭票支付","pay_list":[{"payment_name":"普通饭票","payment_uuid":"undkn1d44f4gs455e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2,"meal_type":1},{"payment_name":"尊享饭票","amount":5000,"payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","meal_type":2,"list":[{"payment_name":"采集饭票","payment_uuid":"5511d1fd12f1ds2g12dsg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2},{"payment_name":"惠州别样城饭票","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2}]}]},"wallet_pay":{"pay_name":"彩钱包支付","pay_list":[{"payment_name":"账户余额支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2},{"payment_name":"银行卡支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2}]},"other_pay":{"pay_name":"其他支付方式","pay_list":[{"payment_name":"支付宝支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2},{"payment_name":"微信支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2}]}}
         */

        private OrderBean order;
        private PaymentBean payment;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public PaymentBean getPayment() {
            return payment;
        }

        public void setPayment(PaymentBean payment) {
            this.payment = payment;
        }

        public static class OrderBean {
            /**
             * colour_sn : 201711_123456789123456
             * total_fee : 22
             * meal_total : 23
             * business_name : e费通-彩科大厦
             */

            private String colour_sn;
            private String total_fee;
            private String meal_total;

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            private String msg;

            public int getTrade_state() {
                return trade_state;
            }

            public void setTrade_state(int trade_state) {
                this.trade_state = trade_state;
            }

            private int trade_state;

            public String getColour_sn() {
                return colour_sn;
            }

            public void setColour_sn(String colour_sn) {
                this.colour_sn = colour_sn;
            }

            public String getTotal_fee() {
                return total_fee;
            }

            public void setTotal_fee(String total_fee) {
                this.total_fee = total_fee;
            }

            public String getMeal_total() {
                return meal_total;
            }

            public void setMeal_total(String meal_total) {
                this.meal_total = meal_total;
            }

        }

        public static class PaymentBean {
            /**
             * meal_pay : {"pay_name":"饭票支付","pay_list":[{"payment_name":"普通饭票","payment_uuid":"undkn1d44f4gs455e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2,"meal_type":1,"list":[{"payment_name":"采集饭票","payment_uuid":"5511d1fd12f1ds2g12dsg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2},{"payment_name":"惠州别样城饭票","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2}]},{"payment_name":"尊享饭票","amount":5000,"payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","meal_type":2,"list":[{"payment_name":"采集饭票","payment_uuid":"5511d1fd12f1ds2g12dsg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2},{"payment_name":"惠州别样城饭票","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2}]}]}
             * wallet_pay : {"pay_name":"彩钱包支付","pay_list":[{"payment_name":"账户余额支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2},{"payment_name":"银行卡支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2}]}
             * other_pay : {"pay_name":"其他支付方式","pay_list":[{"payment_name":"支付宝支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2},{"payment_name":"微信支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2}]}
             */

            private MealPayBean meal_pay;
            private WalletPayBean wallet_pay;
            private OtherPayBean other_pay;

            public MealPayBean getMeal_pay() {
                return meal_pay;
            }

            public void setMeal_pay(MealPayBean meal_pay) {
                this.meal_pay = meal_pay;
            }

            public WalletPayBean getWallet_pay() {
                return wallet_pay;
            }

            public void setWallet_pay(WalletPayBean wallet_pay) {
                this.wallet_pay = wallet_pay;
            }

            public OtherPayBean getOther_pay() {
                return other_pay;
            }

            public void setOther_pay(OtherPayBean other_pay) {
                this.other_pay = other_pay;
            }

            public static class MealPayBean {
                /**
                 * pay_name : 饭票支付
                 * pay_list : [{"payment_name":"普通饭票","payment_uuid":"undkn1d44f4gs455e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2,"meal_type":1},{"payment_name":"尊享饭票","amount":5000,"payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","meal_type":2,"list":[{"payment_name":"采集饭票","payment_uuid":"5511d1fd12f1ds2g12dsg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2},{"payment_name":"惠州别样城饭票","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2}]}]
                 */

                private String pay_name;
                private List<PayListBean> pay_list;

                public String getPay_name() {
                    return pay_name;
                }

                public void setPay_name(String pay_name) {
                    this.pay_name = pay_name;
                }

                public List<PayListBean> getPay_list() {
                    return pay_list;
                }

                public void setPay_list(List<PayListBean> pay_list) {
                    this.pay_list = pay_list;
                }

                public static class PayListBean {
                    /**
                     * payment_name : 普通饭票
                     * payment_uuid : undkn1d44f4gs455e1d221dfg1
                     * payment_logo : https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png
                     * discount : 100
                     * amount : 100.33
                     * pay_url : http://www.baidu.com
                     * is_native : 1
                     * payment_type : 2
                     * meal_type : 1
                     * list : [{"payment_name":"采集饭票","payment_uuid":"5511d1fd12f1ds2g12dsg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2},{"payment_name":"惠州别样城饭票","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2}]
                     */

                    private String payment_name;
                    private String payment_uuid;
                    private String payment_logo;
                    private int discount;
                    private String amount;
                    private String pay_url;
                    private int is_native;
                    private int payment_type;
                    private int meal_type;
                    private List<ListBean> list;

                    public String getPayment_name() {
                        return payment_name;
                    }

                    public void setPayment_name(String payment_name) {
                        this.payment_name = payment_name;
                    }

                    public String getPayment_uuid() {
                        return payment_uuid;
                    }

                    public void setPayment_uuid(String payment_uuid) {
                        this.payment_uuid = payment_uuid;
                    }

                    public String getPayment_logo() {
                        return payment_logo;
                    }

                    public void setPayment_logo(String payment_logo) {
                        this.payment_logo = payment_logo;
                    }

                    public int getDiscount() {
                        return discount;
                    }

                    public void setDiscount(int discount) {
                        this.discount = discount;
                    }

                    public String getAmount() {
                        return amount;
                    }

                    public void setAmount(String amount) {
                        this.amount = amount;
                    }

                    public String getPay_url() {
                        return pay_url;
                    }

                    public void setPay_url(String pay_url) {
                        this.pay_url = pay_url;
                    }

                    public int getIs_native() {
                        return is_native;
                    }

                    public void setIs_native(int is_native) {
                        this.is_native = is_native;
                    }

                    public int getPayment_type() {
                        return payment_type;
                    }

                    public void setPayment_type(int payment_type) {
                        this.payment_type = payment_type;
                    }

                    public int getMeal_type() {
                        return meal_type;
                    }

                    public void setMeal_type(int meal_type) {
                        this.meal_type = meal_type;
                    }

                    public List<ListBean> getList() {
                        return list;
                    }

                    public void setList(List<ListBean> list) {
                        this.list = list;
                    }

                    public static class ListBean {
                        /**
                         * payment_name : 采集饭票
                         * payment_uuid : 5511d1fd12f1ds2g12dsg1
                         * payment_logo : https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png
                         * discount : 100
                         * amount : 100.33
                         * pay_url : http://www.baidu.com
                         * is_native : 1
                         * payment_type : 2
                         */

                        private String payment_name;
                        private String payment_uuid;
                        private String payment_logo;
                        private int discount;
                        private double amount;
                        private String pay_url;
                        private int is_native;
                        private int payment_type;

                        public String getPayment_name() {
                            return payment_name;
                        }

                        public void setPayment_name(String payment_name) {
                            this.payment_name = payment_name;
                        }

                        public String getPayment_uuid() {
                            return payment_uuid;
                        }

                        public void setPayment_uuid(String payment_uuid) {
                            this.payment_uuid = payment_uuid;
                        }

                        public String getPayment_logo() {
                            return payment_logo;
                        }

                        public void setPayment_logo(String payment_logo) {
                            this.payment_logo = payment_logo;
                        }

                        public int getDiscount() {
                            return discount;
                        }

                        public void setDiscount(int discount) {
                            this.discount = discount;
                        }

                        public double getAmount() {
                            return amount;
                        }

                        public void setAmount(double amount) {
                            this.amount = amount;
                        }

                        public String getPay_url() {
                            return pay_url;
                        }

                        public void setPay_url(String pay_url) {
                            this.pay_url = pay_url;
                        }

                        public int getIs_native() {
                            return is_native;
                        }

                        public void setIs_native(int is_native) {
                            this.is_native = is_native;
                        }

                        public int getPayment_type() {
                            return payment_type;
                        }

                        public void setPayment_type(int payment_type) {
                            this.payment_type = payment_type;
                        }
                    }
                }
            }

            public static class WalletPayBean {
                /**
                 * pay_name : 彩钱包支付
                 * pay_list : [{"payment_name":"账户余额支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2},{"payment_name":"银行卡支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2}]
                 */

                private String pay_name;
                private List<PayListBeanX> pay_list;

                public String getPay_name() {
                    return pay_name;
                }

                public void setPay_name(String pay_name) {
                    this.pay_name = pay_name;
                }

                public List<PayListBeanX> getPay_list() {
                    return pay_list;
                }

                public void setPay_list(List<PayListBeanX> pay_list) {
                    this.pay_list = pay_list;
                }

                public static class PayListBeanX {
                    /**
                     * payment_name : 账户余额支付
                     * payment_uuid : undkn1d44f4gs4rtr55e1d221dfg1
                     * payment_logo : https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png
                     * discount : 100
                     * amount : 100.33
                     * pay_url : http://www.baidu.com
                     * is_native : 1
                     * payment_type : 2
                     */

                    private String payment_name;
                    private String payment_uuid;
                    private String payment_logo;
                    private int discount;
                    private String amount;
                    private String pay_url;
                    private int is_native;
                    private int payment_type;

                    public String getPayment_name() {
                        return payment_name;
                    }

                    public void setPayment_name(String payment_name) {
                        this.payment_name = payment_name;
                    }

                    public String getPayment_uuid() {
                        return payment_uuid;
                    }

                    public void setPayment_uuid(String payment_uuid) {
                        this.payment_uuid = payment_uuid;
                    }

                    public String getPayment_logo() {
                        return payment_logo;
                    }

                    public void setPayment_logo(String payment_logo) {
                        this.payment_logo = payment_logo;
                    }

                    public int getDiscount() {
                        return discount;
                    }

                    public void setDiscount(int discount) {
                        this.discount = discount;
                    }

                    public String getAmount() {
                        return amount;
                    }

                    public void setAmount(String amount) {
                        this.amount = amount;
                    }

                    public String getPay_url() {
                        return pay_url;
                    }

                    public void setPay_url(String pay_url) {
                        this.pay_url = pay_url;
                    }

                    public int getIs_native() {
                        return is_native;
                    }

                    public void setIs_native(int is_native) {
                        this.is_native = is_native;
                    }

                    public int getPayment_type() {
                        return payment_type;
                    }

                    public void setPayment_type(int payment_type) {
                        this.payment_type = payment_type;
                    }
                }
            }

            public static class OtherPayBean {
                /**
                 * pay_name : 其他支付方式
                 * pay_list : [{"payment_name":"支付宝支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2},{"payment_name":"微信支付","payment_uuid":"undkn1d44f4gs4rtr55e1d221dfg1","payment_logo":"https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png","discount":100,"amount":100.33,"pay_url":"http://www.baidu.com","is_native":1,"payment_type":2}]
                 */

                private String pay_name;
                private List<PayListBeanXX> pay_list;

                public String getPay_name() {
                    return pay_name;
                }

                public void setPay_name(String pay_name) {
                    this.pay_name = pay_name;
                }

                public List<PayListBeanXX> getPay_list() {
                    return pay_list;
                }

                public void setPay_list(List<PayListBeanXX> pay_list) {
                    this.pay_list = pay_list;
                }

                public static class PayListBeanXX {
                    /**
                     * payment_name : 支付宝支付
                     * payment_uuid : undkn1d44f4gs4rtr55e1d221dfg1
                     * payment_logo : https://cc-czytest.colourlife.com/common/v30/ptfp@2x.png
                     * discount : 100
                     * amount : 100.33
                     * pay_url : http://www.baidu.com
                     * is_native : 1
                     * payment_type : 2
                     */

                    private String payment_name;
                    private String payment_uuid;
                    private String payment_logo;
                    private int discount;
                    private String amount;
                    private String pay_url;
                    private int is_native;
                    private int payment_type;

                    public String getPayment_name() {
                        return payment_name;
                    }

                    public void setPayment_name(String payment_name) {
                        this.payment_name = payment_name;
                    }

                    public String getPayment_uuid() {
                        return payment_uuid;
                    }

                    public void setPayment_uuid(String payment_uuid) {
                        this.payment_uuid = payment_uuid;
                    }

                    public String getPayment_logo() {
                        return payment_logo;
                    }

                    public void setPayment_logo(String payment_logo) {
                        this.payment_logo = payment_logo;
                    }

                    public int getDiscount() {
                        return discount;
                    }

                    public void setDiscount(int discount) {
                        this.discount = discount;
                    }

                    public String getAmount() {
                        return amount;
                    }

                    public void setAmount(String amount) {
                        this.amount = amount;
                    }

                    public String getPay_url() {
                        return pay_url;
                    }

                    public void setPay_url(String pay_url) {
                        this.pay_url = pay_url;
                    }

                    public int getIs_native() {
                        return is_native;
                    }

                    public void setIs_native(int is_native) {
                        this.is_native = is_native;
                    }

                    public int getPayment_type() {
                        return payment_type;
                    }

                    public void setPayment_type(int payment_type) {
                        this.payment_type = payment_type;
                    }
                }
            }
        }
    }
}
