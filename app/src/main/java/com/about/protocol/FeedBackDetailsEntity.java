package com.about.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.about.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/2/16 11:32
 * @change
 * @chang time
 * @class describe
 */
public class FeedBackDetailsEntity extends BaseContentEntity {


    /**
     * content : {"feedback":{"content":"测试","name":"其他","create_at":"2019-02-17 12:24","image_arr":[{"url":"http://cimg.colourlife.com/images/2019/02/17/12/245279028.png"}]},"reply":{"content":"测试","create_at":"2019-02-17 12:24","name":"李四"}}
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
         * feedback : {"content":"测试","name":"其他","create_at":"2019-02-17 12:24","image_arr":[{"url":"http://cimg.colourlife.com/images/2019/02/17/12/245279028.png"}]}
         * reply : {"content":"测试","create_at":"2019-02-17 12:24","name":"李四"}
         */

        private FeedbackBean feedback;


        public FeedbackBean getFeedback() {
            return feedback;
        }

        public void setFeedback(FeedbackBean feedback) {
            this.feedback = feedback;
        }


        public static class FeedbackBean {
            /**
             * content : 测试
             * name : 其他
             * create_at : 2019-02-17 12:24
             * image_arr : [{"url":"http://cimg.colourlife.com/images/2019/02/17/12/245279028.png"}]
             */

            private String content;
            private String name;
            private String create_at;
            private List<ImageArrBean> image_arr;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }

            public List<ImageArrBean> getImage_arr() {
                return image_arr;
            }

            public void setImage_arr(List<ImageArrBean> image_arr) {
                this.image_arr = image_arr;
            }

            public static class ImageArrBean {
                /**
                 * url : http://cimg.colourlife.com/images/2019/02/17/12/245279028.png
                 */

                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
