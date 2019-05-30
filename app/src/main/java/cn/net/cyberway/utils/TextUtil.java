package cn.net.cyberway.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;

import cn.net.cyberway.R;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by hxg on 2019/4/10 20:18
 */
public class TextUtil {

    /**
     * 设置右上角小红点
     */
    public static QBadgeView setPwdGuide(Context context, View v) {
        QBadgeView badge = new QBadgeView(context);
        badge.bindTarget(v)
                .setBadgeBackgroundColor(ContextCompat.getColor(context, R.color.hx_color_red_tag))
                .setBadgeText("")
                .setBadgePadding(4.0f, true)
                .setBadgeGravity(Gravity.END | Gravity.TOP)
                .setShowShadow(false);
        return badge;
    }
}
