package com.door.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.allapp.entity.HomeAllLifeEntity;
import com.allapp.entity.WholeAppSectionEntity;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.csh.colourful.life.utils.GlideUtils;
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
public class DoorNewOpenRecordAdapter extends BaseSectionQuickAdapter<WholeAppSectionEntity, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    private Context context;


    public DoorNewOpenRecordAdapter(Context context, int layoutResId, int sectionHeadResId, List<WholeAppSectionEntity> data) {
        super(layoutResId, sectionHeadResId, data);
        this.context = context;
    }


    @Override
    protected void convertHead(BaseViewHolder helper, WholeAppSectionEntity item) {
        helper.setText(R.id.head_name, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, WholeAppSectionEntity item) {
        HomeAllLifeEntity.ContentBean.DataBean.ListBean listBean = item.t;
        helper.setText(R.id.tv_app_name, listBean.getName());
        GlideUtils.loadImageView(context, listBean.getImg(), (ImageView) helper.getView(R.id.iv_app_logo));
        helper.itemView.setBackgroundResource(R.color.white);
        helper.setVisible(R.id.iv_app_operate, false);
    }
}
