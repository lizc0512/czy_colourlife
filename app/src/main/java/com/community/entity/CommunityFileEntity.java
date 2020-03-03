package com.community.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * author:yuansk
 * cretetime:2020/2/24
 * desc:上传发表动态图片
 **/
public class CommunityFileEntity extends BaseContentEntity {
    /**
     * content : {"obj":"neighbour/devface-5e4f98997113b403575.jpg","url":"https://czy-home-service.oss-cn-shenzhen.aliyuncs.com/neighbour/devface-5e4f98997113b403575.jpg"}
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
         * obj : neighbour/devface-5e4f98997113b403575.jpg
         * url : https://czy-home-service.oss-cn-shenzhen.aliyuncs.com/neighbour/devface-5e4f98997113b403575.jpg
         */

        private String obj;
        private String url;

        public String getObj() {
            return obj;
        }

        public void setObj(String obj) {
            this.obj = obj;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
