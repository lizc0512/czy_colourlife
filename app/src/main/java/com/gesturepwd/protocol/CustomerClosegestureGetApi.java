
package com.gesturepwd.protocol;
import com.BeeFramework.model.HttpApi;

public class CustomerClosegestureGetApi extends HttpApi
{
  public CustomerClosegestureGetRequest request;
  public CustomerClosegestureGetResponse response;
  public static String apiURI="/1.0/customer/closeGesture";
  public CustomerClosegestureGetApi()
  {
     request=new CustomerClosegestureGetRequest();
     response=new CustomerClosegestureGetResponse();
  }
}
