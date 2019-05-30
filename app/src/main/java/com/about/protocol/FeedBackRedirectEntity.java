package com.about.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.about.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/2/20 18:38
 * @change
 * @chang time
 * @class describe
 */
public class FeedBackRedirectEntity extends BaseContentEntity {


    /**
     * content : {"uri":"http://feedback.colourlife.pw/redirect"}
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
         * uri : http://feedback.colourlife.pw/redirect
         */

        private String uri;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }
}
