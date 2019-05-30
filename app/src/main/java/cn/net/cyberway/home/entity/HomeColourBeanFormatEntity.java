package cn.net.cyberway.home.entity;

import java.util.List;

/**
 * Created by hxg on 2019/4/16.
 */
public class HomeColourBeanFormatEntity {

    private List<signBean> sign;

    public List<signBean> getSign() {
        return sign;
    }

    public void setSign(List<signBean> sign) {
        this.sign = sign;
    }

    public static class signBean {
        /**
         * collect : 签到第几天
         * integral : [2,2,10,2,2,2,30]
         */
        private int current;
        private int integral;

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }
    }
}
