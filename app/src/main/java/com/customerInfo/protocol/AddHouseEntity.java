package com.customerInfo.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.customerInfo.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/9/11 10:41
 * @change
 * @chang time
 * @class describe
 */
public class AddHouseEntity extends BaseContentEntity {
    /**
     * content : {"house_id":188}
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
         * house_id : 188
         */

        private String house_id;

        public String getHouse_id() {
            return house_id;
        }

        public void setHouse_id(String house_id) {
            this.house_id = house_id;
        }
    }
}
