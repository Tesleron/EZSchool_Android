package com.tesleron.ezschool.MyUtils;

public class MyStringUtils {
    public static String getDurationFromation(int k) {
        int hour = k;
        int minute = (k % 2) * 30;
        return String.format("%02d:%02d", hour, minute);
    }
}
