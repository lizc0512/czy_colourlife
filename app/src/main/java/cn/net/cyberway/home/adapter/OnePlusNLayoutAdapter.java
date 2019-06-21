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
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/6/18 15:20
 * @change
 * @chang time
 * @class describe
 */
public class OnePlusNLayoutAdapter extends DelegateAdapter.Adapter {
    public Context context;
    private LayoutHelper helper;
    private LayoutInflater inflater;
    private List<HomeBottomAdviseEntity.ContentBean> contentBeanList;

    public OnePlusNLayoutAdapter(Context context, LayoutHelper helper, List<HomeBottomAdviseEntity.ContentBean> contentBeanList) {
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
        MyViewHolder myViewHolder = (MyViewHolder) holder;
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
        return contentBeanList.size() >= 3 ? 3 : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_activity);
        }
    }
}
