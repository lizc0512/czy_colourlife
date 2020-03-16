package com.community.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * author:yuansk
 * cretetime:2020/3/2
 * desc:动态提醒未读数量
 **/
public class CommunityDynamicRemindEntity extends BaseContentEntity {


    /**
     * content : {"count":1}
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
         * count : 1
         */

        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
