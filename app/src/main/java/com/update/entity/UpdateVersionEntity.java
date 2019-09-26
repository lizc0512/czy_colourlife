package com.update.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.update.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/3/26 14:10
 * @change
 * @chang time
 * @class describe
 */
public class UpdateVersionEntity extends BaseContentEntity {


    /**
     * content : {"info":{"func":"测试","bug":"测试","size":"44.43MB","create_time":1552981532,"version":"4.0.0","url":"https://baidu.com"},"result":3,"type":1}
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
         * info : {"func":"测试","bug":"测试","size":"44.43MB","create_time":1552981532,"version":"4.0.0","url":"https://baidu.com"}
         * result : 3
         * type : 1
         */

        private InfoBean info;
        private int result;
        private int type;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public static class InfoBean {
            /**
             * func : 测试
             * bug : 测试
             * size : 44.43MB
             * create_time : 1552981532
             * version : 4.0.0
             * url : https://baidu.com
             */

            private String func;
            private String bug;
            private String size;
            private int create_time;
            private String version;
            private String url;

            public String getFunc() {
                return func;
            }

            public void setFunc(String func) {
                this.func = func;
            }

            public String getBug() {
                return bug;
            }

            public void setBug(String bug) {
                this.bug = bug;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
