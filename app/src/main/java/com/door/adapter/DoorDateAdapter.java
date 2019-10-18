package com.door.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;


/**
 * 用户授权时间的
 */
public class DoorDateAdapter extends RecyclerView.Adapter<DoorDateAdapter.NewDoorAuthorHolder> {

    public List<String> content;
    public int choicePos =0;
    private OnItemClickListener onClickListener;

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setChoicePos(int pos) {
        this.choicePos = pos;
        notifyDataSetChanged();
    }

    public DoorDateAdapter(List<String> content) {
        this.content = content;
    }


    @Override
    public DoorDateAdapter.NewDoorAuthorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.door_date_item, parent, false);
        DoorDateAdapter.NewDoorAuthorHolder doorOfernViewHolder = new DoorDateAdapter.NewDoorAuthorHolder(view);
        doorOfernViewHolder.onClickListener = onClickListener;
        return doorOfernViewHolder;
    }

    @Override
    public void onBindViewHolder(DoorDateAdapter.NewDoorAuthorHolder holder, final int position) {
        holder.tv_door_date.setText(content.get(position));
        if (position == choicePos) {
            holder.tv_door_date.setBackgroundResource(R.drawable.shape_door_date_click);
            holder.tv_door_date.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.tv_door_date.setBackgroundResource(R.drawable.shape_door_date);
            holder.tv_door_date.setTextColor(Color.parseColor("#333333"));
        }
    }

    @Override
    public int getItemCount() {
        return content == null ? 0 : content.size();
    }

    public static class NewDoorAuthorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_door_date;
        OnItemClickListener onClickListener;

        public NewDoorAuthorHolder(View itemView) {
            super(itemView);
            tv_door_date = itemView.findViewById(R.id.tv_door_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
