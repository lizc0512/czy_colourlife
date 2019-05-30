package com.mycarinfo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.mycarinfo.protocol.COLOURTICKETMYCARINFOLIST;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 我的车辆信息列表适配器
 * Created by HX_CHEN on 2017/3/28.
 * TIME: ${YESR}${MONTY} 28 1500
 */

public class MyCarListAdapter extends BeeBaseAdapter {
    public int num = -1;
    public MyCarListAdapter(Context context, List<COLOURTICKETMYCARINFOLIST>list){
        super(context, list);

    }
    public class ViewHolder extends BeeCellHolder {
        public TextView id;//车辆ID
        public TextView customer_id;//用户ID
        public TextView plate_number;//车牌号
        public TextView name;//（姓名）
        public TextView mobile;//手机号
        public TextView vehicle_type_id;//
        public TextView identity_id;//
        public TextView mealticket_id;//
        public TextView bind_mealticket_state;//（绑定饭票状态：0未绑定，1已绑定）
        public TextView state;//
        public TextView create_time;//
        public TextView tv_mycar_bindfp;//
        public ImageView iv_mycar_bindfp;//
    }
    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        ViewHolder holder=new ViewHolder();
        holder.name= (TextView) cellView.findViewById(R.id.tv_userName);
        holder.plate_number= (TextView) cellView.findViewById(R.id.tv_carNumber);
        holder.mobile= (TextView) cellView.findViewById(R.id.tv_userPhone);
        holder.tv_mycar_bindfp= (TextView) cellView.findViewById(R.id.tv_mycar_bindfp);
        holder.iv_mycar_bindfp= (ImageView) cellView.findViewById(R.id.iv_mycar_bindfp);
        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        ViewHolder holder = (ViewHolder) h;
        COLOURTICKETMYCARINFOLIST data= (COLOURTICKETMYCARINFOLIST) dataList.get(position);
        String tvUser_name=data.name;
        String tvCar_number=data.plate_number;
        String tvMobile=data.mobile;
        int bind_mealticket_state=data.bind_mealticket_state;
        if(bind_mealticket_state==1){//已经绑定饭票
            holder.iv_mycar_bindfp.setVisibility(View.GONE);
            holder.tv_mycar_bindfp.setText(cellView.getResources().getString(R.string.car_bindticket_success));
        }else if (bind_mealticket_state==0){//未绑定

        }
        holder.name.setText(tvUser_name);
        holder.plate_number.setText(tvCar_number);
        holder.mobile.setText(tvMobile);
        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.list_item_mycar,null);
    }
}
