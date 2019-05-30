package com.feed.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.feed.adapter.FeedReplyAdapter;
import com.feed.protocol.REPLY;

import java.util.ArrayList;

import cn.net.cyberway.R;


public class FeedReplyView extends LinearLayout {

    private Context 			mContext;
    private ListView            mListView;
    private FeedReplyAdapter    mFeedReplyAdapter;
    private ArrayList<REPLY> feedList = new ArrayList<REPLY>();

    private String              mFeedId;
    public FeedReplyView(Context context) {
        super(context);
        mContext = context;
    }

    public FeedReplyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public FeedReplyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void init() {
        if(mListView == null) {
            mListView = (ListView) findViewById(R.id.feed_reply_list);
        }

        mFeedReplyAdapter = new FeedReplyAdapter(mContext, feedList,mFeedId);
        mListView.setAdapter(mFeedReplyAdapter);

    }

    //绑定数据
    public void bindData(ArrayList<REPLY> list,String feedId) {
        feedList = list;
        mFeedId = feedId;
        init();
    }

}
