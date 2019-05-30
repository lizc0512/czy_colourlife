package com.feed.model;

import android.content.Context;
import android.util.Log;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.Neighborhood.protocol.FeedAdlistApi;
import com.feed.protocol.FEED;
import com.feed.protocol.FeedCommentDeleteApi;
import com.feed.protocol.FeedDeleteApi;
import com.feed.protocol.VerFeedCommentApi;
import com.feed.protocol.VerFeedLikeApi;
import com.feed.protocol.VerFeedListApi;
import com.feed.protocol.VerFeedUnlikeApi;
import com.nohttp.utils.HttpListener;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.UserAppConst;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FeedModel extends BaseModel {

    private static final int PER_PAGE = 10;
    private VerFeedListApi listApi;
    private VerFeedLikeApi likeApi;
    private VerFeedUnlikeApi unlikeApi;
    private VerFeedCommentApi commentApi;
    private FeedDeleteApi deleteFeedApi;
    private FeedCommentDeleteApi deleteCommentApi;
    public ArrayList<FEED> feedList = new ArrayList<FEED>();
    ;
    public String commentId;
    public boolean isMore = false;
    private FeedAdlistApi feedAdlistApi;
    private int user_id;
    private String communti_id;

    public FeedModel(Context context) {
        super(context);
        user_id = shared.getInt(UserAppConst.Colour_User_id, 0);
        communti_id = shared.getString(UserAppConst.Colour_login_community_uuid, "");
    }

    /**
     * 获取邻里列表
     *
     * @param businessResponse
     * @param communityId      小区id
     */
    public void getFeedListPro(HttpApiResponse businessResponse, final String communityId) {
        listApi = new VerFeedListApi();
        listApi.request.community_id = communityId;
        listApi.request.page = 1; //第几页
        listApi.request.per_page = PER_PAGE;//每页数量
        int uid = shared.getInt(UserAppConst.Colour_User_id, 0);
        listApi.request.from_uid = String.valueOf(uid);
        listApi.request.v = "1";
        listApi.httpApiResponse = businessResponse;
        Map<String,Object> params = null;
        try {
            params = Utils.transformJsonToMap(listApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = listApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 4, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>()
        {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            saveCache(UserAppConst.COMMUNITYFRAGMENTLIST + user_id + communti_id, result);
                            listApi.response.fromJson(jsonObject);
                            if (listApi.response.paged.more == 1) {
                                isMore = true;
                            } else {
                                isMore = false;
                            }
                            feedList.clear();
                            feedList.addAll(listApi.response.feeds);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                }
                listApi.httpApiResponse.OnHttpResponse(listApi);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                //showExceptionMessage(what, response);
                listApi.httpApiResponse.OnHttpResponse(listApi);
            }
        }, true, false);

    }

    /**
     * 分页加载邻里列表
     *
     * @param businessResponse
     * @param communityId      小区id
     */
    public void getFeedListNext(HttpApiResponse businessResponse, String communityId) {

        listApi = new VerFeedListApi();
        listApi.request.community_id = communityId;
        listApi.request.page = feedList.size() / PER_PAGE + 1; //第几页
        listApi.request.per_page = PER_PAGE;//每页数量
        int uid = shared.getInt(UserAppConst.Colour_User_id, 0);
        listApi.request.from_uid = String.valueOf(uid);
        listApi.request.v = "1";
        listApi.httpApiResponse = businessResponse;
        Map<String,Object> params = null;
        try {
            params = Utils.transformJsonToMap(listApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = listApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 4, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>()
        {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            listApi.response.fromJson(jsonObject);
                            if (listApi.response.paged.more == 1) {
                                isMore = true;
                            } else {
                                isMore = false;
                            }
                            feedList.addAll(listApi.response.feeds);
                            listApi.httpApiResponse.OnHttpResponse(listApi);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                //showExceptionMessage(what, response);
            }
        }, true, false);

    }

    /**
     * 喜欢某个动态
     *
     * @param businessResponse
     * @param feedId           动态id
     */
    public void like(HttpApiResponse businessResponse, String feedId) {
        likeApi = new VerFeedLikeApi();
        likeApi.httpApiResponse = businessResponse;
        likeApi.request.feed_id = feedId;
        int uid = shared.getInt(UserAppConst.Colour_User_id, 0);
        likeApi.request.from_uid = String.valueOf(uid);
        Map<String, Object> params = null;
        try {
            params = Utils.transformJsonToMap(likeApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = likeApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 4, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>()
        {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            likeApi.response.fromJson(jsonObject);
                            if (likeApi.response.ok == 1) {
                                likeApi.httpApiResponse.OnHttpResponse(likeApi);
                            }else{
                              ToastUtil.toastShow(mContext,likeApi.response.message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    /**
     * 不喜欢某个动态
     *
     * @param businessResponse
     * @param commentId        动态id
     */
    public void unlike(HttpApiResponse businessResponse, String commentId) {
        unlikeApi = new VerFeedUnlikeApi();
        unlikeApi.httpApiResponse = businessResponse;
        unlikeApi.request.feed_id = commentId;
        int uid = shared.getInt(UserAppConst.Colour_User_id, 0);
        unlikeApi.request.from_uid = String.valueOf(uid);
        Map<String, Object> params =null;
        try {
            params = Utils.transformJsonToMap(unlikeApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = unlikeApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 4, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>()
        {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            unlikeApi.response.fromJson(jsonObject);
                            if (unlikeApi.response.ok == 1) {
                                unlikeApi.httpApiResponse.OnHttpResponse(unlikeApi);
                            }else{
                                ToastUtil.toastShow(mContext,unlikeApi.response.message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    /**
     * 最新使用的：回复
     *
     * @param businessResponse
     * @param feedId           动态id
     * @param toUid            用户id
     */
    public void reply(HttpApiResponse businessResponse, String feedId, String content, String toUid) {
        commentApi = new VerFeedCommentApi();
        commentApi.request.feed_id = feedId;
        commentApi.request.content = content;
        if (toUid != null) {
            commentApi.request.to_uid = toUid;
        }
        int uid = shared.getInt(UserAppConst.Colour_User_id, 0);
        commentApi.request.from_uid = String.valueOf(uid);
        commentApi.httpApiResponse = businessResponse;
        Map<String, Object> params =null;
        try {
            params = Utils.transformJsonToMap(commentApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = commentApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 4, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>()
        {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            commentApi.response.fromJson(jsonObject);
                            if (commentApi.response.ok == 1) {
                                commentId = commentApi.response.comment_id;
                                commentApi.httpApiResponse.OnHttpResponse(commentApi);
                            }else{
                                ToastUtil.toastShow(mContext,commentApi.response.message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    /**
     * 删除动态（活动、普通消息）
     *
     * @param businessResponse
     * @param feedId           动态id
     */
    public void deleteFeed(HttpApiResponse businessResponse, String feedId) {
        deleteFeedApi = new FeedDeleteApi();
        deleteFeedApi.request.feed_id = feedId;
        int uid = shared.getInt(UserAppConst.Colour_User_id, 0);
        deleteFeedApi.request.from_uid = String.valueOf(uid);
        deleteFeedApi.httpApiResponse = businessResponse;
        Map<String, Object> params =null;
        try {
            params = Utils.transformJsonToMap(deleteFeedApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = deleteFeedApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 4, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>()
        {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            deleteFeedApi.response.fromJson(jsonObject);
                            if (deleteFeedApi.response.ok == 1) {
                                deleteFeedApi.httpApiResponse.OnHttpResponse(deleteFeedApi);
                            }else{
                                ToastUtil.toastShow(mContext,deleteFeedApi.response.message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    /**
     * 删除评论
     *
     * @param businessResponse
     * @param commentId        动态id
     */
    public void deleteComment(HttpApiResponse businessResponse, String commentId) {
        deleteCommentApi = new FeedCommentDeleteApi();
        deleteCommentApi.request.comment_id = commentId;
        int uid = shared.getInt(UserAppConst.Colour_User_id, 0);
        deleteCommentApi.request.from_uid = String.valueOf(uid);
        deleteCommentApi.httpApiResponse = businessResponse;
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            params = Utils.transformJsonToMap(deleteCommentApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = deleteCommentApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 4, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>()
        {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            deleteCommentApi.response.fromJson(jsonObject);
                            if (deleteCommentApi.response.ok == 1) {
                                deleteCommentApi.httpApiResponse.OnHttpResponse(deleteCommentApi);
                            }else{
                                ToastUtil.toastShow(mContext,deleteCommentApi.response.message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    /**
     * 获取邻里广告图
     *
     * @param httpApiResponse
     */
    public void getFeedBanner(HttpApiResponse httpApiResponse, String community_uuid) {
        feedAdlistApi = new FeedAdlistApi();
        feedAdlistApi.request.community_uuid = community_uuid;
        int uid = shared.getInt(UserAppConst.Colour_User_id, 0);
        feedAdlistApi.request.from_uid = String.valueOf(uid);
        feedAdlistApi.httpApiResponse = httpApiResponse;
        Map<String,Object> params = null;
        try {
            params = Utils.transformJsonToMap(feedAdlistApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = feedAdlistApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 4, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>()
        {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeNullMessage(result);
                    if (null != jsonObject) {
                        try {
                            feedAdlistApi.response.fromJson(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (responseCode == RequestEncryptionUtils.responseRequest) {

                }
                feedAdlistApi.httpApiResponse.OnHttpResponse(feedAdlistApi);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                feedAdlistApi.httpApiResponse.OnHttpResponse(feedAdlistApi);
            }
        }, true, false);

    }
}
