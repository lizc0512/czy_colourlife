package com.door.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.door.entity.DoorCommunityEntity;

import java.util.List;

import cn.net.cyberway.R;


public class SelectCommunityAdapter extends BeeBaseAdapter {

    private int whichCommunitySel = -1;

    public SelectCommunityAdapter(Context context, List<DoorCommunityEntity.ContentBean.CommunitylistBean> data,
                                  int whitch) {
        super(context, data);
        this.whichCommunitySel = whitch;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        HolderViewStatic viewContent = new HolderViewStatic();
        viewContent.textView = (TextView) cellView
                .findViewById(R.id.textview);
        viewContent.iv_tag = (ImageView) cellView
                .findViewById(R.id.iv_tag);
        return (BeeCellHolder) viewContent;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        DoorCommunityEntity.ContentBean.CommunitylistBean communityResp = (DoorCommunityEntity.ContentBean.CommunitylistBean) dataList.get(position);
        HolderViewStatic holder = (HolderViewStatic) h;
        if (whichCommunitySel == position) {
            holder.textView.setSelected(true);
            holder.iv_tag.setSelected(true);
        } else {
            holder.textView.setSelected(false);
            holder.iv_tag.setSelected(false);
        }

        if (communityResp != null) {

            holder.textView.setText(communityResp.getName());
        }

        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.select_community_list_item, null);
    }

    public class HolderViewStatic extends BeeCellHolder {
        TextView textView;
        ImageView iv_tag;
    }

    public void setWhichCommunitySel(int whichCommunitySel) {
        this.whichCommunitySel = whichCommunitySel;
    }

}
