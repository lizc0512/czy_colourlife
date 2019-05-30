package cn.net.cyberway.home.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/4/23 16:54
 * @change
 * @chang time
 * @class describe  主动推送和被动接收的消息实体
 */

public class PushNotificationEntity extends BaseContentEntity {
    private List<ContentBean> content;

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * app_name : 彩惠人生
         * app_logo :
         * msg_id : 77d96e90c009647948558e58e22ed9ed
         * msg_title : 停车信息
         * link_url : link
         * send_time : 1523496064
         * valid_time : 1536456064
         */

        private String app_name;
        private String app_logo;

        public String getMsg_subject() {
            return msg_subject;
        }

        public void setMsg_subject(String msg_subject) {
            this.msg_subject = msg_subject;
        }

        private String msg_subject;
        private String msg_id;
        private String msg_title;
        private String link_url;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        private String img_url;
        private int send_time;
        private int valid_time;

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        private int is_read;

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

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        public int getSend_time() {
            return send_time;
        }

        public void setSend_time(int send_time) {
            this.send_time = send_time;
        }

        public int getValid_time() {
            return valid_time;
        }

        public void setValid_time(int valid_time) {
            this.valid_time = valid_time;
        }
    }
}
