package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/7 15:28
 * @change
 * @chang time
 * @class describe  搜索停车场的实例
 */
public class ParkingAddressEntity extends BaseContentEntity {

    /**
     * content : [{"id":2138,"status":"active","address":"广西柳州市鱼峰区静兰路4号汇东一品","name":"汇东一品","uuid":"e6fe5b4d-4b30-4daa-8248-12cf10352131","city":2101,"latitude":22.618416,"longitude":114.03421},{"id":2552,"status":"active","address":"广东省江门市鹤山市沙坪镇南景湾山水城","name":"南景湾山水城","uuid":"a518155e-954f-4b41-b2d4-29deef358fb8","city":1994,"latitude":22.617035,"longitude":114.038074},{"id":736,"status":"active","address":"江苏省徐州市新城区元和路10号","name":"公园首府","uuid":"834c89c2-52b3-4f5d-b493-4236feabcef7","city":833,"latitude":22.617542,"longitude":114.03947},{"id":1588,"status":"active","address":"东莞市厚街镇康乐南路199号凤山公园对面","name":"君汇华庭","uuid":"5c37d1ec-30b1-415b-b567-1839eb8b9417","city":2069,"latitude":22.614444,"longitude":114.030807},{"id":1374,"status":"active","address":"湖北省武汉市洪山区恒安路141号","name":"祥和苑","uuid":"c0d50737-b0a6-4f6b-9543-b85bbdfa6a6c","city":1690,"latitude":22.613746,"longitude":114.030658},{"id":2521,"status":"active","address":"东莞市长安镇东门中路1号","name":"长安万达","uuid":"0df854a3-a847-4858-a625-1085d4baccbe","city":2069,"latitude":22.613736,"longitude":114.030658},{"id":2575,"status":"active","address":"开封市龙亭区西郊乡温莎尚郡金明大道河大小西门","name":"温莎尚郡","uuid":"b075556d-c441-4267-8fc9-a2478c60a18e","city":1526,"latitude":22.61365,"longitude":114.030025},{"id":1535,"status":"active","address":"广东省东莞市东城街道黄旗山1号8栋1单元5楼5B","name":"黄旗山1号","uuid":"3b7e0957-72b1-492f-9806-760b76bdfb4f","city":2069,"latitude":22.613482,"longitude":114.030279},{"id":105,"status":"active","address":"广西","name":"大城小院","uuid":"694bc906-6970-4eb2-b080-522a8b2707ee","city":2112,"latitude":22.6126,"longitude":114.0288},{"id":2527,"status":"active","address":"温州市平阳县昆阳镇城东良种场东洋二号地块","name":"京都红墅湾","uuid":"cf3a127d-2b38-4adf-a31a-679205bc6970","city":952,"latitude":22.619349,"longitude":114.022214}]
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
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        /**
         * id : 2138
         * status : active
         * address : 广西柳州市鱼峰区静兰路4号汇东一品
         * name : 汇东一品
         * uuid : e6fe5b4d-4b30-4daa-8248-12cf10352131
         * city : 2101
         * latitude : 22.618416
         * longitude : 114.03421
         */

        private String id;
        private String status;
        private String address;
        private String name;
        private String uuid;
        private String city;
        private double latitude;
        private double longitude;


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }


        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
