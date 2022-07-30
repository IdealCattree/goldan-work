package com.ideal.golden.common.utils;

/**
 * @作者 Ideal
 * @时间 2022-07-20 10:05
 * @类名 Strings
 * @类说明 字符串工具类
 */
public class Strings {
    private static final int DELTA = 'a' - 'A';

    /**
     * @param source: 字符串
     * @return boolean
     * @作者: Ideal
     * @方法名: isEmpty
     * @说明: 判断字符串是否为空
     * @时间: 2022/7/20 10:18
     */
    public static boolean isEmpty(String source) {
        return source == null || source.equals("");
    }

    /**
     * @param source: 字符串
     * @return java.lang.String
     * @作者: Ideal
     * @方法名: firstLetterLowercase
     * @说明: 大驼峰转小驼峰
     * @时间: 2022/7/20 10:20
     */
    public static String firstLetterLowercase(String source) {
        if (isEmpty(source)) return source;
        StringBuilder res = processFirstLetterLowercase(source);
        int len = source.length();
        for (int i = 1; i < len; i++) {
            res.append(source.charAt(i));
        }

        return res.toString();

    }


    /**
     * @param source: 字符串
     * @return java.lang.StringBuilder
     * @作者: Ideal
     * @方法名: processFirstLetterLowercase
     * @说明: 首字母转小写，仅返回首字母
     * @时间: 2022/7/20 14:45
     */
    public static StringBuilder processFirstLetterLowercase(String source) {
        StringBuilder string = new StringBuilder();
        char firstChar = source.charAt(0);
        if (isBigLetter(firstChar)) {
            string.append((char) (firstChar + DELTA));
        } else {
            string.append(firstChar);
        }

        return string;

    }

    /**
     * @param source: 字符串
     * @return java.lang.String
     * @作者: Ideal
     * @方法名: camel2underline
     * @说明: 驼峰转下划线
     * @时间: 2022/7/20 14:59
     */
    public static String camel2underline(String source) {
        if (source == null || source.length() == 0) return source;

        StringBuilder res = processFirstLetterLowercase(source);
        int len = source.length();
        for (int i = 1; i < len; i++) {
            char c = source.charAt(i);
            if (isBigLetter(c)) {
                res.append("_").append((char) (c + DELTA));
            } else {
                res.append(c);
            }
        }
        return res.toString();
    }


    /**
     * @param source: 字符串
     * @param isBig:  是否转成大驼峰
     * @return java.lang.String
     * @作者: Ideal
     * @方法名: underline2camel
     * @说明: 下划线转驼峰
     * @时间: 2022/7/20 15:01
     */
    public static String underline2camel(String source, boolean isBig) {
        if (source == null || source.length() == 0) return source;
        StringBuilder res = new StringBuilder();
        int len = source.length();

        boolean prevUnderline = false;
        for (int i = 0; i < len; i++) {
            char c = source.charAt(i);
            if (c == '_') {
                prevUnderline = true;
                continue;
            }
            // 首字母判断 是否转大写
            if (res.length() == 0) {
                if (isBig && isSmallLetter(c)) { // 大驼峰
                    res.append((char) (c - DELTA));
                } else if (!isBig && isBigLetter(c)) { // 小驼峰
                    res.append((char) (c + DELTA));
                } else {
                    res.append(c);
                }
            } else if (prevUnderline && isSmallLetter(c)) { // 判断该字符是否要转大写
                res.append((char) (c - DELTA));
            } else {
                res.append(c);
            }
            prevUnderline = false;
        }
        return res.toString();

    }

    /**
     * @param source: 字符
     * @return boolean
     * @作者: Ideal
     * @方法名: isBigLetter
     * @说明: 判断字符是否是大写
     * @时间: 2022/7/20 10:28
     */
    public static boolean isBigLetter(char source) {
        return source >= 'A' && source <= 'Z';
    }

    /**
     * @param source: 字符
     * @return boolean
     * @作者: Ideal
     * @方法名: isSmallLetter
     * @说明: 判断字符是否是小写
     * @时间: 2022/7/20 10:28
     */
    public static boolean isSmallLetter(char source) {
        return source >= 'a' && source <= 'z';
    }


    /**
    * @作者: Ideal
    * @方法名: notEmpty
    * @说明: 返回字符串数组中第一个不为空的字符串
    * @时间: 2022/7/20 15:35
    * @param sources: 字符串数组
    * @return java.lang.String
    */
    public static String notEmpty(String... sources) {
        if (sources == null) return null;

        for (String source : sources) {
            if (!isEmpty(source)) return source;
        }

        return null;
    }
}
