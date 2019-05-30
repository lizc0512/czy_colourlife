
package com.pay.protocol;

import com.cashier.protocol.CONTENT;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class COMMENT
{

     public CONTENT content;


     public int id;


     public int rank;


     public String   created_at;


     public SIMPLE_USER   user;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          CONTENT  content = new CONTENT();
          content.fromJson(jsonObject.optJSONObject("content"));
          this.content = content;

          this.id = jsonObject.optInt("id");

          this.rank = jsonObject.optInt("rank");

          this.created_at = jsonObject.optString("created_at");
          SIMPLE_USER  user = new SIMPLE_USER();
          user.fromJson(jsonObject.optJSONObject("user"));
          this.user = user;
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          if(null != content)
          {
            localItemObject.put("content", content.toJson());
          }
          localItemObject.put("id", id);
          localItemObject.put("rank", rank);
          localItemObject.put("created_at", created_at);
          if(null != user)
          {
            localItemObject.put("user", user.toJson());
          }
          return localItemObject;
     }

}
