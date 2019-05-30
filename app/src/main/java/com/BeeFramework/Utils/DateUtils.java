package com.BeeFramework.Utils;

import java.text.SimpleDateFormat;

public class DateUtils {
    private static final String TAG = DateUtils.class.getSimpleName();

    public static String DATE_FORMAT_DAY = "yyyy-MM-dd";



    /**
     * php时间转换成字符串
     *
     * @param phpDate php时间
     * @param format  转换格式
     * @return
     */
    public static String phpToString(String phpDate, String format) {

        if (format == null) {
            format = DATE_FORMAT_DAY;
        }
        String date = phpDate + "000";
        String dateTime = "";
        try {
            Long dateLong = Long.parseLong(date);

            SimpleDateFormat df = new SimpleDateFormat(format);
            dateTime = df.format(dateLong);
        } catch (Exception e) {
        }

        return dateTime;

    }


}
