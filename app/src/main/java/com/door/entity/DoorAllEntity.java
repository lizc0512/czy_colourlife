package com.door.entity;

import com.nohttp.entity.BaseContentEntity;

import java.io.Serializable;
import java.util.List;

/**
 * hxg 2019 08.08
 */
public class DoorAllEntity extends BaseContentEntity {
    /**
     * content : {"community":[{"community_name":"未知状态小区","community_uuid":"e513a47b-a8ea-40fe-9aae-fa9b501d06b8"},{"community_name":null,"community_uuid":"bcfe0f35-37b0-49cf-a73d-ca96914a46a5"},{"community_name":"彩生活大厦","community_uuid":"82550031-75e7-4a8e-ab8f-074b285ab0a8"}],"data":[{"community_name":"未知状态小区","community_uuid":"e513a47b-a8ea-40fe-9aae-fa9b501d06b8","list":[{"name":"远程开门","type":"caihuijuDoor","keyList":[{"community_uuid":"e513a47b-a8ea-40fe-9aae-fa9b501d06b8","qr_code":"1554021044860","connection_type":2,"door_type":0,"community_type":1,"door_id":"1554021044860","door_name":"语音视频测试设备 [未知状态小区]  ","door_img":"https://probe-czytest.colourlife.comimages/open_icon_officebuilding@2x.png","open_sp":1,"openSp":0,"position":0,"community_name":"未知状态小区","color_id":0},{"community_uuid":"e513a47b-a8ea-40fe-9aae-fa9b501d06b8","qr_code":"1554021046472","connection_type":2,"door_type":0,"community_type":1,"door_id":"1554021046472","door_name":"测试2 [未知状态小区]  ","door_img":"https://probe-czytest.colourlife.comimages/open_icon_officebuilding@2x.png","open_sp":1,"openSp":0,"position":0,"community_name":"未知状态小区","color_id":0}]},{"name":"蓝牙开门","type":"bluetoothDoor","keyList":[{"id":"1159042041385529346","keyId":"kvEoYyGRugM5f5ayZFFUfT","name":"一栋一单元","accessId":"1157480962280992769","deviceId":"jTDqyAANjkndx4d5kerruP","mac":"0081F9B140A3","model":"ISH012","protocolVersion":"StandardVersion_0","cipherId":"67DDEC0021F1","isUnit":1}]}]},{"community_name":null,"community_uuid":"bcfe0f35-37b0-49cf-a73d-ca96914a46a5","list":[{"name":"远程开门","type":"caihuijuDoor","keyList":[{"community_uuid":"bcfe0f35-37b0-49cf-a73d-ca96914a46a5","qr_code":"1537401129129","connection_type":2,"door_type":0,"community_type":1,"door_id":"1537401129129","door_name":"七星十二楼大门","door_img":"https://probe-czytest.colourlife.comimages/open_icon_officebuilding@2x.png","open_sp":1,"openSp":0,"position":0,"community_name":null,"color_id":3333714}]}]},{"community_name":"彩生活大厦","community_uuid":"82550031-75e7-4a8e-ab8f-074b285ab0a8","list":[{"name":"远程开门","type":"caihuijuDoor","keyList":[{"community_uuid":null,"qr_code":"CSH000289","connection_type":0,"door_type":0,"community_type":2,"door_id":"949","door_name":"电梯1","door_img":"https://probe-czytest.colourlife.comimages/open_icon_officebuilding@2x.png","open_sp":0,"openSp":0,"position":0,"community_name":null,"color_id":0},{"community_uuid":null,"qr_code":"CSH000230","connection_type":0,"door_type":0,"community_type":2,"door_id":"954","door_name":"电梯2","door_img":"https://probe-czytest.colourlife.comimages/open_icon_officebuilding@2x.png","open_sp":0,"openSp":0,"position":1,"community_name":null,"color_id":0},{"community_uuid":null,"qr_code":"CSH000287","connection_type":0,"door_type":0,"community_type":2,"door_id":"317","door_name":"十二楼","door_img":"https://probe-czytest.colourlife.comimages/open_icon_officebuilding@2x.png","open_sp":0,"openSp":0,"position":2,"community_name":null,"color_id":0},{"community_uuid":null,"qr_code":"CSH000229","connection_type":0,"door_type":0,"community_type":2,"door_id":"955","door_name":"消防梯3","door_img":"https://probe-czytest.colourlife.comimages/open_icon_officebuilding@2x.png","open_sp":0,"openSp":0,"position":3,"community_name":null,"color_id":0}]}]}]}
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

    public static class ContentBean {
        private List<CommunityBean> community;
        private List<DataBean> data;

        public List<CommunityBean> getCommunity() {
            return community;
        }

        public void setCommunity(List<CommunityBean> community) {
            this.community = community;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class CommunityBean {
            /**
             * community_name : 未知状态小区
             * community_uuid : e513a47b-a8ea-40fe-9aae-fa9b501d06b8
             */

