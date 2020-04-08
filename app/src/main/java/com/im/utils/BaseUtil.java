package com.im.utils;

import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

public class BaseUtil {

    public final static String[] PHONES_PROJECTION = new String[]{
            Phone.DISPLAY_NAME, Phone.NUMBER};


    public static String trimTelNum(String telNum) {
        if (telNum == null || "".equals(telNum)) {
            return "";
        }
        telNum = telNum.replace("-", "");
        telNum = telNum.replace(" ", "");
        if (substring(telNum, 0, 4).equals("0086"))
            telNum = substring(telNum, 4);
        else if (substring(telNum, 0, 3).equals("+86"))
            telNum = substring(telNum, 3);
        else if (substring(telNum, 0, 5).equals("00186"))
            telNum = substring(telNum, 5);
        else if (substring(telNum, 0, 2).equals("86"))
            telNum = substring(telNum, 2);
        return telNum;
    }

    /**
     * 截取字符串
     *
     * @param s
     * @param from
     * @return
     */
    protected static String substring(String s, int from) {
        try {
            return s.substring(from);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected static String substring(String s, int from, int len) {
        try {
            return s.substring(from, from + len);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatString(String s) {
        try {
            return s.replace(" ", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
