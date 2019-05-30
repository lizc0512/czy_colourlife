package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/3/1 9:48
 * @change
 * @chang time
 * @class describe
 */

public class CheckGatewayEntity extends BaseContentEntity {
    /**
     * content : {"check_result":0,"sms_token":1234}
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
         * check_result : 0
         * sms_token : 1234
         */

        private int check_result;
        private String sms_token;

        public int getCheck_result() {
            return check_result;
        }

        public void setCheck_result(int check_result) {
            this.check_result = check_result;
        }

        public String getSms_token() {
            return sms_token;
        }

        public void setSms_token(String sms_token) {
            this.sms_token = sms_token;
        }
    }
}
