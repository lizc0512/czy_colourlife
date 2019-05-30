package com.scanCode.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.scanCode.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/13 10:54
 * @change
 * @chang time
 * @class describe
 */
public class AnalysisQrCodeEntity extends BaseContentEntity {
    /**
     * content : {"url":"http://dr.ices.io","auth_type":2}
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
         * url : http://dr.ices.io
         * auth_type : 2
         */

        private String url;
        private int auth_type;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getAuth_type() {
            return auth_type;
        }

        public void setAuth_type(int auth_type) {
            this.auth_type = auth_type;
        }
    }
}
