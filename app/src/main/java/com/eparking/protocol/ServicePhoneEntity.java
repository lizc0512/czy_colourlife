package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/22 19:36
 * @change
 * @chang time
 * @class describe  客服电话
 */
public class ServicePhoneEntity extends BaseContentEntity {
    /**
     * content : {"customer_service_phone":"8888888"}
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
         * customer_service_phone : 8888888
         */

        private String customer_service_phone;

        public String getCustomer_service_phone() {
            return customer_service_phone;
        }

        public void setCustomer_service_phone(String customer_service_phone) {
            this.customer_service_phone = customer_service_phone;
        }
    }
}
