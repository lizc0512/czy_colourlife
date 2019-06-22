package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/5/9 11:40
 * @change
 * @chang time
 * @class describe
 */

public class ChangeMobileEntity extends BaseContentEntity {

    /**
     * content : {"show":1,"url":"www.baidu.com"}
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
         * show : 1
         * url : www.baidu.com
         */

        private int show;
        private int is_logout;  // 1,  // 是否显示注销登录  1显示，2不显示
        private String url;

        public int getIs_logout() {
            return is_logout;
        }

        public void setIs_logout(int is_logout) {
            this.is_logout = is_logout;
        }

        public String getLogout_url() {
            return logout_url;
        }

        public void setLogout_url(String logout_url) {
            this.logout_url = logout_url;
        }

        private String logout_url;// 'https://www.baidu.com

        public int getShow() {
            return show;
        }

        public void setShow(int show) {
            this.show = show;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
