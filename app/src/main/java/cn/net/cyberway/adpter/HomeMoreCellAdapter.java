package cn.net.cyberway.adpter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.BeeFramework.adapter.BeeBaseAdapter;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.protocol.ATTR;
import cn.net.cyberway.protocol.HomeLifeEntity;
import cn.net.cyberway.view.HomeMoreCellItem;

/**
 * 生活页面 每一个gridview 适配器
 */
public class HomeMoreCellAdapter extends BeeBaseAdapter {


    public HomeMoreCellAdapter(Context c, List dataList) {
        super(c, dataList);
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        return null;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 10000;
    }

    public int getItemViewType(int position) {
        return position % 10000;
    }

    @Override
    public View createCellView() {
        return null;
    }

    public View getView(int position, View cellView, ViewGroup parent) {
        BeeCellHolder holder = null;
        if (cellView == null) {
            cellView = mInflater.inflate(R.layout.home_more_cell_item, null);
            holder = new BeeCellHolder();
            cellView.setTag(holder);
        } else {
            holder = (BeeCellHolder) cellView.getTag();
        }
        if (null != holder) {
            holder.position = position;
        }
        HomeLifeEntity.ContentBean.ListBean queryCls = (HomeLifeEntity.ContentBean.ListBean) dataList.get(position);
        HomeMoreCellItem itemCell = (HomeMoreCellItem) cellView;
        itemCell.bindData(queryCls);
        return cellView;
    }
}
