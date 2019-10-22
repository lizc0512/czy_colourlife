package com.door.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
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
    public int userId;
    public List<DoorAllEntity.ContentBean.DataBean.ListBean.KeyListBean> mList;
    public List<String> titleList;
    public List<String> typeList;
    public List<String> tagList;
    public List<String> identifyList;
    public List<String> comunityList;
    public List<List<DoorAllEntity.ContentBean.DataBean.ListBean.InvalidUnitBean>> allUnitBeanList;
    private PopupWindow popupWindow;

    public IntelligenceDoorAdapter(Context mContext, int userId, List<String> titleList, List<String> typeList, List<String> tagList, List<String> identifyList, List<DoorAllEntity.ContentBean.DataBean.ListBean.KeyListBean> mList) {
        this.mContext = mContext;
        this.userId = userId;
        this.titleList = titleList;
        this.typeList = typeList;
        this.tagList = tagList;
        this.identifyList = identifyList;
        this.mList = mList;
    }

    public void setData(List<String> titleList, List<String> typeList, List<String> tagList, List<String> identifyList, List<DoorAllEntity.ContentBean.DataBean.ListBean.KeyListBean> mList) {
        this.titleList.addAll(titleList);
        this.typeList.addAll(typeList);
        this.tagList.addAll(tagList);
        this.identifyList.addAll(identifyList);
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    public void setData(List<List<DoorAllEntity.ContentBean.DataBean.ListBean.InvalidUnitBean>> allUnitBeanList, List<String> comunityList) {
        this.allUnitBeanList = allUnitBeanList;
        this.comunityList = comunityList;
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
                holder.ll_kind.setVisibility(View.VISIBLE);
                holder.tv_kind.setText(titleList.get(position));
            } else {
                holder.ll_kind.setVisibility(View.GONE);
            }
            if ("1".equals(tagList.get(position))) {
                holder.tv_apply.setVisibility(View.VISIBLE);
            } else {
                holder.tv_apply.setVisibility(View.INVISIBLE);
            }
            String type = typeList.get(position);
            holder.tv_apply.setOnClickListener(v -> {
                        if ("2".equals(type)) {
                            ((IntelligenceDoorActivity) mContext).apply(comunityList.get(position), identifyList.get(position), typeList.get(position), allUnitBeanList.get(position));
                        } else {
                            ((IntelligenceDoorActivity) mContext).apply(comunityList.get(position), identifyList.get(position), typeList.get(position), null);
                        }
                    }
            );
            switch (type) {
                case "1":
                    holder.rl_key.setVisibility(View.VISIBLE);
                    holder.rl_key.setBackgroundResource(R.drawable.shape_door_key);
                    holder.ll_car.setVisibility(View.GONE);
                    holder.iv_handle.setVisibility(View.VISIBLE);
                    boolean isCommon = "1".equals(item.getIs_common());
                    if (isCommon) {//1 常用门禁， 2非常用门禁
                        holder.tv_common.setVisibility(View.VISIBLE);
                    } else {
                        holder.tv_common.setVisibility(View.GONE);
                    }
                    holder.tv_title.setText(item.getDoor_name());
                    holder.tv_pwd.setVisibility(View.GONE);
                    long stop_time = item.getStop_time();
                    if (stop_time == 0) {
                        holder.tv_avail_time_to.setText("永久有效");
                        holder.iv_icon.setBackgroundResource(R.drawable.ic_door_key);
                    } else {
                        long stop_millis = stop_time * 1000;
                        long current_millis = System.currentTimeMillis();
                        if (stop_millis < current_millis) {
                            holder.iv_icon.setBackgroundResource(R.drawable.ic_door_key_timeout);
                            holder.tv_avail_time_to.setText("已过期  有效期至" + TimeUtil.getYearTime(stop_millis, "yyyy-MM-dd"));
                        } else {
                            if (stop_millis - current_millis <= 5 * 3600 * 24 * 1000) {
                                holder.tv_avail_time_to.setText("快过期  有效期至" + TimeUtil.getYearTime(stop_millis, "yyyy-MM-dd"));
                            } else {
                                holder.tv_avail_time_to.setText("有效期至" + TimeUtil.getYearTime(stop_millis, "yyyy-MM-dd"));
                            }
                            holder.iv_icon.setBackgroundResource(R.drawable.ic_door_key);
                        }
                    }
                    //远程开门
                    holder.rl_key.setOnClickListener(v -> ((IntelligenceDoorActivity) mContext).remoteDoor(item.getQr_code()));

                    holder.iv_handle.setOnClickListener(v -> {
                        try {
                            View popWindowView = LayoutInflater.from(mContext).inflate(R.layout.pop_door_handle, null);
                            popupWindow = new PopupWindow(popWindowView,
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    true);
                            // 使其聚集
                            popupWindow.setFocusable(true);
                            // 设置允许在外点击消失
                            popupWindow.setOutsideTouchable(true);
                            // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
                            popupWindow.setBackgroundDrawable(new BitmapDrawable());
                            popupWindow.showAsDropDown(holder.iv_handle, 0, 0);

                            if (null != popWindowView) {
                                LinearLayout ll_rename = (popWindowView).findViewById(R.id.ll_rename);
                                TextView tv_rename = (popWindowView).findViewById(R.id.tv_rename);
                                TextView tv_remove = (popWindowView).findViewById(R.id.tv_remove);

                                if (isCommon) {
                                    ll_rename.setVisibility(View.VISIBLE);
                                } else {
                                    ll_rename.setVisibility(View.GONE);
                                }

                                tv_rename.setOnClickListener(viewRename -> {
                                    popupWindow.dismiss();
                                    ((IntelligenceDoorActivity) mContext).renameHandle(position, item.getCommunity_uuid(), item.getDoor_id(), item.getDoor_name());
                                });

                                if (isCommon) {
                                    tv_remove.setText("从常用门禁中移除");
                                } else {
                                    tv_remove.setText("添加到常用门禁");
                                }
                                tv_remove.setOnClickListener(viewRemove -> {
                                    popupWindow.dismiss();
                                    ((IntelligenceDoorActivity) mContext).addOrRemoveHandle(position, item.getCommunity_uuid(), item.getDoor_id(), item.getDoor_name(), !isCommon);
                                });

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });
                    break;
                case "2":
                    holder.rl_key.setVisibility(View.VISIBLE);
                    holder.rl_key.setBackgroundResource(R.drawable.shape_door_key_no);
                    holder.ll_car.setVisibility(View.GONE);
                    holder.iv_handle.setVisibility(View.GONE);
                    holder.tv_common.setVisibility(View.GONE);
                    holder.tv_title.setText(item.getName());
                    String model = item.getModel().substring(0, item.getModel().length() - 1);
                    switch (model) {
                        case "ISE00":
                        case "ISE02":
                            holder.tv_pwd.setVisibility(View.VISIBLE);
                            //获取密码
                            holder.tv_pwd.setOnClickListener(v -> ((IntelligenceDoorActivity) mContext).getDevicePwd(item.getDeviceId()));
                            break;
                        default:
                            holder.tv_pwd.setVisibility(View.GONE);
                    }
                    long stopTime = item.getStop_time();
                    if (stopTime == 0) {
                        holder.tv_avail_time_to.setText("永久有效");
                        holder.iv_icon.setBackgroundResource(R.drawable.ic_door_bluetooth);
                    } else {
                        long stop_millis = stopTime * 1000;
                        long current_millis = System.currentTimeMillis();
                        if (stop_millis < current_millis) {
                            holder.iv_icon.setBackgroundResource(R.drawable.ic_door_bluetooth_timeout);
                            holder.tv_avail_time_to.setText("已过期  有效期至" + TimeUtil.getYearTime(stop_millis, "yyyy-MM-dd"));
                        } else {
                            if (stop_millis - current_millis <= 5 * 3600 * 24 * 1000) {
                                holder.tv_avail_time_to.setText("快过期  有效期至" + TimeUtil.getYearTime(stop_millis, "yyyy-MM-dd"));
                            } else {
                                holder.tv_avail_time_to.setText("有效期至" + TimeUtil.getYearTime(stop_millis, "yyyy-MM-dd"));
                            }
                            holder.iv_icon.setBackgroundResource(R.drawable.ic_door_bluetooth);
                        }
                    }
                    break;
                case "3":
                    holder.rl_key.setVisibility(View.GONE);
                    holder.ll_car.setVisibility(View.VISIBLE);
                    holder.iv_handle.setVisibility(View.GONE);
                    holder.tv_common.setVisibility(View.GONE);
                    holder.tv_car_title.setText(item.getName());
                    String time = item.getValid_date();
                    if (time.contains("-")) {
                        String[] times = time.split("-");
                        if (times.length > 1) {
                            time = times[0] + "-\n" + times[1];
                        }
                    }
                    holder.tv_avail_time.setText(time);
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
        private TextView tv_apply;
        private LinearLayout ll_kind;
        private TextView tv_pwd;
        private LinearLayout ll_car;
        private TextView tv_car_title;
        private TextView tv_avail_time;
        private LinearLayout ll_down;
        private LinearLayout ll_up;
        private ImageView iv_handle;
        private ImageView iv_icon1;
        private View v_line1;
        private ImageView iv_icon2;
        private View v_line2;
        private ImageView iv_icon3;
        private TextView tv_avail_time_to;
        private TextView tv_common;

        public ViewHolder(View itemView) {
            super(itemView);
            rl_key = itemView.findViewById(R.id.rl_key);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_kind = itemView.findViewById(R.id.tv_kind);
            tv_apply = itemView.findViewById(R.id.tv_apply);
            ll_kind = itemView.findViewById(R.id.ll_kind);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_pwd = itemView.findViewById(R.id.tv_pwd);
            ll_car = itemView.findViewById(R.id.ll_car);
            tv_car_title = itemView.findViewById(R.id.tv_car_title);
            tv_avail_time = itemView.findViewById(R.id.tv_avail_time);
            ll_down = itemView.findViewById(R.id.ll_down);
            ll_up = itemView.findViewById(R.id.ll_up);
            iv_handle = itemView.findViewById(R.id.iv_handle);
            iv_icon1 = itemView.findViewById(R.id.iv_icon1);
            v_line1 = itemView.findViewById(R.id.v_line1);
            iv_icon2 = itemView.findViewById(R.id.iv_icon2);
            v_line2 = itemView.findViewById(R.id.v_line2);
            iv_icon3 = itemView.findViewById(R.id.iv_icon3);
            tv_avail_time_to = itemView.findViewById(R.id.tv_avail_time_to);
            tv_common = itemView.findViewById(R.id.tv_common);
        }
    }

}
