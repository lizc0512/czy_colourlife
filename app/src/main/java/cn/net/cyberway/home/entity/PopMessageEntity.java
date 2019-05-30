package cn.net.cyberway.home.entity;

import com.nohttp.entity.BaseRetCodeEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/4/23 16:45
 * @change
 * @chang time
 * @class describe   新的弹窗接口的实体
 */

public class PopMessageEntity extends BaseRetCodeEntity {


    /**
     * data : {"categoryCode":14,"data":[{"name":"3.8多\u201c彩\u201d女神节","img":"https://colourhome-czytest.colourlife.com/resources/imgs/goddess.jpg","url":"http://goddessa.ope.czy.colourlife.com/app/dist-publish/"}]}
     */

    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * categoryCode : 14
         * data : [{"name":"3.8多\u201c彩\u201d女神节","img":"https://colourhome-czytest.colourlife.com/resources/imgs/goddess.jpg","url":"http://goddessa.ope.czy.colourlife.com/app/dist-publish/"}]
         */

        private int categoryCode;
        private List<DataBean> data;

        public int getCategoryCode() {
            return categoryCode;
        }

        public void setCategoryCode(int categoryCode) {
            this.categoryCode = categoryCode;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * name : 3.8多“彩”女神节
             * img : https://colourhome-czytest.colourlife.com/resources/imgs/goddess.jpg
             * url : http://goddessa.ope.czy.colourlife.com/app/dist-publish/
             */

            private String name;
            private String img;
            private String url;

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
}
