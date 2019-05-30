
package cn.net.cyberway.protocol;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PROPERTYDATA
{
  public String name;

  public ArrayList<FANPIAOCONTENTDATA> list = new ArrayList<FANPIAOCONTENTDATA>(); //  

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.name = jsonObject.optString("name");
    subItemArray = jsonObject.optJSONArray("list");
    if(null != subItemArray)
    {
      for(int i = 0;i < subItemArray.length();i++)
      {
        JSONObject subItemObject = subItemArray.getJSONObject(i);
        FANPIAOCONTENTDATA subItem = new FANPIAOCONTENTDATA();
        subItem.fromJson(subItemObject);
        this.list.add(subItem);
      }
    }

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("name", name);
    itemJSONArray = new JSONArray();
    for(int i =0; i< list.size(); i++)
    {
      FANPIAOCONTENTDATA itemData =list.get(i);
      JSONObject itemJSONObject = itemData.toJson();
      itemJSONArray.put(itemJSONObject);
    }
    localItemObject.put("list", itemJSONArray);

    return localItemObject;
  }
}
