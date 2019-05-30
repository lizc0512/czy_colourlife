package com.feed.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BeeFramework.BeeFrameworkApp;
import com.BeeFramework.view.TouchImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 大图页面（feed发送成功之后）
 */
public class ImagesDetailAdapter extends PagerAdapter {

	private LayoutInflater mInflater;
    public List<String> list;

    public ImagesDetailAdapter(Context context, List<String> list) {
        mInflater =  LayoutInflater.from(context);
        this.list = list;
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
		String attach = list.get(position);
		if(attach != null && attach.length() > 0) {
			holder.mImage.setImageLoadUrl(attach);
			ImageLoader.getInstance().displayImage(attach, holder.mImage, BeeFrameworkApp.optionsImage);
			holder.mImage.isDownload(true);
			holder.mImage.isClickBack(true);
		} else {
			holder.mImage.setImageResource(R.drawable.products_loading);
		}
		
    	((ViewPager) view).addView(imageLayout, 0);
        return imageLayout;
    }
    
    class ViewHolder {
		private TouchImageView mImage;
	}

}
