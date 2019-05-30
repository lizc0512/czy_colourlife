
package com.mycarinfo.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class VehicleAddvehiclePostRequest {
    public String customer_id; //（用户id）

    public String name; //   （姓名）

    public String mobile; //     （手机号码）

    public String plate_number; //（车牌号码）

    public String vehicle_code; //     （车辆识别代号）

    public String engine_code; //    （发动机号码）

    public String brand_id; //    （汽车品牌id）

    public String model_id; //     （车型id）

    public String colour_id; //       （颜色id）

    public String card_font_id; //       （身份证正面id）

    public String card_back_id; //        （身份证反面id）

    public String driver_card_font_id; //          （行驶证正面id）

    public String driver_card_back_id; //          （行驶证反面id）

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.customer_id = jsonObject.optString("customer_id");
        this.name = jsonObject.optString("name");
        this.mobile = jsonObject.optString("mobile");
        this.plate_number = jsonObject.optString("plate_number");
        this.vehicle_code = jsonObject.optString("vehicle_code");
        this.engine_code = jsonObject.optString("engine_code");
        this.brand_id = jsonObject.optString("brand_id");
        this.model_id = jsonObject.optString("model_id");
        this.colour_id = jsonObject.optString("colour_id");
        this.card_font_id = jsonObject.optString("card_font_id");
        this.card_back_id = jsonObject.optString("card_back_id");
        this.driver_card_font_id = jsonObject.optString("driver_card_font_id");
        this.driver_card_back_id = jsonObject.optString("driver_card_back_id");

        return;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        localItemObject.put("customer_id", customer_id);
        localItemObject.put("name", name);
        localItemObject.put("mobile", mobile);
        localItemObject.put("plate_number", plate_number);
        localItemObject.put("vehicle_code", vehicle_code);
        localItemObject.put("engine_code", engine_code);
        localItemObject.put("brand_id", brand_id);
        localItemObject.put("model_id", model_id);
        localItemObject.put("colour_id", colour_id);
        localItemObject.put("card_font_id", card_font_id);
        localItemObject.put("card_back_id", card_back_id);
        localItemObject.put("driver_card_font_id", driver_card_font_id);
        localItemObject.put("driver_card_back_id", driver_card_back_id);
        return localItemObject;
    }
}
