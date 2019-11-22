package com.point.model;

import android.content.Context;
import android.text.TextUtils;

import com.BeeFramework.Utils.PasswordRSAUtils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;
import java.util.Map;

import static com.user.UserAppConst.COLOUR_WALLET_ACCOUNT_LIST;

/**
 * 文件名:
 * 创建者:yuansongkai
 * 邮箱:827194927@qq.com
 * 创建日期:
 * 描述:彩之云积分的网络请求model
 **/
public class PointModel extends BaseModel {

    private String walletKeywordUrl = "app/wallet/getKeyword";//获取钱包内用户显示的标识
    private String accountListUrl = "app/wallet/accountList";//获取用户的账户列表
    private String accountFlowUrl = "app/wallet/accountFlow";//获取某个账户的详细流水
    private String accountLimitUrl = "app/wallet/transferLimit";//获取用户的转账额度和次数
    private String transferListUrl = "app/wallet/transferList";//获取用户的转账记录
    private String userInfoByMobileUrl = "app/wallet/getInfoByMobile";//根据号码获取转账用户的信息
    private String accountBalanceUrl = "app/wallet/accountBalance";//获取用户某个账户的余额
    private String transactionTokenUrl = "app/transaction/checkpwd";//获取彩之云交易令牌
    private String transactionTransferUrl = "app/transaction/transfer";//彩之云饭票转账交易
    private String transactionReturnPlanUrl = "app/transaction/returnPlan";//彩粮票返还计划

    public PointModel(Context context) {
        super(context);
    }

    public void getWalletKeyWord(int what, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 15, walletKeywordUrl, null), RequestMethod.GET);
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

    public void getAccountFlowList(int what, int page,String pano, long time_start, long time_stop, boolean isLoading, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", 1);
        params.put("page", page);
        params.put("page_size", 20);
        params.put("is_pay", 1);
        params.put("pano", pano);
        if (time_start == 0) {
            params.put("time_start", time_start);
            params.put("time_stop", time_stop);
        }
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 15, accountFlowUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }else{
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                }else{
                    showErrorCodeMessage(responseCode,response);
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                 showExceptionMessage(what,response);
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, isLoading);
    }


    public void getAccountList(int what,boolean isLoading, final NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 15, accountListUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessageTheme(result);
                    if (resultCode == 0) {
                        editor.putString(COLOUR_WALLET_ACCOUNT_LIST, result).apply();
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, isLoading);
    }

    public void getTransferList(int what,String pano, int page,boolean isLoading, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pano",pano);
        params.put("page", page);
        params.put("page_size", 20);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 15, transferListUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }else{
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                }else{
                    showErrorCodeMessage(responseCode,response);
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what,response);
                newHttpResponse.OnHttpResponse(what, "");
            }

        }, true, isLoading);
    }

    public void getAccountLimit(int what,String pano,  final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pano",pano);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 15, accountLimitUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                }else{
                    showErrorCodeMessage(responseCode,response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what,response);
            }
        }, true, true);
    }

    public void getUserInfor(int what,String mobile,  final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile",mobile);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 15, userInfoByMobileUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                }else{
                    showErrorCodeMessage(responseCode,response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what,response);
            }
        }, true, true);
    }

    public void getAccountBalance(int what,String pano,  final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pano",pano);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 15, accountBalanceUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                }else{
                    showErrorCodeMessage(responseCode,response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what,response);
            }
        }, true, true);
    }

    public void getTransactionToken(int what, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("transfer_type",1);
        params.put("timestamp",System.currentTimeMillis()/1000);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 15, transactionTokenUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                }else{
                    showErrorCodeMessage(responseCode,response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what,response);
            }
        }, true, true);
    }

    public void transferTransaction(int what,int transfer_fee,String password,String token,String order_no,String dest_account,String pano,String detail, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("transfer_type",1);
        params.put("transfer_fee",transfer_fee);
        params.put("token",token);
        params.put("order_no",order_no);
        params.put("dest_account",dest_account);
        params.put("pano",pano);
        if (!TextUtils.isEmpty(detail)){
            params.put("detail",detail);
        }
        params.put("password", PasswordRSAUtils.encryptByPublicKey(password));
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 16, transactionTransferUrl), RequestMethod.POST);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                }else{
                    showErrorCodeMessage(responseCode,response);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what,response);
            }
        }, true, true);
    }

    public void getTransactionPlan(int what,String pano,int page, final NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pano",pano);
        params.put("page",page);
        params.put("page_size",20);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 15, transactionReturnPlanUrl, params), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }else{
                        newHttpResponse.OnHttpResponse(what, "");
                    }
                }else{
                    showErrorCodeMessage(responseCode,response);
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showExceptionMessage(what,response);
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, true);
    }



}
