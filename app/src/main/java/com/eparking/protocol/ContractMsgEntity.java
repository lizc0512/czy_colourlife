package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/22 19:46
 * @change
 * @chang time
 * @class describe  月卡续费
 */
public class ContractMsgEntity extends BaseContentEntity {
    /**
     * content : {"car_id":"15475095","station_id":230,"station_name":"国际星城","begin_time":"2017-01-01 00:00:00","end_time":"2019-01-31 23:59:59","cost":70,"rule_name":"地下月卡70"}
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
         * car_id : 15475095
         * station_id : 230
         * station_name : 国际星城
         * begin_time : 2017-01-01 00:00:00
         * end_time : 2019-01-31 23:59:59
         * cost : 70
         * rule_name : 地下月卡70
         */

        private String car_id;
        private String station_id;
        private String station_name;
        private String begin_time;
        private String end_time;
        private String cost;
        private String rule_name;

        public String getRule_id() {
            return rule_id;
        }

        public void setRule_id(String rule_id) {
            this.rule_id = rule_id;
        }

        private String rule_id;
        private int rule_paymin;

        public int getRule_paymin() {
            return rule_paymin;
        }

        public void setRule_paymin(int rule_paymin) {
            this.rule_paymin = rule_paymin;
        }

        public int getRule_paymax() {
            return rule_paymax;
        }

        public void setRule_paymax(int rule_paymax) {
            this.rule_paymax = rule_paymax;
        }

        private int rule_paymax;

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

        public String getStation_name() {
            return station_name;
        }

        public void setStation_name(String station_name) {
            this.station_name = station_name;
        }

        public String getBegin_time() {
            return begin_time;
        }

        public void setBegin_time(String begin_time) {
            this.begin_time = begin_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getRule_name() {
            return rule_name;
        }

        public void setRule_name(String rule_name) {
            this.rule_name = rule_name;
        }
    }
}
