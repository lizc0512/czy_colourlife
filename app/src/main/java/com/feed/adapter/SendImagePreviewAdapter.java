package com.feed.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BeeFramework.view.TouchImageView;
import com.imagepicker.utils.SDCardImageLoader;
import com.imagepicker.utils.ScreenUtils;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 图片预览，大图
 */
public class SendImagePreviewAdapter extends PagerAdapter {

	private LayoutInflater mInflater;
    public List<String> list;
	private SDCardImageLoader loader;
    public SendImagePreviewAdapter(Context context, List<String> list) {
        mInflater =  LayoutInflater.from(context);
        this.list = list;
		loader = new SDCardImageLoader(ScreenUtils.getScreenW(), ScreenUtils.getScreenH());
    }
	
	@Override
	public int getCount() {
		return list.size();
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    @Override
    public Object instantiateItem(ViewGroup view, int position) {
    	final ViewHolder holder;
		holder = new ViewHolder();
		View imageLayout = mInflater.inflate(R.layout.images_item, null);
		holder.mImage = (TouchImageView) imageLayout.findViewById(R.id.images_item_image);
		String filePath = list.get(position);
		holder.mImage.setTag(filePath);
		loader.loadImage(2, filePath, holder.mImage);
    	((ViewPager) view).addView(imageLayout, 0);
        return imageLayout;
    }
    
    class ViewHolder {
		private TouchImageView mImage;
	}

}
