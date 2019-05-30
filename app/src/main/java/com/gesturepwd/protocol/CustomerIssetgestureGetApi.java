
package com.gesturepwd.protocol;
import com.BeeFramework.model.HttpApi;

public class CustomerIssetgestureGetApi extends HttpApi
{
  public CustomerIssetgestureGetRequest request;
  public CustomerIssetgestureGetResponse response;
  public static String apiURI="/1.0/customer/isSetGesture";
  public CustomerIssetgestureGetApi()
  {
     request=new CustomerIssetgestureGetRequest();
     response=new CustomerIssetgestureGetResponse();
  }
}
