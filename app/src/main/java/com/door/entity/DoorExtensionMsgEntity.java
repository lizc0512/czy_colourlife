package com.door.entity;

import com.nohttp.entity.BaseContentEntity;

public class DoorExtensionMsgEntity extends BaseContentEntity {
    /**
     * content : {"user_uuid":"a0127ebf-6179-4276-b65e-50c329e18c41","name":"zhangxi","bid":10003696,"identity_id":"1","community_name":"七星广场","community_uuid":"bcfe0f35-37b0-49cf-a73d-ca96914a46a5"}
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
        /**
         * user_uuid : a0127ebf-6179-4276-b65e-50c329e18c41
         * name : zhangxi
         * bid : 10003696
         * identity_id : 1
         * community_name : 七星广场
         * community_uuid : bcfe0f35-37b0-49cf-a73d-ca96914a46a5
         */

        private String user_uuid;
        private String name;
        private String bid;
        private String identity_id;
        private String community_name;
        private String community_uuid;

        public String getUser_uuid() {
            return user_uuid;
        }

        public void setUser_uuid(String user_uuid) {
            this.user_uuid = user_uuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public String getIdentity_id() {
            return identity_id;
        }

        public void setIdentity_id(String identity_id) {
            this.identity_id = identity_id;
        }

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
}
