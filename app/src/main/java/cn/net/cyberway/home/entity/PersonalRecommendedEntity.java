package cn.net.cyberway.home.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/4/23 16:46
 * @change
 * @chang time
 * @class describe  饭票商城等个人推荐的实体类
 */

public class PersonalRecommendedEntity extends BaseContentEntity {


    /**
     * content : {"app_name":"","app_logo":"","msg_id":"b01488836e05efec0b12659a43b6049a","send_time":1525938116,"expire_time":1528530116,"items":[{"data":[{"view_type":"30000","img_title":"2","img_sub_title":"2","img_url":"2","link_url":"2"}]},{"data":[{"view_type":"30000","img_title":"3","img_sub_title":"3","img_url":"3","link_url":"3"},{"view_type":"30000","img_title":"4","img_sub_title":"4","img_url":"4","link_url":"4"},{"view_type":"30000","img_title":"5","img_sub_title":"5","img_url":"5","link_url":"5"}]},{"data":[{"view_type":"30000","img_title":"6","img_sub_title":"6","img_url":"6","link_url":"6"},{"view_type":"30000","img_title":"7","img_sub_title":"7","img_url":"7","link_url":"7"}]}]}
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
         * app_name :
         * app_logo :
         * msg_id : b01488836e05efec0b12659a43b6049a
         * send_time : 1525938116
         * expire_time : 1528530116
         * items : [{"data":[{"view_type":"30000","img_title":"2","img_sub_title":"2","img_url":"2","link_url":"2"}]},{"data":[{"view_type":"30000","img_title":"3","img_sub_title":"3","img_url":"3","link_url":"3"},{"view_type":"30000","img_title":"4","img_sub_title":"4","img_url":"4","link_url":"4"},{"view_type":"30000","img_title":"5","img_sub_title":"5","img_url":"5","link_url":"5"}]},{"data":[{"view_type":"30000","img_title":"6","img_sub_title":"6","img_url":"6","link_url":"6"},{"view_type":"30000","img_title":"7","img_sub_title":"7","img_url":"7","link_url":"7"}]}]
         */

        private String app_name;
        private String app_logo;

        public String getApp_link_url() {
            return app_link_url;
        }

        public void setApp_link_url(String app_link_url) {
            this.app_link_url = app_link_url;
        }

        private String app_link_url;
        private String msg_id;
        private int send_time;
        private int expire_time;
        private List<ItemsBean> items;

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
            private List<DataBean> data;

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * view_type : 30000
                 * img_title : 2
                 * img_sub_title : 2
                 * img_url : 2
                 * link_url : 2
                 */

                private String view_type;
                private String img_title;
                private String img_sub_title;
                private String img_url;
                private String link_url;

                public String getView_type() {
                    return view_type;
                }

                public void setView_type(String view_type) {
                    this.view_type = view_type;
                }

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
}
