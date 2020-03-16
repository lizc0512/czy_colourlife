package com.BeeFramework.Utils;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 创建时间 : 2017/8/10.
 * 编写人 :  ${yuansk}
 * 功能描述: 主题配置的工具类
 * 版本:
 */

public class ThemeStyleHelper {

    /***只有返回键和标题的 布局为帧布局****/
    public static void onlyFrameTitileBar(Context context, FrameLayout topLayout, ImageView imageView, TextView tvTitle) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(UserAppConst.USERINFO, 0);
//        String themeResult = sharedPreferences.getString(UserAppConst.THEME, "");
//        try {
//            ThemeEntity themeStyleEntity = GsonUtils.gsonToBean(themeResult, ThemeEntity.class);
//            ThemeEntity.ContentBean.DefaultThemeBean defaultThemeBean = themeStyleEntity.getContent().getDefault_theme();
//            ThemeEntity.ContentBean.DefaultThemeBean.NavigationBean navigationBean = defaultThemeBean.getNavigation();
//            String leftUrl = navigationBean.getNavi_close_image();
//            String textColour = navigationBean.getNavi_title_color();
//            String bgColour = navigationBean.getNavi_color();
//            if (topLayout != null && !TextUtils.isEmpty(bgColour)) {
//                topLayout.setBackgroundColor(Color.parseColor(bgColour));
//            }
//            if (!TextUtils.isEmpty(leftUrl)) {
//                GlideUtils.loadImageViewLoding(context, leftUrl, imageView, R.drawable.icon_back, R.drawable.icon_back);
//            }
//            if (!TextUtils.isEmpty(textColour)) {
//                tvTitle.setTextColor(Color.parseColor(textColour));
//            }
//        } catch (Exception e) {
//
//        }
    }

    /***只有返回键和标题,h5加载的网页****/
    public static void webviewTitileBar(Context context, FrameLayout topLayout, ImageView imageView, ImageView img_close, TextView tvTitle,
                                        ImageView moreImage) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(UserAppConst.USERINFO, 0);
//        String themeResult = sharedPreferences.getString(UserAppConst.THEME, "");
//        try {
//            ThemeEntity themeStyleEntity = GsonUtils.gsonToBean(themeResult, ThemeEntity.class);
//            ThemeEntity.ContentBean.DefaultThemeBean defaultThemeBean = themeStyleEntity.getContent().getDefault_theme();
//            ThemeEntity.ContentBean.DefaultThemeBean.NavigationBean navigationBean = defaultThemeBean.getNavigation();
//            String leftUrl = navigationBean.getNavi_close_image();
//            String textColour = navigationBean.getNavi_title_color();
//            String bgColour = navigationBean.getNavi_color();
//            String closeUrl = navigationBean.getNavi_close_image();
//            String righrUrl = navigationBean.getNavi_refresh_image();
//            if (topLayout != null && !TextUtils.isEmpty(bgColour)) {
//                topLayout.setBackgroundColor(Color.parseColor(bgColour));
//            }
//            if (!TextUtils.isEmpty(leftUrl)) {
//                GlideUtils.loadImageViewLoding(context, leftUrl, imageView, R.drawable.icon_back, R.drawable.icon_back);
//            }
//            if (!TextUtils.isEmpty(closeUrl)) {
//                GlideUtils.loadImageViewLoding(context, leftUrl, img_close, R.drawable.ivclose, R.drawable.ivclose);
//            }
//            if (!TextUtils.isEmpty(textColour)) {
//                tvTitle.setTextColor(Color.parseColor(textColour));
//            }
//            if (!TextUtils.isEmpty(righrUrl)) {
//                GlideUtils.loadImageViewLoding(context, righrUrl, moreImage, R.drawable.img_home_more, R.drawable.img_home_more);
//            }
//        } catch (Exception e) {
//
//        }

    }


    /***返回按钮是文字的 FrameLayout****/
    public static void backTexteFrameLayout(Context context, FrameLayout topLayout, TextView backText, TextView tvTitle, TextView rightTv) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(UserAppConst.USERINFO, 0);
