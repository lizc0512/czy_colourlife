package com.community.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * author:yuansk
 * cretetime:2020/3/2
 * desc:评论动态或回复别人评论
 **/
public class CommunityDynamicIdEntity extends BaseContentEntity {
    /**
     * content : {"comment_id":9}
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
         * comment_id : 9
         */

        private String comment_id;

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }
    }
}
