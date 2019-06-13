package cn.net.cyberway.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.customerInfo.activity.CustomerMakeZXingActivity;
import com.door.entity.OpenDoorResultEntity;
import com.door.view.AdvisementBannerAdapter;
import com.door.view.ShowOpenDoorDialog;
import com.gesturepwd.activity.UnlockGesturePasswordActivity;
import com.invite.activity.InviteActivity;
import com.scanCode.activity.CaptureActivity;
import com.user.UserAppConst;
import com.user.activity.UserRegisterAndLoginActivity;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;

import static com.BeeFramework.Utils.Utils.dip2px;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.utils
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:38
 * @change
 * @chang time
 * @class describe
 */
public class TableLayoutUtils {

    public static void reflex(final TabLayout tabLayout) {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                    mTabStrip.setBackgroundColor(Color.parseColor("#ffffff"));
                    int dp16 = dip2px(tabLayout.getContext(), 16);
                    int dp14 = dip2px(tabLayout.getContext(), 14);
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView mTextView = (TextView) mTextViewField.get(tabView);
                        tabView.setPadding(0, 0, 0, 0);
                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }
                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp16;
                        if (i == mTabStrip.getChildCount() - 1) {
                            params.rightMargin = dp16;
                        } else {
                            params.rightMargin = dp14;
                        }
                        tabView.setLayoutParams(params);
                        tabView.invalidate();
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Exception e) {

                }
            }
        });
    }

    /**
     * 相同字数
     */
    public static void reflexSameText(final TabLayout tabLayout) {
        tabLayout.post(() -> {
            try {
                //拿到tabLayout的mTabStrip属性
                LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                    View tabView = mTabStrip.getChildAt(i);
                    //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                    Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                    mTextViewField.setAccessible(true);
                    TextView mTextView = (TextView) mTextViewField.get(tabView);
                    tabView.setPadding(0, 0, 0, 0);
                    //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                    int width = mTextView.getWidth();
                    if (width == 0) {
                        mTextView.measure(0, 0);
                        width = mTextView.getMeasuredWidth();
                    }
                    //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                    params.width = width;
                    //(tablayout长 - 文字宽 * n) = 总白边宽
                    //总白边宽
                    int margin = ((tabLayout.getWidth() - mTextView.getWidth() * mTabStrip.getChildCount())
                            / (mTabStrip.getChildCount() * 5 / 2));
                    params.leftMargin = margin;
                    params.rightMargin = margin;
                    tabView.setLayoutParams(params);
                    tabView.invalidate();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 从 drawable 获取图片 id 给 Imageview 添加 selector
     *
     * @param context   调用方法的 Activity
     * @param idNormal  默认图片的 id
     * @param idPress   点击图片的 id
     * @param imageView 点击的 view
     */
    public static void addSelectorFromDrawable(Context context, int idNormal, int idPress, ImageView imageView) {
        StateListDrawable drawable = new StateListDrawable();
        Drawable normal = context.getResources().getDrawable(idNormal);
        Drawable press = context.getResources().getDrawable(idPress);
        drawable.addState(new int[]{android.R.attr.state_pressed}, press);
        drawable.addState(new int[]{android.R.attr.state_selected}, press);
        drawable.addState(new int[]{android.R.attr.state_checked}, press);
        drawable.addState(new int[]{android.R.attr.state_focused}, press);
        drawable.addState(new int[]{-android.R.attr.state_pressed}, normal);
        imageView.setBackgroundDrawable(drawable);
    }

    /**
     * 从网络获取图片 给 ImageView 设置 selector
     *
     * @param activity  调用方法的对象
     * @param normalUrl 获取默认图片的链接
     * @param pressUrl  获取点击图片的链接
     * @param imageView 点击的 view
     */
    public static void addSeletorFromNet(final Activity activity, final String normalUrl, final String pressUrl, final ImageView imageView) {
        new AsyncTask<Void, Void, Drawable>() {
            @Override
            protected Drawable doInBackground(Void... params) {
                StateListDrawable drawable = new StateListDrawable();
                Drawable normal = loadImageFromNet(activity, normalUrl);
                Drawable press = loadImageFromNet(activity, pressUrl);
                drawable.addState(new int[]{android.R.attr.state_selected}, press);
                drawable.addState(new int[]{android.R.attr.state_checked}, press);
                drawable.addState(new int[]{android.R.attr.state_focused}, press);
                drawable.addState(new int[]{android.R.attr.state_pressed}, press);
                drawable.addState(new int[]{-android.R.attr.state_pressed}, normal);
                return drawable;
            }

            @Override
            protected void onPostExecute(Drawable drawable) {
                super.onPostExecute(drawable);
                imageView.setBackgroundDrawable(drawable);
            }
        }.execute();
    }

    /**
     * 给 TextView 设置 selector
     *
     * @param clazz       调用方法的类
     * @param normalColor 获取默认的颜色
     * @param pressColor  获取点击的颜色
     * @param textView    点击的 view
     */
    public static void addTVSeletor(final Class clazz, final String normalColor, final String pressColor, final TextView textView) {
        int[] colors = new int[]{Color.parseColor(pressColor), Color.parseColor(pressColor),
                Color.parseColor(normalColor), Color.parseColor(pressColor)};
        int[][] states = new int[4][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        textView.setTextColor(colorStateList);
    }

    /**
     * 从网络获取图片
     *
     * @param netUrl 获取图片的链接
     */
    public static Drawable loadImageFromNet(Context context, String netUrl) {
        Drawable drawable = null;
        try {
            drawable = Glide.with(context)
                    .load(netUrl).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).submit(150, 150).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
        return drawable;
    }

    /***Android 手机7.1以上支持****/
    public static void shortEnter(Activity activity, Intent paramterIntent, SharedPreferences mShared) {
        int openTyep = paramterIntent.getIntExtra("shortType", -1);
        boolean isLogin = mShared.getBoolean(UserAppConst.IS_LOGIN, false);
        if (openTyep == 0) {
            if (isLogin) {
                LinkParseUtil.parse(activity, "colourlife://proto?type=EntranceGuard", "");
            } else {
                jumpLoginPage(activity, mShared, 3000);
            }
        } else if (openTyep == 1) {
            if (isLogin) {
                Intent it = new Intent(activity, CaptureActivity.class);
                it.putExtra(CaptureActivity.QRCODE_SOURCE, "default");
                activity.startActivity(it);
            } else {
                jumpLoginPage(activity, mShared, 3000);
            }
        } else if (openTyep == 2) {
            if (isLogin) {
                Intent it = new Intent(activity, InviteActivity.class);
                activity.startActivity(it);
            } else {
                jumpLoginPage(activity, mShared, 3000);
            }
        } else if (openTyep == 3) {
            if (isLogin) {
                Intent intent = new Intent(activity, CustomerMakeZXingActivity.class);
                activity.startActivity(intent);
            } else {
                jumpLoginPage(activity, mShared, 3000);
            }
        } else {
            if (Build.VERSION.SDK_INT >= 25) {
                try {
                    setupShortcuts(activity);
                } catch (Exception e) {

                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private static void setupShortcuts(Context context) {
        int[] imagesource = {R.drawable.short_opendoor, R.drawable.short_scanner, R.drawable.short_share, R.drawable.short_qrcode};
        ShortcutManager mShortcutManager = context.getSystemService(ShortcutManager.class);
        List<ShortcutInfo> infos = new ArrayList<>();
        String[] source = context.getResources().getStringArray(R.array.app_quick_enter);
        for (int i = 0; i < source.length; i++) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra("shortType", i);
            ShortcutInfo info = new ShortcutInfo.Builder(context, "id" + i)
                    .setShortLabel(source[i])
                    .setLongLabel(source[i]) //可以添加描述语言
                    .setIcon(Icon.createWithResource(context, imagesource[i]))
                    .setIntent(intent)
                    .build();
            infos.add(info);
        }
        mShortcutManager.setDynamicShortcuts(infos);
    }

    public static void jumpLoginPage(Activity activity, SharedPreferences mShared, int requestCode) {
        if (mShared.getString(UserAppConst.Colour_login_mobile + UserAppConst.GESTURE_OPENED, "").equals("1")) {
            //如果是开启手势密码跳转到手势密码登录页面
            Intent intent = new Intent(activity, UnlockGesturePasswordActivity.class);
            activity.startActivityForResult(intent, requestCode);
        } else {
            //跳转到普通登录页面
            Intent intent = new Intent(activity, UserRegisterAndLoginActivity.class);
            activity.startActivityForResult(intent, requestCode);
        }
    }

    public static void showOpenDoorResultDialog(final Activity activity, final ShowOpenDoorDialog showOpenDoorDialog, OpenDoorResultEntity.ContentBean contentBean) {
        try {
            int result = contentBean.getOpen_result();
            String title = contentBean.getTitle();
            int price_state = contentBean.getPrice().getPrice_state();
            if (result == 0) {//开门失败
                showOpenDoorDialog.iv_opendoor_img.setImageResource((R.drawable.home_fail_popup_bg));
                showOpenDoorDialog.tv_opendoor_money_ok.setVisibility(View.VISIBLE);
                showOpenDoorDialog.tv_opendoor_cqb_ok.setVisibility(View.VISIBLE);
                showOpenDoorDialog.tv_opendoor_cqb_ok.setText(activity.getResources().getString(R.string.opendoor_again));
                if (null != title && !TextUtils.isEmpty(title)) {
                    showOpenDoorDialog.tv_opendoor_money_ok.setText(title);
                } else {
                    showOpenDoorDialog.tv_opendoor_money_ok.setText(activity.getResources().getString(R.string.neterror_tryagain_opendoor));
                }
                showOpenDoorDialog.tv_opendoor_money_ok.setTextSize(16);
            } else if (result == 1) {//开门成功且抽奖
                showOpenDoorDialog.tv_opendoor_message_ok.setVisibility(View.VISIBLE);
                showOpenDoorDialog.tv_opendoor_money_ok.setVisibility(View.VISIBLE);
                showOpenDoorDialog.ad_opendoor_banner.setVisibility(View.GONE);
                if (price_state == 1) {//中奖
                    showOpenDoorDialog.iv_opendoor_fp_ok.setVisibility(View.VISIBLE);
                    showOpenDoorDialog.tv_opendoor_cqb_ok.setVisibility(View.VISIBLE);
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    Double money = Double.valueOf(contentBean.getPrice().getAmount());
                    showOpenDoorDialog.tv_opendoor_money_ok.setText(decimalFormat.format(money));
                    if (null != title && !TextUtils.isEmpty(title)) {
                        showOpenDoorDialog.tv_opendoor_message_ok.setText(title);
                    } else {
                        showOpenDoorDialog.tv_opendoor_message_ok.setText(activity.getResources().getString(R.string.ticket_enter_wallet));
                    }
                } else {
                    showOpenDoorDialog.tv_opendoor_cqb_ok.setVisibility(View.GONE);
                    if (null != title && !TextUtils.isEmpty(title)) {
                        showOpenDoorDialog.tv_opendoor_money_ok.setText(title);
                    } else {
                        showOpenDoorDialog.tv_opendoor_money_ok.setText(activity.getResources().getString(R.string.no_get_ticket));
                    }
                    showOpenDoorDialog.tv_opendoor_money_ok.setTextSize(16);
                    showOpenDoorDialog.tv_opendoor_message_ok.setText(activity.getResources().getString(R.string.continue_next));
                }
            } else if (result == 2) {//开门成功带广告
                showOpenDoorDialog.tv_opendoor_message_ok.setVisibility(View.GONE);
                List<OpenDoorResultEntity.ContentBean.AdBean> adBeanList = contentBean.getAd();
                if (null != adBeanList && adBeanList.size() > 0) {
                    showOpenDoorDialog.ad_opendoor_banner.setVisibility(View.VISIBLE);
                    showOpenDoorDialog.tv_opendoor_money_ok.setVisibility(View.GONE);
                    if (adBeanList.size() > 1) {
                        showOpenDoorDialog.ad_opendoor_banner.setInfiniteLoop(true);
                        showOpenDoorDialog.ad_opendoor_banner.setAutoScroll(3000);
                    }
                    AdvisementBannerAdapter advisementBannerAdapter = new AdvisementBannerAdapter(activity, false, adBeanList);
                    showOpenDoorDialog.ad_opendoor_banner.setAdapter(advisementBannerAdapter);
                } else {//广告数据为空处理
                    showOpenDoorDialog.ad_opendoor_banner.setVisibility(View.GONE);
                    showOpenDoorDialog.tv_opendoor_money_ok.setVisibility(View.VISIBLE);
                    if (null != title && !TextUtils.isEmpty(title)) {
                        showOpenDoorDialog.tv_opendoor_money_ok.setText(title);
                    } else {
                        showOpenDoorDialog.tv_opendoor_money_ok.setText(activity.getResources().getString(R.string.opendoor_success));
                    }
                    showOpenDoorDialog.tv_opendoor_money_ok.setTextSize(28);
                }
            }
            showOpenDoorDialog.setCanceledOnTouchOutside(true);
            showOpenDoorDialog.iv_opendoor_close_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOpenDoorDialog.dismiss();
                }
            });
        } catch (Exception e) {

        }
    }
}
