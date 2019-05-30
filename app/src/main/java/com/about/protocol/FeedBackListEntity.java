package com.about.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.about.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/2/16 11:21
 * @change
 * @chang time
 * @class describe
 */
public class FeedBackListEntity extends BaseContentEntity {


    /**
     * content : [{"id":1,"name":"功能建议","create_at":"2019-01-18 16:12","content":"\u2026\u2026","is_read":1,"redirect_uri":""},{"id":1,"name":"满意度","create_at":"2019-01-18 16:12","content":"\u2026\u2026","is_read":0,"redirect_uri":"http://www.baidu.com"}]
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

    public static class ContentBean implements Serializable {
        /**
         * id : 1
         * name : 功能建议
         * create_at : 2019-01-18 16:12
         * content : ……
         * is_read : 1
         * redirect_uri :
         */

        private String id;
        private String name;
        private String create_at;
        private String content;
        private int is_read;
        private String redirect_uri;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        public String getRedirect_uri() {
            return redirect_uri;
        }

        public void setRedirect_uri(String redirect_uri) {
            this.redirect_uri = redirect_uri;
        }
    }
}
