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
public class NewNotEditDoorRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    public List<SingleCommunityEntity.ContentBean.NotCommonUseBean> content = new ArrayList<>();
    public DoorOftenCallBack doorOfernCallBack;
    public EditDoorOftenCallBack editDoorOftenCallBack;
    public EditDoorLongCallBack editDoorLongCallBack;

    public void setDoorOftenCallBack(DoorOftenCallBack doorOfernCallBack) {
        this.doorOfernCallBack = doorOfernCallBack;
    }

    public void setEditDoorOftenCallBack(EditDoorOftenCallBack editDoorOftenCallBack) {
        this.editDoorOftenCallBack = editDoorOftenCallBack;
    }

    public void setEditDoorLongCallBack(EditDoorLongCallBack editDoorLongCallBack) {
        this.editDoorLongCallBack = editDoorLongCallBack;
    }

    public NewNotEditDoorRVAdapter(Context context, List<SingleCommunityEntity.ContentBean.NotCommonUseBean> contentBanner) {
        mInflater = LayoutInflater.from(context);
        content = contentBanner;
    }

    public void setData(List<SingleCommunityEntity.ContentBean.NotCommonUseBean> content) {
        this.content = content;
        notifyDataSetChanged();
    }

    public void addData(int position, SingleCommunityEntity.ContentBean.NotCommonUseBean content) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_door_notitem, parent, false);
        DoorNotOfernViewHolder doorNotOfernViewHolder = new DoorNotOfernViewHolder(view);
        return doorNotOfernViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DoorNotOfernViewHolder) {
            ((DoorNotOfernViewHolder) holder).tv_editdoornot_name.setText(content.get(position).getDoor_name());
            GlideImageLoader.loadImageDefaultDisplay(mInflater.getContext(), content.get(position).getDoor_img(), ((DoorNotOfernViewHolder) holder).iv_editdoornot_logo, R.drawable.default_image, R.drawable.default_image);
            ((DoorNotOfernViewHolder) holder).iv_editdoornot_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != doorOfernCallBack) {
                        doorOfernCallBack.getData("", position);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return content == null ? 0 : content.size();

    }

    private static class DoorNotOfernViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_editdoornot_title;
        private ImageView iv_editdoornot_add;
        private ImageView iv_editdoornot_logo;
        private TextView tv_editdoornot_name;
        private RelativeLayout rl_editdoornot_item;

        public DoorNotOfernViewHolder(View itemView) {
            super(itemView);
            rl_editdoornot_item = (RelativeLayout) itemView.findViewById(R.id.rl_editdoornot_item);
            iv_editdoornot_add = (ImageView) itemView.findViewById(R.id.iv_editdoornot_add);
            iv_editdoornot_logo = (ImageView) itemView.findViewById(R.id.iv_editdoornot_logo);
            tv_editdoornot_name = (TextView) itemView.findViewById(R.id.tv_editdoornot_name);
            tv_editdoornot_title = (TextView) itemView.findViewById(R.id.tv_editdoornot_title);
        }
    }
}
