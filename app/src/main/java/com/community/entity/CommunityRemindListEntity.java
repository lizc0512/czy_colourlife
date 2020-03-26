package com.community.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * author:yuansk
 * cretetime:2020/3/2
 * desc:动态提醒列表的
 **/
public class CommunityRemindListEntity extends BaseContentEntity {


    /**
     * content : [{"id":2,"source_id":"2020_3","commenrt_id":"\u201c\u201d","remind_type":1,"content":"有人赞了你的动态 测试发布动态啊","is_read":"\u201c2\u201d","created_at":1583139034,"updated_at":1583140801,"from_id":1002646939,"to_id":1002848703}]
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
         * id : 2
         * source_id : 2020_3
         * commenrt_id : “”
         * remind_type : 1
         * content : 有人赞了你的动态 测试发布动态啊
         * is_read : “2”
         * created_at : 1583139034
         * updated_at : 1583140801
         * from_id : 1002646939
         * to_id : 1002848703
         */

        private String id;
        private String source_id;
        private String commenrt_id;
        private String remind_type;
        private String content;
        private String is_read;
        private int created_at;

        public int getList_type() {
            return list_type;
        }

        public void setList_type(int list_type) {
            this.list_type = list_type;
        }

        private int list_type;
        private int updated_at;
        private String from_id;
        private String to_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSource_id() {
            return source_id;
        }

        public void setSource_id(String source_id) {
            this.source_id = source_id;
        }

        public String getCommenrt_id() {
            return commenrt_id;
        }

        public void setCommenrt_id(String commenrt_id) {
            this.commenrt_id = commenrt_id;
        }

        public String getRemind_type() {
            return remind_type;
        }

        public void setRemind_type(String remind_type) {
            this.remind_type = remind_type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public int getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(int updated_at) {
            this.updated_at = updated_at;
        }

        public String getFrom_id() {
            return from_id;
        }

        public void setFrom_id(String from_id) {
            this.from_id = from_id;
        }

        public String getTo_id() {
            return to_id;
        }

        public void setTo_id(String to_id) {
            this.to_id = to_id;
        }
    }
}
