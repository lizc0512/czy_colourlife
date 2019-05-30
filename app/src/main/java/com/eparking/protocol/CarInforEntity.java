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
 * @class describe 我的卡包 车辆
 */
public class CarInforEntity extends BaseContentEntity {

    /**
     * content : [{"type":"my_car","plate":"闽CM778D","car_id":1220208,"brand":"","logo":""},{"type":"my_car","plate":"闽DC975L","car_id":2524766,"brand":"","logo":""}]
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

    public static class ContentBean implements Serializable {
        /**
         * type : my_car
         * plate : 闽CM778D
         * car_id : 1220208
         * brand :
         * logo :
         */

        private String type;
        private String plate;
        private String car_id;
        private String brand;
        private String logo;
        private String address;
        private String is_accred;
        private String is_contract;

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

        private String station_name;
        private String station_id;


        public int getContract_state() {
            return contract_state;
        }

        public void setContract_state(int contract_state) {
            this.contract_state = contract_state;
        }

        private int contract_state;


        public String getIs_accred() {
            return is_accred;
        }

        public void setIs_accred(String is_accred) {
            this.is_accred = is_accred;
        }



        public String getIs_contract() {
            return is_contract;
        }

        public void setIs_contract(String is_contract) {
            this.is_contract = is_contract;
        }



        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }


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
    }
}
