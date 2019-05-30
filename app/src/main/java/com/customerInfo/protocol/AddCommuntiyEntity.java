package com.customerInfo.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.customerInfo.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/4 10:23
 * @change
 * @chang time
 * @class describe
 */

public class AddCommuntiyEntity extends BaseContentEntity {
    /**
     * content : {"id":"134345"}
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
         * id : 134345
         */

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
