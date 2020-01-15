package com.customerInfo.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * 文件名:yuankaisong
 * 创建者:yuansongkai
 * 邮箱:827194927@qq.com
 * 创建日期:2010 1 14 14 40
 * 描述:
 **/
public class IdentityStateEntity extends BaseContentEntity {


    /**
     * content : {"state":1}
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
         * state : 1
         */

        private String state;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private String status;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        private String remark;

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        private long created_at;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
