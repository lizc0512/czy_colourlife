package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/6/20 9:45
 * @change
 * @chang time
 * @class describe
 */
public class OneKeyShowEntity extends BaseContentEntity {
    /**
     * content : {"isShow":"1，"}
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


        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        private String is_show;


    }
}
