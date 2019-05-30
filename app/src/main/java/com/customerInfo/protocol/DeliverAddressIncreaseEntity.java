package com.customerInfo.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.customerInfo.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/8 9:15
 * @change
 * @chang time
 * @class describe
 */

public class DeliverAddressIncreaseEntity extends BaseContentEntity {
    /**
     * content : {"address_uuid":"xxxxx"}
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
         * address_uuid : xxxxx
         */

        private String address_uuid;

        public String getAddress_uuid() {
            return address_uuid;
        }

        public void setAddress_uuid(String address_uuid) {
            this.address_uuid = address_uuid;
        }
    }
}
