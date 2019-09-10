package com.door.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.net.cyberway.R;


/**
 * Created by chengyun on 2016/1/5.
 */
public class NewDoorAuthorRecordAdapter extends RecyclerView.Adapter<NewDoorAuthorRecordAdapter.NewDoorAuthorHolder> {

    public List<String> content;


    public NewDoorAuthorRecordAdapter(Context context, List<String> content) {
        this.content = content;
    }


    @Override
    public NewDoorAuthorRecordAdapter.NewDoorAuthorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_door_item, parent, false);
        NewDoorAuthorRecordAdapter.NewDoorAuthorHolder doorOfernViewHolder = new NewDoorAuthorRecordAdapter.NewDoorAuthorHolder(view);
        return doorOfernViewHolder;
    }

    @Override
    public void onBindViewHolder(NewDoorAuthorRecordAdapter.NewDoorAuthorHolder holder, final int position) {
        holder.tv_homedoor_item_name.setText(content.get(position));
    }

    @Override
    public int getItemCount() {
        return content == null ? 0 : content.size();

    }

    public static class NewDoorAuthorHolder extends RecyclerView.ViewHolder {

        private ImageView iv_homedoor_item_logo;
        private TextView tv_homedoor_item_name;
        private RelativeLayout rl_homedoor_item;

        public NewDoorAuthorHolder(View itemView) {
            super(itemView);
            rl_homedoor_item = (RelativeLayout) itemView.findViewById(R.id.rl_homedoor_item);
            iv_homedoor_item_logo = (ImageView) itemView.findViewById(R.id.iv_homedoor_item_logo);
            tv_homedoor_item_name = (TextView) itemView.findViewById(R.id.tv_homedoor_item_name);
        }
    }

}
