package cn.net.cyberway.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * 彩惠顶部模块
 * hxg on 2019.07.31.
 *
 * @Description
 */

public class BenefitProfileEntity extends BaseContentEntity {
    /**
     * content : {"nickname":"访客188****7657","order":{"title":"我的订单","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Forder%2Flist"},"personal_center":{"title":"个人中心","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpersonal"},"sign_in":{"title":"签到有礼","is_show":0,"image":"","url":""},"property_address":{"title":"缴费地址","num":2,"url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Fdeduction%2Fproperty%3Froom_uuid%3DSZ-QXSYGC-AD-1DY9%E6%A5%BC"},"arrears":{"title":"欠费","amount":"0.00","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Fdeduction%2Fproperty%3Froom_uuid%3DSZ-QXSYGC-AD-1DY9%E6%A5%BC"},"property_deduction":{"title":"缴费抵扣金","balance":"0.00","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Fdeduction%2Fproperty%3Froom_uuid%3DSZ-QXSYGC-AD-1DY9%E6%A5%BC"},"property_button":{"title":"立即缴费","is_show":1,"image":"https://pics-caihui-cdn.colourlife.com/5cff1fc65f5c6280183.png","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpayment%2Flist"}}
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
         * nickname : 访客188****7657
         * order : {"title":"我的订单","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Forder%2Flist"}
         * personal_center : {"title":"个人中心","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpersonal"}
         * sign_in : {"title":"签到有礼","is_show":0,"image":"","url":""}
         * property_address : {"title":"缴费地址","num":2,"url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Fdeduction%2Fproperty%3Froom_uuid%3DSZ-QXSYGC-AD-1DY9%E6%A5%BC"}
         * arrears : {"title":"欠费","amount":"0.00","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Fdeduction%2Fproperty%3Froom_uuid%3DSZ-QXSYGC-AD-1DY9%E6%A5%BC"}
         * property_deduction : {"title":"缴费抵扣金","balance":"0.00","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Fdeduction%2Fproperty%3Froom_uuid%3DSZ-QXSYGC-AD-1DY9%E6%A5%BC"}
         * property_button : {"title":"立即缴费","is_show":1,"image":"https://pics-caihui-cdn.colourlife.com/5cff1fc65f5c6280183.png","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpayment%2Flist"}
         */

        private String nickname;
        private OrderBean order;
        private PersonalCenterBean personal_center;
        private SignInBean sign_in;
        private PropertyAddressBean property_address;
        private ArrearsBean arrears;
        private PropertyDeductionBean property_deduction;
        private PropertyButtonBean property_button;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public PersonalCenterBean getPersonal_center() {
            return personal_center;
        }

        public void setPersonal_center(PersonalCenterBean personal_center) {
            this.personal_center = personal_center;
        }

        public SignInBean getSign_in() {
            return sign_in;
        }

        public void setSign_in(SignInBean sign_in) {
            this.sign_in = sign_in;
        }

        public PropertyAddressBean getProperty_address() {
            return property_address;
        }

        public void setProperty_address(PropertyAddressBean property_address) {
            this.property_address = property_address;
        }

        public ArrearsBean getArrears() {
            return arrears;
        }

        public void setArrears(ArrearsBean arrears) {
            this.arrears = arrears;
        }

        public PropertyDeductionBean getProperty_deduction() {
            return property_deduction;
        }

        public void setProperty_deduction(PropertyDeductionBean property_deduction) {
            this.property_deduction = property_deduction;
        }

        public PropertyButtonBean getProperty_button() {
            return property_button;
        }

        public void setProperty_button(PropertyButtonBean property_button) {
            this.property_button = property_button;
        }

        public static class OrderBean {
            /**
             * title : 我的订单
             * url : http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Forder%2Flist
             */

