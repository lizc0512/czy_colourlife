package cn.net.cyberway.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * 活动弹窗
 * hxg on 2019.08.20.
 */

public class BenefitActivityEntity extends BaseContentEntity {

    /**
     * content : {"title":"彩惠特供  端午优惠","image":"https://pics-caihui-cdn.colourlife.com/5ce8bc1335252368867.png","url":"http://chrs.wbd99.com/app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile&source=app"}
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
         * title : 彩惠特供  端午优惠
         * image : https://pics-caihui-cdn.colourlife.com/5ce8bc1335252368867.png
         * url : http://chrs.wbd99.com/app/index.php?i=1&c=entry&m=ewei_shopv2&do=mobile&source=app
         */

        private String title;
        private String image;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
