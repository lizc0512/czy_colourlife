package cn.net.cyberway.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * 彩惠热门
 * hxg on 2019.07.31.
 *
 * @Description
 */

public class BenefitHotEntity extends BaseContentEntity {

    /**
     * content : {"title":"热门","desc":"好产品好服务，下单即送物业费","list":[{"title":"青藤教育","desc":"","image":"https://pics-caihui-cdn.colourlife.com/5d0c97eadca31129477.png","url":"http://tweb.qt1v1.com/colourlife/?source=app"},{"title":"彩车位","desc":"领车位抵扣券","image":"https://pics-caihui-cdn.colourlife.com/5d00cb7d22c23901133.png","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Fgood%2Fdetail%3Fgoods_id%3D553"},{"title":"充话费","desc":"充值送物业费","image":"https://pics-caihui-cdn.colourlife.com/5d00cb762769c200349.png","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2F"},{"title":"上京东","desc":"精选自营商品","image":"https://pics-caihui-cdn.colourlife.com/5d00cb6ef2ff7283686.png","url":"http://caihui.xinfuli.net/api/isv/coloured/login?redirect_type=100&source=app"}]}
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
         * title : 热门
         * desc : 好产品好服务，下单即送物业费
         * list : [{"title":"青藤教育","desc":"","image":"https://pics-caihui-cdn.colourlife.com/5d0c97eadca31129477.png","url":"http://tweb.qt1v1.com/colourlife/?source=app"},{"title":"彩车位","desc":"领车位抵扣券","image":"https://pics-caihui-cdn.colourlife.com/5d00cb7d22c23901133.png","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Fgood%2Fdetail%3Fgoods_id%3D553"},{"title":"充话费","desc":"充值送物业费","image":"https://pics-caihui-cdn.colourlife.com/5d00cb762769c200349.png","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2F"},{"title":"上京东","desc":"精选自营商品","image":"https://pics-caihui-cdn.colourlife.com/5d00cb6ef2ff7283686.png","url":"http://caihui.xinfuli.net/api/isv/coloured/login?redirect_type=100&source=app"}]
         */

        private String title;
        private String desc;
        private List<ListBean> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * title : 青藤教育
             * desc :
             * image : https://pics-caihui-cdn.colourlife.com/5d0c97eadca31129477.png
             * url : http://tweb.qt1v1.com/colourlife/?source=app
             */

            private String title;
            private String desc;
            private String image;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
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
}
