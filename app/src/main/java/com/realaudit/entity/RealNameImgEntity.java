package com.realaudit.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * 文件名:yuansongkai
 * 创建者:yuansongkai
 * 邮箱:827194927@qq.com
 * 创建日期:2020 1 14
 * 描述:
 **/
public class RealNameImgEntity extends BaseContentEntity {
    /**
     * content : {"img":"user-identity/dev-5e1cs0a7c60f0941161.jpg","url":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/user-identity/dev-5e1cs0a7c60f0941161.jpg"}
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
         * img : user-identity/dev-5e1cs0a7c60f0941161.jpg
         * url : https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/user-identity/dev-5e1cs0a7c60f0941161.jpg
         */

        private String img;
        private String url;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
