
package com.gesturepwd.protocol;
import com.BeeFramework.model.HttpApi;

public class CustomerLogingesturePostApi extends HttpApi
{
  public CustomerLogingesturePostRequest request;
  public CustomerLogingesturePostResponse response;
  public static String apiURI="/1.0/customer/loginGesture";
  public CustomerLogingesturePostApi()
  {
     request=new CustomerLogingesturePostRequest();
     response=new CustomerLogingesturePostResponse();
  }
}
