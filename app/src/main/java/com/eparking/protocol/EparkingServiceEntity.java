package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/15 17:58
 * @change
 * @chang time
 * @class describe  e停 首页服务的
 */
public class EparkingServiceEntity extends BaseContentEntity {


    /**
     * content : [{"title":"22","remark":"2","type":"text","image_url":"","jump_url":"www.baidu.com"},{"title":"33","remark":"33","type":"image","image_url":"http://eparking.b0.upaiyun.com/driver/licence/2018/08/07/fc917550102d80edecd2805090a1a6a0.png","jump_url":"www.baidu.com"}]
     * contentEncrypt :
     */

    private String contentEncrypt;
    private List<ContentBean> content;

    public String getContentEncrypt() {
        return contentEncrypt;
    }

    public void setContentEncrypt(String contentEncrypt) {
        this.contentEncrypt = contentEncrypt;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * title : 22
         * remark : 2
         * type : text
         * image_url :
         * jump_url : www.baidu.com
         */

        private String title;
        private String remark;
        private String type;
        private String image_url;
        private String jump_url;

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        private String logo_url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getJump_url() {
            return jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }
    }
}
