package cn.net.cyberway.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;

import com.user.UserAppConst;

import java.util.Locale;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.utils
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/9 11:51
 * @change
 * @chang time
 * @class describe
 */
public class ChangeLanguageHelper {
    public static final int CHANGE_LANGUAGE_CHINA = 1; // 简体中文
    public static final int CHANGE_LANGUAGE_TAIWAN = 2;//繁体中文
    public static final int CHANGE_LANGUAGE_ENGLISH = 3;//英文
    public static final int CHANGE_LANGUAGE_FRENCH = 4;//法语
    public static final int CHANGE_LANGUAGE_VN = 5;//越南语

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void changeLanguage(Context context, int choiceLanguage) {
        Resources resources = context.getResources();//获得res资源对象
        Configuration config = resources.getConfiguration();//获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            switch (choiceLanguage) {
                case CHANGE_LANGUAGE_CHINA:
                    config.locale = Locale.SIMPLIFIED_CHINESE;
                    break;
                case CHANGE_LANGUAGE_TAIWAN:
                    config.locale = Locale.TAIWAN;
                    break;
                case CHANGE_LANGUAGE_ENGLISH:
                    config.locale = Locale.ENGLISH;
                    break;
                case CHANGE_LANGUAGE_FRENCH:
                    config.locale = Locale.FRENCH;
                    break;
                case CHANGE_LANGUAGE_VN:
                    config.locale = new Locale("vi");
                    break;
                default:
                    config.locale = Locale.getDefault();
                    break;
            }
            resources.updateConfiguration(config, dm);
        } else {
            updateResourceContext(context, choiceLanguage);
        }
    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Context updateResourceContext(Context context, int choiceLanguage) {
        Resources resources = context.getApplicationContext().getResources();//获得res资源对象
        Configuration config = resources.getConfiguration();//获得设置对象
        switch (choiceLanguage) {
            case CHANGE_LANGUAGE_CHINA:
                config.setLocale(Locale.SIMPLIFIED_CHINESE);
                break;
            case CHANGE_LANGUAGE_TAIWAN:
                config.setLocale(Locale.TAIWAN);
                break;
            case CHANGE_LANGUAGE_ENGLISH:
                config.setLocale(Locale.ENGLISH);
                break;
            case CHANGE_LANGUAGE_FRENCH:
                config.setLocale(Locale.FRENCH);
                break;
            case CHANGE_LANGUAGE_VN:
                config.setLocale(new Locale("vi"));
                break;
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    config.setLocale(Resources.getSystem().getConfiguration().getLocales().get(0));
                }
                break;
        }
        config.setLocales(new LocaleList(config.locale));
        return context.createConfigurationContext(config);
    }

    public static String getLanguageType(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        int currentLanguage = sharedPreferences.getInt(UserAppConst.CURRENTLANGUAGE, 0);
        String result = "";
        switch (currentLanguage) {
            case CHANGE_LANGUAGE_CHINA:
                result = "zh-Hans";
                break;
            case CHANGE_LANGUAGE_TAIWAN://zh-Hant-TW
                result = "zh-Hant-TW";
                break;
            case CHANGE_LANGUAGE_ENGLISH://en
                result = "en";
                break;
            case CHANGE_LANGUAGE_FRENCH://fr
                result = "fr";
                break;
            case CHANGE_LANGUAGE_VN://vi-VN
                result = "vi-VN";
                break;
            default:
                Locale local = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    local = Resources.getSystem().getConfiguration().getLocales().get(0);
                } else {
                    local = Locale.getDefault();
                }
                String country = local.getCountry();
                String language = local.getLanguage();
                switch (language) {
                    case "en":
                        result = "en";
                        break;
                    case "fr":
                        result = "fr";
                        break;
                    case "vi":
                        result = "vi-VN";
                        break;
                    default:
                        if ("TW".equals(country)) {
                            result = "zh-Hant-TW";
                        } else {
                            result = "zh-Hans";
                        }
                        break;
                }
                break;
        }
        return result;
    }
}