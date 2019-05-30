package com.myproperty.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.mycarinfo.protocol.COLOURTICKETPROVINCEINFOLIST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.net.cyberway.R;

/**
 * 省份适配器
 * Created by HX_CHEN on 2017/3/28.
 * TIME: ${YESR}${MONTY} 28 1500
 */

public class ProvinceAdapter extends BeeBaseAdapter {
    public int num = -1;
    private Context context;
    public ProvinceAdapter(Context context, List<COLOURTICKETPROVINCEINFOLIST> list) {
        super(context, list);
        this.context = context;

    }

    public class ViewHolder extends BeeCellHolder {
        public TextView id;//
        public TextView short_name;//
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        ViewHolder holder = new ViewHolder();
        holder.short_name = (TextView) cellView.findViewById(R.id.tv_short_name);
        return holder;
    }

    @Override
    protected View bindData(final int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        final ViewHolder holder = (ViewHolder) h;
        COLOURTICKETPROVINCEINFOLIST data = (COLOURTICKETPROVINCEINFOLIST) dataList.get(position);
        String id = String.valueOf(data.id);//
        String short_name = data.short_name;//
        holder.short_name.setText(short_name);

        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.list_item_province, null);
    }
}
