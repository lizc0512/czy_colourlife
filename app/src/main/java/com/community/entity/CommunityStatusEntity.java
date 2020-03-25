package com.community.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * author:yuansk
 * cretetime:2020/3/25
 * desc:参与活动的状态
 **/
public class CommunityStatusEntity extends BaseContentEntity {


    /**
     * content : {"ac_status":1}
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
         * ac_status : 1
         */

        private String ac_status;

        public String getAc_status() {
            return ac_status;
        }

        public void setAc_status(String ac_status) {
            this.ac_status = ac_status;
        }
    }
}
