package com.update.entity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.update.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/5/20 11:01
 * @change
 * @chang time
 * @class describe
 */
public class UpdateContentEntity {
    /**
     * result : -1
     * info : [{"func":["1.支持人脸门禁音视频；\r","2.首页顶部图片展示优化； \r","3.新增彩钱包活动；\r","4.优化支付问题；\r","5.支持banner上传音视频。"],"bug":["1.支持人脸门禁音视频；\r","2.首页顶部图片展示优化； \r","3.新增彩钱包活动；\r","4.优化支付问题；\r","5.支持banner上传音视频。"],"size":"46.98","create_time":"1555070822","version":"7.0.3","url":"http://czyandroid.oss-cn-shenzhen.aliyuncs.com/colourlife_android/colourlife_v5.0.3.apk"}]
     */

    private int result;
    private List<InfoBean> info;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * func : ["1.支持人脸门禁音视频；\r","2.首页顶部图片展示优化； \r","3.新增彩钱包活动；\r","4.优化支付问题；\r","5.支持banner上传音视频。"]
         * bug : ["1.支持人脸门禁音视频；\r","2.首页顶部图片展示优化； \r","3.新增彩钱包活动；\r","4.优化支付问题；\r","5.支持banner上传音视频。"]
         * size : 46.98
         * create_time : 1555070822
         * version : 7.0.3
         * url : http://czyandroid.oss-cn-shenzhen.aliyuncs.com/colourlife_android/colourlife_v5.0.3.apk
         */

        private String size;
        private String create_time;
        private String version;
        private String url;
        private List<String> func;
        private List<String> bug;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<String> getFunc() {
            return func;
        }

        public void setFunc(List<String> func) {
            this.func = func;
        }

        public List<String> getBug() {
            return bug;
        }

        public void setBug(List<String> bug) {
            this.bug = bug;
        }
    }
}
