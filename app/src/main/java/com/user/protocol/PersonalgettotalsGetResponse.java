
package com.user.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PersonalgettotalsGetResponse
{
  public String balance; //我的红包
  public String name; //
  public String helpContent; //

  public String coupon; //优惠券

  public String complain; //投诉记录

  public PERSONALTOTALS employee;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.balance = jsonObject.optString("balance");
    this.name = jsonObject.optString("name");
    this.helpContent = jsonObject.optString("helpContent");
    this.coupon = jsonObject.optString("coupon");
    this.complain = jsonObject.optString("complain");
    PERSONALTOTALS employee = new PERSONALTOTALS();
    employee.fromJson(jsonObject.optJSONObject("info"));
    this.employee = employee;
    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("balance", balance);
    localItemObject.put("name", name);
    localItemObject.put("helpContent", helpContent);
    localItemObject.put("coupon", coupon);
    localItemObject.put("complain", complain);
    if(null != employee)
    {
      localItemObject.put("employee", employee.toJson());
    }
    return localItemObject;
  }
}
