package com.tesleron.ezschool.MyUtils;

public class MyStringUtils {
    public static String getDurationFromation(long start, long end) {
        String res = "";
        res = String.valueOf(start) + "-" + String.valueOf(end);



        return res;
    }
}
