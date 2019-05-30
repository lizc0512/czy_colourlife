
package com.pay.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SERVICE_TYPE
{


     public int id;


     public String   icon;


     public String   title;


     public String   large_icon;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.id = jsonObject.optInt("id");

          this.icon = jsonObject.optString("icon");

          this.title = jsonObject.optString("title");

          this.large_icon = jsonObject.optString("large_icon");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("id", id);
          localItemObject.put("icon", icon);
          localItemObject.put("title", title);
          localItemObject.put("large_icon", large_icon);
          return localItemObject;
     }

}
