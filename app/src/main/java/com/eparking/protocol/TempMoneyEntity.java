package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/22 19:48
 * @change
 * @chang time
 * @class describe
 */
public class TempMoneyEntity extends BaseContentEntity {

    /**
     * content : {"money":20,"begin":"2018-11-22 17:43:18","end":"2018-11-22 18:43:18","comment":"艾科 - 彩科彩悦大厦- 临停费用","station_name":"国际星城","station_address":"福建"}
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
         * money : 20
         * begin : 2018-11-22 17:43:18
         * end : 2018-11-22 18:43:18
         * comment : 艾科 - 彩科彩悦大厦- 临停费用
         * station_name : 国际星城
         * station_address : 福建
         */

        private String money;
        private String logo;

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }


        public int getStay_time() {
            return stay_time;
        }

        public void setStay_time(int stay_time) {
            this.stay_time = stay_time;
        }

        private int stay_time;
        private String begin;
        private String end;
        private String comment;
        private String car_id;
        private String station_id;
        private String station_name;
        private String station_address;

        public String getCar_id() {
            return car_id;
        }

        public void setCar_id(String car_id) {
            this.car_id = car_id;
        }

        public String getStation_id() {
            return station_id;
        }

        public void setStation_id(String station_id) {
            this.station_id = station_id;
        }

        public String getDiscounts() {
            return discounts;
        }

        public void setDiscounts(String discounts) {
            this.discounts = discounts;
        }

        private String discounts;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getBegin() {
            return begin;
        }

        public void setBegin(String begin) {
            this.begin = begin;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getStation_name() {
            return station_name;
        }

        public void setStation_name(String station_name) {
            this.station_name = station_name;
        }

        public String getStation_address() {
            return station_address;
        }

        public void setStation_address(String station_address) {
            this.station_address = station_address;
        }
    }
}
