package cn.net.cyberway.home.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * Created by hxg on 2019/4/16.
 */
public class HomeColourBeanEntity extends BaseContentEntity {

    /**
     * content : {"current":1,"collect":["2019-04-16","2019-04-15"],"integral":[2,2,10,2,2,2,30]}
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
         * current : 1
         * collect : ["2019-04-16","2019-04-15"]
         * integral : [2,2,10,2,2,2,30]
         */

        private int current;//当前签到天数
        private List<String> collect;
        private List<Integer> integral;//签到每天的积分

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public List<String> getCollect() {
            return collect;
        }

        public void setCollect(List<String> collect) {
            this.collect = collect;
        }

        public List<Integer> getIntegral() {
            return integral;
        }

        public void setIntegral(List<Integer> integral) {
            this.integral = integral;
        }
    }
}
