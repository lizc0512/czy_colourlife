package com.door.entity;

import com.nohttp.entity.BaseContentEntity;

import java.io.Serializable;

public class DoorSupportTypeEntity extends BaseContentEntity {
    /**
     * content : {"remote_door":1,"bluetooth_door":1,"bid":10003696}
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

    public static class ContentBean  implements Serializable {
        /**
         * remote_door : 1
         * bluetooth_door : 1
         * bid : 10003696
         */

        private String remote_door;
        private String bluetooth_door;
        private String bid;

        public String getRemote_door() {
            return remote_door;
        }

        public void setRemote_door(String remote_door) {
            this.remote_door = remote_door;
        }

        public String getBluetooth_door() {
            return bluetooth_door;
        }

        public void setBluetooth_door(String bluetooth_door) {
            this.bluetooth_door = bluetooth_door;
        }

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }
    }
}
