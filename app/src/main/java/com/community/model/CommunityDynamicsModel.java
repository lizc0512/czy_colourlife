package com.community.model;

import android.content.Context;
import android.text.TextUtils;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.yanzhenjie.nohttp.BasicBinary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.user.UserAppConst.COLOUR_DYNAMICS_TIPOFF_LIST;

/**
 * author:ysk
 * cretetime:2020/2/22
 **/
public class CommunityDynamicsModel extends BaseModel {

    private String publicDynamicUrl = "neighbour/publicDynamic"; //发布动态
    private String uploadFileUrl = "neighbour/uploadFile"; //上传文件
    private String communityDynamicListUrl = "neighbour/getDynamic"; //小区动态列表
    private String commentDynamicUrl = "neighbour/commentDynamic"; //评论动态和回复评论
    private String zanDynamicUrl = "neighbour/zanDynamic"; //点赞动态
    private String delDynamicUrl = "neighbour/delDynamic"; //删除动态
    private String reportContentUrl = "neighbour/getReportContent"; //举报内容
    private String reportDynamicUrl = "neighbour/reportDynamic"; //举报违规动态
    private String cancelZanUrl = "neighbour/cancelZan"; //取消点赞
    private String cancelCommentUrl = "neighbour/cancelComment"; //删除评论
    private String reportCommentUrl = "neighbour/reportComment"; //举报违规评论
    private String dynamicDetailUrl = "neighbour/dynamicDetail"; //动态详情
    private String dynamicRemindCountUrl = "neighbour/remindCount"; //动态提醒未读数量
    private String dynamicRemindListUrl = "neighbour/remindList"; //获取未读消息列表
    private String dynamicRemindReadUrl = "neighbour/remindRead"; //设置动态提醒已读


    public CommunityDynamicsModel(Context context) {
        super(context);
    }

    public void publicUserDynamic(int what, String content, String extra_type, String extra, NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("content", content);
        params.put("extra_type", extra_type);
        params.put("extra", extra);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 17, publicDynamicUrl), RequestMethod.POST);
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

    public void uploadDynamicFile(int what, String file, NewHttpResponse newHttpResponse) {
        BasicBinary binary = new FileBinary(new File(file));
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 17, uploadFileUrl), RequestMethod.POST);
        request.add("file", binary);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
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


    public void getCommunityDynamicList(int what, int page, String year, boolean isLoading, NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        if (TextUtils.isEmpty(year)) {
            params.put("year", year);
        }
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 16, communityDynamicListUrl, null), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
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
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, isLoading);

    }


    public void commentDynamic(int what, String content, String source_id
            , String to_id
            , NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("content", content);
        params.put("source_id", source_id);
        if (!TextUtils.isEmpty(to_id)) {
            params.put("to_id", to_id);
        }
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 17, commentDynamicUrl), RequestMethod.POST);
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


    public void likeUserDynamic(int what, String source_id, NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source_id", source_id);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 17, zanDynamicUrl), RequestMethod.POST);
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

    public void delUserDynamic(int what, String source_id, NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source_id", source_id);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 17, delDynamicUrl), RequestMethod.POST);
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

    public void getReportContent(int what, NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 16, reportContentUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        editor.putString(COLOUR_DYNAMICS_TIPOFF_LIST, result).apply();
                    }
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

    public void reportViolationDynamic(int what, String source_id, String report_type, NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source_id", source_id);
        params.put("report_type", report_type);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 17, reportDynamicUrl), RequestMethod.POST);
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

    public void unlikeUserDynamic(int what, String source_id, NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source_id", source_id);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 17, cancelZanUrl), RequestMethod.POST);
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

    public void delOwnerComment(int what, String source_id, String comment_id, NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source_id", source_id);
        params.put("comment_id", comment_id);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 17, cancelCommentUrl), RequestMethod.POST);
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

    public void reportViolationComment(int what, String source_id, String comment_id, String report_type, NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source_id", source_id);
        params.put("comment_id", comment_id);
        params.put("report_type", report_type);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 17, reportCommentUrl), RequestMethod.POST);
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

    public void getDynamicsDetails(int what, String source_id, NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source_id", source_id);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 16, dynamicDetailUrl, null), RequestMethod.GET);
        request(what, request, params, new HttpListener<String>() {
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
                } else {
                    newHttpResponse.OnHttpResponse(what, "");
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                newHttpResponse.OnHttpResponse(what, "");
            }
        }, true, true);
    }

    public void getDynamicRemindCount(int what, NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 16, dynamicRemindCountUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
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

    public void getDynamicRemindList(int what,boolean isLoading, NewHttpResponse newHttpResponse) {
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.getCombileMD5(mContext, 16, dynamicRemindListUrl, null), RequestMethod.GET);
        request(what, request, null, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    int resultCode = showSuccesResultMessage(result);
                    if (resultCode == 0) {
                        newHttpResponse.OnHttpResponse(what, result);
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, isLoading);
    }

    public void setDynamicRemindRead(int what,String source_id, NewHttpResponse newHttpResponse) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source_id", source_id);
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 17, dynamicRemindReadUrl), RequestMethod.POST);
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
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {

            }
        }, true, true);
    }
}
