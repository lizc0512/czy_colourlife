package com.door.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/14.
 *
 * @Description
 */

public class OpenDoorResultEntity {


    /**
     * code : 0
     * message : success
     * content : {"open_result":0,"price":{"price_state":1,"amount":""},"ad":[{"img":"http://www.baidu.com/image","uri":"http://www.baidu.com/image"},{"img":"http://www.baidu.com/image","uri":"http://www.baidu.com/image"}]}
     * contentEncrypt :
     */

    private int code;
    private String message;
    private ContentBean content;
    private String contentEncrypt;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
         * open_result : 0
         * price : {"price_state":1,"amount":""}
         * ad : [{"img":"http://www.baidu.com/image","uri":"http://www.baidu.com/image"},{"img":"http://www.baidu.com/image","uri":"http://www.baidu.com/image"}]
         */

        private int open_result;
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        private PriceBean price;
        private List<AdBean> ad;

        public int getOpen_result() {
            return open_result;
        }

        public void setOpen_result(int open_result) {
            this.open_result = open_result;
        }

        public PriceBean getPrice() {
            return price;
        }

        public void setPrice(PriceBean price) {
            this.price = price;
        }

        public List<AdBean> getAd() {
            return ad;
        }

        public void setAd(List<AdBean> ad) {
            this.ad = ad;
        }

        public static class PriceBean {
            /**
             * price_state : 1
             * amount :
             */

            private int price_state;
            private String amount;

            public int getPrice_state() {
                return price_state;
            }

            public void setPrice_state(int price_state) {
                this.price_state = price_state;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }
        }

        public static class AdBean {
            /**
             * img : http://www.baidu.com/image
             * uri : http://www.baidu.com/image
             */

            private String img;
            private String uri;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }
        }
    }
}
