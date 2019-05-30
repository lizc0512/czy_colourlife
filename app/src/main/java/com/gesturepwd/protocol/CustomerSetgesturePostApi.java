
package com.gesturepwd.protocol;
import com.BeeFramework.model.HttpApi;

public class CustomerSetgesturePostApi extends HttpApi
{
  public CustomerSetgesturePostRequest request;
  public CustomerSetgesturePostResponse response;
  public static String apiURI="/1.0/customer/setGesture";
  public CustomerSetgesturePostApi()
  {
     request=new CustomerSetgesturePostRequest();
     response=new CustomerSetgesturePostResponse();
  }
}
