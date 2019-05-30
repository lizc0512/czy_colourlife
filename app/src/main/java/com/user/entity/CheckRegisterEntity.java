package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/2/26 11:15
 * @change
 * @chang time
 * @class describe  4.1.0 检查用户是否注册
 */

public class CheckRegisterEntity extends BaseContentEntity {

    /**
     * content : {"is_register":1}
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
         */

        private int is_register = 1;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        private String mobile;


        public int getIs_register() {
            return is_register;
        }

        public void setIs_register(int is_register) {
            this.is_register = is_register;
        }
    }
}
