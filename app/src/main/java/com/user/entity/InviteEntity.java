package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/2/26 16:33
 * @change
 * @chang time
 * @class describe   邀请注册的
 */

public class InviteEntity extends BaseContentEntity {
    /**
     * content : {"invite_result":"1","invite_message":"邀请成功"}
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
         * invite_result : 1
         * invite_message : 邀请成功
         */

        private String invite_result;
        private String invite_message;

        public String getInvite_result() {
            return invite_result;
        }

        public void setInvite_result(String invite_result) {
            this.invite_result = invite_result;
        }

        public String getInvite_message() {
            return invite_message;
        }

        public void setInvite_message(String invite_message) {
            this.invite_message = invite_message;
        }
    }
}
