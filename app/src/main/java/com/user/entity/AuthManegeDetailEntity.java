package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * hxg 2019.06.17
 */

public class AuthManegeDetailEntity extends BaseContentEntity {
    /**
     * content : {"icon":"http://www.colourlife.com/img-1","app_id":"ICEAPP-IIJG-JKJBGF-JJN","name":"电子卡券","auth_time":"2019-06-02 09:56","expires_time":"-1","auth_content":"获取你的公开信息（登录名、头像、性别等）","auth_state":1}
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
         * icon : http://www.colourlife.com/img-1
         * app_id : ICEAPP-IIJG-JKJBGF-JJN
         * name : 电子卡券
         * auth_time : 2019-06-02 09:56
         * expires_time : -1
         * auth_content : 获取你的公开信息（登录名、头像、性别等）
         * auth_state : 1
         */

        private String icon;
        private String app_id;
        private String name;
        private String auth_time;
        private String expires_time;
        private String auth_content;

        public Integer getAuth_state() {
            return auth_state;
        }

        public void setAuth_state(Integer auth_state) {
            this.auth_state = auth_state;
        }

        private Integer auth_state;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuth_time() {
            return auth_time;
        }

        public void setAuth_time(String auth_time) {
            this.auth_time = auth_time;
        }

        public String getExpires_time() {
            return expires_time;
        }

        public void setExpires_time(String expires_time) {
            this.expires_time = expires_time;
        }

        public String getAuth_content() {
            return auth_content;
        }

        public void setAuth_content(String auth_content) {
            this.auth_content = auth_content;
        }


    }
}
