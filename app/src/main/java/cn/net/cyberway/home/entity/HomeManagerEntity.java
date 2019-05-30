package cn.net.cyberway.home.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/12/13 20:32
 * @change
 * @chang time
 * @class describe
 */
public class HomeManagerEntity extends BaseContentEntity {

    /**
     * content : {"is_show":1,"bind_state":1,"username":"曾丽霞","mobile":"13418950558","avatar":"http://cc-czytest.colourlife.com/common/images/nopic.png","link":"http://evisit.czytest.colourlife.com/redirect"}
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
         * is_show : 1
         * bind_state : 1
         * username : 曾丽霞
         * mobile : 13418950558
         * avatar : http://cc-czytest.colourlife.com/common/images/nopic.png
         * link : http://evisit.czytest.colourlife.com/redirect
         */

        private int is_show;
        private int bind_state;
        private String username;
        private String mobile;
        private String avatar;
        private String link;

        public String getOa() {
            return oa;
        }

        public void setOa(String oa) {
            this.oa = oa;
        }

        private String oa;
        private String uuid;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }


        private String realname;
        private String nickname;
        private String sex;




        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        public int getBind_state() {
            return bind_state;
        }

        public void setBind_state(int bind_state) {
            this.bind_state = bind_state;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
