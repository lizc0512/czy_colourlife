package com.customerInfo.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.customerInfo.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/2 17:13
 * @change
 * @chang time
 * @class describe
 */

public class AddressListEntity extends BaseContentEntity {

    /**
     * content : {"paging":{"page_size":10,"total_page":2,"current_page":1,"total_record":12},"data":[{"id":324943,"community_uuid":"a7c36eb5-1f33-4cdf-9f57-2a89096db5be","community_name":"河畔新城","build_uuid":"","build_name":"42158","unit_uuid":"","unit_name":"","room_uuid":"","room_name":"153","address":"河畔新城42158153","is_default":0}]}
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
         * paging : {"page_size":10,"total_page":2,"current_page":1,"total_record":12}
         * data : [{"id":324943,"community_uuid":"a7c36eb5-1f33-4cdf-9f57-2a89096db5be","community_name":"河畔新城","build_uuid":"","build_name":"42158","unit_uuid":"","unit_name":"","room_uuid":"","room_name":"153","address":"河畔新城42158153","is_default":0}]
         */

        private PagingBean paging;
        private List<DataBean> data;

        public PagingBean getPaging() {
            return paging;
        }

        public void setPaging(PagingBean paging) {
            this.paging = paging;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class PagingBean {
            /**
             * page_size : 10
             * total_page : 2
             * current_page : 1
             * total_record : 12
             */

            private int page_size;
            private int total_page;
            private int current_page;
            private int total_record;

            public int getPage_size() {
                return page_size;
            }

            public void setPage_size(int page_size) {
                this.page_size = page_size;
            }

            public int getTotal_page() {
                return total_page;
            }

            public void setTotal_page(int total_page) {
                this.total_page = total_page;
            }

            public int getCurrent_page() {
                return current_page;
            }

            public void setCurrent_page(int current_page) {
                this.current_page = current_page;
            }

            public int getTotal_record() {
                return total_record;
            }

            public void setTotal_record(int total_record) {
                this.total_record = total_record;
            }
        }

        public static class DataBean {
            /**
             * id : 324943
             * community_uuid : a7c36eb5-1f33-4cdf-9f57-2a89096db5be
             * community_name : 河畔新城
             * build_uuid :
             * build_name : 42158
             * unit_uuid :
             * unit_name :
             * room_uuid :
             * room_name : 153
             * address : 河畔新城42158153
             * is_default : 0
             * authentication 是否认证 1：是，2：否
             * employee 是否员工 1：是，2：否
             * identity_state_name 身份认证状态  待审核
             * identity_id 身份标志编号 0
             * identity_name 身份名称  业主
             */

            private String id;
            private String community_uuid;
            private String community_name;
            private String build_uuid;
            private String build_name;
            private String unit_uuid;
            private String unit_name;
            private String room_uuid;
            private String room_name;
            private String address;
            private int is_default;
            private String city_name;
            private String authentication;
            private String employee;
            private String identity_state_name;//身份认证状态  待审核
            private String identity_id;//身份标志编号 0 为0表示未选择身份
            private String identity_name;//身份名称  业主

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

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

            public int getIs_default() {
                return is_default;
            }

            public void setIs_default(int is_default) {
                this.is_default = is_default;
            }

            public String getAuthentication() {
                return authentication;
            }

            public void setAuthentication(String authentication) {
                this.authentication = authentication;
            }

            public String getEmployee() {
                return employee;
            }

            public void setEmployee(String employee) {
                this.employee = employee;
            }

            public String getIdentity_state_name() {
                return identity_state_name;
            }

            public void setIdentity_state_name(String identity_state_name) {
                this.identity_state_name = identity_state_name;
            }

            public String getIdentity_id() {
                return identity_id;
            }

            public void setIdentity_id(String identity_id) {
                this.identity_id = identity_id;
            }

            public String getIdentity_name() {
                return identity_name;
            }

            public void setIdentity_name(String identity_name) {
                this.identity_name = identity_name;
            }
        }
    }
}
