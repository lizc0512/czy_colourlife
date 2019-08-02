package cn.net.cyberway.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * 彩惠发现
 * hxg on 2019.07.31.
 */

public class BenefitFindEntity extends BaseContentEntity {
    /**
     * content : {"title":"发现","desc":"","total":12,"page":1,"page_size":10,"total_page":2,"list":[{"id":1,"title":"测试文章1","image":"http://pics-caihui-cdn.colourlife.com/5cd143480d1fb127517.jpg","date":"2019-05-17","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F1"},{"id":13,"title":"测试文章2","image":"http://caihui.test.colourlife.com/image/5ce37264b2842587102.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F13"},{"id":12,"title":"测试09","image":"http://caihui.test.colourlife.com/image/5ce370f501b71124920.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F12"},{"id":11,"title":"测试08","image":"http://caihui.test.colourlife.com/image/5ce370425af26393423.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F11"},{"id":10,"title":"测试06","image":"http://caihui.test.colourlife.com/image/5ce36fe918474930042.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F10"},{"id":9,"title":"测试05","image":"http://caihui.test.colourlife.com/image/5ce36f97b0be3117671.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F9"},{"id":8,"title":"测试04","image":"http://caihui.test.colourlife.com/image/5ce36f4ceeadc509799.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F8"},{"id":7,"title":"测试03","image":"http://caihui.test.colourlife.com/image/5ce36eff96d4e668542.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F7"},{"id":6,"title":"测试02","image":"http://caihui.test.colourlife.com/image/5ce36eab4c33f240896.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F6"},{"id":4,"title":"测试07","image":"https://img3.doubanio.com/view/subject/l/public/s2952121.jpg","date":"2019-05-20","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F4"}]}
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
         * title : 发现
         * desc :
         * total : 12
         * page : 1
         * page_size : 10
         * total_page : 2
         * list : [{"id":1,"title":"测试文章1","image":"http://pics-caihui-cdn.colourlife.com/5cd143480d1fb127517.jpg","date":"2019-05-17","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F1"},{"id":13,"title":"测试文章2","image":"http://caihui.test.colourlife.com/image/5ce37264b2842587102.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F13"},{"id":12,"title":"测试09","image":"http://caihui.test.colourlife.com/image/5ce370f501b71124920.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F12"},{"id":11,"title":"测试08","image":"http://caihui.test.colourlife.com/image/5ce370425af26393423.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F11"},{"id":10,"title":"测试06","image":"http://caihui.test.colourlife.com/image/5ce36fe918474930042.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F10"},{"id":9,"title":"测试05","image":"http://caihui.test.colourlife.com/image/5ce36f97b0be3117671.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F9"},{"id":8,"title":"测试04","image":"http://caihui.test.colourlife.com/image/5ce36f4ceeadc509799.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F8"},{"id":7,"title":"测试03","image":"http://caihui.test.colourlife.com/image/5ce36eff96d4e668542.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F7"},{"id":6,"title":"测试02","image":"http://caihui.test.colourlife.com/image/5ce36eab4c33f240896.jpg","date":"2019-05-21","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F6"},{"id":4,"title":"测试07","image":"https://img3.doubanio.com/view/subject/l/public/s2952121.jpg","date":"2019-05-20","url":"http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F4"}]
         */

        private String title;
        private String desc;
        private int total;
        private int page;
        private int page_size;
        private int total_page;
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

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 1
             * title : 测试文章1
             * image : http://pics-caihui-cdn.colourlife.com/5cd143480d1fb127517.jpg
             * date : 2019-05-17
             * url : http://caihui.test.colourlife.com/redirect?redirect_link=http%3A%2F%2Fcaihui.test.colourlife.com%2Fdist%2F%23%2Farticle%2Fdetail%2F1
             */

            private int id;
            private String title;
            private String image;
            private String date;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

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

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
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
