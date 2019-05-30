package com.im.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.im.activity.IMApplyFriendRecordActivity;
import com.im.entity.ApplyRecordEntity;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/28 19:03
 * @change
 * @chang time
 * @class describe  申请好友记录的adapter
 */
public class ApplyRecordAdapter extends RecyclerView.Adapter<ApplyRecordAdapter.DefaultViewHolder> {

    public List<ApplyRecordEntity> applyRecordEntityList;


    private OnItemClickListener mOnItemClickListener;

    public ApplyRecordAdapter(List<ApplyRecordEntity> applyRecordEntityList) {
        this.applyRecordEntityList = applyRecordEntityList;
    }

    @NonNull
    @Override
    public DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_apply_friend_record, parent, false);
        ApplyRecordAdapter.DefaultViewHolder viewHolder = new ApplyRecordAdapter.DefaultViewHolder(view);
        viewHolder.onItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewHolder holder, final int position) {
        ApplyRecordEntity applyRecordEntity = applyRecordEntityList.get(position);
        final String applyStatus = applyRecordEntity.getState();
        if ("0".equals(applyStatus)) {
            holder.user_apply_status.setText("接受");
            holder.user_apply_status.setTextColor(Color.parseColor("#ffffff"));
            holder.user_apply_status.setBackgroundResource(R.drawable.rect_round_blue);
        } else {
            holder.user_apply_status.setText("已添加");
            holder.user_apply_status.setTextColor(Color.parseColor("#D4D9DC"));
            holder.user_apply_status.setBackgroundResource(R.color.white);
        }
        final Context mCotext = holder.itemView.getContext();
        GlideImageLoader.loadImageDefaultDisplay(mCotext, applyRecordEntity.getPortrait(),
                holder.user_photo, R.drawable.im_icon_default_head, R.drawable.im_icon_default_head);
        holder.user_apply_notes.setText(applyRecordEntity.getNewComment());
        String name = applyRecordEntity.getName();
        if (TextUtils.isEmpty(name)) {
            holder.user_nickname.setText(name);
        } else {
            holder.user_nickname.setText(applyRecordEntity.getNickName());
        }
        holder.user_apply_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick()){
                    if ("0".equals(applyStatus)) {
                        ((IMApplyFriendRecordActivity) mCotext).agreeFriendApply(position);
                    }
                }
            }
        });
    }

    protected boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return applyRecordEntityList == null ? 0 : applyRecordEntityList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView user_photo;
        private TextView user_nickname;
        private TextView user_apply_notes;
        private TextView user_apply_status;
        OnItemClickListener onItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            user_photo = itemView.findViewById(R.id.user_photo);
            user_nickname = itemView.findViewById(R.id.user_nickname);
            user_apply_notes = itemView.findViewById(R.id.user_apply_notes);
            user_apply_status = itemView.findViewById(R.id.user_apply_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
