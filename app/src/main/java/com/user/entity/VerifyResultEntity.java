package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/2/26 14:56
 * @change
 * @chang time
 * @class describe  验证手势密码的接口
 */

public class VerifyResultEntity extends BaseContentEntity {
    /**
     * content : {"verify_result":"1"}
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
         * verify_result : 1
         */

        private String verify_result;

        public String getVerify_result() {
            return verify_result;
        }

        public void setVerify_result(String verify_result) {
            this.verify_result = verify_result;
        }
    }
}
