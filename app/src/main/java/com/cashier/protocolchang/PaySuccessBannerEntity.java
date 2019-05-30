package com.cashier.protocolchang;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.cashier.protocolchang
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/7/21 11:29
 * @change
 * @chang time
 * @class describe
 */

public class PaySuccessBannerEntity extends BaseContentEntity {

    /**
     * content : {"banner_arr":[{"id":1,"redirect_uri":"http://mp.wx.czytest.colourlife.com/app-activity-rryfp/dist_publish/index-app.html","category":1,"img":"https://vhczy.com/\\resources\\imgs\\banner.png","name":"人人有饭票banner"},{"id":17,"redirect_uri":null,"category":1,"img":"https://vhczy.com/\\resources\\imgs\\banner1.png","name":"非业主banner"}],"show":1,"swing_show":1,"swing_img":"","swing_url":"","swing_desc":""}
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
         * banner_arr : [{"id":1,"redirect_uri":"http://mp.wx.czytest.colourlife.com/app-activity-rryfp/dist_publish/index-app.html","category":1,"img":"https://vhczy.com/\\resources\\imgs\\banner.png","name":"人人有饭票banner"},{"id":17,"redirect_uri":null,"category":1,"img":"https://vhczy.com/\\resources\\imgs\\banner1.png","name":"非业主banner"}]
         * show : 1
         * swing_show : 1
         * swing_img :
         * swing_url :
         * swing_desc :
         */

        private int show;
        private int swing_show;
        private String swing_img;
        private String swing_url;

        public String getSwing_gif() {
            return swing_gif;
        }

        public void setSwing_gif(String swing_gif) {
            this.swing_gif = swing_gif;
        }

        private String swing_gif;
        private String swing_desc;
        private List<BannerArrBean> banner_arr;

        public int getShow() {
            return show;
        }

        public void setShow(int show) {
            this.show = show;
        }

        public int getSwing_show() {
            return swing_show;
        }

        public void setSwing_show(int swing_show) {
            this.swing_show = swing_show;
        }

        public String getSwing_img() {
            return swing_img;
        }

        public void setSwing_img(String swing_img) {
            this.swing_img = swing_img;
        }

        public String getSwing_url() {
            return swing_url;
        }

        public void setSwing_url(String swing_url) {
            this.swing_url = swing_url;
        }

        public String getSwing_desc() {
            return swing_desc;
        }

        public void setSwing_desc(String swing_desc) {
            this.swing_desc = swing_desc;
        }

        public List<BannerArrBean> getBanner_arr() {
            return banner_arr;
        }

        public void setBanner_arr(List<BannerArrBean> banner_arr) {
            this.banner_arr = banner_arr;
        }

        public static class BannerArrBean {
            /**
             * id : 1
             * redirect_uri : http://mp.wx.czytest.colourlife.com/app-activity-rryfp/dist_publish/index-app.html
             * category : 1
             * img : https://vhczy.com/\resources\imgs\banner.png
             * name : 人人有饭票banner
             */

            private int id;
            private String redirect_uri;
            private int category;
            private String img;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getRedirect_uri() {
                return redirect_uri;
            }

            public void setRedirect_uri(String redirect_uri) {
                this.redirect_uri = redirect_uri;
            }

            public int getCategory() {
                return category;
            }

            public void setCategory(int category) {
                this.category = category;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
