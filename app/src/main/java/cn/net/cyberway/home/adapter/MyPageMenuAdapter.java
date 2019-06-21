package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.home.entity.HomeBottomAdviseEntity;

/**
 * 我的页面上面的菜单
 */
public class MyPageMenuAdapter extends RecyclerView.Adapter<MyPageMenuAdapter.DefaultViewHolder> {

    private List<HomeBottomAdviseEntity.ContentBean> contentBeanList;
    private Context mContext;
    private OnItemClickListener onClickListener;

    public MyPageMenuAdapter(Context context, List<HomeBottomAdviseEntity.ContentBean> contentBeanList) {
        this.mContext = context;
        this.contentBeanList = contentBeanList;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public MyPageMenuAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyPageMenuAdapter.DefaultViewHolder viewHolder = new MyPageMenuAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_menu_item, parent, false));
        viewHolder.onClickListener = onClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyPageMenuAdapter.DefaultViewHolder holder, int position) {
        final HomeBottomAdviseEntity.ContentBean contentBean = contentBeanList.get(position);
        GlideImageLoader.loadImageDefaultDisplay(mContext, contentBean.getImg_url(), holder.iv_mypage_menu, R.drawable.icon_style_four, R.drawable.icon_style_four);
        holder.tv_mypage_menu.setText(contentBean.getName());
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return contentBeanList == null ? 0 : contentBeanList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_mypage_menu;
        TextView tv_mypage_menu;
        OnItemClickListener onClickListener;


        public DefaultViewHolder(View itemView) {
            super(itemView);
            iv_mypage_menu = itemView.findViewById(R.id.iv_mypage_menu);
            tv_mypage_menu = itemView.findViewById(R.id.tv_mypage_menu);
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
