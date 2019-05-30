package com.notification.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 *
 * @Description
 */

public class NotificationListEntity extends BaseContentEntity{


    /**
     * content : {"read":[{"template_type":"2102","app_id":"cfrs","app_name":"彩富人生","msg_id":"714f276626dc45579aa3f42047922e48","msg_title":"财富人生推送","msg_intro":"","msg_url":"","send_time":1532661395,"expire_time":1535253395,"show_type":0,"isread":1,"order_status":"增值成功","order_amount":"9999.99","order_total_time":"12期","order_name":"增值宝","items":[{"keyword_name":"状态","keyword_value":"test1","keyword_type":1,"keyword_note":"geg"},{"keyword_name":"交易后金额","keyword_value":"去查看","keyword_type":3,"keyword_note":"geg"}],"msg_sub_title":"子标题","msg_status":"发送成功"},{"template_type":"2102","app_id":"cfrs","app_name":"彩富人生","msg_id":"579310154e8841669738db0facd44ff0","msg_title":"财富人生推送","msg_sub_title":"子标题","msg_intro":"介绍啊","msg_url":"colourlife://proto?type=notificationList","send_time":1532596443,"expire_time":1535188443,"show_type":0,"isread":1,"msg_status":"发送成功","items":[]},{"template_type":"2102","app_id":"cfrs","app_name":"彩富人生","msg_id":"541893f20ceb4094b883e52e6f761303","msg_title":"财富人生推送","msg_sub_title":"子标题","msg_intro":"介绍啊","msg_url":"colourlife://proto?type=notificationList","send_time":1532596234,"expire_time":1535188234,"show_type":0,"isread":1,"msg_status":"发送成功","items":[]},{"template_type":"2102","app_id":"cfrs","app_name":"彩富人生","msg_id":"3afa121be4a240dfa0abc7d9891951a3","msg_title":"财富人生推送","msg_sub_title":"子标题","msg_intro":"介绍啊","msg_url":"colourlife://proto?type=notificationList","send_time":1532596135,"expire_time":1535188135,"show_type":0,"isread":1,"msg_status":"发送成功","items":[]},{"template_type":"2102","app_id":"cfrs","app_name":"彩富人生","msg_id":"330166215ace4961a35f2943399d19f2","msg_title":"财富人生推送","msg_sub_title":"子标题","msg_intro":"介绍啊","msg_url":"colourlife://proto?type=notificationList","send_time":1532596117,"expire_time":1535188117,"show_type":0,"isread":1,"msg_status":"发送成功","items":[]},{"template_type":"2102","app_id":"cfrs","app_name":"彩富人生","msg_id":"2dd3e8272a5c4429a4b9f3a692280906","msg_title":"财富人生推送","msg_sub_title":"子标题","msg_intro":"介绍啊","msg_url":"colourlife://proto?type=notificationList","send_time":1532592481,"expire_time":1535184481,"show_type":0,"isread":1,"msg_status":"发送成功","items":[]},{"template_type":"2102","app_id":"cfrs","app_name":"彩富人生","msg_id":"b4712a6322eb41c08f16aa7a435db623","msg_title":"title","msg_sub_title":"sub_title","msg_intro":"msgintro","msg_url":"msgurl","send_time":1532483328,"expire_time":1535075328,"show_type":0,"isread":1,"msg_status":"1","items":[]}],"unRead":[{"template_type":"2102","app_id":"cfrs","app_name":"彩富人生","msg_id":"e45385c2541040979f36db5129232763","msg_title":"财富人生推送2018-07-27 17:24:26","msg_intro":"","msg_url":"","send_time":1532683466,"expire_time":1535275466,"show_type":0,"isread":0,"order_status":"增值成功","order_amount":"9999.99","order_total_time":"12期","order_name":"增值宝","items":[{"keyword_name":"状态","keyword_value":"test1","keyword_type":1,"keyword_note":"geg"},{"keyword_name":"交易后金额","keyword_value":"去查看","keyword_type":3,"keyword_note":"geg"}]}]}
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
        private List<ReadBean> read;
        private List<UnReadBean> unRead;

        public List<ReadBean> getRead() {
            return read;
        }

        public void setRead(List<ReadBean> read) {
            this.read = read;
        }

        public List<UnReadBean> getUnRead() {
            return unRead;
        }

        public void setUnRead(List<UnReadBean> unRead) {
            this.unRead = unRead;
        }

        public static class ReadBean {
            /**
             * template_type : 2102
             * app_id : cfrs
             * app_name : 彩富人生
             * msg_id : 714f276626dc45579aa3f42047922e48
             * msg_title : 财富人生推送
             * msg_intro :
             * msg_url :
             * send_time : 1532661395
             * expire_time : 1535253395
             * show_type : 0
             * isread : 1
             * order_status : 增值成功
             * order_amount : 9999.99
             * order_total_time : 12期
             * order_name : 增值宝
             * items : [{"keyword_name":"状态","keyword_value":"test1","keyword_type":1,"keyword_note":"geg"},{"keyword_name":"交易后金额","keyword_value":"去查看","keyword_type":3,"keyword_note":"geg"}]
             * msg_sub_title : 子标题
             * msg_status : 发送成功
             */

