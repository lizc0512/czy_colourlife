package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.home.protocol.OPTIONSDATA;

/**
 * adapter lizc 我的页面item适配器
 * Created by chenql on 17/10/11.
 */
public class MyPageItemListAdapter extends RecyclerView.Adapter<MyPageItemListAdapter.DefaultViewHolder> {
    private Context context;
    private List<OPTIONSDATA> data;
    private OnItemClickListener onClickListener;

    public void setData(List<OPTIONSDATA> lists) {
        this.data = lists;
        notifyDataSetChanged();
    }

    public MyPageItemListAdapter(Context context, List<OPTIONSDATA> list) {
        this.context = context;
        this.data = list;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyPageItemListAdapter.DefaultViewHolder viewHolder = new MyPageItemListAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mypageitemlist, parent, false));
        viewHolder.onClickListener = onClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewHolder holder, int position) {
        final OPTIONSDATA list = (OPTIONSDATA) data.get(position);
        holder.name.setText(list.name);
        String img = list.img;
        GlideImageLoader.loadImageDefaultDisplay(context, img, holder.image, R.drawable.default_image, R.drawable.default_image);
        int currentStr = data.get(position).group_id;//获取ID
        int previewStr = (position - 1) >= 0 ? (data.get(position - 1).group_id) : 0;
        if (previewStr != (currentStr)) {
            holder.tv_mypageline.setVisibility(View.VISIBLE);
            holder.view.setVisibility(View.GONE);
        } else {
            holder.tv_mypageline.setVisibility(View.GONE);//是，隐藏起来
            holder.view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_mypageline;
        RelativeLayout rl_mypage_item;
        TextView id;//
        TextView name;//
        ImageView image;//
        TextView view;
        OnItemClickListener onClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_mypageline = itemView.findViewById(R.id.tv_mypageline);
            image = itemView.findViewById(R.id.iv_mypage_photo);
            name = itemView.findViewById(R.id.tv_mypage_nickname);
            rl_mypage_item = itemView.findViewById(R.id.rl_mypage_item);
            view = itemView.findViewById(R.id.mypage_line);
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