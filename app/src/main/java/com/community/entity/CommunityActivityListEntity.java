package com.community.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * author:yuansk
 * cretetime:2020/3/25
 * desc:活动留言列表
 **/
public class CommunityActivityListEntity extends BaseContentEntity {


    /**
     * content : {"current_page":1,"data":[{"id":16,"source_id":2,"from_id":1002848700,"to_id":1002848703,"content":"回复你了，老弟","status":1,"created_at":"2020-03-23 18:39:48","updated_at":"1584959988","operate_reason":"\u201c\u201d","operator_id":"\u201c\u201d","from_nickname":"访客188****8888","from_avatar":"http://user.czytest.colourlife.com//images/default_head.png","from_gender":"0","from_mobile":"18818818888","to_nickname":"测试3","to_avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5e217724bae00169614.jpg","to_gender":"1","to_mobile":"15200000157","list_type":2}],"from":1,"last_page":1,"next_page_url":"\u201c\u201d","path":"http://www.neighbour.com/app/neighbour/getActivityComment","per_page":10,"prev_page_url":"\u201c\u201d","to":3,"total":3}
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
         * current_page : 1
         * data : [{"id":16,"source_id":2,"from_id":1002848700,"to_id":1002848703,"content":"回复你了，老弟","status":1,"created_at":"2020-03-23 18:39:48","updated_at":"1584959988","operate_reason":"\u201c\u201d","operator_id":"\u201c\u201d","from_nickname":"访客188****8888","from_avatar":"http://user.czytest.colourlife.com//images/default_head.png","from_gender":"0","from_mobile":"18818818888","to_nickname":"测试3","to_avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5e217724bae00169614.jpg","to_gender":"1","to_mobile":"15200000157","list_type":2}]
         * from : 1
         * last_page : 1
         * next_page_url : “”
         * path : http://www.neighbour.com/app/neighbour/getActivityComment
         * per_page : 10
         * prev_page_url : “”
         * to : 3
         * total : 3
         */

        private int current_page;
        private int from;
        private int last_page;
        private String next_page_url;
        private String path;
        private int per_page;
        private String prev_page_url;
        private int to;
        private int total;
        private List<DataBean> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public String getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(String next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public String getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(String prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 16
             * source_id : 2
             * from_id : 1002848700
             * to_id : 1002848703
             * content : 回复你了，老弟
             * status : 1
             * created_at : 2020-03-23 18:39:48
             * updated_at : 1584959988
             * operate_reason : “”
             * operator_id : “”
             * from_nickname : 访客188****8888
             * from_avatar : http://user.czytest.colourlife.com//images/default_head.png
             * from_gender : 0
             * from_mobile : 18818818888
             * to_nickname : 测试3
             * to_avatar : https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5e217724bae00169614.jpg
             * to_gender : 1
             * to_mobile : 15200000157
             * list_type : 2
             */

            private String id;
            private String source_id;
            private String from_id;
            private String to_id;
            private String content;
            private int status;
            private long created_at;
            private long updated_at;
            private String operate_reason;
            private String operator_id;
            private String from_nickname;
            private String from_avatar;
            private String from_gender;
            private String from_mobile;
            private String to_nickname;
            private String to_avatar;
            private String to_gender;
            private String to_mobile;
            private String list_type;

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getCreated_at() {
                return created_at;
            }

            public void setCreated_at(long created_at) {
                this.created_at = created_at;
            }

            public long getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(long updated_at) {
                this.updated_at = updated_at;
            }

            public String getOperate_reason() {
                return operate_reason;
            }

            public void setOperate_reason(String operate_reason) {
                this.operate_reason = operate_reason;
            }

            public String getOperator_id() {
                return operator_id;
            }

            public void setOperator_id(String operator_id) {
                this.operator_id = operator_id;
            }

            public String getFrom_nickname() {
                return from_nickname;
            }

            public void setFrom_nickname(String from_nickname) {
                this.from_nickname = from_nickname;
            }

            public String getFrom_avatar() {
                return from_avatar;
            }

            public void setFrom_avatar(String from_avatar) {
                this.from_avatar = from_avatar;
            }

            public String getFrom_gender() {
                return from_gender;
            }

            public void setFrom_gender(String from_gender) {
                this.from_gender = from_gender;
            }

            public String getFrom_mobile() {
                return from_mobile;
            }

            public void setFrom_mobile(String from_mobile) {
                this.from_mobile = from_mobile;
            }

            public String getTo_nickname() {
                return to_nickname;
            }

            public void setTo_nickname(String to_nickname) {
                this.to_nickname = to_nickname;
            }

            public String getTo_avatar() {
                return to_avatar;
            }

            public void setTo_avatar(String to_avatar) {
                this.to_avatar = to_avatar;
            }

            public String getTo_gender() {
                return to_gender;
            }

            public void setTo_gender(String to_gender) {
                this.to_gender = to_gender;
            }

            public String getTo_mobile() {
                return to_mobile;
            }

            public void setTo_mobile(String to_mobile) {
                this.to_mobile = to_mobile;
            }

            public String getList_type() {
                return list_type;
            }

            public void setList_type(String list_type) {
                this.list_type = list_type;
            }
        }
    }
}
