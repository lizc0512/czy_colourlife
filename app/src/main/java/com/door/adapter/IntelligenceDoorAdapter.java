package com.door.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.door.activity.IntelligenceDoorActivity;
import com.door.entity.DoorAllEntity;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 智能门禁
 * Created by hxg on 2019/8/7 09:47
 */
public class IntelligenceDoorAdapter extends RecyclerView.Adapter<IntelligenceDoorAdapter.ViewHolder> {

    private Context mContext;
    public List<DoorAllEntity.ContentBean.DataBean.ListBean.KeyListBean> mList;
    public List<String> titleList;
    public List<String> typeList;

    public IntelligenceDoorAdapter(Context mContext, List<String> titleList, List<String> typeList, List<DoorAllEntity.ContentBean.DataBean.ListBean.KeyListBean> mList) {
        this.mContext = mContext;
        this.titleList = titleList;
        this.typeList = typeList;
        this.mList = mList;
    }

    public void setData(List<String> titleList, List<String> typeList, List<DoorAllEntity.ContentBean.DataBean.ListBean.KeyListBean> mList) {
        this.titleList.addAll(titleList);
        this.typeList.addAll(typeList);
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    public void clearData() {
        if (null != titleList) {
            titleList.clear();
        }
        if (null != typeList) {
            typeList.clear();
        }
        if (null != mList) {
            mList.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public IntelligenceDoorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_intelligence_door, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(IntelligenceDoorAdapter.ViewHolder holder, int position) {
        DoorAllEntity.ContentBean.DataBean.ListBean.KeyListBean item = mList.get(position);
        try {
            if (!TextUtils.isEmpty(titleList.get(position))) {
                holder.tv_kind.setVisibility(View.VISIBLE);
                holder.tv_kind.setText(titleList.get(position));
            } else {
                holder.tv_kind.setVisibility(View.GONE);
            }
            String type = typeList.get(position);
            switch (type) {
                case "1":
                    holder.rl_key.setVisibility(View.VISIBLE);
                    holder.rl_key.setBackgroundResource(R.drawable.shape_door_key);
                    holder.ll_car.setVisibility(View.GONE);
                    holder.tv_title.setText(item.getDoor_name());
                    holder.iv_icon.setBackgroundResource(R.drawable.ic_door_key);
                    holder.tv_pwd.setVisibility(View.GONE);
                    //远程开门
                    holder.rl_key.setOnClickListener(v -> ((IntelligenceDoorActivity) mContext).remoteDoor(item.getDoor_id()));
                    break;
                case "2":
                    holder.rl_key.setVisibility(View.VISIBLE);
                    holder.rl_key.setBackgroundResource(R.drawable.shape_door_key_no);
                    holder.ll_car.setVisibility(View.GONE);
                    holder.tv_title.setText(item.getName());
                    holder.iv_icon.setBackgroundResource(R.drawable.ic_door_bluetooth);
                    String model = item.getModel().substring(0, item.getModel().length() - 1);
                    switch (model) {
                        case "ISE00":
                        case "ISE02":
                            holder.tv_pwd.setVisibility(View.VISIBLE);
                            //获取密码
                            holder.tv_pwd.setOnClickListener(v -> ((IntelligenceDoorActivity) mContext).getDevicePwd(item.getDeviceId()));
                            break;
                        case "ISE10":
                            holder.tv_pwd.setVisibility(View.GONE);
                            break;
                        default:
                            holder.tv_pwd.setVisibility(View.GONE);
                    }
                    break;
                case "3":
                    holder.rl_key.setVisibility(View.GONE);
                    holder.ll_car.setVisibility(View.VISIBLE);
                    holder.tv_car_title.setText(item.getName());
                    holder.tv_avail_time.setText("有效期：" + item.getValid_date());
                    holder.ll_down.setOnClickListener(v -> {
                        ((IntelligenceDoorActivity) mContext).parkDown(item.getMac().replace(":", ""));// 倒下
                    });
                    holder.ll_up.setOnClickListener(v -> {
                        ((IntelligenceDoorActivity) mContext).parkUp(item.getMac().replace(":", ""));// 抬起
                    });
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_key;
        private ImageView iv_icon;
        private TextView tv_title;
        private TextView tv_kind;
        private TextView tv_pwd;
        private LinearLayout ll_car;
        private TextView tv_car_title;
        private TextView tv_avail_time;
        private LinearLayout ll_down;
        private LinearLayout ll_up;

        public ViewHolder(View itemView) {
            super(itemView);
            rl_key = itemView.findViewById(R.id.rl_key);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_kind = itemView.findViewById(R.id.tv_kind);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_pwd = itemView.findViewById(R.id.tv_pwd);
            ll_car = itemView.findViewById(R.id.ll_car);
            tv_car_title = itemView.findViewById(R.id.tv_car_title);
            tv_avail_time = itemView.findViewById(R.id.tv_avail_time);
            ll_down = itemView.findViewById(R.id.ll_down);
            ll_up = itemView.findViewById(R.id.ll_up);
        }
    }

}
