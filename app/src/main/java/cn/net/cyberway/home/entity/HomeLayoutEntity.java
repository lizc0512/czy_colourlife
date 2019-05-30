package cn.net.cyberway.home.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/4/20 9:22
 * @change
 * @chang time
 * @class describe
 */

public class HomeLayoutEntity extends BaseContentEntity {

    /**
     * content : [{"app_code":1001,"app_name":"recently_module","is_show":1},{"app_code":1002,"app_name":"open_door","is_show":1},{"app_code":1003,"app_name":"community_notify","is_show":1},{"app_code":1004,"app_name":"mannger","is_show":1},{"app_code":1005,"app_name":"banner","is_show":1},{"app_code":1006,"app_name":"ad","is_show":1}]
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
         * app_code : 1001
         * app_name : recently_module
         * is_show : 1
         */

        private int app_code;
        private String app_name;
        private int is_show;

        public int getApp_code() {
            return app_code;
        }

        public void setApp_code(int app_code) {
            this.app_code = app_code;
        }

        public String getApp_name() {
            return app_name;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }
    }
}
