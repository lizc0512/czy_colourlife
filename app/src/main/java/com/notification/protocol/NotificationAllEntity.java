package com.notification.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25.
 *
 * @Description
 */

public class NotificationAllEntity extends BaseContentEntity {


    /**
     * total : 12
     * count : 12
     * page_size : 20
     * content : [{"template_type":"2102","app_id":"cfrs","app_name":"彩富人生","msg_id":"7693a6b664f54f16bc19f916ce917ba7","msg_title":"彩富人生 投资成功","msg_sub_title":"恭喜您，您投资的XXX产品成功，快去订单详情页面查看您得订单吧。","msg_intro":"恭喜您，您投资的【信用宝-彩富年盈】产品已生效，感谢您的参与，如有疑问请联系400-000-000","msg_url":"colourlife://proto?type=notificationList","send_time":1533090372,"expire_time":1533090373,"show_type":0,"isread":1,"order_status":"投资成功","order_type":"2","items":[{"keyword_name":"产品类型","keyword_value":"彩富人生","keyword_type":1,"keyword_note":"http://www.baidu.com"},{"keyword_name":"地址","keyword_value":"彩生活","keyword_type":1,"keyword_note":"http://www.baidu.com"},{"keyword_name":"订单详情","keyword_value":"投资","keyword_type":3,"keyword_note":"http://www.baidu.com"},{"keyword_name":"下单时间","keyword_value":"2018/8/1 10:13","keyword_type":1,"keyword_note":"http://www.baidu.com"},{"keyword_name":"联系客服","keyword_value":"拨号","keyword_type":1,"keyword_note":"http://www.baidu.com"}]},{"template_type":"2100","app_id":"cfrs","app_name":"彩富人生","msg_id":"227da92b5ac94d1a8adb0c52553eca9a","msg_title":"彩富人生","msg_intro":"","msg_url":"","send_time":1532748458,"expire_time":1532748459,"show_type":1,"isread":1,"order_status":"增值成功","order_amount":"9999.99","order_total_time":"12期","order_name":"增值宝","order_descript":"增值期数","items":[{"keyword_name":"状态","keyword_value":"test1","keyword_type":1,"keyword_note":"geg"},{"keyword_name":"交易后金额","keyword_value":"去查看","keyword_type":3,"keyword_note":"geg"}]},{"template_type":"2100","app_id":"cfrs","app_name":"彩富人生","msg_id":"e45385c2541040979f36db5129232763","msg_title":"财富人生推送2018-07-27 17:24:26","msg_intro":"","msg_url":"","send_time":1532683466,"expire_time":1532683467,"show_type":1,"isread":1,"order_status":"增值成功","order_amount":"9999.99","order_total_time":"12期","order_name":"增值宝","items":[{"keyword_name":"状态","keyword_value":"test1","keyword_type":1,"keyword_note":"geg"},{"keyword_name":"交易后金额","keyword_value":"去查看","keyword_type":3,"keyword_note":"geg"}]},{"template_type":"2100","app_id":"cfrs","app_name":"彩富人生","msg_id":"714f276626dc45579aa3f42047922e48","msg_title":"财富人生推送","msg_intro":"","msg_url":"","send_time":1532661395,"expire_time":1532661396,"show_type":1,"isread":1,"order_status":"增值成功","order_amount":"9999.99","order_total_time":"12期","order_name":"增值宝","items":[{"keyword_name":"状态","keyword_value":"test1","keyword_type":1,"keyword_note":"geg"},{"keyword_name":"交易后金额","keyword_value":"去查看","keyword_type":3,"keyword_note":"geg"}]},{"template_type":"2100","app_id":"cfrs","app_name":"彩富人生","msg_id":"579310154e8841669738db0facd44ff0","msg_title":"财富人生推送","msg_sub_title":"子标题","msg_intro":"介绍啊","msg_url":"colourlife://proto?type=notificationList","send_time":1532596443,"expire_time":1532596444,"show_type":0,"isread":1,"msg_status":"发送成功","items":[]},{"template_type":"2100","app_id":"cfrs","app_name":"彩富人生","msg_id":"541893f20ceb4094b883e52e6f761303","msg_title":"财富人生推送","msg_sub_title":"子标题","msg_intro":"介绍啊","msg_url":"colourlife://proto?type=notificationList","send_time":1532596234,"expire_time":1532596235,"show_type":0,"isread":1,"msg_status":"发送成功","items":[]},{"template_type":"2100","app_id":"cfrs","app_name":"彩富人生","msg_id":"3afa121be4a240dfa0abc7d9891951a3","msg_title":"财富人生推送","msg_sub_title":"子标题","msg_intro":"介绍啊","msg_url":"colourlife://proto?type=notificationList","send_time":1532596135,"expire_time":1532596136,"show_type":1,"isread":1,"msg_status":"发送成功","items":[]},{"template_type":"2100","app_id":"cfrs","app_name":"彩富人生","msg_id":"330166215ace4961a35f2943399d19f2","msg_title":"财富人生推送","msg_sub_title":"子标题","msg_intro":"介绍啊","msg_url":"colourlife://proto?type=notificationList","send_time":1532596117,"expire_time":1532596118,"show_type":0,"isread":1,"msg_status":"发送成功","items":[]},{"template_type":"2100","app_id":"cfrs","app_name":"彩富人生","msg_id":"2dd3e8272a5c4429a4b9f3a692280906","msg_title":"财富人生推送","msg_sub_title":"子标题","msg_intro":"介绍啊","msg_url":"colourlife://proto?type=notificationList","send_time":1532592481,"expire_time":1532592482,"show_type":0,"isread":1,"msg_status":"发送成功","items":[]},{"template_type":"2100","app_id":"cfrs","app_name":"彩富人生","msg_id":"b4712a6322eb41c08f16aa7a435db623","msg_title":"title","msg_sub_title":"sub_title","msg_intro":"msgintro","msg_url":"msgurl","send_time":1532483328,"expire_time":1532483329,"show_type":0,"isread":1,"msg_status":"1","items":[]}]
     */

