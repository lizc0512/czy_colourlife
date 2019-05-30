package cn.net.cyberway.home.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/4/23 17:33
 * @change
 * @chang time
 * @class describe  首页社区通话的模块
 */

public class HomeCommunityMsgEntity extends BaseContentEntity {

    /**
     * content : {"unread":0,"data":[{"app_id":"zfzs","app_name":"支付助手","app_logo":"https://xxtz.oss-cn-shenzhen.aliyuncs.com/appinfo/logo/a175b0727f794e928fd74d740469f7cb.png","msg_id":"028dc8932a69432d9232b6b9bc7acd7b","msg_subject":"¥35.8支付成功","msg_title":"彩惠人生","link_url":"colourlife://proto?type=notificationList","send_time":"1544588321","expire_time":"1547180321","is_read":"1"}]}
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
         * unread : 0
         * data : [{"app_id":"zfzs","app_name":"支付助手","app_logo":"https://xxtz.oss-cn-shenzhen.aliyuncs.com/appinfo/logo/a175b0727f794e928fd74d740469f7cb.png","msg_id":"028dc8932a69432d9232b6b9bc7acd7b","msg_subject":"¥35.8支付成功","msg_title":"彩惠人生","link_url":"colourlife://proto?type=notificationList","send_time":"1544588321","expire_time":"1547180321","is_read":"1"}]
         */

        private int unread;
        private List<DataBean> data;

        public int getUnread() {
            return unread;
        }

        public void setUnread(int unread) {
            this.unread = unread;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * app_id : zfzs
             * app_name : 支付助手
             * app_logo : https://xxtz.oss-cn-shenzhen.aliyuncs.com/appinfo/logo/a175b0727f794e928fd74d740469f7cb.png
             * msg_id : 028dc8932a69432d9232b6b9bc7acd7b
             * msg_subject : ¥35.8支付成功
             * msg_title : 彩惠人生
             * link_url : colourlife://proto?type=notificationList
             * send_time : 1544588321
             * expire_time : 1547180321
             * is_read : 1
             */

            private String app_id;
            private String app_name;

            private String app_logo;
            private String msg_id;
            private String msg_subject;
            private String msg_title;

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }

            private String order_status;
            private String link_url;
            private long send_time;
            private long expire_time;
            private String is_read;

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

            public String getMsg_subject() {
                return msg_subject;
            }

            public void setMsg_subject(String msg_subject) {
                this.msg_subject = msg_subject;
            }

            public String getMsg_title() {
                return msg_title;
            }

            public void setMsg_title(String msg_title) {
                this.msg_title = msg_title;
            }

            public String getLink_url() {
                return link_url;
            }

            public void setLink_url(String link_url) {
                this.link_url = link_url;
            }

            public long getSend_time() {
                return send_time;
            }

            public void setSend_time(long send_time) {
                this.send_time = send_time;
            }

            public long getExpire_time() {
                return expire_time;
            }

            public void setExpire_time(long expire_time) {
                this.expire_time = expire_time;
            }

            public String getIs_read() {
                return is_read;
            }

            public void setIs_read(String is_read) {
                this.is_read = is_read;
            }
        }
    }
}