//        String themeResult = sharedPreferences.getString(UserAppConst.THEME, "");
//        try {
//            ThemeEntity themeStyleEntity = GsonUtils.gsonToBean(themeResult, ThemeEntity.class);
//            ThemeEntity.ContentBean.DefaultThemeBean defaultThemeBean = themeStyleEntity.getContent().getDefault_theme();
//            ThemeEntity.ContentBean.DefaultThemeBean.NavigationBean navigationBean = defaultThemeBean.getNavigation();
//            String leftUrl = navigationBean.getNavi_close_image();
//            String textColour = navigationBean.getNavi_title_color();
//            String bgColour = navigationBean.getNavi_color();
//            if (topLayout != null && !TextUtils.isEmpty(bgColour)) {
//                topLayout.setBackgroundColor(Color.parseColor(bgColour));
//            }
//            if (!TextUtils.isEmpty(textColour)) {
//                backText.setTextColor(Color.parseColor(textColour));
//            }
//
//            if (!TextUtils.isEmpty(textColour)) {
//                tvTitle.setTextColor(Color.parseColor(textColour));
//            }
//            if (!TextUtils.isEmpty(textColour)) {
//                rightTv.setTextColor(Color.parseColor(textColour));
//            }
//        } catch (Exception e) {
//
//        }

    }


    /***右边带有文字的****/
    public static void rightTexteFrameLayout(Context context, FrameLayout topLayout, ImageView imageView, TextView tvTitle, TextView rightTv) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(UserAppConst.USERINFO, 0);
//        String themeResult = sharedPreferences.getString(UserAppConst.THEME, "");
//        try {
//            ThemeEntity themeStyleEntity = GsonUtils.gsonToBean(themeResult, ThemeEntity.class);
//            ThemeEntity.ContentBean.DefaultThemeBean defaultThemeBean = themeStyleEntity.getContent().getDefault_theme();
//            ThemeEntity.ContentBean.DefaultThemeBean.NavigationBean navigationBean = defaultThemeBean.getNavigation();
//            String leftUrl = navigationBean.getNavi_close_image();
//            String textColour = navigationBean.getNavi_title_color();
//            String bgColour = navigationBean.getNavi_color();
//            if (topLayout != null && !TextUtils.isEmpty(bgColour)) {
//                topLayout.setBackgroundColor(Color.parseColor(bgColour));
//            }
//            if (!TextUtils.isEmpty(leftUrl)) {
//                GlideUtils.loadImageViewLoding(context, leftUrl, imageView, R.drawable.icon_back, R.drawable.icon_back);
//            }
//
//            if (!TextUtils.isEmpty(textColour)) {
//                tvTitle.setTextColor(Color.parseColor(textColour));
//            }
//            if (!TextUtils.isEmpty(textColour)) {
//                rightTv.setTextColor(Color.parseColor(textColour));
//            }
//        } catch (Exception e) {
//
//        }

    }

    /***右边是图片是更多...如门禁***/
    public static void rightImageFramelayout(Context context, FrameLayout topLayout, ImageView imageView, TextView tvTitle, ImageView rightImage) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(UserAppConst.USERINFO, 0);
//        String themeResult = sharedPreferences.getString(UserAppConst.THEME, "");
//        try {
//            ThemeEntity themeStyleEntity = GsonUtils.gsonToBean(themeResult, ThemeEntity.class);
//            ThemeEntity.ContentBean.DefaultThemeBean defaultThemeBean = themeStyleEntity.getContent().getDefault_theme();
//            ThemeEntity.ContentBean.DefaultThemeBean.NavigationBean navigationBean = defaultThemeBean.getNavigation();
//            String leftUrl = navigationBean.getNavi_close_image();
//            String textColour = navigationBean.getNavi_title_color();
//            String bgColour = navigationBean.getNavi_color();
//            String rightUrl = navigationBean.getNavi_message_image();
//            if (topLayout != null && !TextUtils.isEmpty(bgColour)) {
//                topLayout.setBackgroundColor(Color.parseColor(bgColour));
//            }
//            if (!TextUtils.isEmpty(leftUrl)) {
//                GlideUtils.loadImageViewLoding(context, leftUrl, imageView, R.drawable.icon_back, R.drawable.icon_back);
//            }
//            if (!TextUtils.isEmpty(textColour)) {
//                tvTitle.setTextColor(Color.parseColor(textColour));
//            }
//            if (!TextUtils.isEmpty(textColour)) {
//                GlideUtils.loadImageViewLoding(context, rightUrl, rightImage, R.drawable.img_home_more, R.drawable.img_home_more);
//            }
//        } catch (Exception e) {
//
//        }

    }

    /***个人中心选择地址****/
    public static void rightDeleteFramelayout(Context context, FrameLayout topLayout, ImageView imageView, TextView tvTitle, ImageView rightImage) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(UserAppConst.USERINFO, 0);
