package com.myproperty.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * 可认证房产列表
 * hxg 2019/4/27
 */
public class AddressAuthListEntity extends BaseContentEntity {
    /**
     * content : [{"id":"29587","community_uuid":"12832b57-1acc-4c38-a811-5781f178a477","community_name":"深圳家天下","build_uuid":"","build_name":"10542","unit_uuid":"","is_default":1,"unit_name":"","room_uuid":"","room_name":"11","address":"深圳家天下1054211","employee":1},{"id":"29587","community_uuid":"12832b57-1acc-4c38-a811-5781f178a477","community_name":"深圳家天下","build_uuid":"","build_name":"10542","unit_uuid":"","is_default":1,"unit_name":"","room_uuid":"","room_name":"11","address":"深圳家天下1054211","employee":1}]
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
         * id : 29587
         * community_uuid : 12832b57-1acc-4c38-a811-5781f178a477
         * community_name : 深圳家天下
         * build_uuid :
         * build_name : 10542
         * unit_uuid :
         * is_default : 1
         * unit_name :
         * room_uuid :
         * room_name : 11
         * address : 深圳家天下1054211
         * employee : 1
         * select:0
         */

        private String id;
        private String community_uuid;
        private String community_name;
        private String build_uuid;
        private String build_name;
        private String unit_uuid;
        private String is_default;
        private String unit_name;
        private String room_uuid;
        private String room_name;
        private String address;
        private String employee;
        private String select;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCommunity_uuid() {
            return community_uuid;
        }

        public void setCommunity_uuid(String community_uuid) {
            this.community_uuid = community_uuid;
        }

        public String getCommunity_name() {
            return community_name;
        }

        public void setCommunity_name(String community_name) {
            this.community_name = community_name;
        }

        public String getBuild_uuid() {
            return build_uuid;
        }

        public void setBuild_uuid(String build_uuid) {
            this.build_uuid = build_uuid;
        }

        public String getBuild_name() {
            return build_name;
        }

        public void setBuild_name(String build_name) {
            this.build_name = build_name;
        }

        public String getUnit_uuid() {
            return unit_uuid;
        }

        public void setUnit_uuid(String unit_uuid) {
            this.unit_uuid = unit_uuid;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public String getRoom_uuid() {
            return room_uuid;
        }

        public void setRoom_uuid(String room_uuid) {
            this.room_uuid = room_uuid;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmployee() {
            return employee;
        }

        public void setEmployee(String employee) {
            this.employee = employee;
        }

        public String getSelect() {
            return select;
        }

        public void setSelect(String select) {
            this.select = select;
        }
    }
}
