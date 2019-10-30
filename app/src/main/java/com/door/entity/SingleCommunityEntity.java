package com.door.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/9/12.
 *
 * @Description
 */

public class SingleCommunityEntity {


    /**
     * code : 0
     * message :
     * content : {"common_use":[{"qr_code":"CSH000019","connection_type":"0","door_type":"0","door_name":"南国大门","position":4,"community_type":1,"door_id":752,"door_img":1},{"qr_code":"CSH000020","connection_type":"0","door_type":"0","door_name":"6岗门禁","position":5,"community_type":1,"door_id":753,"door_img":1}],"not_common_use":[{"qr_code":"CSH000019","connection_type":"0","door_type":"0","door_name":"南国大门","position":4,"community_type":1,"door_id":752,"door_img":1},{"qr_code":"CSH000020","connection_type":"0","door_type":"0","door_name":"6岗门禁","position":5,"community_type":1,"door_id":753,"door_img":1}]}
     * contentEncrypt :
     */

    private int code;
    private String message;
    private ContentBean content;
    private String contentEncrypt;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }

        private String  authority;
        private List<CommonUseBean> common_use;
        private List<NotCommonUseBean> not_common_use;
        private List<BluetoothBean> bluetooth;

        public List<CommonUseBean> getCommon_use() {
            return common_use;
        }

        public void setCommon_use(List<CommonUseBean> common_use) {
            this.common_use = common_use;
        }

        public List<NotCommonUseBean> getNot_common_use() {
            return not_common_use;
        }

        public void setNot_common_use(List<NotCommonUseBean> not_common_use) {
            this.not_common_use = not_common_use;
        }

        public List<BluetoothBean> getBluetooth() {
            return bluetooth;
        }

        public void setBluetooth(List<BluetoothBean> bluetooth) {
            this.bluetooth = bluetooth;
        }

        public static class CommonUseBean implements Serializable{
            /**
             * qr_code : CSH000019
             * connection_type : 0
             * door_type : 0
             * door_name : 南国大门
             * position : 4
             * community_type : 1
             * door_id : 752
             * door_img : 1
             */

            private String qr_code;
            private String connection_type;
            private String door_type;
            private String door_name;
            private String position;
            private String community_type;
            private String door_id;
            private String door_img;

            public String getQr_code() {
                return qr_code;
            }

            public void setQr_code(String qr_code) {
                this.qr_code = qr_code;
            }

            public String getConnection_type() {
                return connection_type;
            }

            public void setConnection_type(String connection_type) {
                this.connection_type = connection_type;
            }

            public String getDoor_type() {
                return door_type;
            }

            public void setDoor_type(String door_type) {
                this.door_type = door_type;
            }

            public String getDoor_name() {
                return door_name;
            }

            public void setDoor_name(String door_name) {
                this.door_name = door_name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getCommunity_type() {
                return community_type;
            }

            public void setCommunity_type(String community_type) {
                this.community_type = community_type;
            }

            public String getDoor_id() {
                return door_id;
            }

            public void setDoor_id(String door_id) {
                this.door_id = door_id;
            }

            public String getDoor_img() {
                return door_img;
            }

            public void setDoor_img(String door_img) {
                this.door_img = door_img;
            }
        }

        public static class NotCommonUseBean {
            /**
             * qr_code : CSH000019
             * connection_type : 0
             * door_type : 0
             * door_name : 南国大门
             * position : 4
             * community_type : 1
             * door_id : 752
             * door_img : 1
             */

            private String qr_code;
            private String connection_type;
            private String door_type;
            private String door_name;
            private String position;
            private String community_type;
            private String door_id;
            private String door_img;

            public String getQr_code() {
                return qr_code;
            }

            public void setQr_code(String qr_code) {
                this.qr_code = qr_code;
            }

            public String getConnection_type() {
                return connection_type;
            }

            public void setConnection_type(String connection_type) {
                this.connection_type = connection_type;
            }

            public String getDoor_type() {
                return door_type;
            }

            public void setDoor_type(String door_type) {
                this.door_type = door_type;
            }

            public String getDoor_name() {
                return door_name;
            }

            public void setDoor_name(String door_name) {
                this.door_name = door_name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getCommunity_type() {
                return community_type;
            }

            public void setCommunity_type(String community_type) {
                this.community_type = community_type;
            }

            public String getDoor_id() {
                return door_id;
            }

            public void setDoor_id(String door_id) {
                this.door_id = door_id;
            }

            public String getDoor_img() {
                return door_img;
            }

            public void setDoor_img(String door_img) {
                this.door_img = door_img;
            }
        }

        public static class BluetoothBean {
            /**
             * id : 1169444541615775746
             * keyId : HQC8bJT8BwPZk3UKFwTGak
             * name : 集团总部大门2222
             * accessId : 1
             * deviceId : vehyF2isobKHJawXmndhSb
             * mac : 0081F971CC5F
             * model : ISH012
             * protocolVersion : StandardVersion_0
             * cipherId : 7919FA95231E
             * isUnit : 0
             * startTime : 2019-09-05 17:05:48
             * endTime : 2020-01-06 17:05:48
             */

            private String id;
            private String keyId;
            private String name;
            private String accessId;
            private String deviceId;
            private String mac;
            private String model;
            private String protocolVersion;
            private String cipherId;
            private String isUnit;
            private String startTime;
            private String endTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getKeyId() {
                return keyId;
            }

            public void setKeyId(String keyId) {
                this.keyId = keyId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAccessId() {
                return accessId;
            }

            public void setAccessId(String accessId) {
                this.accessId = accessId;
            }

            public String getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }

            public String getMac() {
                return mac;
            }

            public void setMac(String mac) {
                this.mac = mac;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public String getProtocolVersion() {
                return protocolVersion;
            }

            public void setProtocolVersion(String protocolVersion) {
                this.protocolVersion = protocolVersion;
            }

            public String getCipherId() {
                return cipherId;
            }

            public void setCipherId(String cipherId) {
                this.cipherId = cipherId;
            }

            public String getIsUnit() {
                return isUnit;
            }

            public void setIsUnit(String isUnit) {
                this.isUnit = isUnit;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }
        }
    }
}
