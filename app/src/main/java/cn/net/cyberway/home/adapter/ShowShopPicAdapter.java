package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BeeFramework.Utils.Utils;
import com.nohttp.utils.GridSpacingItemDecoration;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.home.entity.PersonalRecommendedEntity;

/**
 * 饭票商城的二级子菜单
 */
public class ShowShopPicAdapter extends RecyclerView.Adapter<ShowShopPicAdapter.DefaultViewHolder> {
    private Context mContext;
    private List<PersonalRecommendedEntity.ContentBean.ItemsBean> itemsBeanList;
    private String msgId;


    public ShowShopPicAdapter(Context context, List<PersonalRecommendedEntity.ContentBean.ItemsBean> itemsBeanList,String msgId) {
        this.mContext = context;
        this.itemsBeanList = itemsBeanList;
        this.msgId=msgId;
    }

    @Override
    public DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ShowShopPicAdapter.DefaultViewHolder viewHolder = new ShowShopPicAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_rv, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        PersonalRecommendedEntity.ContentBean.ItemsBean itemsBean = itemsBeanList.get(position);
        if (null != itemsBean) {
            final List<PersonalRecommendedEntity.ContentBean.ItemsBean.DataBean> itemsBeanData = itemsBean.getData();
            SecondShopPicAdapter secondShopPicAdapter = new SecondShopPicAdapter(mContext, itemsBeanData,msgId);
            int spanCount = itemsBeanData.size();
            holder.rv_item_pic.setLayoutManager(new GridLayoutManager(mContext, spanCount, GridLayoutManager.VERTICAL, false));
            holder.rv_item_pic.setNestedScrollingEnabled(false);
            holder.rv_item_pic.addItemDecoration(new GridSpacingItemDecoration(spanCount, Utils.dip2px(mContext, 0.5f), false));
            holder.rv_item_pic.setAdapter(secondShopPicAdapter);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return itemsBeanList == null ? 0 : itemsBeanList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv_item_pic;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            rv_item_pic = (RecyclerView) itemView.findViewById(R.id.rv_item_pic);
        }
    }
}
