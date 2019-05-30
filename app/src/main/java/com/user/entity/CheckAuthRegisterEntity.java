package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/3/7 16:47
 * @change
 * @chang time
 * @class describe
 */

public class CheckAuthRegisterEntity extends BaseContentEntity {


    /**
     * content : {"is_register":1,"mobile":"18565863695"}
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
         * is_register : 1
         * mobile : 18565863695
         */

        private int is_register;
        private String mobile;

        public int getIs_register() {
            return is_register;
        }

        public void setIs_register(int is_register) {
            this.is_register = is_register;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
