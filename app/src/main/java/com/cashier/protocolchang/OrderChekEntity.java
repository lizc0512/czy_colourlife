package com.cashier.protocolchang;

import com.nohttp.entity.BaseContentEntity;

public class OrderChekEntity extends BaseContentEntity {
    /**
     * content : {"pay_success":1}  //1：已实名，2：未实名
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
        public String getIs_identity() {
            return is_identity;
        }

        public void setIs_identity(String is_identity) {
            this.is_identity = is_identity;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        /**
         * pay_success : 1
         */

        private String is_identity;
        private String note;


    }

}