            private String community_name;
            private String community_uuid;

            public String getCommunity_name() {
                return community_name;
            }

            public void setCommunity_name(String community_name) {
                this.community_name = community_name;
            }

            public String getCommunity_uuid() {
                return community_uuid;
            }

            public void setCommunity_uuid(String community_uuid) {
                this.community_uuid = community_uuid;
            }
        }

        public static class DataBean {
            /**
             * community_name : 未知状态小区
             * community_uuid : e513a47b-a8ea-40fe-9aae-fa9b501d06b8
             * list : [{"name":"远程开门","type":"caihuijuDoor","keyList":[{"community_uuid":"e513a47b-a8ea-40fe-9aae-fa9b501d06b8","qr_code":"1554021044860","connection_type":2,"door_type":0,"community_type":1,"door_id":"1554021044860","door_name":"语音视频测试设备 [未知状态小区]  ","door_img":"https://probe-czytest.colourlife.comimages/open_icon_officebuilding@2x.png","open_sp":1,"openSp":0,"position":0,"community_name":"未知状态小区","color_id":0},{"community_uuid":"e513a47b-a8ea-40fe-9aae-fa9b501d06b8","qr_code":"1554021046472","connection_type":2,"door_type":0,"community_type":1,"door_id":"1554021046472","door_name":"测试2 [未知状态小区]  ","door_img":"https://probe-czytest.colourlife.comimages/open_icon_officebuilding@2x.png","open_sp":1,"openSp":0,"position":0,"community_name":"未知状态小区","color_id":0}]},{"name":"蓝牙开门","type":"bluetoothDoor","keyList":[{"id":"1159042041385529346","keyId":"kvEoYyGRugM5f5ayZFFUfT","name":"一栋一单元","accessId":"1157480962280992769","deviceId":"jTDqyAANjkndx4d5kerruP","mac":"0081F9B140A3","model":"ISH012","protocolVersion":"StandardVersion_0","cipherId":"67DDEC0021F1","isUnit":1}]}]
             */

            private String community_name;
            private String community_uuid;
            private List<ListBean> list;

            public String getCommunity_name() {
                return community_name;
            }

            public void setCommunity_name(String community_name) {
                this.community_name = community_name;
            }

            public String getCommunity_uuid() {
                return community_uuid;
            }

            public void setCommunity_uuid(String community_uuid) {
                this.community_uuid = community_uuid;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * name : 远程开门
                 * type : caihuijuDoor
                 * keyList : [{"community_uuid":"e513a47b-a8ea-40fe-9aae-fa9b501d06b8","qr_code":"1554021044860","connection_type":2,"door_type":0,"community_type":1,"door_id":"1554021044860","door_name":"语音视频测试设备 [未知状态小区]  ","door_img":"https://probe-czytest.colourlife.comimages/open_icon_officebuilding@2x.png","open_sp":1,"openSp":0,"position":0,"community_name":"未知状态小区","color_id":0},{"community_uuid":"e513a47b-a8ea-40fe-9aae-fa9b501d06b8","qr_code":"1554021046472","connection_type":2,"door_type":0,"community_type":1,"door_id":"1554021046472","door_name":"测试2 [未知状态小区]  ","door_img":"https://probe-czytest.colourlife.comimages/open_icon_officebuilding@2x.png","open_sp":1,"openSp":0,"position":0,"community_name":"未知状态小区","color_id":0}]
                 */

                private String name;
                private String type;

                public String getCommunity_uuid() {
                    return community_uuid;
                }

                public void setCommunity_uuid(String community_uuid) {
                    this.community_uuid = community_uuid;
                }

                private String community_uuid;
                private List<InvalidUnitBean> invalid_unit;

                public String getIdentity_id() {
                    return identity_id;
                }

                public void setIdentity_id(String identity_id) {
                    this.identity_id = identity_id;
                }

                private String identity_id;

                public String getApply_tag() {
                    return apply_tag;
                }

                public void setApply_tag(String apply_tag) {
                    this.apply_tag = apply_tag;
                }

                private String apply_tag;
                private List<KeyListBean> keyList;


                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public List<KeyListBean> getKeyList() {
                    return keyList;
                }

                public void setKeyList(List<KeyListBean> keyList) {
                    this.keyList = keyList;
                }

                public List<InvalidUnitBean> getInvalid_unit() {
                    return invalid_unit;
                }

                public void setInvalid_unit(List<InvalidUnitBean> invalid_unit) {
                    this.invalid_unit = invalid_unit;
                }


                public static class KeyListBean implements Serializable {
                    /**
                     * community_uuid : e513a47b-a8ea-40fe-9aae-fa9b501d06b8
                     * qr_code : 1554021044860
                     * connection_type : 2
                     * door_type : 0
                     * community_type : 1
                     * door_id : 1554021044860
                     * door_name : 语音视频测试设备 [未知状态小区]
                     * door_img : https://probe-czytest.colourlife.comimages/open_icon_officebuilding@2x.png
                     * open_sp : 1
                     * openSp : 0
                     * position : 0
                     * community_name : 未知状态小区
                     * color_id : 0
                     */
                    //远程开门
                    private String community_uuid;
                    private String qr_code;//用于远程开门
                    private int connection_type;
                    private int door_type;
                    private int community_type;
                    private String door_id;//用于编辑
                    private String door_name;
                    private String door_img;
                    private int open_sp;
                    private int openSp;
                    private int position;

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

                    private int start_time;
                    private int stop_time;
                    private String community_name;
                    private int color_id;

                    /**
                     * id : 1159042041385529346
                     * keyId : kvEoYyGRugM5f5ayZFFUfT
                     * name : 一栋一单元
                     * accessId : 1157480962280992769
                     * deviceId : jTDqyAANjkndx4d5kerruP
                     * mac : 0081F9B140A3
                     * model : ISH012
                     * protocolVersion : StandardVersion_0
                     * cipherId : 67DDEC0021F1
                     * isUnit : 1
                     */
                    //蓝牙开门
                    private String id;
                    private String keyId;
                    private String name;
                    private String accessId;
                    private String deviceId;
                    private String mac;
                    private String model;
                    private String protocolVersion;
                    private String cipherId;

                    public String getCommunityUuid() {
                        return communityUuid;
                    }

                    public void setCommunityUuid(String communityUuid) {
                        this.communityUuid = communityUuid;
                    }

                    private String communityUuid;
                    private int isUnit;

                    /**
                     * deviceId : WWsmczEgV6diMhuuanyXkV
                     * community_uuid : 6160e5da-f1aa-4faf-b6b6-30ce4d75e758
                     * keyId : u68rFjZJFDY87MnCdWEGoY
                     * protocolVersion : StandardVersion_0
                     * cipherId : 9D6A40E2BFEE
                     * name : 测试车位锁E07DEA3DD86A
                     * valid_begin : 0
                     * valid_end : 0
                     * mac : E07DEA3DD86A
                     * model : ISP001
                     * valid_date : 永久有效
                     */
                    //车位锁
                    private int valid_begin;
                    private int valid_end;
                    private String valid_date;
                    private String is_common;

                    public int getValid_begin() {
                        return valid_begin;
                    }

                    public void setValid_begin(int valid_begin) {
                        this.valid_begin = valid_begin;
                    }

                    public int getValid_end() {
                        return valid_end;
                    }

                    public void setValid_end(int valid_end) {
                        this.valid_end = valid_end;
                    }

                    public String getValid_date() {
                        return valid_date;
                    }

                    public void setValid_date(String valid_date) {
                        this.valid_date = valid_date;
                    }

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

                    public int getIsUnit() {
                        return isUnit;
                    }

                    public void setIsUnit(int isUnit) {
                        this.isUnit = isUnit;
                    }


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

                    public int getConnection_type() {
                        return connection_type;
                    }

                    public void setConnection_type(int connection_type) {
                        this.connection_type = connection_type;
                    }

                    public int getDoor_type() {
                        return door_type;
                    }

                    public void setDoor_type(int door_type) {
                        this.door_type = door_type;
                    }

                    public int getCommunity_type() {
                        return community_type;
                    }

                    public void setCommunity_type(int community_type) {
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

                    public int getOpen_sp() {
                        return open_sp;
                    }

                    public void setOpen_sp(int open_sp) {
                        this.open_sp = open_sp;
                    }

                    public int getOpenSp() {
                        return openSp;
                    }

                    public void setOpenSp(int openSp) {
                        this.openSp = openSp;
                    }

                    public int getPosition() {
                        return position;
                    }

                    public void setPosition(int position) {
                        this.position = position;
                    }

                    public String getCommunity_name() {
                        return community_name;
                    }

                    public void setCommunity_name(String community_name) {
                        this.community_name = community_name;
                    }

                    public int getColor_id() {
                        return color_id;
                    }

                    public void setColor_id(int color_id) {
                        this.color_id = color_id;
                    }

                    public String getIs_common() {
                        return is_common;
                    }

                    public void setIs_common(String is_common) {
                        this.is_common = is_common;
                    }

                }

                public static class InvalidUnitBean implements Serializable{
                    /**
                     * unit_name : 二单元
                     * unit_uuid : gwg-herh-heh-heh
                     */

                    private String unit_name;
                    private String unit_uuid;

                    public String getUnit_name() {
                        return unit_name;
                    }

                    public void setUnit_name(String unit_name) {
                        this.unit_name = unit_name;
                    }

                    public String getUnit_uuid() {
                        return unit_uuid;
                    }

                    public void setUnit_uuid(String unit_uuid) {
                        this.unit_uuid = unit_uuid;
                    }
                }
            }
        }
    }

}
