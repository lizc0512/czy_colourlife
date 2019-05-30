package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/16 14:39
 * @change
 * @chang time
 * @class describe 紧急开闸
 */
public class OpenGateEntity extends BaseContentEntity {
    /**
     * content : {"title":"彩之云将电话通知您和车主，请等候来电！","info":"彩之云将电话通知您和车主，请等候来电！"}
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
         * title : 彩之云将电话通知您和车主，请等候来电！
         * info : 彩之云将电话通知您和车主，请等候来电！
         */

        private String title;
        private String info;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
