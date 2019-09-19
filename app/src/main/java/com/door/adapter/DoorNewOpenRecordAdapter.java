package com.door.adapter;

import android.content.Context;
import android.graphics.Color;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.door.entity.DoorOpenRecordEntity;
import com.door.entity.DoorOpenRecordSectionEntity;

import java.util.List;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.adpter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/1/14 15:13
 * @change
 * @chang time
 * @class describe
 */
public class DoorNewOpenRecordAdapter extends BaseSectionQuickAdapter<DoorOpenRecordSectionEntity, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    private Context context;


    public DoorNewOpenRecordAdapter(Context context, int layoutResId, int sectionHeadResId, List<DoorOpenRecordSectionEntity> data) {
        super(layoutResId, sectionHeadResId, data);
        this.context = context;
    }


    @Override
    protected void convertHead(BaseViewHolder helper, DoorOpenRecordSectionEntity item) {
        helper.setText(R.id.head_name, item.header);
        helper.setBackgroundColor(R.id.head_name, Color.parseColor("#f5f5f5"));
    }

    @Override
    protected void convert(BaseViewHolder helper, DoorOpenRecordSectionEntity item) {
        DoorOpenRecordEntity.ContentBean.DataBeanX.DataBean listBean = item.t;
        String status = listBean.getStatus();
        if ("1".equals(status)) {
            helper.setText(R.id.tv_door_status, "开门成功");
        } else {
            helper.setText(R.id.tv_door_status, "开门失败");
        }
        helper.setText(R.id.tv_door_name, listBean.getDoor_name());
        helper.setText(R.id.tv_door_time, listBean.getTime());
    }
}
