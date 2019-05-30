package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/7 15:28
 * @change
 * @chang time
 * @class describe  绑定月卡楼栋
 */
public class ParkingBuildingEntity extends BaseContentEntity {


    /**
     * content : [{"id":"SZ-CKDS-CKDS","name":"彩科大厦","h_uuid":"516657ee-0376-11e6-8155-e247bfe54195"},{"id":"SZ-CKDS-CYDS","name":"彩悦大厦","h_uuid":"e13fe052-35ba-4e36-9eee-3d439e5412a7"},{"id":"SZ-CKDS-YZYCK","name":"硬资源仓库","h_uuid":"516654ba-0376-11e6-8155-e247bfe54195"}]
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

    public static class ContentBean implements Serializable {
        /**
         * id : SZ-CKDS-CKDS
         * name : 彩科大厦
         * h_uuid : 516657ee-0376-11e6-8155-e247bfe54195
         */

        private String id;
        private String name;
        private String h_uuid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getH_uuid() {
            return h_uuid;
        }

        public void setH_uuid(String h_uuid) {
            this.h_uuid = h_uuid;
        }
    }
}
