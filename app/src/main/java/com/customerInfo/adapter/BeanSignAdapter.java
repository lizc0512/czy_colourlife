package com.customerInfo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.user.UserAppConst;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.home.entity.HomeColourBeanFormatEntity;

/**
 * 彩豆签到适配器
 * <p>
 * Created by hxg on 2019/4/16 09:47
 */
public class BeanSignAdapter extends BeeBaseAdapter {

    private SharedPreferences mShared;
    private int customer_id;

    public BeanSignAdapter(Context c, List dataList) {
        super(c, dataList);
        mShared = c.getSharedPreferences(UserAppConst.USERINFO, 0);
    }

    public class ViewHolder extends BeeCellHolder {
        TextView tv_day;
        TextView tv_today;
        ImageView iv_bean;
        TextView tv_add_bean;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        BeanSignAdapter.ViewHolder holder = new BeanSignAdapter.ViewHolder();
        holder.tv_day = cellView.findViewById(R.id.tv_day);
        holder.tv_today = cellView.findViewById(R.id.tv_today);
        holder.iv_bean = cellView.findViewById(R.id.iv_bean);
        holder.tv_add_bean = cellView.findViewById(R.id.tv_add_bean);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        BeanSignAdapter.ViewHolder holder = (BeanSignAdapter.ViewHolder) h;
        final HomeColourBeanFormatEntity.signBean item = (HomeColourBeanFormatEntity.signBean) dataList.get(position);
        int day = position + 1;

        customer_id = mShared.getInt(UserAppConst.Colour_User_id, 0);
        boolean hasSignPoint = mShared.getBoolean(UserAppConst.COLOUR_BEAN_SIGN_POINT + customer_id, false);

        if (position + 1 == item.getCurrent() && hasSignPoint || position + 1 < item.getCurrent()) {
            holder.tv_today.setVisibility(View.GONE);
            holder.tv_day.setVisibility(View.VISIBLE);
            holder.iv_bean.setBackgroundResource(R.drawable.icon_bean_light);
            holder.tv_day.setText("第" + day + "天");
            holder.tv_day.setTextColor(mContext.getResources().getColor(R.color.color_f28146));
            holder.tv_add_bean.setTextColor(mContext.getResources().getColor(R.color.color_f28146));
        } else if (position + 1 == item.getCurrent()) {//连续签到的天
            holder.iv_bean.setBackgroundResource(R.drawable.icon_bean_light);
            holder.tv_day.setVisibility(View.GONE);
            holder.tv_today.setVisibility(View.VISIBLE);
            holder.tv_add_bean.setTextColor(mContext.getResources().getColor(R.color.color_f28146));
        } else {//未签到的天
            holder.tv_today.setVisibility(View.GONE);
            holder.tv_day.setVisibility(View.VISIBLE);
            holder.iv_bean.setBackgroundResource(R.drawable.icon_bean_dark);
            holder.tv_day.setText("第" + day + "天");
            holder.tv_day.setTextColor(mContext.getResources().getColor(R.color.grayred_text_color));
            holder.tv_add_bean.setTextColor(mContext.getResources().getColor(R.color.grayred_text_color));
        }

        holder.tv_add_bean.setText("+" + item.getIntegral());

        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.item_bean_sign, null);
    }
}
