package cn.net.cyberway.home.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.BeeFramework.Utils.ImageUtil;
import com.BeeFramework.view.MyDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.door.entity.SingleCommunityEntity;
import com.myproperty.activity.MyPropertyActivity;
import com.setting.activity.GesturePwdMainActivity;
import com.user.UserAppConst;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
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

    public static void showChangeCommunityDialog(final Activity activity) {
        ((MainActivity) activity).delayIntoPoup(true);
        final MyDialog dialog = new MyDialog(activity, activity.getResources().getString(R.string.home_change_community));
        dialog.positive.setText(activity.getResources().getString(R.string.home_change));
        dialog.positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(activity, MyPropertyActivity.class);
                intent.putExtra(MyPropertyActivity.SHOW_ACTIVITY, true);
                activity.startActivityForResult(intent, 4000);
            }
        });
        dialog.negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((MainActivity) activity).delayIntoPoup(false);
                ((MainActivity) activity).laterIntoPopup();
            }
        });
        dialog.show();
    }

    public static void showSetGestureDialog(final Activity activity) {
        final LeadGestureDialog leadGestureDialog = new LeadGestureDialog(activity, R.style.custom_dialog_theme);
        leadGestureDialog.show();
        leadGestureDialog.setCancelable(false);
        leadGestureDialog.setCanceledOnTouchOutside(false);
        leadGestureDialog.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leadGestureDialog.dismiss();
                Intent intent = new Intent(activity, GesturePwdMainActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });
        leadGestureDialog.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) activity).delayIntoPoup(false);
                leadGestureDialog.dismiss();
            }
        });
        leadGestureDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ((MainActivity) activity).intoPopup();
            }
        });
    }

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
        return commonUseBeanList;
    }

    public static void downLoadStartImage(Context context) {
        SharedPreferences shared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        String splashCathe = shared.getString(UserAppConst.Colour_SPLASH_CACHE, "");
        if (!TextUtils.isEmpty(splashCathe)) {//有缓存
            try {
                JSONObject jsonObject = new JSONObject(splashCathe);
                String startImage = jsonObject.optString("img");
                if (!TextUtils.isEmpty(startImage)) {
                    Glide.with(context).asBitmap().load(startImage).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            File splashDir = new File(UserAppConst.FILEPATH + "splash");
                            if (!splashDir.exists()) {
                                splashDir.mkdirs();
                            }
                            File splashImg = new File(splashDir + startImage.substring(startImage.lastIndexOf("/")));
                            if (!splashImg.exists()) {
                                ImageUtil.saveMyBitmap(UserAppConst.FILEPATH + "splash" + startImage.substring(startImage.lastIndexOf("/")), resource);
                            }
                        }
                    });
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * 遮罩引导
     */
    public static GuideView guideView(Context c, View hintView, GuideView guideView, View layoutView) {
        if (null == guideView) {
            guideView = new GuideView.Builder(c)
                    .setTargetView(R.id.rl_local)
                    .setHintView(hintView)
                    .setHintViewDirection(GuideView.Direction.BOTTOM)
                    .setmForm(GuideView.Form.ELLIPSE)
                    .create();
        }
        if (!guideView.show()) {
            layoutView.setVisibility(View.GONE);
        }
        return guideView;
    }
}