    private int total;
    private int count;
    private int page_size;
    private List<ContentBean> content;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * template_type : 2102
         * app_id : cfrs
         * app_name : 彩富人生
         * msg_id : 7693a6b664f54f16bc19f916ce917ba7
         * msg_title : 彩富人生 投资成功
         * msg_sub_title : 恭喜您，您投资的XXX产品成功，快去订单详情页面查看您得订单吧。
         * msg_intro : 恭喜您，您投资的【信用宝-彩富年盈】产品已生效，感谢您的参与，如有疑问请联系400-000-000
         * msg_url : colourlife://proto?type=notificationList
         * send_time : 1533090372
         * expire_time : 1533090373
         * show_type : 0
         * isread : 1
         * order_status : 投资成功
         * order_type : 2
         * items : [{"keyword_name":"产品类型","keyword_value":"彩富人生","keyword_type":1,"keyword_note":"http://www.baidu.com"},{"keyword_name":"地址","keyword_value":"彩生活","keyword_type":1,"keyword_note":"http://www.baidu.com"},{"keyword_name":"订单详情","keyword_value":"投资","keyword_type":3,"keyword_note":"http://www.baidu.com"},{"keyword_name":"下单时间","keyword_value":"2018/8/1 10:13","keyword_type":1,"keyword_note":"http://www.baidu.com"},{"keyword_name":"联系客服","keyword_value":"拨号","keyword_type":1,"keyword_note":"http://www.baidu.com"}]
         * order_amount : 9999.99
         * order_total_time : 12期
         * order_name : 增值宝
         * order_descript : 增值期数
         * msg_status : 发送成功
         */

        private String template_type;
        private String app_id;
        private String app_name;
        private String msg_id;
        private String msg_title;
        private String msg_sub_title;
        private String msg_intro;
        private String msg_url;

        public String getDetail_url() {
            return detail_url;
        }

        public void setDetail_url(String detail_url) {
            this.detail_url = detail_url;
        }

        private String detail_url;
        private int send_time;
        private int expire_time;
        private int show_type;
        private int isread;
        private String order_status;
        private String order_type;
        private String order_amount;

        public String getOrder_amount_type() {
            return order_amount_type;
        }

        public void setOrder_amount_type(String order_amount_type) {
            this.order_amount_type = order_amount_type;
        }

        private String order_amount_type;
        private String order_total_time;
        private String order_name;
        private String order_descript;
        private String msg_status;
        private List<ItemsBean> items;

        public String getTemplate_type() {
            return template_type;
        }

        public void setTemplate_type(String template_type) {
            this.template_type = template_type;
        }

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

        public String getMsg_sub_title() {
            return msg_sub_title;
        }

        public void setMsg_sub_title(String msg_sub_title) {
            this.msg_sub_title = msg_sub_title;
        }

        public String getMsg_intro() {
            return msg_intro;
        }

        public void setMsg_intro(String msg_intro) {
            this.msg_intro = msg_intro;
        }

        public String getMsg_url() {
            return msg_url;
        }

        public void setMsg_url(String msg_url) {
            this.msg_url = msg_url;
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

        public int getShow_type() {
            return show_type;
        }

        public void setShow_type(int show_type) {
            this.show_type = show_type;
        }

        public int getIsread() {
            return isread;
        }

        public void setIsread(int isread) {
            this.isread = isread;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
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

        public String getMsg_status() {
            return msg_status;
        }

        public void setMsg_status(String msg_status) {
            this.msg_status = msg_status;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * keyword_name : 产品类型
             * keyword_value : 彩富人生
             * keyword_type : 1
             * keyword_note : http://www.baidu.com
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
