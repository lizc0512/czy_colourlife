package com.Neighborhood.model;

import android.content.Context;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.HttpApiResponse;
import com.Neighborhood.protocol.FeedActivityfeedlistApi;
import com.Neighborhood.protocol.FeedActivityfeedlistRequest;
import com.feed.protocol.FEED;
import com.feed.protocol.FeedCommentDeleteApi;
import com.feed.protocol.FeedDeleteApi;
import com.feed.protocol.VerFeedCommentApi;
import com.feed.protocol.VerFeedLikeApi;
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

/**
 * Created by HX_CHEN on 2016/5/10.
 */
public class ActiveListModel extends BaseModel {
    private VerFeedLikeApi likeApi;
    private VerFeedUnlikeApi unlikeApi;
    private VerFeedCommentApi commentApi;
    private FeedDeleteApi deleteFeedApi;
    private FeedCommentDeleteApi deleteCommentApi;
    public ArrayList<FEED> feedList = new ArrayList<FEED>();
    ;
    public String commentId;
    public boolean isMore = false;
    private FeedActivityfeedlistApi feedActivityfeedlistApi;


    public ActiveListModel(Context context) {
        super(context);
    }

    /**
     * 获取邻里列表
     *
     * @param businessResponse
     */
    public void getActiveListPro(HttpApiResponse businessResponse, FeedActivityfeedlistRequest request) {

        feedActivityfeedlistApi = new FeedActivityfeedlistApi();
        feedActivityfeedlistApi.request = request;
        feedActivityfeedlistApi.request.page = 1; //第几页
        feedActivityfeedlistApi.request.per_page = 10;//每页数量
        int uid = shared.getInt(UserAppConst.Colour_User_id, 0);
        feedActivityfeedlistApi.request.from_uid = String.valueOf(uid);
        feedActivityfeedlistApi.httpApiResponse = businessResponse;
        Map<String, Object> params = null;
        try {
            params = Utils.transformJsonToMap(feedActivityfeedlistApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = feedActivityfeedlistApi.apiURI;
        final Request<String> requestss = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 4, basePath), RequestMethod.POST);
        request(0, requestss, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            feedActivityfeedlistApi.response.fromJson(jsonObject);
                            if (feedActivityfeedlistApi.response.paged.more == 1) {
                                isMore = true;
                            } else {
                                isMore = false;
                            }
                            feedList.clear();
                            feedList.addAll(feedActivityfeedlistApi.response.feeds);
                            feedActivityfeedlistApi.httpApiResponse.OnHttpResponse(feedActivityfeedlistApi);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showErrorCodeMessage(responseCode, response);
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
     * 分页加载邻里列表
     *
     * @param businessResponse
     */
    public void getActiveListNext(HttpApiResponse businessResponse, FeedActivityfeedlistRequest request) {
        feedActivityfeedlistApi = new FeedActivityfeedlistApi();
        feedActivityfeedlistApi.request = request;
        feedActivityfeedlistApi.request.page = feedList.size() / 10 + 1; //第几页
        feedActivityfeedlistApi.request.per_page = 10;//每页数量
        feedActivityfeedlistApi.httpApiResponse = businessResponse;
        Map<String, Object> params = null;
        try {
            params = Utils.transformJsonToMap(feedActivityfeedlistApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = feedActivityfeedlistApi.apiURI;
        final Request<String> requests = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 4, basePath), RequestMethod.POST);
        request(0, requests, params, new HttpListener<String>() {

            @Override
            public void onSucceed(int what, Response<String> response) {
                int responseCode = response.getHeaders().getResponseCode();
                String result = response.get();
                if (responseCode == RequestEncryptionUtils.responseSuccess) {
                    JSONObject jsonObject = showSuccesCodeMessage(result);
                    if (null != jsonObject) {
                        try {
                            feedActivityfeedlistApi.response.fromJson(jsonObject);
                            if (feedActivityfeedlistApi.response.paged.more == 1) {
                                isMore = true;
                            } else {
                                isMore = false;
                            }
                            feedList.addAll(feedActivityfeedlistApi.response.feeds);
                            feedActivityfeedlistApi.httpApiResponse.OnHttpResponse(feedActivityfeedlistApi);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showErrorCodeMessage(responseCode, response);
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
        request(0, request, params, new HttpListener<String>() {

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
                            } else {
                                ToastUtil.toastShow(mContext, likeApi.response.message);
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
        Map<String, Object> params = null;
        try {
            params = Utils.transformJsonToMap(unlikeApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = unlikeApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 4, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>() {

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
                            } else {
                                ToastUtil.toastShow(mContext, unlikeApi.response.message);
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
     * 回复
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
        Map<String, Object> params = null;
        try {
            params = Utils.transformJsonToMap(commentApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = commentApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 4, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>() {

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
                            } else {
                                ToastUtil.toastShow(mContext, commentApi.response.message);
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
        Map<String, Object> params = null;
        try {
            params = Utils.transformJsonToMap(deleteFeedApi.request.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String basePath = deleteFeedApi.apiURI;
        final Request<String> request = NoHttp.createStringRequest(RequestEncryptionUtils.postCombileMD5(mContext, 4, basePath), RequestMethod.POST);
        request(0, request, params, new HttpListener<String>() {

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
                            } else {
                                ToastUtil.toastShow(mContext, deleteFeedApi.response.message);
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
        request(0, request, params, new HttpListener<String>() {

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
                            } else {
                                ToastUtil.toastShow(mContext, deleteCommentApi.response.message);
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


}
