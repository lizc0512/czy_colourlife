package com.point.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * 文件名:饭票或积分的标识
 * 创建者:yuansongkai
 * 邮箱:827194927@qq.com
 * 创建日期:2019.11.13 9:35
 * 描述:
 **/
public class PointKeywordEntity extends BaseContentEntity {


    /**
     * content : {"keyword":"积分"}
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
         * keyword : 积分
         */

        private String keyword;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }
}