//        String themeResult = sharedPreferences.getString(UserAppConst.THEME, "");
//        try {
//            ThemeEntity themeStyleEntity = GsonUtils.gsonToBean(themeResult, ThemeEntity.class);
//            ThemeEntity.ContentBean.DefaultThemeBean defaultThemeBean = themeStyleEntity.getContent().getDefault_theme();
//            ThemeEntity.ContentBean.DefaultThemeBean.NavigationBean navigationBean = defaultThemeBean.getNavigation();
//            String leftUrl = navigationBean.getNavi_close_image();
//            String textColour = navigationBean.getNavi_title_color();
//            String bgColour = navigationBean.getNavi_color();
//            String rightUrl = navigationBean.getNavi_message_image();
//            if (topLayout != null && !TextUtils.isEmpty(bgColour)) {
//                topLayout.setBackgroundColor(Color.parseColor(bgColour));
//            }
//            if (!TextUtils.isEmpty(leftUrl)) {
//                GlideUtils.loadImageViewLoding(context, leftUrl, imageView, R.drawable.icon_back, R.drawable.icon_back);
//            }
//            if (!TextUtils.isEmpty(textColour)) {
//                tvTitle.setTextColor(Color.parseColor(textColour));
//            }
//            if (!TextUtils.isEmpty(textColour)) {
//                GlideUtils.loadImageViewLoding(context, rightUrl, rightImage, R.drawable.f1_icon_delete, R.drawable.f1_icon_delete);
//            }
//        } catch (Exception e) {
//
//        }

    }

    /***旧彩富主页****/
    public static void rightCaiFuFramelayout(Context context, FrameLayout topLayout, ImageView imageView, TextView tvTitle, ImageView rightImage) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(UserAppConst.USERINFO, 0);
//        String themeResult = sharedPreferences.getString(UserAppConst.THEME, "");
//        try {
//            ThemeEntity themeStyleEntity = GsonUtils.gsonToBean(themeResult, ThemeEntity.class);
//            ThemeEntity.ContentBean.DefaultThemeBean defaultThemeBean = themeStyleEntity.getContent().getDefault_theme();
//            ThemeEntity.ContentBean.DefaultThemeBean.NavigationBean navigationBean = defaultThemeBean.getNavigation();
//            String leftUrl = navigationBean.getNavi_close_image();
//            String textColour = navigationBean.getNavi_title_color();
//            String bgColour = navigationBean.getNavi_color();
//            String rightUrl = navigationBean.getNavi_message_image();
//            if (topLayout != null && !TextUtils.isEmpty(bgColour)) {
//                topLayout.setBackgroundColor(Color.parseColor(bgColour));
//            }
//            if (!TextUtils.isEmpty(leftUrl)) {
//                GlideUtils.loadImageViewLoding(context, leftUrl, imageView, R.drawable.icon_back, R.drawable.icon_back);
//            }
//            if (!TextUtils.isEmpty(textColour)) {
//                tvTitle.setTextColor(Color.parseColor(textColour));
//            }
//            if (!TextUtils.isEmpty(textColour)) {
//                GlideUtils.loadImageViewLoding(context, rightUrl, rightImage, R.drawable.icon_help, R.drawable.icon_help);
//            }
//        } catch (Exception e) {
//
//        }
    }




    /*****显示缴费和冲抵物业记录的******/
    public static void showPaymentLayout(Context context, FrameLayout topLayout, ImageView
            imageView, TextView tvTitle, ImageView arrowImage) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(UserAppConst.USERINFO, 0);
//        String themeResult = sharedPreferences.getString(UserAppConst.THEME, "");
//        try {
//            ThemeEntity themeStyleEntity = GsonUtils.gsonToBean(themeResult, ThemeEntity.class);
//            ThemeEntity.ContentBean.DefaultThemeBean defaultThemeBean = themeStyleEntity.getContent().getDefault_theme();
//            ThemeEntity.ContentBean.DefaultThemeBean.NavigationBean navigationBean = defaultThemeBean.getNavigation();
//            String leftUrl = navigationBean.getNavi_close_image();
//            String textColour = navigationBean.getNavi_title_color();
//            String bgColour = navigationBean.getNavi_color();
//            String rightUrl = navigationBean.getNavi_message_image();
//            if (topLayout != null && !TextUtils.isEmpty(bgColour)) {
//                topLayout.setBackgroundColor(Color.parseColor(bgColour));
//            }
//            if (!TextUtils.isEmpty(leftUrl)) {
//                GlideUtils.loadImageViewLoding(context, leftUrl, imageView, R.drawable.icon_back, R.drawable.icon_back);
//            }
//            if (!TextUtils.isEmpty(textColour)) {
//                tvTitle.setTextColor(Color.parseColor(textColour));
//            }
//            if (!TextUtils.isEmpty(textColour)) {
//                GlideUtils.loadImageViewLoding(context, rightUrl, arrowImage, R.drawable.icon_home_arrow, R.drawable.icon_home_arrow);
//            }
//        } catch (Exception e) {
//
//        }
    }
}
