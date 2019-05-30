package com.door.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.door.entity.DoorOftenCallBack;
import com.door.entity.EditDoorLongCallBack;
import com.door.entity.EditDoorOftenCallBack;
import com.door.entity.SingleCommunityEntity;
import com.nohttp.utils.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;


/**
 * 编辑门禁适配器
 * Created by lizc on 2018/08/12.
 */
public class NewEditDoorRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    public List<SingleCommunityEntity.ContentBean.CommonUseBean> content = new ArrayList<>();
    public DoorOftenCallBack doorOfernCallBack;
    public EditDoorOftenCallBack editDoorOftenCallBack;
    public EditDoorLongCallBack editDoorLongCallBack;
    private String name;

    public void setDoorOftenCallBack(DoorOftenCallBack doorOfernCallBack) {
        this.doorOfernCallBack = doorOfernCallBack;
    }

    public void setEditDoorOftenCallBack(EditDoorOftenCallBack editDoorOftenCallBack) {
        this.editDoorOftenCallBack = editDoorOftenCallBack;
    }

    public void setEditDoorLongCallBack(EditDoorLongCallBack editDoorLongCallBack) {
        this.editDoorLongCallBack = editDoorLongCallBack;
    }

    public NewEditDoorRVAdapter(Context context, List<SingleCommunityEntity.ContentBean.CommonUseBean> contentBanner) {
        mInflater = LayoutInflater.from(context);
        content = contentBanner;
    }

    public void setData(List<SingleCommunityEntity.ContentBean.CommonUseBean> content) {
        this.content = content;
        notifyDataSetChanged();
    }

    public void addData(int position, SingleCommunityEntity.ContentBean.CommonUseBean content) {
        this.content.add(content);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, this.content.size() - position);//通知数据与界面重新绑
    }
    // 删除数据
    public void removeData(int position) {
        this.content.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, this.content.size() - position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_door_item, parent, false);
        DoorOfernViewHolder doorOfernViewHolder = new DoorOfernViewHolder(view);
        return doorOfernViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DoorOfernViewHolder) {
            ((DoorOfernViewHolder) holder).tv_editdoor_name.setText(content.get(position).getDoor_name());
            ((DoorOfernViewHolder) holder).tv_editdoor_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != doorOfernCallBack) {
                        doorOfernCallBack.getData("", position);
                    }
                }
            });
            ((DoorOfernViewHolder) holder).iv_editdoor_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != editDoorOftenCallBack) {
                        editDoorOftenCallBack.getEditData("", position);
                    }
                }
            });
            GlideImageLoader.loadImageDefaultDisplay(mInflater.getContext(), content.get(position).getDoor_img(), ((DoorOfernViewHolder) holder).iv_editdoor_logo, R.drawable.default_image, R.drawable.default_image);
        }
    }

    @Override
    public int getItemCount() {
        return content == null ? 0 : content.size();
    }


    private static class DoorOfernViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_editdoor_delete;
        private ImageView iv_editdoor_logo;
        private ImageView iv_editdoor_move;
        private TextView tv_editdoor_name;
        private RelativeLayout rl_editdoor_item;


        public DoorOfernViewHolder(View itemView) {
            super(itemView);
            rl_editdoor_item = (RelativeLayout) itemView.findViewById(R.id.rl_editdoor_item);
            iv_editdoor_delete = (ImageView) itemView.findViewById(R.id.iv_editdoor_delete);
            iv_editdoor_logo = (ImageView) itemView.findViewById(R.id.iv_editdoor_logo);
            iv_editdoor_move = (ImageView) itemView.findViewById(R.id.iv_editdoor_move);
            tv_editdoor_name = (TextView) itemView.findViewById(R.id.tv_editdoor_name);
        }
    }
}
