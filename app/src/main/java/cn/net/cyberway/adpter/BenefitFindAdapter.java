package cn.net.cyberway.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.protocol.BenefitFindEntity;

/**
 * 彩惠人生-发现
 * Created by hxg on 2019/07/15.
 */
public class BenefitFindAdapter extends RecyclerView.Adapter<BenefitFindAdapter.ViewHolder> {

    private List<BenefitFindEntity.ContentBean.ListBean> mList;
    private Context mContext;
    private OnItemClickListener onClickListener;
    public int total;

    public BenefitFindAdapter(Context mContext, List<BenefitFindEntity.ContentBean.ListBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public BenefitFindAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_benefit_find, parent, false);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(BenefitFindAdapter.ViewHolder holder, int position) {
        final BenefitFindEntity.ContentBean.ListBean item = mList.get(position);
        holder.tv_title.setText(item.getTitle());
        holder.tv_time.setText(item.getDate());

        GlideImageLoader.loadImageDefaultDisplay(mContext, item.getImage(), holder.iv_img, R.drawable.default_image, R.drawable.default_image);

        if (position == total - 1) {
            holder.ll_end.setVisibility(View.VISIBLE);
            holder.v_line.setVisibility(View.GONE);
        } else {
            holder.v_line.setVisibility(View.VISIBLE);
            holder.ll_end.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_title;
        private TextView tv_time;
        private ImageView iv_img;
        private View v_line;
        private LinearLayout ll_end;
        private RelativeLayout rl_item;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
            iv_img = itemView.findViewById(R.id.iv_img);
            v_line = itemView.findViewById(R.id.v_line);
            ll_end = itemView.findViewById(R.id.ll_end);
            rl_item = itemView.findViewById(R.id.rl_item);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
