package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.door.activity.DoorApplyActivity;
import com.door.activity.IntelligenceDoorActivity;
import com.door.activity.NewDoorIndetifyActivity;
import com.door.entity.SingleCommunityEntity;

import java.util.ArrayList;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
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
        TextView tv_door_name = doorView.findViewById(R.id.tv_door_name);
        doorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    String doorQrcode = commonUseBeansBeanList.get(position).getQr_code();
                    if (TextUtils.isEmpty(doorQrcode)) {
//                        Intent intent = new Intent(mContext, DoorApplyActivity.class);
                        Intent intent = new Intent(mContext, NewDoorIndetifyActivity.class);
                        mContext.startActivity(intent);
                    } else {
                        ((MainActivity) mContext).openDoor(doorQrcode);
                    }
                } else {
                    LinkParseUtil.parse(mContext, "", "");
                }
            }
        });
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
