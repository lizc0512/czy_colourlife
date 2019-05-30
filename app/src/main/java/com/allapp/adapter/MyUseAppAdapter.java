package com.allapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.csh.colourful.life.utils.GlideUtils;
import cn.net.cyberway.R;
import cn.net.cyberway.protocol.HomeLifeEntity;

/**
 * @name ${yuansk}
 * @class name：com.allapp.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/1/15 16:13
 * @change
 * @chang time
 * @class describe
 */
public class MyUseAppAdapter extends BaseQuickAdapter<HomeLifeEntity.ContentBean.ListBean, BaseViewHolder> {

    private Context context;


    public MyUseAppAdapter(Context context, int layoutResId, @Nullable List<HomeLifeEntity.ContentBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeLifeEntity.ContentBean.ListBean item) {
        helper.setText(R.id.tv_app_name, item.getName());
        GlideUtils.loadImageView(context, item.getImg(), (ImageView) helper.getView(R.id.iv_app_logo));
        helper.itemView.setBackgroundResource(R.color.white);
        helper.setVisible(R.id.iv_app_operate, false);
    }
}
