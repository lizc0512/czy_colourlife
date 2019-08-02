package cn.net.cyberway.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * 彩惠banner
 * hxg on 2019.07.31.
 *
 * @Description
 */

public class BenefitBannerEntity extends BaseContentEntity {

    /**
     * content : [{"id":65,"name":"京东精选","img":"http://pics-caihui-cdn.colourlife.com/5c9207f141ae9713086.png","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Flottery%2Flottery"}]
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
         * id : 65
         * name : 京东精选
         * img : http://pics-caihui-cdn.colourlife.com/5c9207f141ae9713086.png
         * url : http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Flottery%2Flottery
         */

        private int id;
        private String name;
        private String img;
        private String url;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
