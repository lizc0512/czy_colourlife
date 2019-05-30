
package com.mycarinfo.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class COLOURTICKETMYCARINFOLIST
{
  public int id;

  public int customer_id;

  public String plate_number; // （车牌号码）

  public String name; //    （姓名）

  public String mobile; //   （手机号码）

  public int vehicle_type_id;

  public int identity_id;

  public String mealticket_id;

  public int bind_mealticket_state; // （绑定饭票状态：0未绑定，1已绑定）

  public int state;

  public int create_time;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }
    this.id = jsonObject.optInt("id");
    this.customer_id = jsonObject.optInt("customer_id");
    this.plate_number = jsonObject.optString("plate_number");
    this.name = jsonObject.optString("name");
    this.mobile = jsonObject.optString("mobile");
    this.vehicle_type_id = jsonObject.optInt("vehicle_type_id");
    this.identity_id = jsonObject.optInt("identity_id");
    this.mealticket_id = jsonObject.optString("mealticket_id");
    this.bind_mealticket_state = jsonObject.optInt("bind_mealticket_state");
    this.state = jsonObject.optInt("state");
    this.create_time = jsonObject.optInt("create_time");

    return;
  }
}
