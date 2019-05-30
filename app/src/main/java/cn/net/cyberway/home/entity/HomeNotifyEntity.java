package cn.net.cyberway.home.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/5/8 10:20
 * @change
 * @chang time
 * @class describe
 */

public class HomeNotifyEntity extends BaseContentEntity {

    private List<ContentBean> content;

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * msg_title : 精选爆款好货
         * msg_sub_title : 上彩之云，赚物业费
         * msg_url : caihui.test.colourlife.com/is/order
         * app_logo : https://colourhome-czytest.colourlife.com//resources/imgs/fortest/icon_caihui1.png
         * app_name : 猜你喜欢
         * msg_id : 96a7990de88cc64dbaf5fcc41aa027aa
         * send_time : 1526888185
         * expire_time : 1529480185
         * items : [{"img_title":"快速购买","img_sub_title":"快速购买快速购买快速购买快速购买","img_url":"https://colourhome-czytest.colourlife.com//resources/imgs/caihui2/caihui_banner1_16.png","link_url":"caihui.test.colourlife.com/is/order"}]
         */

        private String msg_title;
        private String msg_sub_title;
        private String msg_url;



        private String app_code;
        private String app_logo;
        private String app_name;
        private String msg_id;
        private int send_time;
        private int expire_time;
        private List<ItemsBean> items;

        public String getApp_code() {
            return app_code;
        }

        public void setApp_code(String app_code) {
            this.app_code = app_code;
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

        public String getMsg_url() {
            return msg_url;
        }

        public void setMsg_url(String msg_url) {
            this.msg_url = msg_url;
        }

        public String getApp_logo() {
            return app_logo;
        }

        public void setApp_logo(String app_logo) {
            this.app_logo = app_logo;
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

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * img_title : 快速购买
             * img_sub_title : 快速购买快速购买快速购买快速购买
             * img_url : https://colourhome-czytest.colourlife.com//resources/imgs/caihui2/caihui_banner1_16.png
             * link_url : caihui.test.colourlife.com/is/order
             */

            private String img_title;
            private String img_sub_title;
            private String img_url;
            private String link_url;

            public String getImg_title() {
                return img_title;
            }

            public void setImg_title(String img_title) {
                this.img_title = img_title;
            }

            public String getImg_sub_title() {
                return img_sub_title;
            }

            public void setImg_sub_title(String img_sub_title) {
                this.img_sub_title = img_sub_title;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getLink_url() {
                return link_url;
            }

            public void setLink_url(String link_url) {
                this.link_url = link_url;
            }
        }
    }
}
