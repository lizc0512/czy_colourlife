package com.allapp.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.allapp.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/2/18 18:43
 * @change
 * @chang time
 * @class describe
 */
public class HomeAllLifeEntity extends BaseContentEntity {


    /**
     * content : {"arr":["物业"],"data":[{"id":27,"desc":"物业","list":[{"id":1,"img":"https://vhczy.com/\\resources\\imgs\\fpsc.png","url":"http://ticketmall-czytest.colourlife.com:80","name":"周边优惠"}]}]}
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
        private List<String> arr;
        private List<DataBean> data;

        public List<String> getArr() {
            return arr;
        }

        public void setArr(List<String> arr) {
            this.arr = arr;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 27
             * desc : 物业
             * list : [{"id":1,"img":"https://vhczy.com/\\resources\\imgs\\fpsc.png","url":"http://ticketmall-czytest.colourlife.com:80","name":"周边优惠"}]
             */

            private int id;
            private String desc;
            private List<ListBean> list;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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
                 * id : 1
                 * img : https://vhczy.com/\resources\imgs\fpsc.png
                 * url : http://ticketmall-czytest.colourlife.com:80
                 * name : 周边优惠
                 * is_auth : 是否需要认证
                 */

                private String id;
                private String img;
                private String url;
                private String name;

                private String is_auth;//是否需要认证 1：需要实名，2：不需要实名

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
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

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getIs_auth() {
                    return is_auth;
                }

                public void setIs_auth(String is_auth) {
                    this.is_auth = is_auth;
                }
            }
        }
    }
}
