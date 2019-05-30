package com.feed.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.BeeFramework.Utils.Utils;
import com.feed.adapter.FeedGirdViewImageAdapter;

import java.util.ArrayList;

import cn.net.cyberway.R;

public class FeedImageGridView extends LinearLayout {

	private Context mContext;
	private GridView mGridView;
	private FeedGirdViewImageAdapter mImageGredViewAdapter;
    private boolean mIsNotify;
	private ArrayList<String> list = new ArrayList<String>();
	public FeedImageGridView(Context context) {
        super(context);
        mContext = context;
    }

    public FeedImageGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public FeedImageGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void init() {
    	if(mGridView == null) {
			mGridView = (GridView) findViewById(R.id.feed_image_gridview);
			mGridView.setVisibility(View.GONE);
    	}

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int hight = (width- Utils.dip2px(mContext, 85)) / 3;

        if(list.size() == 4) {
            ViewGroup.LayoutParams params = mGridView.getLayoutParams();
            params.width = hight * 2;
            mGridView.setLayoutParams(params);
            mGridView.setNumColumns(2);
        } else {
            ViewGroup.LayoutParams params = mGridView.getLayoutParams();
            params.width = hight * 3;
            mGridView.setLayoutParams(params);
            mGridView.setNumColumns(3);
        }

        if(mImageGredViewAdapter == null) {
            mImageGredViewAdapter = new FeedGirdViewImageAdapter(mContext, list);
            mGridView.setAdapter(mImageGredViewAdapter);
        } else {
            if(mIsNotify) {
                mImageGredViewAdapter.mList = list;
                mImageGredViewAdapter.notifyDataSetChanged();
            }
        }
    }

	//绑定数据
    public void bindData(ArrayList<String> list, boolean isNotify) {
    	this.list = list;
        mIsNotify = isNotify;
        init();
        if(list.size() > 0) {
            mGridView.setVisibility(View.VISIBLE);
        } else {
            mGridView.setVisibility(View.GONE);
        }
    }
}
