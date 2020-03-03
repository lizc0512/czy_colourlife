package cn.net.cyberway.home.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.door.entity.SingleCommunityEntity;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import q.rorbin.badgeview.QBadgeView;

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

}