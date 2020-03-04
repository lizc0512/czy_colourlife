package com.BeeFramework.Utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 字符串相关工具类
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-02-19  9:52
 */

public class StringUtil {

    private StringUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return (a == b) || (b != null) && (a.length() == b.length()) && a.regionMatches(true, 0, b, 0, b.length());
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    public static int String2Int(String data) {
        int result = -1;

        try {
            result = Integer.valueOf(data);
        } catch (Exception e) {
            // TODO
        }

        return result;
    }

    /**
     * 整形转换
     *
     * @param data 输入
     * @return Integer
     */
    public static Integer toInt(String data) {
        Integer result = -1;

        try {
            result = Integer.valueOf(data);
        } catch (Exception e) {
            // TODO
        }

        return result;
    }

    /**
     * Long转换
     *
     * @param data 输入
     * @return Long
     */
    public static Long toLong(String data) {
        Long result = 0L;

        try {
            result = Long.valueOf(data);
        } catch (Exception e) {
            // TODO
        }

        return result;
    }

    /**
     * 浮点转换
     *
     * @param data 输入
     * @return Float
     */
    public static Float toFloat(String data) {
        Float result = 0.0f;

        if (data != null && data.length() > 0) {
            try {
                result = Float.valueOf(data);
            } catch (Exception e) {
                // TODO
            }
        }

        return result;
    }

    /**
     * 浮点转换
     *
     * @param data 输入
     * @return Double
     */
    public static Double toDouble(String data) {
        Double result = 0.0;

        try {
            result = Double.valueOf(data);
        } catch (Exception e) {
            // TODO
        }

        return result;
    }

    /**
     * 判断是否是有效的值，0和0.0均视为false
     *
     * @param data 输入
     * @return boolean
     */
    public static boolean isEmpty(String data) {
        String tmp = (data != null ? data.replaceAll(" ", "") : "");

        if (TextUtils.isEmpty(tmp)) {
            return true;
        } else {
            try {
                return Double.valueOf(tmp) < 0.00001;
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * 字符串截取
     *
     * @param in  原始数据
     * @param max 最大文字截取尺寸
     */
    public static String subString(String in, int max) {
        float j = 0;  // 半角数目
        int offset = 0;

        if (max == -1) max = Integer.MAX_VALUE;

        if (in != null && max > 0) {
            for (offset = 0; offset < in.codePointCount(0, in.length()); offset++) {
                int c = in.codePointAt(offset);

                if (j > max) {
                    break;
                } else {
                    if (c > 32 && c <= 127) {
                        j += 0.5;
                    } else if (c == 10 || c == 13) {
                        break;
                    } else {
                        j += 1;
                    }
                }
            }
        }

        if (in != null) {
            offset = offset > in.length() ? in.length() : offset;
            return in.substring(0, offset);
        } else {
            return "";
        }
    }

    /**
     * 字符串截取
     *
     * @param in           原始数据
     * @param maxLine      做多行数
     * @param eachLineSize 每行字数
     */
    public static String[] subString(String in, int maxLine, int eachLineSize) {
        String inputString = in;
        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < maxLine; i++) {
            String tmp = subString(inputString, eachLineSize);

            if (i == maxLine - 1 && i > 0) {
                tmp += ".";
            }

            arrayList.add(tmp);

            inputString = inputString.substring(Math.min(inputString.length(), tmp.length()),
                    inputString.length());
            if (inputString.startsWith("\r\n")) {
                inputString = inputString.substring(2, inputString.length());
            } else if (inputString.startsWith("\r")) {
                inputString = inputString.substring(1, inputString.length());
            } else if (inputString.startsWith("\n")) {
                inputString = inputString.substring(1, inputString.length());
            }

            if (inputString.length() == 0) {
                break;
            }
        }

        String[] ret = new String[arrayList.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = arrayList.get(i);
        }

        return ret;
    }

    public static String getString(String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        }
        return s;
    }


    public static String listToString(List<String> stringList) {
        StringBuilder content = new StringBuilder();
        if (stringList != null && stringList.size() > 0) {
            for (int i = 0; i < stringList.size(); i++) {
                if (!TextUtils.isEmpty(stringList.get(i))) {
                    content.append(stringList.get(i));
                    content.append(",");
                }
            }
        } else {
            return "";
        }
        return content.toString().length() > 0 ? content.toString().substring(0, content.length() - 1) : "";
    }
}
