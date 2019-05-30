package com.feed.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.feed.protocol.FEED;
import com.feed.view.FeedView;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * Feed
 */
public class FeedAdapter extends BeeBaseAdapter {

	public FeedAdapter(Context c, ArrayList<FEED> dataList) {
		super(c, dataList);
	}

	public void setData(List<FEED> list){
		dataList = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	protected BeeCellHolder createCellHolder(View cellView) {
		return null;
	}

	@Override
	protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
		FEED feed = (FEED)dataList.get(position);
		((FeedView)cellView).bindData(feed, position);
		return null;
	}

	@Override
	public View createCellView() {

		return mInflater.inflate(R.layout.feed_item, null);
	}



}
