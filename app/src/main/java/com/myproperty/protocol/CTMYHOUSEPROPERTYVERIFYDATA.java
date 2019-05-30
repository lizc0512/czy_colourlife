
package com.myproperty.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class CTMYHOUSEPROPERTYVERIFYDATA
{
  public int property_verify_id;

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    this.property_verify_id = jsonObject.optInt("property_verify_id");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    localItemObject.put("property_verify_id", property_verify_id);
    return localItemObject;
  }
}
