package com.update.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.update.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/3/26 14:10
 * @change
 * @chang time
 * @class describe
 */
public class UpdateVersionEntity extends BaseContentEntity {


    /**
     * content : {"update_code":0,"update_info":{"bug_list":["细节优化","其他bug修复"],"func_list":["首页新增IM","支付新增多种方式"],"size":37.84,"create_time":"2018.12.15","new_version":"6.6.4","url":"http://www.colourlife"}}
     */

    private ContentBean content;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * update_code : 0
         * update_info : {"bug_list":["细节优化","其他bug修复"],"func_list":["首页新增IM","支付新增多种方式"],"size":37.84,"create_time":"2018.12.15","new_version":"6.6.4","url":"http://www.colourlife"}
         */

        private int update_code;
        private UpdateInfoBean update_info;

        public int getUpdate_code() {
            return update_code;
        }

        public void setUpdate_code(int update_code) {
            this.update_code = update_code;
        }

        public UpdateInfoBean getUpdate_info() {
            return update_info;
        }

        public void setUpdate_info(UpdateInfoBean update_info) {
            this.update_info = update_info;
        }

        public static class UpdateInfoBean {
            /**
             * bug_list : ["细节优化","其他bug修复"]
             * func_list : ["首页新增IM","支付新增多种方式"]
             * size : 37.84
             * create_time : 2018.12.15
             * new_version : 6.6.4
             * url : http://www.colourlife
             */

            private double size;
            private String create_time;
            private String new_version;
            private String url;
            private List<String> bug_list;
            private List<String> func_list;

            public double getSize() {
                return size;
            }

            public void setSize(double size) {
                this.size = size;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getNew_version() {
                return new_version;
            }

            public void setNew_version(String new_version) {
                this.new_version = new_version;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public List<String> getBug_list() {
                return bug_list;
            }

            public void setBug_list(List<String> bug_list) {
                this.bug_list = bug_list;
            }

            public List<String> getFunc_list() {
                return func_list;
            }

            public void setFunc_list(List<String> func_list) {
                this.func_list = func_list;
            }
        }
    }
}
