package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.door.activity.DoorApplyActivity;
import com.door.entity.SingleCommunityEntity;

import java.util.ArrayList;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.BuryingPointUtils;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 *
 */
public class HomeDoorAdapter extends PagerAdapter {
    private boolean isMultiScr;
    private ArrayList<SingleCommunityEntity.ContentBean.CommonUseBean> commonUseBeansBeanList;
    private boolean isLogin;


    public HomeDoorAdapter(ArrayList<SingleCommunityEntity.ContentBean.CommonUseBean> commonUseBeansBeanList, boolean isMultiScr, boolean isLogin) {
        this.commonUseBeansBeanList = commonUseBeansBeanList;
        this.isMultiScr = isMultiScr;
        this.isLogin = isLogin;
    }

    @Override
    public int getCount() {
        return commonUseBeansBeanList == null ? 0 : commonUseBeansBeanList.size();
    }

    public void setDataList(ArrayList<SingleCommunityEntity.ContentBean.CommonUseBean> commonUseBeansBeanList) {
        this.commonUseBeansBeanList = commonUseBeansBeanList;
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        final SingleCommunityEntity.ContentBean.CommonUseBean commonUseBean = commonUseBeansBeanList.get(position);
        final Context mContext = container.getContext();
        View doorView = LayoutInflater.from(mContext).inflate(R.layout.adapter_usedoor_item, null);
        ImageView iv_door = doorView.findViewById(R.id.iv_door);
        TextView tv_door_name = doorView.findViewById(R.id.tv_door_name);
        int totalSize = commonUseBeansBeanList.size();
        if (totalSize == 1) {
            doorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isLogin) {
                        Intent intent = new Intent(mContext, DoorApplyActivity.class);
                        mContext.startActivity(intent);
                    } else {
                        LinkParseUtil.parse(mContext, "", "");
                    }
                }
            });
            doorView.setEnabled(true);
        } else {
            if (position == totalSize - 1) {
                doorView.setEnabled(true);
                iv_door.setVisibility(View.GONE);
                doorView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BuryingPointUtils.uploadClickMethod(mContext, BuryingPointUtils.homePageName, BuryingPointUtils.homeDoorCode, "门禁", "10401");
                        LinkParseUtil.parse(mContext, "colourlife://proto?type=EntranceGuard", "");
                    }
                });
            } else {
                doorView.setEnabled(false);
            }
        }
        String doorName = commonUseBean.getDoor_name();
        int length = doorName.length();
        if (length <= 6) {
            tv_door_name.setTextSize(16.0f);
        } else if (length <= 8) {
            tv_door_name.setTextSize(14.0f);
        } else {
            tv_door_name.setTextSize(12.0f);
        }
        tv_door_name.setText(commonUseBean.getDoor_name());
        container.addView(doorView);
        return doorView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
