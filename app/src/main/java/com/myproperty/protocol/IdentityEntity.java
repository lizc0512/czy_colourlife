package com.myproperty.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * 可认证房产列表
 * hxg 2019/4/27
 */
public class IdentityEntity extends BaseContentEntity {
    /**
     * content : [{"id":1,"name":"业主"},{"id":2,"name":"家属"},{"id":3,"name":"租户"},{"id":4,"name":"游客"}]
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
         * id : 1
         * name : 业主
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
