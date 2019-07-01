package com.setting.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nohttp.utils.GlideImageLoader;
import com.setting.activity.AuthManegeListActivity;
import com.user.entity.AuthManegeListEntity;

import java.util.List;

import cn.net.cyberway.R;

/**
 * Created by hxg on 2019/4/16 09:47
 */
public class AuthManegeAdapter extends RecyclerView.Adapter<AuthManegeAdapter.DefaultViewHolder> {

    public Context mContext;
    public List<AuthManegeListEntity.ContentBean> mList;

    public AuthManegeAdapter(Context context, List<AuthManegeListEntity.ContentBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public AuthManegeAdapter.DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_auth_list, parent, false);
        return new DefaultViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AuthManegeAdapter.DefaultViewHolder holder, int position) {
        final AuthManegeListEntity.ContentBean item = mList.get(position);
        if (!TextUtils.isEmpty(item.getIcon())) {
            GlideImageLoader.loadImageDefaultDisplay(mContext, item.getIcon(), holder.iv_icon, R.drawable.default_image, R.drawable.default_image);
        }
        holder.tv_auth_name.setText(item.getName());
        holder.tv_auth_time.setText("授权时间：" + item.getAuth_time());
        holder.ll_auth.setOnClickListener(v -> ((AuthManegeListActivity) mContext).clickItem(item.getApp_id()));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class DefaultViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_auth;
        ImageView iv_icon;
        TextView tv_auth_name;
        TextView tv_auth_time;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            ll_auth = itemView.findViewById(R.id.ll_auth);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_auth_name = itemView.findViewById(R.id.tv_auth_name);
            tv_auth_time = itemView.findViewById(R.id.tv_auth_time);
        }
    }
}
