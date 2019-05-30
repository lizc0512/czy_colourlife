package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/2/26 14:31
 * @change
 * @chang time
 * @class describe  用户上传头像
 */

public class PortraitEntity extends BaseContentEntity {

    /**
     * content : {"img":"dev-5c3e96749330d253424.jpg","url":"https://pics-czy-cdn.colourlife.com/dev-5c3e96749330d253424.jpg"}
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
         * img : dev-5c3e96749330d253424.jpg
         * url : https://pics-czy-cdn.colourlife.com/dev-5c3e96749330d253424.jpg
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
