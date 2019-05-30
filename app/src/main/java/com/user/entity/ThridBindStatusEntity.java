package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/4/27 9:30
 * @change
 * @chang time
 * @class describe
 */
public class ThridBindStatusEntity extends BaseContentEntity {


    /**
     * content : {"bind_qq":1,"bind_weixin":1}
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
         * bind_qq : 1
         * bind_weixin : 1
         */

        private int bind_qq;
        private int bind_weixin;

        public int getBind_qq() {
            return bind_qq;
        }

        public void setBind_qq(int bind_qq) {
            this.bind_qq = bind_qq;
        }

        public int getBind_weixin() {
            return bind_weixin;
        }

        public void setBind_weixin(int bind_weixin) {
            this.bind_weixin = bind_weixin;
        }
    }
}
