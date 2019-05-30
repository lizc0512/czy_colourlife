package com.customerInfo.protocol;

import com.google.gson.annotations.SerializedName;
import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * Created by hxg on 2019/4/4 09:55
 */
public class CityListEntity extends BaseContentEntity {
    /**
     * content : [{"code":"A","city_list":["阿城市","阿尔山市"]},{"code":"B","city_list":["巴中市","北京市"]}]
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
         * code : A
         * city_list : ["阿城市","阿尔山市"]
         */

        @SerializedName("code")
        private String codeX;
        private List<String> city_list;

        public String getCodeX() {
            return codeX;
        }

        public void setCodeX(String codeX) {
            this.codeX = codeX;
        }

        public List<String> getCity_list() {
            return city_list;
        }

        public void setCity_list(List<String> city_list) {
            this.city_list = city_list;
        }
    }
}
