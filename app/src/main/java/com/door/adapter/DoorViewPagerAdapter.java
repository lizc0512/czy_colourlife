package com.door.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lizc on 2018/9/4.
 * 开门页面viewpager适配器
 *
 * @Description
 */

public class DoorViewPagerAdapter extends PagerAdapter {
    private List<View> mList;
    private Context mContext;

    public DoorViewPagerAdapter(Context context, List<View> list) {
        this.mContext = context;
        this.mList = list;
    }
    public void setData(List<View> list){
        this.mList = list;
        notifyDataSetChanged();
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
}
