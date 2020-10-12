package com.uos.mybilibili.utils;

/**
 * @author xxc
 * @date 创建时间：2020年10月12日14:38:44
 * 描述:装换
 */
public class NumberUtils {
    public static String format   (String num) {
        Integer integer = Integer.valueOf(num);
        if (integer < 10000) {
            return String.valueOf(num);
        }
        String unit = "万";
        double newNum = integer / 10000.0;
        String numStr = String.format("%." + 1 + "f", newNum);
        return numStr + unit;
    }
}
