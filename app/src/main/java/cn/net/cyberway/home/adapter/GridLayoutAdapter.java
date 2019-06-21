package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.home.entity.HomeBottomAdviseEntity;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * Created by admin on 2017/5/18.
 */

public class GridLayoutAdapter extends DelegateAdapter.Adapter {
    public Context context;
    private LayoutHelper helper;
    private LayoutInflater inflater;
    private List<HomeBottomAdviseEntity.ContentBean> contentBeanList;

    public GridLayoutAdapter(Context context, LayoutHelper helper, List<HomeBottomAdviseEntity.ContentBean> contentBeanList) {
        this.inflater = LayoutInflater.from(context);
        this.helper = helper;
        this.context = context;
        this.contentBeanList = contentBeanList;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return this.helper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.adapter_activity_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GridLayoutAdapter.MyViewHolder myViewHolder = (GridLayoutAdapter.MyViewHolder) holder;
        if (contentBeanList.size() > 0) {
            HomeBottomAdviseEntity.ContentBean contentBean = contentBeanList.get(position);
            GlideImageLoader.loadImageDisplay(context, contentBean.getImg_url(), myViewHolder.imageView);
            myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinkParseUtil.parse(context, contentBean.getUrl(), "");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return contentBeanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_activity);
        }
    }
}
