package com.door.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

public class CommunityAllDoorEntity extends BaseContentEntity {
    /**
     * content : {"authority":1,"common_use":[{"community_uuid":"132adf54-ee31-4a29-8fd8-082030f07faf","qr_code":"CSH001556","connection_type":0,"door_type":0,"community_type":1,"door_id":"1705","door_name":"东门","door_img":"https://probe-czytest.colourlife.comimages/open_icon_door@2x.png","open_sp":0,"openSp":0,"position":0,"community_name":null,"color_id":0,"start_time":1509149565,"stop_time":1540685565,"user_type":5}],"not_common_use":[],"bluetooth":[{"id":"1169444541615775746","keyId":"HQC8bJT8BwPZk3UKFwTGak","name":"集团总部大门2222","accessId":"1","deviceId":"vehyF2isobKHJawXmndhSb","mac":"0081F971CC5F","model":"ISH012","protocolVersion":"StandardVersion_0","cipherId":"7919FA95231E","isUnit":0,"startTime":"2019-09-05 17:05:48","endTime":"2020-01-06 17:05:48"}]}
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
         * authority : 1
         * common_use : [{"community_uuid":"132adf54-ee31-4a29-8fd8-082030f07faf","qr_code":"CSH001556","connection_type":0,"door_type":0,"community_type":1,"door_id":"1705","door_name":"东门","door_img":"https://probe-czytest.colourlife.comimages/open_icon_door@2x.png","open_sp":0,"openSp":0,"position":0,"community_name":null,"color_id":0,"start_time":1509149565,"stop_time":1540685565,"user_type":5}]
         * not_common_use : []
         * bluetooth : [{"id":"1169444541615775746","keyId":"HQC8bJT8BwPZk3UKFwTGak","name":"集团总部大门2222","accessId":"1","deviceId":"vehyF2isobKHJawXmndhSb","mac":"0081F971CC5F","model":"ISH012","protocolVersion":"StandardVersion_0","cipherId":"7919FA95231E","isUnit":0,"startTime":"2019-09-05 17:05:48","endTime":"2020-01-06 17:05:48"}]
         */

        private int authority;
        private List<CommonUseBean> common_use;
        private List<?> not_common_use;
        private List<BluetoothBean> bluetooth;

        public int getAuthority() {
            return authority;
        }

        public void setAuthority(int authority) {
            this.authority = authority;
        }

        public List<CommonUseBean> getCommon_use() {
            return common_use;
        }

        public void setCommon_use(List<CommonUseBean> common_use) {
            this.common_use = common_use;
        }

        public List<?> getNot_common_use() {
            return not_common_use;
        }

        public void setNot_common_use(List<?> not_common_use) {
            this.not_common_use = not_common_use;
        }

        public List<BluetoothBean> getBluetooth() {
            return bluetooth;
        }

        public void setBluetooth(List<BluetoothBean> bluetooth) {
            this.bluetooth = bluetooth;
        }

        public static class CommonUseBean {
            /**
             * community_uuid : 132adf54-ee31-4a29-8fd8-082030f07faf
             * qr_code : CSH001556
             * connection_type : 0
             * door_type : 0
             * community_type : 1
             * door_id : 1705
             * door_name : 东门
             * door_img : https://probe-czytest.colourlife.comimages/open_icon_door@2x.png
             * open_sp : 0
             * openSp : 0
             * position : 0
             * community_name : null
             * color_id : 0
             * start_time : 1509149565
             * stop_time : 1540685565
             * user_type : 5
             */

            private String community_uuid;
            private String qr_code;
            private String connection_type;
            private String door_type;
            private String community_type;
            private String door_id;
            private String door_name;
            private String door_img;
            private String open_sp;
            private String openSp;
            private String position;
            private String community_name;
            private String color_id;
            private int start_time;
            private int stop_time;
            private String user_type;

            public String getCommunity_uuid() {
                return community_uuid;
            }

            public void setCommunity_uuid(String community_uuid) {
                this.community_uuid = community_uuid;
            }

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

            public String getDoor_name() {
                return door_name;
            }

            public void setDoor_name(String door_name) {
                this.door_name = door_name;
            }

            public String getDoor_img() {
                return door_img;
            }

            public void setDoor_img(String door_img) {
                this.door_img = door_img;
            }

            public String getOpen_sp() {
                return open_sp;
            }

            public void setOpen_sp(String open_sp) {
                this.open_sp = open_sp;
            }

            public String getOpenSp() {
                return openSp;
            }

            public void setOpenSp(String openSp) {
                this.openSp = openSp;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getCommunity_name() {
                return community_name;
            }

            public void setCommunity_name(String community_name) {
                this.community_name = community_name;
            }

            public String getColor_id() {
                return color_id;
            }

            public void setColor_id(String color_id) {
                this.color_id = color_id;
            }

            public int getStart_time() {
                return start_time;
            }

            public void setStart_time(int start_time) {
                this.start_time = start_time;
            }

            public int getStop_time() {
                return stop_time;
            }

            public void setStop_time(int stop_time) {
                this.stop_time = stop_time;
            }

            public String getUser_type() {
                return user_type;
            }

            public void setUser_type(String user_type) {
                this.user_type = user_type;
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
