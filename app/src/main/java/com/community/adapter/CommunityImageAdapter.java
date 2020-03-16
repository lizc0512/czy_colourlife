package com.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.BeeFramework.view.Util;
import com.community.activity.ImagesDetailActivity;
import com.nohttp.utils.GlideImageLoader;

import java.util.ArrayList;

import cn.net.cyberway.R;

/**
 * @ProjectName:
 * @Package: com.community.adapter
 * @ClassName: CommunityDynamicsAdapter
 * @Description: 社区动态图片的adapter
 * @Author: yuansk
 * @CreateDate: 2020/2/25 9:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/2/25 9:47
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */
public class CommunityImageAdapter extends RecyclerView.Adapter<CommunityImageAdapter.DefaultViewHolder> {

    private ArrayList<String> dynamicImagesList;
    private int extra_type;
    private int distance = 0;
    private Context mContext;
    private int itemWidth;
    private int marginPx;

    public CommunityImageAdapter(Context context, ArrayList<String> dynamicImagesList, int extra_type, int distance) {
        this.mContext = context;
        this.dynamicImagesList = dynamicImagesList;
        this.distance = distance;
        this.extra_type = extra_type;
        int screenWidth = Util.DensityUtil.getScreenWidth(mContext, false);
        marginPx = Util.DensityUtil.dip2px(mContext, 6);
        itemWidth = (screenWidth - Util.DensityUtil.dip2px(mContext, distance)) / 3 - marginPx;
    }


    @Override
    public CommunityImageAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommunityImageAdapter.DefaultViewHolder viewHolder = new CommunityImageAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dynamics_imagelist, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommunityImageAdapter.DefaultViewHolder holder, int position) {
        String imageUrl = dynamicImagesList.get(position);
        holder.iv_dynamic_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImagesDetailActivity.class);
                intent.putExtra(ImagesDetailActivity.POSITION, position);
                intent.putExtra(ImagesDetailActivity.IMAGES, dynamicImagesList);
                mContext.startActivity(intent);
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(itemWidth, itemWidth);
        layoutParams.setMargins(0, 0, 0, marginPx);
        holder.iv_dynamic_publish.setLayoutParams(layoutParams);
//        holder.iv_dynamic_publish.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideImageLoader.loadCenterCropImageDisplay(mContext, imageUrl, holder.iv_dynamic_publish, R.drawable.default_image, R.drawable.default_image);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dynamicImagesList == null ? 0 : dynamicImagesList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_dynamic_publish;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            iv_dynamic_publish = itemView.findViewById(R.id.iv_dynamic_publish);
        }
    }
}
