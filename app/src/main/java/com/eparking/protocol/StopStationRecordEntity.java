package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/21 16:13
 * @change
 * @chang time
 * @class describe  停车记录
 */
public class StopStationRecordEntity extends BaseContentEntity {
    private List<ContentBean> content;

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean implements Serializable {
        /**
         * station_name : 深圳花乡
         * plate : 粤B12345
         * city_name : 深圳市
         * time_in : 2018-08-14 03:34:44
         * time_out : 2018-08-14 15:01:45
         * device_name : 收费岗出口
         * month : 临停
         */

        private String station_name;

        public String getStation_id() {
            return station_id;
        }

        public void setStation_id(String station_id) {
            this.station_id = station_id;
        }

        private String station_id;
        private String plate;
        private String city_name;
        private String time_in;
        private String time_out;
        private String device_name;
        private String month;

        public String getStation_name() {
            return station_name;
        }

        public void setStation_name(String station_name) {
            this.station_name = station_name;
        }

        public String getPlate() {
            return plate;
        }

        public void setPlate(String plate) {
            this.plate = plate;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getTime_in() {
            return time_in;
        }

        public void setTime_in(String time_in) {
            this.time_in = time_in;
        }

        public String getTime_out() {
            return time_out;
        }

        public void setTime_out(String time_out) {
            this.time_out = time_out;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }
}
