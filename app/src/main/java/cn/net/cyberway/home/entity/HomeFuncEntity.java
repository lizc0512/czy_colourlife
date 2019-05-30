package cn.net.cyberway.home.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/4/20 9:28
 * @change
 * @chang time
 * @class describe  首页的banner 功能模块  和未登录时底部的都用这个实体类
 */

public class HomeFuncEntity extends BaseContentEntity {

    /**
     * content : [{"resource_id":18,"img":"https://vhczy.com/\\resources\\imgs\\caizhuzhai.png","name":"cjzx","redirect_uri":"http://www.colourlife.com/colourHouseVip.html","desc":"测试","superscript":""},{"resource_id":1,"img":"https://vhczy.com/\\resources\\imgs\\banner.png","name":"人人有饭票banner","redirect_uri":"http://mp.wx.czytest.colourlife.com/app-activity-rryfp/dist_publish/index-app.html","desc":"业主版","superscript":""}]
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
         * resource_id : 18
         * img : https://vhczy.com/\resources\imgs\caizhuzhai.png
         * name : cjzx
         * redirect_uri : http://www.colourlife.com/colourHouseVip.html
         * desc : 测试
         * superscript :
         * type :是否是视频
         * app_code :
         * filesize :视频大小
         * is_auth :是否需要认证
         */

        private String resource_id;
        private String img;
        private String name;
        private String redirect_uri;
        private String desc;
        private String superscript;

        private String type;//是否是视频
        private String app_code;
        private String filesize;//视频大小

        private String is_auth;//是否需要认证 1：需要实名，2：不需要实名

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getApp_code() {
            return app_code;
        }

        public void setApp_code(String app_code) {
            this.app_code = app_code;
        }

        public String getFilesize() {
            return filesize;
        }

        public void setFilesize(String filesize) {
            this.filesize = filesize;
        }

        public String getResource_id() {
            return resource_id;
        }

        public void setResource_id(String resource_id) {
            this.resource_id = resource_id;
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

        public String getRedirect_uri() {
            return redirect_uri;
        }

        public void setRedirect_uri(String redirect_uri) {
            this.redirect_uri = redirect_uri;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getSuperscript() {
            return superscript;
        }

        public void setSuperscript(String superscript) {
            this.superscript = superscript;
        }

        public String getIs_auth() {
            return is_auth;
        }

        public void setIs_auth(String is_auth) {
            this.is_auth = is_auth;
        }
    }
}
