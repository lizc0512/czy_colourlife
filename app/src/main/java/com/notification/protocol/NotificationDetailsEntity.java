package com.notification.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 *
 * @Description
 */

public class NotificationDetailsEntity extends BaseContentEntity {


    /**
     * content : {"app_id":"cfrs","app_name":"彩富人生","app_logo":"","msg_id":"227da92b5ac94d1a8adb0c52553eca9a","msg_title":"彩富人生","msg_intro":"","send_time":1532748458,"expire_time":1535340458,"link_url":"","is_read":0,"order_status":"增值成功","order_amount":"9999.99","order_total_time":"12期","order_name":"增值宝","order_descript":"增值期数","items":[{"keyword_name":"状态","keyword_value":"test1","keyword_type":1,"keyword_note":"geg"},{"keyword_name":"交易后金额","keyword_value":"去查看","keyword_type":3,"keyword_note":"geg"}]}
     */

    private ContentBean content;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * app_id : cfrs
         * app_name : 彩富人生
         * app_logo :
         * msg_id : 227da92b5ac94d1a8adb0c52553eca9a
         * msg_title : 彩富人生
         * msg_intro :
         * send_time : 1532748458
         * expire_time : 1535340458
         * link_url :
         * is_read : 0
         * order_status : 增值成功
         * order_amount : 9999.99
         * order_total_time : 12期
         * order_name : 增值宝
         * order_descript : 增值期数
         * items : [{"keyword_name":"状态","keyword_value":"test1","keyword_type":1,"keyword_note":"geg"},{"keyword_name":"交易后金额","keyword_value":"去查看","keyword_type":3,"keyword_note":"geg"}]
         */

        private String app_id;
        private String app_name;
        private String app_logo;

        public String getDetail_title() {
            return detail_title;
        }

        public void setDetail_title(String detail_title) {
            this.detail_title = detail_title;
        }

        private String detail_title;

        public String getMsg_sub_title() {
            return msg_sub_title;
        }

        public void setMsg_sub_title(String msg_sub_title) {
            this.msg_sub_title = msg_sub_title;
        }

        private String msg_sub_title;
        private String msg_id;
        private String msg_title;
        private String msg_intro;
        private int send_time;
        private int expire_time;
        private String link_url;
        private int is_read;
        private String order_status;
        private String order_amount;
        private String order_total_time;
        private String order_name;
        private String order_descript;

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getOrder_amount_type() {
            return order_amount_type;
        }

        public void setOrder_amount_type(String order_amount_type) {
            this.order_amount_type = order_amount_type;
        }

        private String order_type;

        public String getDetail_type() {
            return detail_type;
        }

        public void setDetail_type(String detail_type) {
            this.detail_type = detail_type;
        }

        private String detail_type;
        private String order_amount_type;
        private List<ItemsBean> items;

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getApp_name() {
            return app_name;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public String getApp_logo() {
            return app_logo;
        }

        public void setApp_logo(String app_logo) {
            this.app_logo = app_logo;
        }

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getMsg_title() {
            return msg_title;
        }

        public void setMsg_title(String msg_title) {
            this.msg_title = msg_title;
        }

        public String getMsg_intro() {
            return msg_intro;
        }

        public void setMsg_intro(String msg_intro) {
            this.msg_intro = msg_intro;
        }

        public int getSend_time() {
            return send_time;
        }

        public void setSend_time(int send_time) {
            this.send_time = send_time;
        }

        public int getExpire_time() {
            return expire_time;
        }

        public void setExpire_time(int expire_time) {
            this.expire_time = expire_time;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public String getOrder_total_time() {
            return order_total_time;
        }

        public void setOrder_total_time(String order_total_time) {
            this.order_total_time = order_total_time;
        }

        public String getOrder_name() {
            return order_name;
        }

        public void setOrder_name(String order_name) {
            this.order_name = order_name;
        }

        public String getOrder_descript() {
            return order_descript;
        }

        public void setOrder_descript(String order_descript) {
            this.order_descript = order_descript;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * keyword_name : 状态
             * keyword_value : test1
             * keyword_type : 1
             * keyword_note : geg
             */

            private String keyword_name;
            private String keyword_value;
            private int keyword_type;
            private String keyword_note;

            public String getKeyword_name() {
                return keyword_name;
            }

            public void setKeyword_name(String keyword_name) {
                this.keyword_name = keyword_name;
            }

            public String getKeyword_value() {
                return keyword_value;
            }

            public void setKeyword_value(String keyword_value) {
                this.keyword_value = keyword_value;
            }

            public int getKeyword_type() {
                return keyword_type;
            }

            public void setKeyword_type(int keyword_type) {
                this.keyword_type = keyword_type;
            }

            public String getKeyword_note() {
                return keyword_note;
            }

            public void setKeyword_note(String keyword_note) {
                this.keyword_note = keyword_note;
            }
        }
    }
}
