package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/2/26 16:35
 * @change
 * @chang time
 * @class describe 邀请注册记录
 */

public class InviteRecordEntity extends BaseContentEntity {
    /**
     * content : [{"mobile":153203480257,"time_create":"1517900715","invite_state":1},{"mobile":153203480257,"time_create":"1517900715","invite_state":1}]
     * contentEncrypt :
     */

    private String contentEncrypt;
    private List<ContentBean> content;

    public String getContentEncrypt() {
        return contentEncrypt;
    }

    public void setContentEncrypt(String contentEncrypt) {
        this.contentEncrypt = contentEncrypt;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * mobile : 153203480257
         * time_create : 1517900715
         * invite_state : 1
         */

        private String mobile;
        private String time_create;
        private int invite_state;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTime_create() {
            return time_create;
        }

        public void setTime_create(String time_create) {
            this.time_create = time_create;
        }

        public int getInvite_state() {
            return invite_state;
        }

        public void setInvite_state(int invite_state) {
            this.invite_state = invite_state;
        }
    }
}
