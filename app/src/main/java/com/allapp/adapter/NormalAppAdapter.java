package com.allapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
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
public class NormalAppAdapter extends BaseItemDraggableAdapter<HomeLifeEntity.ContentBean.ListBean, BaseViewHolder> {

    private Context context;
    private int is_edit;

    public NormalAppAdapter(Context context, int layoutResId, @Nullable List<HomeLifeEntity.ContentBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    public void setIs_edit(int is_edit) {
        this.is_edit = is_edit;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeLifeEntity.ContentBean.ListBean item) {
        helper.setText(R.id.tv_app_name, item.getName());
        GlideUtils.loadImageView(context, item.getImg(), (ImageView) helper.getView(R.id.iv_app_logo));
        if (is_edit == 0) {
            helper.itemView.setBackgroundResource(R.color.white);
            helper.setVisible(R.id.iv_app_operate, false)
                    .addOnClickListener(R.id.iv_app_operate);;
        } else {
            helper.itemView.setBackgroundResource(R.color.bg_color);
            helper.setVisible(R.id.iv_app_operate, true).
            setImageResource(R.id.iv_app_operate, R.mipmap.reduce_single_app)
                    .addOnClickListener(R.id.iv_app_operate);;
        }
    }
}