            private String template_type;
            private String app_id;
            private String app_name;

            public String getApp_logo() {
                return app_logo;
            }

            public void setApp_logo(String app_logo) {
                this.app_logo = app_logo;
            }

            private String app_logo;
            private String msg_id;
            private String msg_title;
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

            public String getShow_more() {
                return show_more;
            }

            public void setShow_more(String show_more) {
                this.show_more = show_more;
            }

            private String show_more;
            private int isread;
            private String order_status;
            private String order_amount;
            private String order_total_time;
            private String order_name;

            public String getOrder_descript() {
                return order_descript;
            }

            public void setOrder_descript(String order_descript) {
                this.order_descript = order_descript;
            }

            private String order_descript;

            public String getOrder_amount_type() {
                return order_amount_type;
            }

            public void setOrder_amount_type(String order_amount_type) {
                this.order_amount_type = order_amount_type;
            }

            private String order_amount_type;
            private String msg_sub_title;
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

            public String getMsg_sub_title() {
                return msg_sub_title;
            }

            public void setMsg_sub_title(String msg_sub_title) {
                this.msg_sub_title = msg_sub_title;
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

        public static class UnReadBean {
            public String getOrder_descript() {
                return order_descript;
            }

            public void setOrder_descript(String order_descript) {
                this.order_descript = order_descript;
            }

            public String getOrder_amount_type() {
                return order_amount_type;
            }

            public void setOrder_amount_type(String order_amount_type) {
                this.order_amount_type = order_amount_type;
            }

            public String getApp_logo() {
                return app_logo;
            }

            public void setApp_logo(String app_logo) {
                this.app_logo = app_logo;
            }

            /**
             * template_type : 2102
             * app_id : cfrs
             * app_name : 彩富人生
             * msg_id : e45385c2541040979f36db5129232763
             * msg_title : 财富人生推送2018-07-27 17:24:26
             * msg_intro :
             * msg_url :
             * send_time : 1532683466
             * expire_time : 1535275466
             * show_type : 0
             * isread : 0
             * order_status : 增值成功
             * order_amount : 9999.99
             * order_total_time : 12期
             * order_name : 增值宝
             * items : [{"keyword_name":"状态","keyword_value":"test1","keyword_type":1,"keyword_note":"geg"},{"keyword_name":"交易后金额","keyword_value":"去查看","keyword_type":3,"keyword_note":"geg"}]
             */
            private String order_descript;
            private String order_amount_type;
            private String template_type;
            private String app_id;
            private String app_name;
            private String msg_id;
            private String msg_title;
            private String msg_intro;
            private String msg_url;
            private int send_time;
            private int expire_time;
            private int show_type;
            private int isread;
            private String app_logo;
            private String order_status;
            private String order_amount;
            private String order_total_time;
            private String order_name;

            public String getDetail_url() {
                return detail_url;
            }

            public void setDetail_url(String detail_url) {
                this.detail_url = detail_url;
            }

            private String detail_url;

            public String getShow_more() {
                return show_more;
            }

            public void setShow_more(String show_more) {
                this.show_more = show_more;
            }

            private String show_more;

            public String getMsg_sub_title() {
                return msg_sub_title;
            }

            public void setMsg_sub_title(String msg_sub_title) {
                this.msg_sub_title = msg_sub_title;
            }

            private String msg_sub_title;
            private List<ItemsBeanX> items;

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

            public List<ItemsBeanX> getItems() {
                return items;
            }

            public void setItems(List<ItemsBeanX> items) {
                this.items = items;
            }

            public static class ItemsBeanX {
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
}
