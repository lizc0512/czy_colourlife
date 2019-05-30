
package com.pay.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LOCATION
{


     public double   lon;


     public String   name;


     public double   lat;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.lon = jsonObject.optDouble("lon");

          this.name = jsonObject.optString("name");

          this.lat = jsonObject.optDouble("lat");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("lon", lon);
          localItemObject.put("name", name);
          localItemObject.put("lat", lat);
          return localItemObject;
     }

}
