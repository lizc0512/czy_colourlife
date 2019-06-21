package cn.net.cyberway.home.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/6/19 10:09
 * @change
 * @chang time
 * @class describe
 */
public class HomeBottomAdviseEntity extends BaseContentEntity {


    /**
     * content : [{"id":1,"name":"彩车位","url":"https://salesv2-ccw-test.colourlife.com/oauth","desc":"给您的爱车一个五星级的家","img_url":"https://pics-czy-cdn.colourlife.com/dev-5d099018b1957467773.png"},{"id":2,"name":"彩住宅","url":"https://salesv2-ccw-test.colourlife.com/oauth/czz","desc":"玩游戏可获话费、油卡","img_url":"https://pics-czy-cdn.colourlife.com/dev-5d09905627bdc431967.png"},{"id":3,"name":"彩富人生","url":"https://caifu-czytest.colourlife.com/h5/v35/index","desc":"开启社区服务新时代","img_url":"https://pics-czy-cdn.colourlife.com/dev-5d0990c0ed282103501.png"}]
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
         * id : 1
         * name : 彩车位
         * url : https://salesv2-ccw-test.colourlife.com/oauth
         * desc : 给您的爱车一个五星级的家
         * img_url : https://pics-czy-cdn.colourlife.com/dev-5d099018b1957467773.png
         */

        private int id;
        private String name;
        private String url;
        private String desc;
        private String img_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }
}
