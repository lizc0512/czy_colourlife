package com.notification.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.external.maxwin.view.XListView;

import java.util.List;

/**
 * Created by chenql on 15/12/17.
 */
public class ENotificationPagerAdapter extends PagerAdapter {

    private List<XListView> views;

    public ENotificationPagerAdapter(List<XListView> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position,
                               Object object) {
        super.setPrimaryItem(container, position, object);
    }
}
