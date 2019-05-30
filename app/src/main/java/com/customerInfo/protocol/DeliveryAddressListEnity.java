package com.customerInfo.protocol;

import android.text.TextUtils;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.customerInfo.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/8 8:47
 * @change
 * @chang time
 * @class describe
 */

public class DeliveryAddressListEnity extends BaseContentEntity {

    /**
     * content : [{"address_uuid":"asdsdas","province":"","city":"","county":"","town":"","address":"","community":"","name":"","mobile":"","postcode":""}]
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
         * address_uuid : asdsdas
         * province :
         * city :
         * county :
         * town :
         * address :
         * community :
         * name :
         * mobile :
         * postcode :
         */

        private String address_uuid;
        private String province;
        private String province_uuid;
        private String city;
        private String city_uuid;
        private String county;

        public String getProvince_uuid() {
            return province_uuid;
        }

        public void setProvince_uuid(String province_uuid) {
            this.province_uuid = province_uuid;
        }

        public String getCity_uuid() {
            return city_uuid;
        }

        public void setCity_uuid(String city_uuid) {
            this.city_uuid = city_uuid;
        }

        public String getCounty_uuid() {
            return county_uuid;
        }

        public void setCounty_uuid(String county_uuid) {
            this.county_uuid = county_uuid;
        }

        public String getTown_uuid() {
            return town_uuid;
        }

        public void setTown_uuid(String town_uuid) {
            this.town_uuid = town_uuid;
        }

        public String getCommunity_uuid() {
            return community_uuid;
        }

        public void setCommunity_uuid(String community_uuid) {
            this.community_uuid = community_uuid;
        }

        private String county_uuid;
        private String town;
        private String town_uuid;
        private String community;
        private String community_uuid;
        private String address;
        private String name;
        private String mobile;
        private String postcode;

        public String getAddress_uuid() {
            return address_uuid;
        }

        public void setAddress_uuid(String address_uuid) {
            this.address_uuid = address_uuid;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }
    }
}
