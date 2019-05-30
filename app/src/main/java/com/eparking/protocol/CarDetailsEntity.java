package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/26 17:07
 * @change
 * @chang time
 * @class describe
 */
public class CarDetailsEntity extends BaseContentEntity {
    /**
     * content : {"id":68194,"plate":"粤B12345","client_id":326506,"status":"N","autolock":"Y","push":"Y","brand":"","logo":""}
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
         * id : 68194
         * plate : 粤B12345
         * client_id : 326506
         * status : N
         * autolock : Y
         * push : Y
         * brand :
         * logo :
         */

        private int id;
        private String plate;
        private int client_id;
        private String status;
        private String autolock;
        private String push;
        private String brand;
        private String logo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPlate() {
            return plate;
        }

        public void setPlate(String plate) {
            this.plate = plate;
        }

        public int getClient_id() {
            return client_id;
        }

        public void setClient_id(int client_id) {
            this.client_id = client_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAutolock() {
            return autolock;
        }

        public void setAutolock(String autolock) {
            this.autolock = autolock;
        }

        public String getPush() {
            return push;
        }

        public void setPush(String push) {
            this.push = push;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
