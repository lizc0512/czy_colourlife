package com.invite.model;

import java.util.regex.Pattern;

/**
 * 汉字首字母帮助类
 * Created by chenql on 16/1/11.
 */
public class Alpha {
    public static String getAlpha(String str) {
        if (str == null) {
            return "#";
        }

        if (str.trim().length() == 0) {
            return "#";
        }

        char c = str.trim().substring(0, 1).charAt(0);
        // 正則表達式，判斷首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else {
            return "#";
        }
    }


}
