package com.eparking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eparking.protocol.HomeFunctionListEntity;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/22 10:39
 * @change
 * @chang time
 * @class describe    弹出的popwindow的adapter
 */

public class ParkingMenuAdapter extends BaseAdapter {

    private Context mContext;
    private List<HomeFunctionListEntity.ContentBean> funcMenuList;

    public ParkingMenuAdapter(Context context, List<HomeFunctionListEntity.ContentBean> funcMenuList) {
        super();
        mContext = context;
        this.funcMenuList = funcMenuList;
    }

    @Override
    public int getCount() {
        return funcMenuList == null ? 0 : funcMenuList.size();
    }

    @Override
    public Object getItem(int position) {
        return funcMenuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_pop_item, null);
        }
        ImageView iv_pop_logo = convertView.findViewById(R.id.iv_pop_logo);
        TextView tv_pop_name = convertView.findViewById(R.id.tv_pop_name);
        HomeFunctionListEntity.ContentBean contentBean = funcMenuList.get(position);
        GlideImageLoader.loadImageDefaultDisplay(mContext, contentBean.getIcon(), iv_pop_logo,
                R.drawable.eparking_img_down_kaizha, R.drawable.eparking_img_down_kaizha);
        tv_pop_name.setText(contentBean.getName());
        return convertView;
    }
}
