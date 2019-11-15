package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
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
        ImageView iv_door = doorView.findViewById(R.id.iv_door);
        String doorQrcode = commonUseBean.getQr_code();
        String doorId = commonUseBean.getDoor_id();
        if (TextUtils.isEmpty(doorId)) {
            iv_door.setImageResource(R.drawable.home_key);
        } else {
            if (!TextUtils.isEmpty(doorQrcode)) {  //远程门禁
                iv_door.setImageResource(R.drawable.home_key);
            } else {
                iv_door.setImageResource(R.drawable.home_bluetooth);
            }
        }
        doorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    SingleCommunityEntity.ContentBean.CommonUseBean commonUseBean = commonUseBeansBeanList.get(position);

                    if (TextUtils.isEmpty(doorId)) {  //申请门禁
//                        Intent intent = new Intent(mContext, DoorApplyActivity.class);
                        Intent intent = new Intent(mContext, NewDoorIndetifyActivity.class);
                        mContext.startActivity(intent);
                    } else {
                        if (!TextUtils.isEmpty(doorQrcode)) {  //远程门禁
                            ((MainActivity) mContext).openDoor(doorQrcode);
                        } else {
                            ToastUtil.toastShow(mContext, "打开蓝牙靠近门禁设备即可感应开门哦～");
                        }
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
