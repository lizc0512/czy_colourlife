package com.feed.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.BeeFramework.BeeFrameworkApp;
import com.BeeFramework.Utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.feed.activity.ImagesDetailActivity;
import com.nohttp.utils.GlideImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import cn.net.cyberway.R;

/**
 * feed里面图片的adapter
 */
public class FeedGirdViewImageAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    public ArrayList<String> mList;
    public int hight;

    public FeedGirdViewImageAdapter(Context context, ArrayList<String> list) {
        this.mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(context);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        hight = (width - Utils.dip2px(mContext, 103)) / 3;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.feed_gridview_item, null);
            holder.mImage = (ImageView) convertView.findViewById(R.id.feed_gridview_item_image);
            ViewGroup.LayoutParams params = holder.mImage.getLayoutParams();
            params.height = hight;
            holder.mImage.setLayoutParams(params);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String attach = mList.get(position);

        GlideImageLoader.loadImageDefaultDisplay(mContext, attach, holder.mImage, R.drawable.default_image, R.drawable.default_image);

        holder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImagesDetailActivity.class);
                intent.putExtra(ImagesDetailActivity.POSITION, position);
                intent.putExtra(ImagesDetailActivity.IMAGES, mList);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        private ImageView mImage;
    }

}
