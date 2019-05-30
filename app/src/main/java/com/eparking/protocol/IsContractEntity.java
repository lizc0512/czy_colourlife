package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/12/6 11:55
 * @change
 * @chang time
 * @class describe
 */
public class IsContractEntity extends BaseContentEntity {
    /**
     * content : {"is_contract":1}
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
         * is_contract : 1
         */

        private int is_contract;

        public String getIs_accred() {
            return is_accred;
        }

        public void setIs_accred(String is_accred) {
            this.is_accred = is_accred;
        }

        private String is_accred;

        public int getIs_contract() {
            return is_contract;
        }

        public void setIs_contract(int is_contract) {
            this.is_contract = is_contract;
        }
    }
}
