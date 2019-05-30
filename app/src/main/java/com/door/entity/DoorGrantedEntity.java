package com.door.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.door.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/4/1 10:01
 * @change
 * @chang time
 * @class describe
 */
public class DoorGrantedEntity extends BaseContentEntity {
    /**
     * content : {"isgranted":1}
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
         * isgranted : 1
         */

        private String isgranted;

        public String getIsgranted() {
            return isgranted;
        }

        public void setIsgranted(String isgranted) {
            this.isgranted = isgranted;
        }
    }
}
