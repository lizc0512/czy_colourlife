package com.mycarinfo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.mycarinfo.activity.BindFPActivity;
import com.myproperty.protocol.CTGLORYTICKETMYTICKETLISTDATA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.net.cyberway.R;

/**
 * 车辆绑定饭票信息列表适配器
 * Created by HX_CHEN on 2017/3/28.
 * TIME: ${YESR}${MONTY} 28 1500
 */

public class BindFpAdapter extends BeeBaseAdapter {
    public int num = -1;
    private Context context;
    private  String checkId;

    public BindFpAdapter(Context context, List<CTGLORYTICKETMYTICKETLISTDATA> list) {
        super(context, list);
        this.context = context;

    }

    public class ViewHolder extends BeeCellHolder {
        public TextView name;//（名称）
        public TextView user_amount;//余额
        public ImageView imageView;//选择按钮
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        ViewHolder holder = new ViewHolder();
        holder.name = (TextView) cellView.findViewById(R.id.tv_localfp_name);
        holder.user_amount = (TextView) cellView.findViewById(R.id.tv_localfp_balance);
        holder.imageView = (ImageView) cellView.findViewById(R.id.ib_localfp_select);
        return holder;
    }

    @Override
    protected View bindData(final int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        final ViewHolder holder = (ViewHolder) h;
        CTGLORYTICKETMYTICKETLISTDATA data = (CTGLORYTICKETMYTICKETLISTDATA) dataList.get(position);
        String name = data.name;//
        String user_amount=data.balance;
        holder.name.setText(name);//名字
        holder.user_amount.setText(user_amount);//余额
        final String pano=data.pano;
        if (pano.equals(checkId)) {
            holder.imageView.setSelected(true);
            BindFPActivity.i=position;
        }else {
            holder.imageView.setSelected(false);
        }
        return cellView;
    }

    public void setCheckId(String checkId){
        this.checkId=checkId;
        notifyDataSetChanged();
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.list_item_bindfp, null);
    }
}
