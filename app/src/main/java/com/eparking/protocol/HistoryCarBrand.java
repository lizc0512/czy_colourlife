package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

import cn.csh.colourful.life.view.pickview.model.IPickerViewData;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/13 17:53
 * @change
 * @chang time
 * @class describe  历史车牌信息
 */
public class HistoryCarBrand extends BaseContentEntity {
    /**
     * content : [{"car_id":68194,"plate":"粤B12345"},{"car_id":11755829,"plate":"粤B89757"},{"car_id":11804782,"plate":"蒙E33268"}]
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

    public static class ContentBean implements IPickerViewData {
        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        /**
         * car_id : 68194
         * plate : 粤B12345
         */

        private String car;
        private String plate;



        public String getPlate() {
            return plate;
        }

        public void setPlate(String plate) {
            this.plate = plate;
        }

        @Override
        public String getPickerViewText() {
            return plate;
        }
    }
}
