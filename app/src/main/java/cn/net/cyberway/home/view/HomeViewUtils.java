package cn.net.cyberway.home.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.door.entity.SingleCommunityEntity;
import com.nohttp.utils.GlideImageLoader;
import com.youmai.hxsdk.HuxinSdkManager;
import com.youmai.hxsdk.ServiceInfo;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.home.adapter.GridLayoutAdapter;
import cn.net.cyberway.home.adapter.OnePlusNLayoutAdapter;
import cn.net.cyberway.home.entity.HomeBottomAdviseEntity;
import cn.net.cyberway.home.entity.HomeManagerEntity;
import q.rorbin.badgeview.QBadgeView;

import static com.youmai.hxsdk.utils.DisplayUtil.getStatusBarHeight;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.view
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/12/14 16:40
 * @change
 * @chang time
 * @class describe
 */
public class HomeViewUtils {

    public static void smoothScrollTop(RecyclerView home_rv) {
        if (!home_rv.canScrollVertically(-1)) { //不在顶部滑动到顶部
            moveToPosition(home_rv, 0);
        }
    }

    public static void moveToPosition(RecyclerView mRecyclerView, int n) {
        LinearLayoutManager manager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }
    }

    public static int getScollYDistance(LinearLayoutManager layoutManager) {
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        int moveDistance = (position) * itemHeight - firstVisiableChildView.getTop();
        return moveDistance;
    }

    public static void setBadgeCommonPro(Context mContext, QBadgeView badgeView, View tageView) {
        badgeView.bindTarget(tageView);
        badgeView.setBadgeGravity(Gravity.END | Gravity.TOP);
        badgeView.setBadgeTextSize(6f, true);
        badgeView.setBadgeBackgroundColor(ContextCompat.getColor(mContext, R.color.hx_color_red_tag));
        badgeView.setShowShadow(false);
        badgeView.setVisibility(View.VISIBLE);
    }

    /***用户没有常用门禁 但是有非常用门禁**/
    public static ArrayList<SingleCommunityEntity.ContentBean.CommonUseBean> addCommmonDoorList(SingleCommunityEntity.ContentBean contentBean) {
        ArrayList<SingleCommunityEntity.ContentBean.CommonUseBean> commonUseBeanList = new ArrayList<>();
        if (contentBean.getNot_common_use() != null) {
            List<SingleCommunityEntity.ContentBean.NotCommonUseBean> notCommonUseBeanList = contentBean.getNot_common_use();
            for (SingleCommunityEntity.ContentBean.NotCommonUseBean notCommonUseBean : notCommonUseBeanList) {
                SingleCommunityEntity.ContentBean.CommonUseBean singleCommonUse = new SingleCommunityEntity.ContentBean.CommonUseBean();
                singleCommonUse.setDoor_name(notCommonUseBean.getDoor_name());
                singleCommonUse.setCommunity_type(notCommonUseBean.getCommunity_type());
                singleCommonUse.setConnection_type(notCommonUseBean.getConnection_type());
                singleCommonUse.setDoor_id(notCommonUseBean.getDoor_id());
                singleCommonUse.setDoor_img(notCommonUseBean.getDoor_img());
                singleCommonUse.setDoor_type(notCommonUseBean.getDoor_type());
                singleCommonUse.setPosition(notCommonUseBean.getPosition());
                singleCommonUse.setQr_code(notCommonUseBean.getQr_code());
                commonUseBeanList.add(singleCommonUse);
                if (commonUseBeanList.size() == 6) {//只添加6个非常用门禁
                    break;
                }
            }
        }
        return commonUseBeanList;
    }

    /***用户没有远程门禁 显示蓝牙门禁**/
    public static ArrayList<SingleCommunityEntity.ContentBean.CommonUseBean> addBlueToothDoorList(SingleCommunityEntity.ContentBean contentBean) {
        ArrayList<SingleCommunityEntity.ContentBean.CommonUseBean> commonUseBeanList = new ArrayList<>();
        if (contentBean.getBluetooth() != null) {
            List<SingleCommunityEntity.ContentBean.BluetoothBean> bluetoothBeanList = contentBean.getBluetooth();
            for (SingleCommunityEntity.ContentBean.BluetoothBean bluetoothBean : bluetoothBeanList) {
                SingleCommunityEntity.ContentBean.CommonUseBean singleCommonUse = new SingleCommunityEntity.ContentBean.CommonUseBean();
                singleCommonUse.setDoor_name(bluetoothBean.getName());
                singleCommonUse.setDoor_id(bluetoothBean.getId());
                singleCommonUse.setQr_code("");
                commonUseBeanList.add(singleCommonUse);
            }
        }
        return commonUseBeanList;
    }

    public static GridLayoutAdapter initLinerLayout(Context context, List<HomeBottomAdviseEntity.ContentBean> homeBottomList) {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        //设置间隔高度
        gridLayoutHelper.setAutoExpand(true);
        //设置布局底部与下个布局的间隔
        //设置间距
        gridLayoutHelper.setMargin(10, 10, 10, 10);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        GridLayoutAdapter delegateRecyclerAdapter = new GridLayoutAdapter(context, gridLayoutHelper, homeBottomList);
        return delegateRecyclerAdapter;
    }

    public static OnePlusNLayoutAdapter initOnePlusNLayout(Context context, List<HomeBottomAdviseEntity.ContentBean> homeBottomList) {
        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper();
        //设置布局底部与下个布局的间隔
        onePlusNLayoutHelper.setMargin(10, 10, 10, 10);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        OnePlusNLayoutAdapter onePlusNLayoutAdapter = new OnePlusNLayoutAdapter(context, onePlusNLayoutHelper, homeBottomList);
        return onePlusNLayoutAdapter;
    }

    public static void setImageLogo(Activity activity, String path, ImageView imageView, int defaultId) {
        if (!TextUtils.isEmpty(path)) {
            GlideImageLoader.loadImageDisplay(activity, path, imageView);
        } else {
            imageView.setImageResource(defaultId);
        }
    }

    public static void setTextColor(String colorValue, TextView textView) {
        textView.setTextColor(Color.parseColor(colorValue));
    }

    public static void setLinearTabViewHeight(Activity activity, View tabBarView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        tabBarView.setLayoutParams(layoutParams);
    }

    public static void setComunnityInfor(HomeManagerEntity.ContentBean contentBean, TextView tv_manager_name) {
        String username = contentBean.getUsername();
        String avatar = contentBean.getAvatar();
        String managerPhone = contentBean.getMobile();
        ServiceInfo serviceInfo = new ServiceInfo();
        if (TextUtils.isEmpty(avatar)) {
            serviceInfo.setAvatar(" https://cc.colourlife.com/common/v30/logo/app_logo_v30.png");
        } else {
            serviceInfo.setAvatar(avatar);
        }
        serviceInfo.setSex(contentBean.getSex());
        serviceInfo.setUuid(contentBean.getUuid());
        serviceInfo.setPhoneNum(managerPhone);
        String realName = contentBean.getRealname();
        String oa = contentBean.getOa();
        if (TextUtils.isEmpty(realName)) {
            serviceInfo.setRealName(username);
            tv_manager_name.setText(username);
        } else {
            serviceInfo.setRealName(realName);
            tv_manager_name.setText(realName);
        }
        serviceInfo.setNickName(contentBean.getNickname());
        if (TextUtils.isEmpty(oa)) {
            serviceInfo.setUserName(username);
        } else {
            serviceInfo.setUserName(oa);
        }
        HuxinSdkManager.instance().setServiceInfo(serviceInfo);
    }

    public  static  void showCommunityActivity(Context activity,int  joinNumber,List<String> join_user_pics,ImageView iv_first_photo,ImageView iv_second_photo,ImageView iv_thrid_photo,TextView tv_join_person){
        if (joinNumber<=0){
            iv_first_photo.setVisibility(View.GONE);
            iv_second_photo.setVisibility(View.GONE);
            iv_thrid_photo.setVisibility(View.GONE);
            tv_join_person.setText("快来参与活动吧");
        }else{
            if(joinNumber==1){
                GlideImageLoader.loadImageDisplay(activity,join_user_pics.get(0),iv_first_photo);
                iv_first_photo.setVisibility(View.VISIBLE);
                iv_second_photo.setVisibility(View.GONE);
                iv_thrid_photo.setVisibility(View.GONE);
            }else if (joinNumber==2){
                GlideImageLoader.loadImageDisplay(activity,join_user_pics.get(0),iv_first_photo);
                GlideImageLoader.loadImageDisplay(activity,join_user_pics.get(1),iv_second_photo);
                iv_first_photo.setVisibility(View.VISIBLE);
                iv_second_photo.setVisibility(View.VISIBLE);
                iv_thrid_photo.setVisibility(View.GONE);
            }else{
                iv_first_photo.setVisibility(View.VISIBLE);
                iv_second_photo.setVisibility(View.VISIBLE);
                iv_thrid_photo.setVisibility(View.VISIBLE);
                GlideImageLoader.loadImageDisplay(activity,join_user_pics.get(0),iv_first_photo);
                GlideImageLoader.loadImageDisplay(activity,join_user_pics.get(1),iv_second_photo);
                GlideImageLoader.loadImageDisplay(activity,join_user_pics.get(2),iv_thrid_photo);
            }
            tv_join_person.setText(joinNumber+"个邻居参与");
        }
    }

}