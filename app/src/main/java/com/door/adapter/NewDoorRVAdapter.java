package com.door.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.door.entity.DoorCommunityListEntity;
import com.door.entity.DoorOftenCallBack;
import com.door.entity.DoorOftenLongCallBack;
import com.nohttp.utils.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;


/**
 * 获取常用门禁适配器
 * Created by lizc on 2018/01/10.
 */
public class NewDoorRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    public List<DoorCommunityListEntity.ContentBean.DoorListBean> content = new ArrayList<>();
    public DoorOftenCallBack doorOfernCallBack;
    public DoorOftenLongCallBack doorOftenLongCallBack;

    public void setDoorOftenCallBack(DoorOftenCallBack doorOfernCallBack) {
        this.doorOfernCallBack = doorOfernCallBack;
    }

    public void setDoorOftenLongCallBack(DoorOftenLongCallBack doorOftenLongCallBack) {
        this.doorOftenLongCallBack = doorOftenLongCallBack;
    }

    public NewDoorRVAdapter(Context context, List<DoorCommunityListEntity.ContentBean.DoorListBean> contentBanner) {
        mInflater = LayoutInflater.from(context);
        content = contentBanner;
    }

    public void setData(List<DoorCommunityListEntity.ContentBean.DoorListBean> content) {
        this.content = content;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_door_item, parent, false);
        DoorOfernViewHolder doorOfernViewHolder = new DoorOfernViewHolder(view);
        return doorOfernViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String name = content.get(position).getDoor_name();
        ((DoorOfernViewHolder) holder).tv_homedoor_item_name.setText(name);
        GlideImageLoader.loadImageDefaultDisplay(mInflater.getContext(), content.get(position).getDoor_img(), ((DoorOfernViewHolder) holder).iv_homedoor_item_logo, R.drawable.default_image, R.drawable.default_image);
        ((DoorOfernViewHolder) holder).rl_homedoor_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != doorOfernCallBack) {
                    doorOfernCallBack.getData("", position);
                }
            }
        });
        ((DoorOfernViewHolder) holder).rl_homedoor_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != doorOftenLongCallBack) {
                    doorOftenLongCallBack.getLongData("", position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return content == null ? 0 : content.size();

    }

    private static class DoorOfernViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_homedoor_item_logo;
        private TextView tv_homedoor_item_name;
        private RelativeLayout rl_homedoor_item;

        public DoorOfernViewHolder(View itemView) {
            super(itemView);
            rl_homedoor_item = (RelativeLayout) itemView.findViewById(R.id.rl_homedoor_item);
            iv_homedoor_item_logo = (ImageView) itemView.findViewById(R.id.iv_homedoor_item_logo);
            tv_homedoor_item_name = (TextView) itemView.findViewById(R.id.tv_homedoor_item_name);
        }
    }

}
