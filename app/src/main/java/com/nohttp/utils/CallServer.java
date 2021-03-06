package com.nohttp.utils;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建时间 : 2017/7/3.
 * 编写人 :  ${yuansk}
 * 功能描述:
 * 版本:
 */
public class CallServer {

    private static CallServer instance;

    public static CallServer getInstance() {
        if (instance == null)
            synchronized (CallServer.class) {
                if (instance == null)
                    instance = new CallServer();
            }
        return instance;
    }

    private RequestQueue queue;

    private CallServer() {
        queue = NoHttp.newRequestQueue(6);
    }

    public <T> void request(int what, Request<T> request, HttpResponseListener<T> listener) {
        queue.add(what, request, listener);
    }

    // 完全退出app时，调用这个方法释放CPU。
    public void stop() {
        queue.stop();
    }

    public void CancelSingle(Object object) {
        queue.cancelBySign(object);
    }

    public void CancelAll() {
        queue.cancelAll();
    }


    private List<Integer> requestWhat = new ArrayList<>();
    private List<Request> requestList = new ArrayList<>();
    private List<Map<String, Object>> requestMap = new ArrayList<>();
    private List<Boolean> requestCancel = new ArrayList<>();
    private List<Boolean> requestLoading = new ArrayList<>();
    private List<HttpListener> requestLister = new ArrayList<>();

    public <T> void addQuestParams(final int what, final Request<T> request, Map<String, Object> paramsMap, final HttpListener<T> callback, final boolean canCancel, final boolean
            isLoading) {
        requestWhat.add(what);
        requestList.add(request);
        requestMap.add(paramsMap);
        requestCancel.add(canCancel);
        requestLister.add(callback);
        requestLoading.add(isLoading);
    }

    public List<Integer> getRequestWhat() {
        return requestWhat;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public List<Map<String, Object>> getRequestMap() {
        return requestMap;
    }

    public List<Boolean> getRequestCancel() {
        return requestCancel;
    }

    public List<Boolean> getRequestLoading() {
        return requestLoading;
    }

    public List<HttpListener> getRequestLister() {
        return requestLister;
    }

    public <T> void deleteSendRequsetDelete(int what, Request<T> request, Map<String, Object> paramsMap, HttpListener<T> callback, boolean canCancel, boolean
            isLoading) { //轮询刷新成功删除已经发起的请求
        requestWhat.remove((Object) what);
        requestList.remove(request);
        requestMap.remove(paramsMap);
        requestLister.remove(callback);
        requestCancel.remove(canCancel);
        requestLoading.remove(isLoading);
    }

    public void clearAllQuest() {  //单个刷新成功清除队列
        requestWhat.clear();
        requestList.clear();
        requestMap.clear();
        requestLister.clear();
        requestCancel.clear();
        requestLoading.clear();
    }
}
