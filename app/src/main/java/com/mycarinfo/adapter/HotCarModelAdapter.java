package com.mycarinfo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.BeeFrameworkApp;
import com.BeeFramework.adapter.BeeBaseAdapter;
import com.mycarinfo.protocol.COLOURTICKETHOTCARINFOLIST;
import com.nohttp.utils.GlideImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 热门车型信息列表适配器
 * Created by HX_CHEN on 2017/3/28.
 * TIME: ${YESR}${MONTY} 28 1500
 */

public class HotCarModelAdapter extends BeeBaseAdapter {
    public int num = -1;
    private Context mContext;

    public HotCarModelAdapter(Context context, List<COLOURTICKETHOTCARINFOLIST> list) {
        super(context, list);
        mContext = context;

    }

    public class ViewHolder extends BeeCellHolder {
        public TextView id;//ID
        public TextView name;//
        public TextView total;//
        public ImageView img_path;//
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        ViewHolder holder = new ViewHolder();
        holder.img_path = (ImageView) cellView.findViewById(R.id.iv_hotcar_logo);
        holder.name = (TextView) cellView.findViewById(R.id.tv_hotcar_name);
        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        ViewHolder holder = (ViewHolder) h;
        COLOURTICKETHOTCARINFOLIST data = (COLOURTICKETHOTCARINFOLIST) dataList.get(position);
        String tvUser_name = data.name;
        String img_path = data.img_path;
        holder.name.setText(tvUser_name);
        if (!TextUtils.isEmpty(img_path)) {
            GlideImageLoader.loadImageDisplay(mContext, img_path, holder.img_path);
        }
        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.gridview_item_hotcar, null);
    }
}
