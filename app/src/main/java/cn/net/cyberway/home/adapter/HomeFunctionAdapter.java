package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nohttp.utils.GlideImageLoader;

import java.util.ArrayList;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.home.entity.HomeFuncEntity;

import static com.BeeFramework.Utils.Utils.getSpannable;

/**
 *
 */
public class HomeFunctionAdapter extends RecyclerView.Adapter<HomeFunctionAdapter.DefaultViewHolder> {
    private Context mContext;
    private ArrayList<HomeFuncEntity.ContentBean> funcDataBeanList;
    private OnItemClickListener onClickListener;

    public HomeFunctionAdapter(Context context, ArrayList<HomeFuncEntity.ContentBean> funcDataBeanList) {
        this.mContext = context;
        this.funcDataBeanList = funcDataBeanList;
    }

    @Override
    public HomeFunctionAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HomeFunctionAdapter.DefaultViewHolder viewHolder = new HomeFunctionAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_function_item, parent, false));
        viewHolder.onClickListener = onClickListener;
        return viewHolder;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @Override
    public void onBindViewHolder(final HomeFunctionAdapter.DefaultViewHolder holder, int position) {
        final HomeFuncEntity.ContentBean dataBean = funcDataBeanList.get(position);
        String imgUrl = dataBean.getSuperscript();
        String image = dataBean.getImg();
        GlideImageLoader.loadImageDisplay(mContext, imgUrl, holder.iv_function_bg);
        GlideImageLoader.loadImageDefaultDisplay(mContext, image, holder.iv_function, R.drawable.home_default_icon, R.drawable.home_default_icon);
        holder.tv_function_name.setText(dataBean.getName());
        holder.tv_function_desc.setText(getSpannable(dataBean.getDesc()));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return funcDataBeanList == null ? 0 : funcDataBeanList.size();
    }


    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_function_bg;
        ImageView iv_function;
        TextView tv_function_name;
        TextView tv_function_desc;
        OnItemClickListener onClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            iv_function_bg = itemView.findViewById(R.id.iv_function_bg);
            iv_function = itemView.findViewById(R.id.iv_function);
            tv_function_name = itemView.findViewById(R.id.tv_function_name);
            tv_function_desc = itemView.findViewById(R.id.tv_function_desc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
