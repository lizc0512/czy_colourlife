package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/13 15:56
 * @change
 * @chang time
 * @class describe   e 停首页数据模型
 */
public class ParkingHomeEntity extends BaseContentEntity {

    /**
     * content : [{"plate":"闽C211GV","car_id":15475095,"brand":"","logo":"","is_park":0,"station_name":"国际星城","station_id":230,"card_type":"my_contract","begin_time":"2017-01-01 00:00:00","end_time":"2019-01-31 23:59:59"},{"plate":"闽DC975L","car_id":2524766,"brand":"","logo":"","is_park":0,"station_name":"国际星城","station_id":230,"card_type":"my_contract","begin_time":"2017-01-01 00:00:00","end_time":"2018-09-30 23:59:59"},{"plate":"闽CM778D","car_id":1220208,"brand":"","logo":"","is_park":0,"station_name":"国际星城","station_id":230,"card_type":"my_contract","begin_time":"2018-05-01 00:00:00","end_time":"2018-06-30 23:59:59"},{"type":"my_lock","station_id":19,"station_name":"七星商业广场","etcode":"00190192","lock_id":3,"lock_name":"190","status":"close"}]
     * contentEncrypt :
     */
    private List<ContentBean> content;

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * plate : 闽C211GV
         * car_id : 15475095
         * brand :
         * logo :
         * is_park : 0
         * station_name : 国际星城
         * station_id : 230
         * card_type : my_contract
         * begin_time : 2017-01-01 00:00:00
         * end_time : 2019-01-31 23:59:59
         * type : my_lock
         * etcode : 00190192
         * lock_id : 3
         * lock_name : 190
         * status : close
         */

        private String plate;
        private String car_id;
        private String brand;
        private String logo;
        private int is_park;
        private String station_name;
        private String station_id;
        private String card_type;
        private String begin_time;
        private String end_time;
        private String type;
        private String etcode;
        private String lock_id;
        private String lock_name;
        private String status;

        private String temp_money;
        private String leave;
        private String arrival;
        private String lock_user_name;

        public String getLock_user_name() {
            return lock_user_name;
        }

        public void setLock_user_name(String lock_user_name) {
            this.lock_user_name = lock_user_name;
        }

        public String getNow_time() {
            return now_time;
        }

        public void setNow_time(String now_time) {
            this.now_time = now_time;
        }

        private String now_time;

        public String getTemp_money() {
            return temp_money;
        }

        public void setTemp_money(String temp_money) {
            this.temp_money = temp_money;
        }

        public String getLeave() {
            return leave;
        }

        public void setLeave(String leave) {
            this.leave = leave;
        }

        public String getArrival() {
            return arrival;
        }

        public void setArrival(String arrival) {
            this.arrival = arrival;
        }


        public String getPlate() {
            return plate;
        }

        public void setPlate(String plate) {
            this.plate = plate;
        }

        public String getCar_id() {
            return car_id;
        }

        public void setCar_id(String car_id) {
            this.car_id = car_id;
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

        public int getIs_park() {
            return is_park;
        }

        public void setIs_park(int is_park) {
            this.is_park = is_park;
        }

        public String getStation_name() {
            return station_name;
        }

        public void setStation_name(String station_name) {
            this.station_name = station_name;
        }

        public String getStation_id() {
            return station_id;
        }

        public void setStation_id(String station_id) {
            this.station_id = station_id;
        }

        public String getCard_type() {
            return card_type;
        }

        public void setCard_type(String card_type) {
            this.card_type = card_type;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getEtcode() {
            return etcode;
        }

        public void setEtcode(String etcode) {
            this.etcode = etcode;
        }

        public String getLock_id() {
            return lock_id;
        }

        public void setLock_id(String lock_id) {
            this.lock_id = lock_id;
        }

        public String getLock_name() {
            return lock_name;
        }

        public void setLock_name(String lock_name) {
            this.lock_name = lock_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
