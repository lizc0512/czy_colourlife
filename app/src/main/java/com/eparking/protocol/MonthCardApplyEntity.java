package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/1 9:46
 * @change
 * @chang time
 * @class describe  月卡申请
 */
public class MonthCardApplyEntity extends BaseContentEntity {


    /**
     * content : [{"apply_time":"2018-07-23 08:54:25","apply_state":0,"apply_id":159856,"apply_car_plate":"闽C56GV6","apply_station_name":"国际星城"},{"apply_time":"2018-07-10 09:17:43","apply_state":0,"apply_id":155042,"apply_car_plate":"闽DB3737","apply_station_name":"国际星城"}]
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
         * apply_time : 2018-07-23 08:54:25
         * apply_state : 0
         * apply_id : 159856
         * apply_car_plate : 闽C56GV6
         * apply_station_name : 国际星城
         */

        private String apply_time;
        private int apply_state;
        private int apply_id;
        private String apply_car_plate;
        private String apply_station_name;

        public String getApply_time() {
            return apply_time;
        }

        public void setApply_time(String apply_time) {
            this.apply_time = apply_time;
        }

        public int getApply_state() {
            return apply_state;
        }

        public void setApply_state(int apply_state) {
            this.apply_state = apply_state;
        }

        public int getApply_id() {
            return apply_id;
        }

        public void setApply_id(int apply_id) {
            this.apply_id = apply_id;
        }

        public String getApply_car_plate() {
            return apply_car_plate;
        }

        public void setApply_car_plate(String apply_car_plate) {
            this.apply_car_plate = apply_car_plate;
        }

        public String getApply_station_name() {
            return apply_station_name;
        }

        public void setApply_station_name(String apply_station_name) {
            this.apply_station_name = apply_station_name;
        }
    }
}
