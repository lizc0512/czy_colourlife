package com.feed.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.feed.adapter.ImagesDetailAdapter;

import java.util.ArrayList;

import cn.net.cyberway.R;

/**
 * 图片大图页面
 */
public class ImagesDetailActivity extends Activity {
	public static final String IMAGES = "images";
	public static final String POSITION = "POSITION";
	public static final String NAME = "NAME";
	private ViewPager mImagePager;
	private ImagesDetailAdapter mGalleryImageAdapter;
	private TextView 			mImagePosition;
	private TextView			mImageName;
	private ArrayList<String> mImageList;
	private int position;
	private String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_detail);
		mImagePager 	= (ViewPager) 	findViewById(R.id.image_pager);
		mImagePosition 	= (TextView) 	findViewById(R.id.image_position);
		mImageName		= (TextView) 	findViewById(R.id.image_name);


		mImageList = (ArrayList<String>) getIntent().getSerializableExtra(IMAGES);
		position = getIntent().getIntExtra(POSITION , 0);
		name = getIntent().getStringExtra(NAME);
		if(name != null && name.length()> 0){
			mImageName.setText(name);
		}
		mGalleryImageAdapter = new ImagesDetailAdapter(this, mImageList);
		mImagePager.setAdapter(mGalleryImageAdapter);

		if(mImageList.size() > 1){
			mImagePosition.setVisibility(View.VISIBLE);
			mImagePosition.setText(position + 1 + "/" + mImageList.size());
		}else{
			mImagePosition.setVisibility(View.GONE);
		}

		mImagePager.setCurrentItem(position);

		mImagePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mImagePosition.setText(arg0 + 1 + "/" + mImageList.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }

			@Override
			public void onPageScrollStateChanged(int arg0) { }
		});
	}
	
}
