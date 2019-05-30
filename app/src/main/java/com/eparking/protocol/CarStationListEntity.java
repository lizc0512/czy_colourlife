package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

import cn.csh.colourful.life.view.pickview.model.IPickerViewData;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/16 14:35
 * @change
 * @chang time
 * @class describe  车辆和停车场
 */
public class CarStationListEntity extends BaseContentEntity {
    /**
     * content : [{"car_id":13635074,"plate":"闽C845BB","station_list":[{"station_id":230,"station_name":"国际星城"}]},{"car_id":15475095,"plate":"闽C211GV","station_list":[{"station_id":230,"station_name":"国际星城"}]},{"car_id":13635074,"plate":"闽C845BB","station_list":[{"station_id":3,"station_name":"南国丽园"}]},{"car_id":1220208,"plate":"闽CM778D","station_list":[{"station_id":3,"station_name":"南国丽园"}]}]
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

    public static class ContentBean implements IPickerViewData {
        /**
         * car_id : 13635074
         * plate : 闽C845BB
         * station_list : [{"station_id":230,"station_name":"国际星城"}]
         */

        private String car_id;
        private String plate;
        private List<StationListBean> station_list;

        public String getCar_id() {
            return car_id;
        }

        public void setCar_id(String car_id) {
            this.car_id = car_id;
        }

        public String getPlate() {
            return plate;
        }

        public void setPlate(String plate) {
            this.plate = plate;
        }

        public List<StationListBean> getStation_list() {
            return station_list;
        }

        public void setStation_list(List<StationListBean> station_list) {
            this.station_list = station_list;
        }

        @Override
        public String getPickerViewText() {
            return plate;
        }

        public static class StationListBean  implements IPickerViewData {
            /**
             * station_id : 230
             * station_name : 国际星城
             */

            private String station_id;
            private String station_name;

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

            @Override
            public String getPickerViewText() {

                return station_name;
            }
        }
    }
}