            private String title;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class PersonalCenterBean {
            /**
             * title : 个人中心
             * url : http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpersonal
             */

            private String title;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class SignInBean {
            /**
             * title : 签到有礼
             * is_show : 0
             * image :
             * url :
             */

            private String title;
            private int is_show;
            private String image;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getIs_show() {
                return is_show;
            }

            public void setIs_show(int is_show) {
                this.is_show = is_show;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class PropertyAddressBean {
            /**
             * title : 缴费地址
             * num : 2
             * url : http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Fdeduction%2Fproperty%3Froom_uuid%3DSZ-QXSYGC-AD-1DY9%E6%A5%BC
             */

            private String title;
            private int num;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class ArrearsBean {
            /**
             * title : 欠费
             * amount : 0.00
             * url : http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Fdeduction%2Fproperty%3Froom_uuid%3DSZ-QXSYGC-AD-1DY9%E6%A5%BC
             */

            private String title;
            private String amount;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class PropertyDeductionBean {
            /**
             * title : 缴费抵扣金
             * balance : 0.00
             * url : http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Fdeduction%2Fproperty%3Froom_uuid%3DSZ-QXSYGC-AD-1DY9%E6%A5%BC
             */

            private String title;
            private String balance;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class PropertyButtonBean {
            /**
             * title : 立即缴费
             * is_show : 1
             * image : https://pics-caihui-cdn.colourlife.com/5cff1fc65f5c6280183.png
             * url : http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpayment%2Flist
             */

            private String title;
            private int is_show;
            private String image;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getIs_show() {
                return is_show;
            }

            public void setIs_show(int is_show) {
                this.is_show = is_show;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }


//    /**
//     * content : {"nickname":"测试131","order":{"title":"我的订单","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Forder%2Flist"},"personal_center":{"title":"个人中心","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpersonal"},"sign_in":{"title":"签到有礼","is_show":0,"image":"","url":""},"property_address":{"title":"缴费地址","num":0,"url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpayment%2Flist"},"arrears":{"title":"欠费","amount":0,"url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpayment%2Flist"},"property_deduction":{"title":"缴费抵扣金","balance":"0.00","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpayment%2Flist"},"property_button":{"title":"立即缴费","is_show":1,"image":"https://pics-caihui-cdn.colourlife.com/5cff1fc65f5c6280183.png","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpayment%2Flist"}}
//     * contentEncrypt :
//     */
//
//    private ContentBean content;
//    private String contentEncrypt;
//
//    public ContentBean getContent() {
//        return content;
//    }
//
//    public void setContent(ContentBean content) {
//        this.content = content;
//    }
//
//    public String getContentEncrypt() {
//        return contentEncrypt;
//    }
//
//    public void setContentEncrypt(String contentEncrypt) {
//        this.contentEncrypt = contentEncrypt;
//    }
//
//    public static class ContentBean {
//        /**
//         * nickname : 测试131
//         * order : {"title":"我的订单","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Forder%2Flist"}
//         * personal_center : {"title":"个人中心","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpersonal"}
//         * sign_in : {"title":"签到有礼","is_show":0,"image":"","url":""}
//         * property_address : {"title":"缴费地址","num":0,"url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpayment%2Flist"}
//         * arrears : {"title":"欠费","amount":0,"url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpayment%2Flist"}
//         * property_deduction : {"title":"缴费抵扣金","balance":"0.00","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpayment%2Flist"}
//         * property_button : {"title":"立即缴费","is_show":1,"image":"https://pics-caihui-cdn.colourlife.com/5cff1fc65f5c6280183.png","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpayment%2Flist"}
//         */
//
//        private String nickname;
//        private OrderBean order;
//        private PersonalCenterBean personal_center;
//        private SignInBean sign_in;
//        private PropertyAddressBean property_address;
//        private ArrearsBean arrears;
//        private PropertyDeductionBean property_deduction;
//        private PropertyButtonBean property_button;
//
//        public String getNickname() {
//            return nickname;
//        }
//
//        public void setNickname(String nickname) {
//            this.nickname = nickname;
//        }
//
//        public OrderBean getOrder() {
//            return order;
//        }
//
//        public void setOrder(OrderBean order) {
//            this.order = order;
//        }
//
//        public PersonalCenterBean getPersonal_center() {
//            return personal_center;
//        }
//
//        public void setPersonal_center(PersonalCenterBean personal_center) {
//            this.personal_center = personal_center;
//        }
//
//        public SignInBean getSign_in() {
//            return sign_in;
//        }
//
//        public void setSign_in(SignInBean sign_in) {
//            this.sign_in = sign_in;
//        }
//
//        public PropertyAddressBean getProperty_address() {
//            return property_address;
//        }
//
//        public void setProperty_address(PropertyAddressBean property_address) {
//            this.property_address = property_address;
//        }
//
//        public ArrearsBean getArrears() {
//            return arrears;
//        }
//
//        public void setArrears(ArrearsBean arrears) {
//            this.arrears = arrears;
//        }
//
//        public PropertyDeductionBean getProperty_deduction() {
//            return property_deduction;
//        }
//
//        public void setProperty_deduction(PropertyDeductionBean property_deduction) {
//            this.property_deduction = property_deduction;
//        }
//
//        public PropertyButtonBean getProperty_button() {
//            return property_button;
//        }
//
//        public void setProperty_button(PropertyButtonBean property_button) {
//            this.property_button = property_button;
//        }
//
//        public static class OrderBean {
//            /**
//             * title : 我的订单
//             * url : http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Forder%2Flist
//             */
//
//            private String title;
//            private String url;
//
//            public String getTitle() {
//                return title;
//            }
//
//            public void setTitle(String title) {
//                this.title = title;
//            }
//
//            public String getUrl() {
//                return url;
//            }
//
//            public void setUrl(String url) {
//                this.url = url;
//            }
//        }
//
//        public static class PersonalCenterBean {
//            /**
//             * title : 个人中心
//             * url : http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpersonal
//             */
//
//            private String title;
//            private String url;
//
//            public String getTitle() {
//                return title;
//            }
//
//            public void setTitle(String title) {
//                this.title = title;
//            }
//
//            public String getUrl() {
//                return url;
//            }
//
//            public void setUrl(String url) {
//                this.url = url;
//            }
//        }
//
//        public static class SignInBean {
//            /**
//             * title : 签到有礼
//             * is_show : 0
//             * image :
//             * url :
//             */
//
//            private String title;
//            private int is_show;
//            private String image;
//            private String url;
//
//            public String getTitle() {
//                return title;
//            }
//
//            public void setTitle(String title) {
//                this.title = title;
//            }
//
//            public int getIs_show() {
//                return is_show;
//            }
//
//            public void setIs_show(int is_show) {
//                this.is_show = is_show;
//            }
//
//            public String getImage() {
//                return image;
//            }
//
//            public void setImage(String image) {
//                this.image = image;
//            }
//
//            public String getUrl() {
//                return url;
//            }
//
//            public void setUrl(String url) {
//                this.url = url;
//            }
//        }
//
//        public static class PropertyAddressBean {
//            /**
//             * title : 缴费地址
//             * num : 0
//             * url : http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpayment%2Flist
//             */
//
//            private String title;
//            private int num;
//            private String url;
//
//            public String getTitle() {
//                return title;
//            }
//
//            public void setTitle(String title) {
//                this.title = title;
//            }
//
//            public int getNum() {
//                return num;
//            }
//
//            public void setNum(int num) {
//                this.num = num;
//            }
//
//            public String getUrl() {
//                return url;
//            }
//
//            public void setUrl(String url) {
//                this.url = url;
//            }
//        }
//
//        public static class ArrearsBean {
//            /**
//             * title : 欠费
//             * amount : 0
//             * url : http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpayment%2Flist
//             */
//
//            private String title;
//            private int amount;
//            private String url;
//
//            public String getTitle() {
//                return title;
//            }
//
//            public void setTitle(String title) {
//                this.title = title;
//            }
//
//            public int getAmount() {
//                return amount;
//            }
//
//            public void setAmount(int amount) {
//                this.amount = amount;
//            }
//
//            public String getUrl() {
//                return url;
//            }
//
//            public void setUrl(String url) {
//                this.url = url;
//            }
//        }
//
//        public static class PropertyDeductionBean {
//            /**
//             * title : 缴费抵扣金
//             * balance : 0.00
//             * url : http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpayment%2Flist
//             */
//
//            private String title;
//            private String balance;
//            private String url;
//
//            public String getTitle() {
//                return title;
//            }
//
//            public void setTitle(String title) {
//                this.title = title;
//            }
//
//            public String getBalance() {
//                return balance;
//            }
//
//            public void setBalance(String balance) {
//                this.balance = balance;
//            }
//
//            public String getUrl() {
//                return url;
//            }
//
//            public void setUrl(String url) {
//                this.url = url;
//            }
//        }
//
//        public static class PropertyButtonBean {
//            /**
//             * title : 立即缴费
//             * is_show : 1
//             * image : https://pics-caihui-cdn.colourlife.com/5cff1fc65f5c6280183.png
//             * url : http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2Findex.html%23%2Fpayment%2Flist
//             */
//
//            private String title;
//            private int is_show;
//            private String image;
//            private String url;
//
//            public String getTitle() {
//                return title;
//            }
//
//            public void setTitle(String title) {
//                this.title = title;
//            }
//
//            public int getIs_show() {
//                return is_show;
//            }
//
//            public void setIs_show(int is_show) {
//                this.is_show = is_show;
//            }
//
//            public String getImage() {
//                return image;
//            }
//
//            public void setImage(String image) {
//                this.image = image;
//            }
//
//            public String getUrl() {
//                return url;
//            }
//
//            public void setUrl(String url) {
//                this.url = url;
//            }
//        }
//    }
}
