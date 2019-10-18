package com.door.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.door.entity.IdentityListEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;


public class NewDoorIdentifyAdapter extends RecyclerView.Adapter<NewDoorIdentifyAdapter.NewDoorAuthorHolder> {

    private List<IdentityListEntity.ContentBean> identityList;
    //  img_owner img_tenant  img_family  img_tourist
    private int[] imgArrs = {R.drawable.img_owner, R.drawable.img_family, R.drawable.img_tenant, R.drawable.img_tourist};
    public int choicePos=-1;
    private OnItemClickListener onClickListener;

    public NewDoorIdentifyAdapter(List<IdentityListEntity.ContentBean> identityList) {
        this.identityList = identityList;
    }


    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setChoicePos(int pos) {
        this.choicePos = pos;
        notifyDataSetChanged();
    }

    @Override
    public NewDoorIdentifyAdapter.NewDoorAuthorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.door_identify_item, parent, false);
        NewDoorIdentifyAdapter.NewDoorAuthorHolder doorOfernViewHolder = new NewDoorIdentifyAdapter.NewDoorAuthorHolder(view);
        doorOfernViewHolder.onClickListener = onClickListener;
        return doorOfernViewHolder;
    }

    @Override
    public void onBindViewHolder(NewDoorIdentifyAdapter.NewDoorAuthorHolder holder, final int position) {
        IdentityListEntity.ContentBean contentBean = identityList.get(position);
        holder.tv_identify.setText(contentBean.getName());
        Context context = holder.itemView.getContext();
        if (position < imgArrs.length) {
            holder.iv_identify.setImageResource(imgArrs[position]);
        }
        if (choicePos == position) {
            holder.cardview_layout.setCardBackgroundColor(context.getResources().getColor(R.color.color_cecfd1));
        } else {
            holder.cardview_layout.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return identityList == null ? 0 : identityList.size();

    }

    public static class NewDoorAuthorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView cardview_layout;
        private TextView tv_identify;
        private ImageView iv_identify;
        OnItemClickListener onClickListener;


        public NewDoorAuthorHolder(View itemView) {
            super(itemView);
            cardview_layout = itemView.findViewById(R.id.cardview_layout);
            tv_identify = itemView.findViewById(R.id.tv_identify);
            iv_identify = itemView.findViewById(R.id.iv_identify);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onItemClick(getAdapterPosition());
        }
    }

}
