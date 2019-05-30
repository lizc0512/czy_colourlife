package com.mycarinfo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.mycarinfo.activity.ColorView;
import com.mycarinfo.protocol.COLOURTICKETCARCOLOURINFOLIST;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 汽车颜色适配器
 * Created by HX_CHEN on 2017/3/28.
 * TIME: ${YESR}${MONTY} 28 1500
 */

public class CarColurAdapter extends BeeBaseAdapter {
    public int num = -1;
    private  int checkId;
    private ColorView colorView = new ColorView(mContext);        //  创建CircleCanvas（画布类）对象;
    private List<Integer> list;

    public CarColurAdapter(Context context, List<COLOURTICKETCARCOLOURINFOLIST>list){
        super(context, list);

    }
    public class ViewHolder extends BeeCellHolder {
        public TextView id;//ID
        public TextView colour;//
        public TextView colour_rgb;//
        public ColorView colorView;

    }
    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        ViewHolder holder=new ViewHolder();
        holder.colour= (TextView) cellView.findViewById(R.id.tv_carcolourtext);//黑色
        holder.colorView= (ColorView) cellView.findViewById(R.id.tv_carcolour);//自定义view
        return holder;
    }
    public static int cc;
    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        ViewHolder holder = (ViewHolder) h;
        COLOURTICKETCARCOLOURINFOLIST data= (COLOURTICKETCARCOLOURINFOLIST) dataList.get(position);
        String colour=data.colour;
        String colour_rgb=data.colour_rgb;
        holder.colour.setText(colour);
        holder.colorView.setColor(Color.parseColor(colour_rgb));
        return cellView;
    }

    public void setCheckId(int checkId){
        this.checkId=checkId;
        notifyDataSetChanged();
    }
    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.list_item_carcolour,null);
    }
}
