package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/2/26 11:25
 * @change
 * @chang time
 * @class describe  检查用户是不是白名单的
 */

public class CheckWhiteEntity extends BaseContentEntity {
    /**
     * content : {"is_white":1}
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
         * is_white : 1
         */

        private int is_white;

        public String getNotice() {
            return notice;
        }

        public String getHotLine() {
            return hotLine;
        }

        public void setHotLine(String hotLine) {
            this.hotLine = hotLine;
        }

        public String hotLine;

        public void setNotice(String notice) {
            this.notice = notice;
        }

        private String notice;

        public int getIs_white() {
            return is_white;
        }

        public void setIs_white(int is_white) {
            this.is_white = is_white;
        }
    }
}
