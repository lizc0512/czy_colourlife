package cn.net.cyberway.adpter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.utils.GlideUtils;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.JoinCommunityActivity;
import cn.net.cyberway.model.DataTree;
import cn.net.cyberway.protocol.HomeNearByEntity;
import cn.net.cyberway.protocol.ItemStatus;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.adpter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/10/18 19:08
 * @change
 * @chang time
 * @class describe
 */

public class HomeNearbyCommunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<DataTree<HomeNearByEntity.ContentBean, HomeNearByEntity.ContentBean.ListBean>> dataTrees;

    //向外暴露设置显示数据的方法
    public void setDataTrees(List<DataTree<HomeNearByEntity.ContentBean, HomeNearByEntity.ContentBean.ListBean>> dt) {
        this.dataTrees = dt;
        initGroupItemStatus(groupItemStatus);
        notifyDataSetChanged();
    }

    private List<Boolean> groupItemStatus = new ArrayList<>();

    //设置初始值，所有 groupItem 默认为关闭状态
    private void initGroupItemStatus(List l) {
        for (int i = 0; i < dataTrees.size(); i++) {
            l.add(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItemStatusByPosition(position).getViewType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemStatus itemStatus = getItemStatusByPosition(position);
        final DataTree<HomeNearByEntity.ContentBean, HomeNearByEntity.ContentBean.ListBean> dt = dataTrees.get(itemStatus.getGroupItemIndex());
        if (itemStatus.getViewType() == ItemStatus.VIEW_TYPE_GROUPITEM) {
            final GroupItemViewHolder groupItemVh = (GroupItemViewHolder) holder;
            groupItemVh.tvCommunityTitle.setText(dt.getGroupItem().getDesc());
        } else if (itemStatus.getViewType() == ItemStatus.VIEW_TYPE_SUBITEM) {
            SubItemViewHolder subItemVh = (SubItemViewHolder) holder;
            final HomeNearByEntity.ContentBean.ListBean listBean = dt.getSubItems().get(itemStatus.getSubItemIndex());
            String imageUrl = listBean.getImg();
            final String type = listBean.getType();
            final String communityName = listBean.getName();
            String buildName = listBean.getBuild_name();
            String roomName = listBean.getRoom_name();
            final String communityId = listBean.getCommunity_uuid();
            final String address = listBean.getAddress();
            final String czyId = listBean.getCzy_id();
            final Context mContext = subItemVh.itemView.getContext();
            if (TextUtils.isEmpty(imageUrl)) {
                if (type.equals("1")) {
                    subItemVh.iv_community.setImageResource(R.drawable.home_shequ_on);
                    subItemVh.tv_join.setVisibility(View.GONE);
                    subItemVh.tv_communityname.setTextColor(Color.parseColor("#7caff5"));
                    subItemVh.tv_provincename.setTextColor(Color.parseColor("#aa7caff5"));
                } else if (type.equals("2")) {
                    subItemVh.iv_community.setImageResource(R.drawable.home_shequ_off);
                    subItemVh.tv_join.setVisibility(View.VISIBLE);
                    subItemVh.tv_join.setText(holder.itemView.getResources().getString(R.string.home_enter));
                    subItemVh.tv_communityname.setTextColor(Color.parseColor("#333b46"));
                    subItemVh.tv_provincename.setTextColor(Color.parseColor("#a3aaae"));
                } else {
                    subItemVh.iv_community.setImageResource(R.drawable.home_shequ_off);
                    subItemVh.tv_join.setVisibility(View.VISIBLE);
                    subItemVh.tv_join.setText(holder.itemView.getResources().getString(R.string.home_join));
                    subItemVh.tv_communityname.setTextColor(Color.parseColor("#333b46"));
                    subItemVh.tv_provincename.setTextColor(Color.parseColor("#a3aaae"));
                }
            } else {
                GlideUtils.loadImageView(mContext, imageUrl, subItemVh.iv_community);
            }
            if (TextUtils.isEmpty(buildName)) {
                buildName = "";
            }
            if (TextUtils.isEmpty(roomName)) {
                roomName = "";
            }
            subItemVh.tv_communityname.setText(communityName + " " + buildName + " " + roomName);
            if (!TextUtils.isEmpty(address)) {
                subItemVh.tv_provincename.setText(address);
            } else {
                subItemVh.tv_provincename.setText("");
            }
            final String finalBuildName = buildName;
            final String finalRoomName = roomName;
            subItemVh.tv_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((JoinCommunityActivity) mContext).joinCommunity(communityName, communityId, finalBuildName, finalRoomName, czyId, address, type);
                }
            });
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == ItemStatus.VIEW_TYPE_GROUPITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .community_parent_layout, parent, false);
            viewHolder = new GroupItemViewHolder(v);
        } else if (viewType == ItemStatus.VIEW_TYPE_SUBITEM) {

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .community_item_layout, parent, false);
            viewHolder = new SubItemViewHolder(v);
        }

        return viewHolder;
    }


    private ItemStatus getItemStatusByPosition(int position) {

        ItemStatus itemStatus = new ItemStatus();

        int count = 0;    //计算groupItemIndex = i 时，position最大值
        int i = 0;

        //轮询 groupItem 的开关状态
        for (i = 0; i < groupItemStatus.size(); i++) {
            //pos刚好等于计数时，item为groupItem
            if (count == position) {
                itemStatus.setViewType(ItemStatus.VIEW_TYPE_GROUPITEM);
                itemStatus.setGroupItemIndex(i);
                break;
                //pos大于计数时，item为groupItem(i - 1)中的某个subItem
            } else if (count > position) {
                itemStatus.setViewType(ItemStatus.VIEW_TYPE_SUBITEM);
                itemStatus.setGroupItemIndex(i - 1);
                itemStatus.setSubItemIndex(position - (count - dataTrees.get(i - 1).getSubItems().size()));
                break;
            }
            //无论groupItem状态是开或者关，它在列表中都会存在，所有count++
            count++;

            //当轮询到的groupItem的状态为“开”的话，count需要加上该groupItem下面的子项目数目
            if (groupItemStatus.get(i)) {

                count += dataTrees.get(i).getSubItems().size();

            }


        }

        //简单地处理当轮询到最后一项groupItem的时候
        if (i >= groupItemStatus.size()) {
            itemStatus.setGroupItemIndex(i - 1);
            itemStatus.setViewType(ItemStatus.VIEW_TYPE_SUBITEM);
            itemStatus.setSubItemIndex(position - (count - dataTrees.get(i - 1).getSubItems().size()));
        }

        return itemStatus;
    }

    public static class GroupItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvCommunityTitle;

        public GroupItemViewHolder(View itemView) {
            super(itemView);
            tvCommunityTitle = (TextView) itemView.findViewById(R.id.tv_community_title);
        }
    }

    public static class SubItemViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_community;
        TextView tv_communityname;
        TextView tv_provincename;
        TextView tv_join;

        public SubItemViewHolder(View itemView) {
            super(itemView);
            iv_community = (ImageView) itemView.findViewById(R.id.iv_community);
            tv_communityname = (TextView) itemView.findViewById(R.id.tv_communityname);
            tv_provincename = (TextView) itemView.findViewById(R.id.tv_provincename);
            tv_join = (TextView) itemView.findViewById(R.id.tv_join);
        }
    }

    @Override
    public int getItemCount() {


        int itemCount = 0;

        if (groupItemStatus.size() == 0) {
            return 0;
        }

        for (int i = 0; i < dataTrees.size(); i++) {

            if (groupItemStatus.get(i)) {
                itemCount += dataTrees.get(i).getSubItems().size() + 1;
            } else {
                itemCount++;
            }

        }

        return itemCount;
    }

}
