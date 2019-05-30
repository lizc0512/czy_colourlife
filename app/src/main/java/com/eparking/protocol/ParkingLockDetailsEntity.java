package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/26 16:23
 * @change
 * @chang time
 * @class describe
 */
public class ParkingLockDetailsEntity extends BaseContentEntity {
    /**
     * content : {"id":1,"station":11,"vendor":32,"battery":0,"status":"close","extcode":"000B57000011C2CF1DB6","ble":"13413513513","name":"测试车位锁","maintaintime":0,"creationtime":"2018-07-20 16:58:28","client":805,"position":"测试用的111","etcode":"ET1101","caseid":777840,"vendor_name":"小马","station_name":"彩科彩悦大厦","lock_user_name":"15811202074","lock_user_phone":"15811202074","lock_client":"358868"}
     */

    private ContentBean content;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * id : 1
         * station : 11
         * vendor : 32
         * battery : 0
         * status : close
         * extcode : 000B57000011C2CF1DB6
         * ble : 13413513513
         * name : 测试车位锁
         * maintaintime : 0
         * creationtime : 2018-07-20 16:58:28
         * client : 805
         * position : 测试用的111
         * etcode : ET1101
         * caseid : 777840
         * vendor_name : 小马
         * station_name : 彩科彩悦大厦
         * lock_user_name : 15811202074
         * lock_user_phone : 15811202074
         * lock_client : 358868
         */

        private int id;
        private int station;
        private int vendor;
        private int battery;
        private String status;
        private String extcode;
        private String ble;
        private String name;
        private int maintaintime;
        private String creationtime;
        private int client;
        private String position;
        private String etcode;
        private int caseid;
        private String vendor_name;
        private String station_name;
        private String lock_user_name;
        private String lock_user_phone;
        private String lock_client;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStation() {
            return station;
        }

        public void setStation(int station) {
            this.station = station;
        }

        public int getVendor() {
            return vendor;
        }

        public void setVendor(int vendor) {
            this.vendor = vendor;
        }

        public int getBattery() {
            return battery;
        }

        public void setBattery(int battery) {
            this.battery = battery;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getExtcode() {
            return extcode;
        }

        public void setExtcode(String extcode) {
            this.extcode = extcode;
        }

        public String getBle() {
            return ble;
        }

        public void setBle(String ble) {
            this.ble = ble;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMaintaintime() {
            return maintaintime;
        }

        public void setMaintaintime(int maintaintime) {
            this.maintaintime = maintaintime;
        }

        public String getCreationtime() {
            return creationtime;
        }

        public void setCreationtime(String creationtime) {
            this.creationtime = creationtime;
        }

        public int getClient() {
            return client;
        }

        public void setClient(int client) {
            this.client = client;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getEtcode() {
            return etcode;
        }

        public void setEtcode(String etcode) {
            this.etcode = etcode;
        }

        public int getCaseid() {
            return caseid;
        }

        public void setCaseid(int caseid) {
            this.caseid = caseid;
        }

        public String getVendor_name() {
            return vendor_name;
        }

        public void setVendor_name(String vendor_name) {
            this.vendor_name = vendor_name;
        }

        public String getStation_name() {
            return station_name;
        }

        public void setStation_name(String station_name) {
            this.station_name = station_name;
        }

        public String getLock_user_name() {
            return lock_user_name;
        }

        public void setLock_user_name(String lock_user_name) {
            this.lock_user_name = lock_user_name;
        }

        public String getLock_user_phone() {
            return lock_user_phone;
        }

        public void setLock_user_phone(String lock_user_phone) {
            this.lock_user_phone = lock_user_phone;
        }

        public String getLock_client() {
            return lock_client;
        }

        public void setLock_client(String lock_client) {
            this.lock_client = lock_client;
        }
    }
}
