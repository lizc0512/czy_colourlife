
package cn.net.cyberway.protocol;
import java.util.ArrayList;
import com.BeeFramework.model.HttpApi;

public class AppHomePropertyListGetApi extends HttpApi
{
  public AppHomePropertyListGetRequest request;
  public AppHomePropertyListGetResponse response;
  public static String apiURI="app/home/property/list";
  public AppHomePropertyListGetApi()
  {
     request=new AppHomePropertyListGetRequest();
     response=new AppHomePropertyListGetResponse();
  }
}
