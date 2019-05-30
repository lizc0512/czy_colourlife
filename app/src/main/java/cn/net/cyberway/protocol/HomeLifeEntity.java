package cn.net.cyberway.protocol;

import com.nohttp.entity.BaseContentEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 * 生活页面接口数据
 *
 * @Description
 */

public class HomeLifeEntity extends BaseContentEntity {

    /**
     * code : 0
     * message : success
     * content : [{"id":56,"desc":"为您推荐","list":[{"id":7,"img":"https://czy-home-service.oss-cn-shenzhen.aliyuncs.com/local-5a1f6256d2359537149.png","url":"2","name":"饭票商城"},{"id":1,"img":"https://colourhome-czytest.colourlife.com//resources/imgs/lifeimg/icon_ezx.png","url":"httpeg.com","name":"e装修"}]},{"id":57,"desc":"管家服务","list":[{"id":3,"img":"https://colourhome-czytest.colourlife.com//resources/imgs/lifeimg/icon_ewh.png","url":"www.kaimen.com","name":"e文化"},{"id":4,"img":"https://czy-home-service.oss-cn-shenzhen.aliyuncs.com/local-5a20a9404640b931751.png","url":"www.kaimen.com","name":"e家政"},{"id":7,"img":"https://czy-home-service.oss-cn-shenzhen.aliyuncs.com/local-5a1f6256d2359537149.png","url":"2","name":"饭票商城"},{"id":22,"img":"https://czy-home-service.oss-cn-shenzhen.aliyuncs.com/local-5a211c5c3fcd5787828.png","url":"http://homemate.orvibo.com/oem/color_life/index.html","name":"智家365"},{"id":26,"img":"https://czy-home-service.oss-cn-shenzhen.aliyuncs.com/local-5a791543907d1146388.png","url":"http://demo.tgj-care.com/app/czy/czy_index.html","name":"泰管家"},{"id":1,"img":"https://colourhome-czytest.colourlife.com//resources/imgs/lifeimg/icon_ezx.png","url":"httpeg.com","name":"e装修"}]}]
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
         * id : 56
         * desc : 为您推荐
         * list : [{"id":7,"img":"https://czy-home-service.oss-cn-shenzhen.aliyuncs.com/local-5a1f6256d2359537149.png","url":"2","name":"饭票商城"},{"id":1,"img":"https://colourhome-czytest.colourlife.com//resources/imgs/lifeimg/icon_ezx.png","url":"httpeg.com","name":"e装修"}]
         */

        private int id;
        private String desc;
        private List<ListBean> list;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 7
             * img : https://czy-home-service.oss-cn-shenzhen.aliyuncs.com/local-5a1f6256d2359537149.png
             * url : 2
             * name : 饭票商城
             */

            private int id;
            private String img;
            private String url;
            private String name;
            private String is_auth;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIs_auth() {
                return is_auth;
            }

            public void setIs_auth(String is_auth) {
                this.is_auth = is_auth;
            }

            public JSONObject toJson() throws JSONException {
                JSONObject localItemObject = new JSONObject();
                localItemObject.put("id", id);
                localItemObject.put("name", name);
                localItemObject.put("img", img);
                localItemObject.put("url", url);
                localItemObject.put("is_auth", is_auth);
                return localItemObject;
            }

            public void fromJson(JSONObject jsonObject) throws JSONException {
                if (null == jsonObject) {
                    return;
                }
                this.id = jsonObject.optInt("id");
                this.name = jsonObject.optString("name");
                this.img = jsonObject.optString("img");
                this.url = jsonObject.optString("url");
                this.is_auth = jsonObject.optString("is_auth");
                return;
            }
        }
    }


}
