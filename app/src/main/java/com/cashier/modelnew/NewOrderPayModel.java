package com.cashier.modelnew;

import android.content.Context;

import com.BeeFramework.Utils.PasswordRSAUtils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.Utils.TokenUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * @name ${yuansk}
 * @class name：com.cashier.modelnew
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/9 9:44
 * @change
 * @chang time
 * @class describe
 */

public class NewOrderPayModel extends BaseModel {
    private final String orderDetailUrl = "pay/orderdetail";//支付订单支付方式列表
    private final String payOrderUrl = "pay/orderpay";//支付
    private final String payTransactionPoint= "app/transaction/consume";//积分支付
    private final String payOrderList = "pay/orderlist";//订单列表
    private final String payOrderSingleInfo = "pay/orderquery";//订单详情
    private final String orderCancel = "pay/ordercancel";//取消订单
    private final String orderCheck = "pay/ordercheck";//取消订单
    private final String orderStatus = "pay/checkorder";//查询订单是否支付成功
    private final String payBannerUrl = "app/home/utility/getPayBanner";//获取支付完成的banner(4.0的接口)
    private final String payPopupUrl = "app/home/utility/getPayPopup";//获取支付完成的banner(4.0的接口)

    public NewOrderPayModel(Context context) {
        super(context);
    }

    /****获取支付订单的详情和列表**/
    public void getPayOrderDetails(int what, String sn, final boolean isShowNotice, final NewHttpResponse newHttpResponse) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("colour_sn", sn);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 5, orderDetailUrl), RequestMethod.POST);
        request.setConnectTimeout(25000);
        request.setReadTimeout(25000);
        request(what, request, paramsMap, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {
                    showErrorCodeMessage(responseCode, response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (isShowNotice) {
                    showExceptionMessage(what, response);
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }
        }, true, true);
    }


    public void getPayPopupDate(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 3, payPopupUrl), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, false);
    }

    /****获取支付订单的状态**/
    public void getPayOrderStatus(int what, String sn, final NewHttpResponse newHttpResponse) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("colour_sn", sn);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 5, orderStatus), RequestMethod.POST);
        request(what, request, paramsMap, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    newHttpResponse.OnHttpResponse(what, "");
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, false);
    }

    /****去支付订单**/
    public void goOrderPay(int what, String sn, String payment_uuid, final NewHttpResponse newHttpResponse) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("colour_sn", sn);
        paramsMap.put("payment_uuid", payment_uuid);
        paramsMap.put("limit", 1);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 5, payOrderUrl), RequestMethod.POST);
        request.setConnectTimeout(25000);
        request.setReadTimeout(25000);
        request(what, request, paramsMap, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    newHttpResponse.OnHttpResponse(what, result);
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {
                    showErrorCodeMessage(responseCode, response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
            }
        }, true, true);
    }

    public void goOrderPayByPoint(int what, String encrypt, String password,String token, final NewHttpResponse newHttpResponse) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("encrypt", encrypt);
        paramsMap.put("password",PasswordRSAUtils.encryptByPublicKey(password));
        paramsMap.put("token", token);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 16, payTransactionPoint), RequestMethod.POST);
        request.setConnectTimeout(25000);
        request.setReadTimeout(25000);
        request(what, request, paramsMap, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {
                    showErrorCodeMessage(responseCode, response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
            }
        }, true, true);
    }

    /****获取支付订单列表**/
    public void getPayOrderList(int what, String status, String timestamp, int page, int pagesize, final NewHttpResponse newHttpResponse) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("trade_state", status);
        paramsMap.put("date", timestamp);
        paramsMap.put("page", page);
        paramsMap.put("page_size", pagesize);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 2, payOrderList, paramsMap), RequestMethod.GET);
        request(what, request, paramsMap, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    newHttpResponse.OnHttpResponse(what, "");
                } else {
                    showErrorCodeMessage(responseCode, response);
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, false);
    }


    /****获取当个订单的信息**/
    public void getSingleOrderInfor(int what, String colour_sn, final NewHttpResponse newHttpResponse) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("colour_sn", colour_sn);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 2, payOrderSingleInfo, paramsMap), RequestMethod.GET);
        request(what, request, paramsMap, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {
                    showErrorCodeMessage(responseCode, response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
            }
        }, true, true);
    }

    /****取消单个订单**/
    public void cancelSingleOrder(int what, String colour_sn, final NewHttpResponse newHttpResponse) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("colour_sn", colour_sn);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 5, orderCancel), RequestMethod.POST);
        request(what, request, paramsMap, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                } else {
                    showErrorCodeMessage(responseCode, response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
            }
        }, true, false);
    }


    /****获取支付完成banner**/
    public void getAdvertiseBanner(int what, String payment_uuid, final NewHttpResponse newHttpResponse) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("payment_uuid", payment_uuid);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 1, payBannerUrl, paramsMap), RequestMethod.GET);
        request(what, request, paramsMap, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, true);
    }


    public void getUserRealCertificate(int what, String colour_sn, final NewHttpResponse newHttpResponse) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("colour_sn", colour_sn);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 2, orderCheck, paramsMap), RequestMethod.GET);
        request(what, request, paramsMap, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    } else {
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {
                    newHttpResponse.OnHttpResponse(what, "");
                } else {
                    showErrorCodeMessage(responseCode, response);
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what, response);
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, true);
    }
}
