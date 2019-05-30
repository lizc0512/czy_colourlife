package com.cashier.protocolchang;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.cashier.protocolchang
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/4/16 14:38
 * @change
 * @chang time
 * @class describe
 */

public class PayStatusEntity extends BaseContentEntity {
    /**
     * content : {"pay_success":1}
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
         * pay_success : 1
         */

        private int pay_success;

        public int getPay_success() {
            return pay_success;
        }

        public void setPay_success(int pay_success) {
            this.pay_success = pay_success;
        }
    }
}
