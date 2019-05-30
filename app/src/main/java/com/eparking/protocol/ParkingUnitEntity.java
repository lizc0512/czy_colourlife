package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/16 16:31
 * @change
 * @chang time
 * @class describe  绑定月卡 房间号
 */
public class ParkingUnitEntity extends BaseContentEntity {
    /**
     * content : [{"id":"SZ-CKDS-CKDS-1DY1-A2","unitname":"1单元","name":"-A2","floor":1,"tenement":"北京神州汽车租赁有限公司深圳分公司"}]
     * contentEncrypt :
     */

    private String contentEncrypt;
    private List<ContentBean> content;

    public String getContentEncrypt() {
        return contentEncrypt;
    }

    public void setContentEncrypt(String contentEncrypt) {
        this.contentEncrypt = contentEncrypt;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * id : SZ-CKDS-CKDS-1DY1-A2
         * unitname : 1单元
         * name : -A2
         * floor : 1
         * tenement : 北京神州汽车租赁有限公司深圳分公司
         */

        private String id;
        private String unitname;
        private String name;
        private int floor;
        private String tenement;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUnitname() {
            return unitname;
        }

        public void setUnitname(String unitname) {
            this.unitname = unitname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }

        public String getTenement() {
            return tenement;
        }

        public void setTenement(String tenement) {
            this.tenement = tenement;
        }
    }
}
