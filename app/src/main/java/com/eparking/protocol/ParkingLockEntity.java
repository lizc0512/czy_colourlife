package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.io.Serializable;
import java.util.List;

import cn.csh.colourful.life.view.pickview.model.IPickerViewData;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/19 15:21
 * @change
 * @chang time
 * @class describe  我的卡包 车位锁
 */
public class ParkingLockEntity extends BaseContentEntity {


    /**
     * content : [{"type":"my_lock","station_id":19,"station_name":"七星商业广场","etcode":"00190192","lock_id":3,"lock_name":"190","status":"close"}]
     * contentEncrypt :
     */

    private List<ContentBean> content;


    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean implements Serializable, IPickerViewData {
        /**
         * type : my_lock
         * station_id : 19
         * station_name : 七星商业广场
         * etcode : 00190192
         * lock_id : 3
         * lock_name : 190
         * status : close
         */

        private String type;
        private String station_id;
        private String station_name;
        private String etcode;
        private String lock_id;
        private String lock_name;
        private String status;

        public String getStation_address() {
            return station_address;
        }

        public void setStation_address(String station_address) {
            this.station_address = station_address;
        }

        private String station_address;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        @Override
        public String getPickerViewText() {
            return station_name;
        }
    }
}
