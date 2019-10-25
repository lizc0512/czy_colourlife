package com.customerInfo.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * Created by hxg on 2019/4/20 09:55
 */
public class BeanDetailListEntity extends BaseContentEntity {
    /**
     * content : {"current_page":1,"data":[{"add_time":"2019-04-16 09:48","quantity":2,"comment":"获取-签到任务奖励"},{"add_time":"2019-04-15 14:39","quantity":2,"comment":"获取-设置性别任务奖励"},{"add_time":"2019-04-15 11:04","quantity":2,"comment":"获取-设置昵称任务奖励"},{"add_time":"2019-04-15 10:27","quantity":2,"comment":"获取-设置头像任务奖励"},{"add_time":"2019-04-14 12:00","quantity":2,"comment":"获取-签到任务奖励"},{"add_time":"2019-04-13 12:00","quantity":2,"comment":"获取-签到任务奖励"}],"from":1,"last_page":1,"next_page_url":null,"path":"http://www.integral.com/integral/user/flowDetail","per_page":10,"prev_page_url":null,"to":6,"total":6}
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
         * data : [{"add_time":"2019-04-16 09:48","quantity":2,"comment":"获取-签到任务奖励"},{"add_time":"2019-04-15 14:39","quantity":2,"comment":"获取-设置性别任务奖励"},{"add_time":"2019-04-15 11:04","quantity":2,"comment":"获取-设置昵称任务奖励"},{"add_time":"2019-04-15 10:27","quantity":2,"comment":"获取-设置头像任务奖励"},{"add_time":"2019-04-14 12:00","quantity":2,"comment":"获取-签到任务奖励"},{"add_time":"2019-04-13 12:00","quantity":2,"comment":"获取-签到任务奖励"}]
         * from : 1
         * last_page : 1
         * next_page_url : null
         * path : http://www.integral.com/integral/user/flowDetail
         * per_page : 10
         * prev_page_url : null
         * to : 6
         * total : 6
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
             * add_time : 2019-04-16 09:48
             * quantity : 2
             * comment : 获取-签到任务奖励
             */
            private String add_time;//操作时间
            private int quantity;//涉及彩豆数
            private String comment;//备注信息

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }
        }
    }
}