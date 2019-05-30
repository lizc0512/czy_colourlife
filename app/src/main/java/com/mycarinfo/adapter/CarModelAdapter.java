package com.mycarinfo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.mycarinfo.protocol.COLOURTICKETCARMODELINFOLIST;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 品牌N个车型信息适配器
 * Created by HX_CHEN on 2017/3/28.
 * TIME: ${YESR}${MONTY} 28 1500
 */

public class CarModelAdapter extends BeeBaseAdapter {
    public int num = -1;
    public CarModelAdapter(Context context, List<COLOURTICKETCARMODELINFOLIST>list){
        super(context, list);

    }
    public class ViewHolder extends BeeCellHolder {
        public TextView id;//车辆ID
        public TextView name;//

    }
    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        ViewHolder holder=new ViewHolder();
        holder.name= (TextView) cellView.findViewById(R.id.tv_carmodel_name);
        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        ViewHolder holder = (ViewHolder) h;
        COLOURTICKETCARMODELINFOLIST data= (COLOURTICKETCARMODELINFOLIST) dataList.get(position);
        String name=data.name;
        holder.name.setText(name);
        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.list_item_carmodel,null);
    }
}
