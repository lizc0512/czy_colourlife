package com.youmai.hxsdk.entity;

import java.util.List;

public class ScoreItem {


    private int code;
    private String message;
    private String contentEncrypt;
    private List<ContentBean> content;

    public boolean isSuccess() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
         * name : �ҷ�ο��
         * content : 123
         * time_create : 2019-04-23 16:40:47
         * level : 4
         */

        private String name;
        private String content;
        private String time_create;
        private int level;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime_create() {
            return time_create;
        }

        public void setTime_create(String time_create) {
            this.time_create = time_create;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}
