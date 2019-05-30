package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/19 15:13
 * @change
 * @chang time
 * @class describe 我的卡包 月卡
 */
public class MonthCardInforEntity extends BaseContentEntity {


    /**
     * content : [{"type":"my_contract","plate":"闽CM778D","car_id":1220208,"station_id":3,"station_name":"南国丽园","brand":"","logo":"","begin_time":"2017-01-01 16:08:44","end_time":"2018-05-31 23:59:59","rule_id":34,"rule_name":"月卡用户","contract_id":11179},{"type":"my_contract","plate":"闽DC975L","car_id":2524766,"station_id":230,"station_name":"国际星城","brand":"","logo":"","begin_time":"2017-01-01 00:00:00","end_time":"2018-09-30 23:59:59","rule_id":1338,"rule_name":"地上月卡50","contract_id":478722}]
     * contentEncrypt :
     */

    private List<ContentBean> content;


    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean implements Serializable {
        /**
         * type : my_contract
         * plate : 闽CM778D
         * car_id : 1220208
         * station_id : 3
         * station_name : 南国丽园
         * brand :
         * logo :
         * begin_time : 2017-01-01 16:08:44
         * end_time : 2018-05-31 23:59:59
         * rule_id : 34
         * rule_name : 月卡用户
         * contract_id : 11179
         */

        private String type;
        private String plate;
        private String car_id;

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

        private String station_id;
        private String station_name;
        private String brand;
        private String logo;
        private String begin_time;
        private String end_time;
        private int rule_id;
        private String rule_name;
        private int contract_id;
        private String amount;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public int getRemain_day() {
            return remain_day;
        }

        public void setRemain_day(int remain_day) {
            this.remain_day = remain_day;
        }

        private int progress;
        private int remain_day;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPlate() {
            return plate;
        }

        public void setPlate(String plate) {
            this.plate = plate;
        }


        public String getStation_name() {
            return station_name;
        }

        public void setStation_name(String station_name) {
            this.station_name = station_name;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
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

        public int getRule_id() {
            return rule_id;
        }

        public void setRule_id(int rule_id) {
            this.rule_id = rule_id;
        }

        public String getRule_name() {
            return rule_name;
        }

        public void setRule_name(String rule_name) {
            this.rule_name = rule_name;
        }

        public int getContract_id() {
            return contract_id;
        }

        public void setContract_id(int contract_id) {
            this.contract_id = contract_id;
        }
    }
}
