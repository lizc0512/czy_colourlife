package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/3/13 9:32
 * @change
 * @chang time
 * @class describe
 */

public class LoginVerifyEntity extends BaseContentEntity {
    /**
     * content : {"status":"1"}
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
         * status : 1
         */

        private String status;

        public String getUser_error() {
            return user_error;
        }

        public void setUser_error(String user_error) {
            this.user_error = user_error;
        }

        public String getError_code() {
            return error_code;
        }

        public void setError_code(String error_code) {
            this.error_code = error_code;
        }

        private String user_error;
        private String error_code;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
