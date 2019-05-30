package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/2/26 14:41
 * @change
 * @chang time
 * @class describe  用户二维码的
 */

public class QrCodeEntity extends BaseContentEntity {
    /**
     * content : {"qr_url":"http://cimg.colourlife.com/11.png"}
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
         * qr_url : http://cimg.colourlife.com/11.png
         */

        private String qr_url;

        public String getQr_url() {
            return qr_url;
        }

        public void setQr_url(String qr_url) {
            this.qr_url = qr_url;
        }
    }
}
