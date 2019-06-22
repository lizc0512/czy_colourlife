package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * hxg 2019.06.17
 */

public class AuthManegeListEntity extends BaseContentEntity {
    /**
     * content : [{"icon":"http://www.colourlife.com/img-1","app_id":"ICEAPP-IIJG-JKJBGF-JJN","name":"电子卡券","auth_time":"2019-06-02 09:56"}]
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
         * icon : http://www.colourlife.com/img-1
         * app_id : ICEAPP-IIJG-JKJBGF-JJN
         * name : 电子卡券
         * auth_time : 2019-06-02 09:56
         */

        private String icon;
        private String app_id;
        private String name;
        private String auth_time;

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
    }
}
