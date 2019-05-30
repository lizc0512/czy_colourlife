package cn.net.cyberway.home.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/5/8 17:45
 * @change
 * @chang time
 * @class describe
 */
public class IsManagerEntity extends BaseContentEntity {


    /**
     * content : {"isManager":2,"oa":""}
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
         * isManager : 2
         * oa :
         */

        private int isManager;
        private String oa;

        public int getIsManager() {
            return isManager;
        }

        public void setIsManager(int isManager) {
            this.isManager = isManager;
        }

        public String getOa() {
            return oa;
        }

        public void setOa(String oa) {
            this.oa = oa;
        }
    }
}
