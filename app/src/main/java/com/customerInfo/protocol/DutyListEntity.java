package com.customerInfo.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * Created by hxg on 2019/4/4 09:55
 */
public class DutyListEntity extends BaseContentEntity {

    /**
     * content : {"once":[{"name":"设置头像","quantity":2,"url":"colourlife://proto?Information","is_finish":1,"img":null},{"name":"设置昵称","quantity":2,"url":"colourlife://proto?Information","is_finish":1,"img":null},{"name":"设置性别","quantity":2,"url":"colourlife://proto?Information","is_finish":1,"img":null}],"month":[{"name":"物业缴费","quantity":10,"url":"","is_finish":2,"img":null}]}
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
        private List<OnceBean> once;
        private List<MonthBean> month;

        public List<OnceBean> getOnce() {
            return once;
        }

        public void setOnce(List<OnceBean> once) {
            this.once = once;
        }

        public List<MonthBean> getMonth() {
            return month;
        }

        public void setMonth(List<MonthBean> month) {
            this.month = month;
        }

        public static class OnceBean {
            /**
             * name : 设置头像
             * quantity : 2
             * url : colourlife://proto?Information
             * is_finish : 1
             * img : null
             */

            private String name;
            private int quantity;
            private String url;
            private int is_finish;
            private String img;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getIs_finish() {
                return is_finish;
            }

            public void setIs_finish(int is_finish) {
                this.is_finish = is_finish;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class MonthBean {
            /**
             * name : 物业缴费
             * quantity : 10
             * url :
             * is_finish : 2
             * img : null
             */

            private String name;
            private int quantity;
            private String url;
            private int is_finish;
            private String img;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getIs_finish() {
                return is_finish;
            }

            public void setIs_finish(int is_finish) {
                this.is_finish = is_finish;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
